package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.PageUtils;
import xyz.xcye.core.util.ValidationUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.message.dao.MessageLogDao;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.vo.MessageLogVO;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author qsyyke
 */

@Service
public class MessageLogServiceImpl implements MessageLogService {

    @Resource
    private MessageLogDao messageLogDao;
    @Resource
    private AuroraProperties auroraProperties;

    @Override
    public int insertMessageLog(MessageLog messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Insert.class);
        Assert.notNull(messageLog, "插入mq消息不能为null");
        //设置创建时间
        messageLog.setCreateTime(DateUtils.format(new Date()));
        return messageLogDao.insertMessageLog(messageLog);
    }

    @Override
    public int deleteMessageLog(long uid) {
        return messageLogDao.deleteMessageLog(uid);
    }

    @Override
    public int updateMessageLog(MessageLog messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Update.class);
        //设置updateTime
        messageLog.setUpdateTime(DateUtils.format(new Date()));
        //如果修改成功，返回最新的数据
        return messageLogDao.updateMessageLog(messageLog);
    }

    @Override
    public PageData<MessageLogVO> queryAllMessageLog(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.pageList(BeanUtils.copyList(messageLogDao.queryAllMessageLog(condition),MessageLogVO.class));
    }

    @Override
    public MessageLogVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        return BeanUtils.getSingleObjFromList(messageLogDao.queryAllMessageLog(condition),MessageLogVO.class);
    }
}
