package xyz.xcye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.FileStorageModeEnum;
import xyz.xcye.common.enums.ResultStatusCode;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.common.util.NameUtil;
import xyz.xcye.common.util.id.GenerateInfoUtil;
import xyz.xcye.dao.FileDao;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.exception.CustomFileException;
import xyz.xcye.interfaces.FileStorageService;
import xyz.xcye.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.service.FileService;

import java.io.IOException;
import java.math.BigInteger;
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
    public ModifyResult insertFile(FileEntity fileEntity, File file, int storageMode) throws CustomFileException {

        if (fileEntity.getName() == null || fileEntity.getInputStream() == null) {
            return new ModifyResult(0,false,"获取流失败或者获取文件名字失败",null);
        }

        //生成一个uid
        BigInteger uid = new BigInteger(GenerateInfoUtil.generateUid(workerId,datacenterId) + "");

        //根据storageMode获取需要使用的文件存储方式
        FileStorageService fileStorageService = getNeedFileStorageService(storageMode);

        FileEntity uploadFileEntity = null;
        try {
            uploadFileEntity = fileStorageService.upload(fileEntity.getInputStream(), fileEntity);
        }catch (IOException e) {
            throw new CustomFileException(e.getMessage(), ResultStatusCode.UNKNOWN.getCode());
        }

        //对file对象进行赋值
        file = new File(uid,DateUtil.format(new Date()),false,
                uploadFileEntity.getName(),uploadFileEntity.getSize(),
                DateUtil.format(new Date()),file.getSummary(),
                uploadFileEntity.getRemoteUrl(),storageMode,uploadFileEntity.getStoragePath());

        int insertFileNum = fileDao.insertFile(file);
        String message = insertFileNum == 1 ? "插入成功" : "插入失败";
        return new ModifyResult(insertFileNum, insertFileNum == 1,message,file);
    }

    @Override
    public ModifyResult updateFile(File file) {
        int updateRow = fileDao.updateFile(file);
        //无论修改成功，还是失败，都调用查询返回file
        String message = updateRow == 1 ? "修改成功" : "修改失败";
        List<File> fileList = queryAllFile(file, new Pagination(defaultPageNum, defaultPageSize, ""));
        File modifiedFile = null;
        if (fileList.size() != 0) {
            modifiedFile = fileList.get(0);
        }
        return new ModifyResult(updateRow,updateRow == 1,message,modifiedFile);
    }

    @Override
    public ModifyResult deleteFile(File file) {
        //查询出此uid对应的文件的存储位置
        List<File> fileList = queryAllFile(file, new Pagination());
        if (fileList.size() == 0) {
            //此uid无效 这里会存在一个问题，因为deleteStatus默认为false，如果你已经删除了文件，但是只传入uid，也会进入到这里
            return new ModifyResult(0,false,"uid无效或已被删除",null);
        }

        File deleteFileInfo = fileList.get(0);

        //如果已经删除，直接返回
        if (deleteFileInfo.isDeleteStatus()) {
            return new ModifyResult(0,false,deleteFileInfo.getFileName() + "文件在此之前已被删除",deleteFileInfo);
        }

        //获取此文件的存储模式，然后删除文件
        FileStorageService fileStorageService = getNeedFileStorageService(deleteFileInfo.getStorageMode());

        //从对应的存储模式中删除文件
        if (!fileStorageService.delete(deleteFileInfo.getStoragePath())) {
            //没有删除成功，直接返回null
            return new ModifyResult(0,false,"从存储服务中删除" + deleteFileInfo.getFileName() + "文件失败，操作被终止，可能原因：文件不存在",deleteFileInfo);
        }

        //删除成功，修改数据表文件的状态
        deleteFileInfo.setDeleteStatus(true);
        //这里使用deleteFileInfo对象的原因是：deleteFileInfo对象中存放的属性值比较完整，而file对象中，可能只存在一个uid

        //创建一个新对象File最为原始数据对象
        File originalFile = deleteFileInfo;
        return updateFile(deleteFileInfo);
    }


    @Override
    public List<File> queryAllFile(File file, Pagination pagination) {
        if (pagination.getPageNum() == 0) {
            pagination.setPageNum(defaultPageNum);
        }
        if (pagination.getPageSize() == 0) {
            pagination.setPageSize(defaultPageSize);
        }
        //将orderBy中的驼峰命名转换为下划线
        if (pagination.getOrderBy() != null) {
            pagination.setOrderBy(NameUtil.getUnderlineName(pagination.getOrderBy()));
        }else {
            pagination.setOrderBy(NameUtil.getUnderlineName(""));
        }

        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());
        List<File> query = fileDao.query(file);
        PageInfo<File> filePageInfo = new PageInfo<>(query);
        return filePageInfo.getList();
    }

    private FileStorageService getNeedFileStorageService(int storageMode) {

        FileStorageService fileStorageService = null;
        //查看文件的存储方式
        if (storageMode == FileStorageModeEnum.LOCAL_STORAGE) {
            //本地存储
            fileStorageService = localStorageService;
        }

        return fileStorageService;
    }
}
