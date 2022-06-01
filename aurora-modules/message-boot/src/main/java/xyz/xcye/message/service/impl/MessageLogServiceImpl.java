package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.api.AmqpSenderService;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ValidationUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;
import xyz.xcye.message.dao.MessageLogDao;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.vo.MessageLogVO;

import javax.annotation.Resource;

/**
 * @author qsyyke
 */

@Service
public class MessageLogServiceImpl implements MessageLogService {

    @Resource
    private MessageLogDao messageLogDao;
    @Resource
    private AuroraProperties auroraProperties;
    @Autowired
    private AmqpSenderService amqpSenderService;

    @Override
    public int insertMessageLog(MessageLog messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Insert.class);
        Assert.notNull(messageLog, "插入mq消息不能为null");
        return messageLogDao.insertMessageLog(messageLog);
    }

    @Override
    public int deleteMessageLog(long uid) {
        return messageLogDao.deleteMessageLog(uid);
    }

    @Override
    public int updateMessageLog(MessageLog messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Update.class);
        //如果修改成功，返回最新的数据
        return messageLogDao.updateMessageLog(messageLog);
    }

    @Override
    public PageData<MessageLogVO> queryAllMessageLog(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(messageLogDao.queryAllMessageLog(condition),MessageLogVO.class));
    }

    @Override
    public MessageLogVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        condition.setStatus(null);
        return BeanUtils.getSingleObjFromList(messageLogDao.queryAllMessageLog(condition),MessageLogVO.class);
    }

    @Override
    public void resendMqMessage(long uid) throws BindException {
        MessageLogVO messageLogVO = queryByUid(uid);
        AssertUtils.stateThrow(messageLogVO != null, () -> new RuntimeException("没有此mq消息"));

        // 重新发送
        amqpSenderService.sendMQMsg(messageLogVO.getMessage(), messageLogVO.getExchange(),
                messageLogVO.getRoutingKey(), messageLogVO.getExchangeType());
    }
}
