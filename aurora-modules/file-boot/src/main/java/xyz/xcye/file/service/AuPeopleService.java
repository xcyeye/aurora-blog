package xyz.xcye.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.file.po.AuPeople;
import xyz.xcye.file.dao.AuPeopleDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table au_people <br/>
 * @description au_people 数据表Service层 <br/>
 * @date 2022-12-13 18:59:12 <br/>
 * @author xcye <br/>
 */

@Service
public class AuPeopleService extends BaseService<AuPeople> {
	@SuppressWarnings("unused")
	private AuPeopleDao auPeopleDao;
	
	@Autowired
    public void setInfoDao(AuPeopleDao auPeopleDao) {
        super.setBaseDao(auPeopleDao);
        this.auPeopleDao = auPeopleDao;
    }
}