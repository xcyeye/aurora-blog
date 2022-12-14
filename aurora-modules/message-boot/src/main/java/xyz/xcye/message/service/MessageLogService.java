package xyz.xcye.message.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.pojo.MessageLogPojo;
import xyz.xcye.message.vo.MessageLogVO;

import javax.annotation.Resource;

/**
 * @author qsyyke
 */

@Service
public class MessageLogService {

    @Resource
    private AuroraMessageLogService auroraMessageLogService;
    @Resource
    private AuroraProperties auroraProperties;
    @Autowired
    private AmqpSenderService amqpSenderService;

    public void insertMessageLog(MessageLogPojo messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Insert.class);
        Assert.notNull(messageLog, "插入mq消息不能为null");
        auroraMessageLogService.insert(BeanUtils.copyProperties(messageLog, MessageLog.class));
    }

    public int deleteMessageLog(long uid) {
        return auroraMessageLogService.deleteById(uid);
    }

    public int updateMessageLog(MessageLogPojo messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Update.class);
        //如果修改成功，返回最新的数据
        return auroraMessageLogService.updateById(BeanUtils.copyProperties(messageLog, MessageLog.class));
    }

    public PageData<MessageLogVO> queryAllMessageLog(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(auroraMessageLogService.queryListByCondition(condition),MessageLogVO.class));
    }

    public MessageLogVO queryByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        condition.setStatus(null);
        return BeanUtils.getSingleObjFromList(auroraMessageLogService.queryListByCondition(condition),MessageLogVO.class);
    }

    public void resendMqMessage(long uid) throws BindException {
        MessageLogVO messageLogVO = queryByUid(uid);
        AssertUtils.stateThrow(messageLogVO != null, () -> new RuntimeException("没有此mq消息"));

        // 重新发送
        amqpSenderService.sendMQMsg(messageLogVO.getMessage(), messageLogVO.getExchange(),
                messageLogVO.getRoutingKey(), messageLogVO.getExchangeType());
    }
}
