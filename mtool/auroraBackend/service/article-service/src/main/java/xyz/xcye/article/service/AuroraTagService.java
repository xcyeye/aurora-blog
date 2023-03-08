package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.article.po.Tag;
import xyz.xcye.article.dao.AuroraTagDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table tag <br/>
 * @description tag 数据表Service层 <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraTagService extends BaseService<Tag> {
	@SuppressWarnings("unused")
	private AuroraTagDao tagDao;
	
	@Autowired
    public void setInfoDao(AuroraTagDao tagDao) {
        super.setBaseDao(tagDao);
        this.tagDao = tagDao;
    }
}