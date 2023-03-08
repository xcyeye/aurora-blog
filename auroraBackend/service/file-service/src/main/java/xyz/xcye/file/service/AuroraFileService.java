package xyz.xcye.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.file.po.File;
import xyz.xcye.file.dao.AuroraFileDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table file <br/>
 * @description file 数据表Service层 <br/>
 * @date 2022-12-14 22:31:22 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraFileService extends BaseService<File> {
	@SuppressWarnings("unused")
	private AuroraFileDao fileDao;
	
	@Autowired
    public void setInfoDao(AuroraFileDao fileDao) {
        super.setBaseDao(fileDao);
        this.fileDao = fileDao;
    }
}