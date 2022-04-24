package xyz.xcye.file.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.common.dos.FileDO;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.file.dao.FileDao;
import xyz.xcye.file.exception.CustomFileException;
import xyz.xcye.file.interfaces.FileStorageService;
import xyz.xcye.file.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.constant.FileStorageModeConstant;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.dto.FileEntityDTO;
import xyz.xcye.file.service.FileService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 此类中不会存在对对象的属性判断
 * @author qsyyke
 */

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    /**
     * 查询时默认的初始页数
     */
    @Value("${aurora.pagination.pageNum}")
    private int defaultPageNum;

    /**
     * 查询时默认的返回数目
     */
    @Value("${aurora.pagination.pageSize}")
    private int defaultPageSize;

    @Autowired
    private LocalFileStorageServiceImpl localStorageService;

    @Override
    public ModifyResult insertFile(FileEntityDTO fileEntity, FileDO file, int storageMode) throws CustomFileException {

        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_PROPERTY.getMessage(),
                    0,ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_PROPERTY.getCode(), 0);
        }
        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(workerId,datacenterId);

        //根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(storageMode);
        FileEntityDTO uploadFileEntity = null;
        try {
            uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);
        }catch (IOException e) {
            throw new CustomFileException(e.getMessage(), ResponseStatusCodeEnum.UNKNOWN.getCode());
        }

        //对file对象进行赋值
        file = new FileDO(uid, DateUtils.format(new Date()),false,
                uploadFileEntity.getName(),uploadFileEntity.getSize(),
                null,file.getSummary(),
                uploadFileEntity.getRemoteUrl(),storageMode,uploadFileEntity.getStoragePath());

        int insertFileNum = fileDao.insertFile(file);
        return ModifyResult.operateResult(insertFileNum, "插入"+ file.getFileName() +"文件",
                ResponseStatusCodeEnum.SUCCESS.getCode(),uid);
    }

    @Override
    public ModifyResult updateFile(FileDO file) {
        int updateRow = fileDao.updateFile(file);
        //无论修改成功，还是失败，都调用查询返回file
        List<FileDO> fileList = queryAllFile(file, new PaginationDTO(defaultPageNum, defaultPageSize, ""));
        FileDO modifiedFile = null;
        if (fileList.size() != 0) {
            modifiedFile = fileList.get(0);
        }
        return ModifyResult.operateResult(updateRow,"更新文件",
                ResponseStatusCodeEnum.SUCCESS.getCode(), file.getUid());
    }

    @Override
    public ModifyResult deleteFile(long uid) {
        FileDO fileDO = new FileDO();
        fileDO.setUid(uid);
        //查询出此uid对应的文件的存储位置
        List<FileDO> fileList = queryAllFile(fileDO, new PaginationDTO());
        if (fileList.size() == 0) {
            //此uid无效 这里会存在一个问题，因为deleteStatus默认为false，如果你已经删除了文件，但是只传入uid，也会进入到这里
            return ModifyResult.operateResult(ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getMessage(),
                    0,ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getCode(), uid);
        }

        FileDO deleteFileInfo = fileList.get(0);

        //如果已经删除，直接返回
        if (deleteFileInfo.getDelete()) {
            return ModifyResult.operateResult(deleteFileInfo.getFileName() + ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_HAD_DELETED.getMessage(),
                    0,ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_HAD_DELETED.getCode(), uid);
        }

        //获取此文件的存储模式，然后删除文件
        FileStorageService fileStorageService = getNeedFileStorageService(deleteFileInfo.getStorageMode());

        //从对应的存储模式中删除文件
        if (!fileStorageService.delete(deleteFileInfo.getStoragePath())) {
            //没有删除成功，直接返回null
            return ModifyResult.operateResult(ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_DELETE.getMessage() + " 可能原因：文件不存在",
                    0,ResponseStatusCodeEnum.EXCEPTION_FILE_FAIL_DELETE.getCode(), uid);
        }

        //删除成功，修改数据表文件的状态
        deleteFileInfo.setDelete(true);
        //这里使用deleteFileInfo对象的原因是：deleteFileInfo对象中存放的属性值比较完整，而file对象中，可能只存在一个uid

        //创建一个新对象File最为原始数据对象
        deleteFileInfo.setDeleteTime(DateUtils.format(new Date()));
        return updateFile(deleteFileInfo);
    }


    @Override
    public List<FileDO> queryAllFile(FileDO file, PaginationDTO pagination) {
        pagination = PaginationDTO.initPagination(pagination,defaultPageNum,defaultPageSize);

        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());
        List<FileDO> query = fileDao.query(file);
        PageInfo<FileDO> filePageInfo = new PageInfo<>(query);
        return filePageInfo.getList();
    }

    @Override
    public FileDO queryByUid(long uid) {
        return fileDao.queryByUid(uid);
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
}
