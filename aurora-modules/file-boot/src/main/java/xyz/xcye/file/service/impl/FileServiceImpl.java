package xyz.xcye.file.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
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
import xyz.xcye.file.dao.FileMapper;
import xyz.xcye.file.dto.FileEntityDTO;
import xyz.xcye.file.interfaces.FileStorageService;
import xyz.xcye.file.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.file.po.File;
import xyz.xcye.file.service.FileService;
import xyz.xcye.file.vo.FileVO;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 此类中不会存在对对象的属性判断
 * @author qsyyke
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private LocalFileStorageServiceImpl localStorageService;
    @Autowired
    private AuroraProperties auroraProperties;

    @Override
    public FileVO insertFile(FileEntityDTO fileEntity, File file, int storageMode, long userUid) throws FileException {
        Assert.notNull(fileEntity, "文件对象不能为null");
        Assert.notNull(file, "文件信息不能为null");
        AssertUtils.stateThrow(userUid != 0, () -> new FileException("必须要传入UserUid"));
        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            throw new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getMessage() + "原因: 文件名为null或者获取文件流失败",
                    ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getCode());
        }

        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId());

        //根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(storageMode);
        FileEntityDTO uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);

        File newFile = File.builder()
                .uid(uid).delete(false).fileName(uploadFileEntity.getName())
                .size(uploadFileEntity.getSize()).summary(file.getSummary())
                .path(uploadFileEntity.getRemoteUrl()).storageMode(storageMode)
                .storagePath(uploadFileEntity.getStoragePath())
                .userUid(userUid)
                .build();
        fileMapper.insertSelective(newFile);
        return queryByUid(newFile.getUid());
    }

    @Override
    public int updateFile(File file) {
        file.setSummary(Optional.ofNullable(file.getSummary()).orElse(" "));
        return fileMapper.updateByPrimaryKeySelective(file);
    }

    @Override
    public int deleteFile(long uid) throws IOException, FileException {
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
            updateFileNum = updateFile(deleteFileInfo);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
        }
        if (updateFileNum != 1) {
            // 修改失败，恢复原来的文件
            fileStorageService.upload(originFileEntity.getInputStream(), originFileEntity);
        }
        return updateFileNum;
    }

    @Override
    public int deleteFileInfo(long uid) {
        // 先查看此uid对应的文件是否被删除，只有先删除文件，才可以删除文件记录
        FileVO fileVO = queryByUid(uid);
        AssertUtils.stateThrow(fileVO != null, () -> new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_NOT_FOUND));
        AssertUtils.stateThrow(fileVO.getDelete(), () -> new FileException("请先删除 " + fileVO.getFileName() + " 文件才可以删除记录"));
        return fileMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public PageData<FileVO> queryAllFile(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(fileMapper.selectByCondition(condition), FileVO.class));
    }

    @Override
    public FileVO queryByUid(long uid) {
        return BeanUtils.getSingleObjFromList(fileMapper.selectByCondition(Condition.instant(uid, true)), FileVO.class);
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

    @Override
    public FileEntityDTO downloadFile(long uid)
            throws FileException, IOException, ReflectiveOperationException {
        File deleteFileInfo = getFileDOByUid(uid);
        //获取此文件的存储模式，然后删除文件
        FileStorageService fileStorageService = getNeedFileStorageService(deleteFileInfo.getStorageMode());

        // 先获取原文件的数据流
        return fileStorageService.query(deleteFileInfo.getStoragePath());
    }

    @Override
    public PageData<FileVO> selectSpecifyFormatFiles(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> fileMapper.selectSpecifyFormatFiles(condition), FileVO.class);
    }

    @Override
    public List<String> selectAllFileFormat(long userUid) {
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
        return fileMapper.selectAllFileFormat(userUid).stream()
                .map(fileName -> fileName.split("\\.")[1])
                .distinct()
                .collect(Collectors.toList());
    }

    private File getFileDOByUid(long uid)
            throws FileException {
        // 查询出此uid对应的文件的存储位置
        File deleteFileInfo = BeanUtils.copyProperties(queryByUid(uid), File.class);
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
