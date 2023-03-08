package xyz.xcye.file.service.ext;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.file.dao.ext.FileExtDao;
import xyz.xcye.file.po.File;

import java.util.List;

/**
 * @author xcye
 * @description
 * @date 2022-12-14 22:47:58
 */

@Service
public class FileExtService {

    @Autowired
    private FileExtDao fileExtDao;
    public List<File> selectSpecifyFormatFiles(Condition<Long> condition) {
        return fileExtDao.selectSpecifyFormatFiles(condition);
    }

    /**
     * 查询此userUid对应的所有文件格式
     * @param userUid
     * @return
     */
    public List<String> selectAllFileFormat(Long userUid) {
        return fileExtDao.selectAllFileFormat(userUid);
    }
}
