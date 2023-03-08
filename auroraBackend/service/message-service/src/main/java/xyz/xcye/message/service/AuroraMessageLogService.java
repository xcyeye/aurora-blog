package xyz.xcye.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.dao.AuroraMessageLogDao;
import xyz.xcye.service.base.BaseService;

/**
 * @table message_log <br/>
 * @description message_log 数据表Service层 <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Service
public class AuroraMessageLogService extends BaseService<MessageLog> {
	@SuppressWarnings("unused")
	private AuroraMessageLogDao messageLogDao;
	
	@Autowired
    public void setInfoDao(AuroraMessageLogDao messageLogDao) {
        super.setBaseDao(messageLogDao);
        this.messageLogDao = messageLogDao;
    }
}