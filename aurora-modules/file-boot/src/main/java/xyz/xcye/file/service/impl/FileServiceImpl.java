package xyz.xcye.file.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.file.FileException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.file.constant.FileStorageModeConstant;
import xyz.xcye.file.dao.FileDao;
import xyz.xcye.file.dto.FileEntityDTO;
import xyz.xcye.file.interfaces.FileStorageService;
import xyz.xcye.file.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.file.po.File;
import xyz.xcye.file.service.FileService;
import xyz.xcye.file.vo.FileVO;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 此类中不会存在对对象的属性判断
 * @author qsyyke
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private LocalFileStorageServiceImpl localStorageService;
    @Autowired
    private AuroraProperties auroraProperties;

    @Override
    public FileVO insertFile(FileEntityDTO fileEntity, File file, int storageMode)
            throws FileException {

        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            throw new FileException(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getMessage() + "原因: 文件名为null或者获取文件流失败",
                    ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_UPLOAD.getCode());
        }

        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId());

        //根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(storageMode);
        FileEntityDTO uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);

        //对file对象进行赋值
        file = new File(uid, DateUtils.format(new Date()),
                false,uploadFileEntity.getName(),uploadFileEntity.getSize(),
                null,file.getSummary(),
                uploadFileEntity.getRemoteUrl(),storageMode,uploadFileEntity.getStoragePath());

        fileDao.insertFile(file);
        return queryByUid(file.getUid());
    }

    @Override
    public int updateFile(File file) {
        file.setSummary(Optional.ofNullable(file.getSummary()).orElse(" "));
        return fileDao.updateFile(file);
    }

    @Override
    public int deleteFile(long uid)
            throws IOException, FileException {
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
        deleteFileInfo.setDeleteTime(DateUtils.format(new Date()));
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
    public PageData<FileVO> queryAllFile(Condition<Long> condition) {
        return xyz.xcye.mybatis.util.PageUtils.pageList(condition, t -> BeanUtils.copyList(fileDao.queryAll(condition), FileVO.class));
    }

    @Override
    public FileVO queryByUid(long uid) {
        Condition<Long> conditionDTO = new Condition<>();
        conditionDTO.setUid(uid);
        return BeanUtils.getSingleObjFromList(fileDao.queryAll(conditionDTO), FileVO.class);
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
