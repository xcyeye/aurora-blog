package xyz.xcye.file.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.file.FileException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;
import xyz.xcye.file.constant.FileStorageModeConstant;
import xyz.xcye.file.dao.ext.FileExtDao;
import xyz.xcye.file.dto.FileEntityDTO;
import xyz.xcye.file.interfaces.FileStorageService;
import xyz.xcye.file.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.file.po.File;
import xyz.xcye.file.pojo.FilePojo;
import xyz.xcye.file.service.ext.FileExtService;
import xyz.xcye.file.vo.FileVO;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 此类中不会存在对对象的属性判断
 * @author qsyyke
 */

@Service
public class FileService {

    @Autowired
    private AuroraFileService auroraFileService;
    @Autowired
    private LocalFileStorageServiceImpl localStorageService;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private FileExtService fileExtService;

    public FileVO insertFile(FileEntityDTO fileEntity, FilePojo filePojo) throws FileException, IOException, ExecutionException, InterruptedException {
        Assert.notNull(fileEntity, "文件对象不能为null");
        AssertUtils.stateThrow(filePojo.getUserUid() != 0, () -> new FileException("必须要传入UserUid"));
        File file = BeanUtils.copyProperties(filePojo, File.class);
        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            throw new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getMessage() + "原因: 文件名为null或者获取文件流失败",
                    ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getCode());
        }

        // 生成一个uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId());

        // 根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(filePojo.getStorageMode());
        FileEntityDTO uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);

        File newFile = File.builder()
                .uid(uid).delete(false).fileName(uploadFileEntity.getName())
                .size(uploadFileEntity.getSize()).summary(file.getSummary())
                .path(uploadFileEntity.getRemoteUrl()).storageMode(filePojo.getStorageMode())
                .storagePath(uploadFileEntity.getStoragePath())
                .userUid(filePojo.getUserUid())
                .build();
        auroraFileService.insert(newFile);
        FileVO fileVO = queryFileByUid(newFile.getUid());
        fileVO.setFilePathUri(uploadFileEntity.getFilePathUri());
        return fileVO;
    }

    public int updateFile(FilePojo file) {
        file.setSummary(Optional.ofNullable(file.getSummary()).orElse(" "));
        return auroraFileService.updateById(BeanUtils.copyProperties(file, File.class));
    }

    public int deleteFile(long uid) throws IOException, FileException, ExecutionException, InterruptedException {
        // 查询出此uid对应的文件的存储位置
        File deleteFileInfo = getFileDOByUid(uid);

        //获取此文件的存储模式，然后删除文件
        FileStorageService fileStorageService = getNeedFileStorageService(deleteFileInfo.getStorageMode());
        Objects.requireNonNull(fileStorageService, "没有发现此文件存储对象");
        // 先获取原文件的数据流
        FileEntityDTO originFileEntity = fileStorageService.query(deleteFileInfo.getStoragePath());

        //从对应的存储模式中删除文件
        if (!fileStorageService.delete(deleteFileInfo.getStoragePath())) {
            //没有删除成功，直接返回null
            throw new FileException (ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_DELETE.getMessage() + " 可能原因：文件不存在",
                    ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_DELETE.getCode());
        }

        //删除成功，修改数据表文件的状态
        deleteFileInfo.setDelete(true);
        //这里使用deleteFileInfo对象的原因是：deleteFileInfo对象中存放的属性值比较完整，而file对象中，可能只存在一个uid

        //创建一个新对象File最为原始数据对象
        int updateFileNum = 0;
        try {
            updateFileNum = updateFile(BeanUtils.copyProperties(deleteFileInfo, FilePojo.class));
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
        }
        if (updateFileNum != 1) {
            // 修改失败，恢复原来的文件
            fileStorageService.upload(originFileEntity.getInputStream(), originFileEntity);
        }
        return updateFileNum;
    }

    public int physicalDeleteFileInfo(long uid) {
        // 先查看此uid对应的文件是否被删除，只有先删除文件，才可以删除文件记录
        FileVO fileVO = queryFileByUid(uid);
        AssertUtils.stateThrow(fileVO != null, () -> new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_NOT_FOUND));
        AssertUtils.stateThrow(fileVO.getDelete(), () -> new FileException("请先删除 " + fileVO.getFileName() + " 文件才可以删除记录"));
        return auroraFileService.deleteById(uid);
    }

    public PageData<FileVO> queryListFileByCondition(Condition<Long> condition) {
        // 如果是超级管理员，则查询所有的数据
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        if (isSuperRole(jwtUserInfo)) {
            condition.setOtherUid(null);
        }
        return PageUtils.copyPageDataResult(auroraFileService.queryListByCondition(condition), FileVO.class);
    }

    public FileVO queryFileByUid(long uid) {
        return BeanUtils.getSingleObjFromList(auroraFileService.queryListByCondition(Condition.instant(uid, true)).getResult(), FileVO.class);
    }

    private FileStorageService getNeedFileStorageService(int storageMode) {
        FileStorageService fileStorageService = null;
        //查看文件的存储方式
        if (storageMode == FileStorageModeConstant.LOCAL_STORAGE) {
            //本地存储
            fileStorageService = localStorageService;
        }

        return fileStorageService;
    }

    public FileEntityDTO downloadFile(long uid)
            throws FileException, IOException, ReflectiveOperationException {
        File deleteFileInfo = getFileDOByUid(uid);
        //获取此文件的存储模式，然后删除文件
        FileStorageService fileStorageService = getNeedFileStorageService(deleteFileInfo.getStorageMode());

        // 先获取原文件的数据流
        return fileStorageService.query(deleteFileInfo.getStoragePath());
    }

    public PageData<FileVO> querySpecifyFormatFiles(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        List<FileVO> files = BeanUtils.copyList(fileExtService.selectSpecifyFormatFiles(condition), FileVO.class);
        PageData<FileVO> pageData = new PageData<>();
        pageData.setResult(files);
        pageData.setPages(1);
        pageData.setTotal((long) files.size());
        return pageData;
    }

    public List<String> queryListFileFormat(long userUid) {
        // 如果是超级管理员，则查询所有的数据
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        if (isSuperRole(jwtUserInfo)) {
            return queryFileFormat(null);
        }

        return queryFileFormat(userUid);
    }

    /**
     * 判断当前请求用户是否是超级管理员
     * @param jwtUserInfo
     * @return
     */
    private boolean isSuperRole(JwtUserInfo jwtUserInfo) {
        if (jwtUserInfo == null) {
            return  false;
        }
        return jwtUserInfo.getRoleList().contains(OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME);
    }

    private List<String> queryFileFormat(Long userUid) {
        return fileExtService.selectAllFileFormat(userUid).stream()
                .map(fileName -> fileName.split("\\.")[1])
                .distinct()
                .collect(Collectors.toList());
    }

    private File getFileDOByUid(long uid)
            throws FileException {
        // 查询出此uid对应的文件的存储位置
        File deleteFileInfo = BeanUtils.copyProperties(queryFileByUid(uid), File.class);
        if (deleteFileInfo == null) {
            // 此uid无效 这里会存在一个问题，因为deleteStatus默认为false，如果你已经删除了文件，但是只传入uid，也会进入到这里
            throw new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_NOT_FOUND.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_FILE_NOT_FOUND.getCode());
        }

        //如果已经删除，直接返回
        if (deleteFileInfo.getDelete()) {
            throw new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_HAD_DELETED.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_HAD_DELETED.getCode());
        }

        return deleteFileInfo;
    }
}
