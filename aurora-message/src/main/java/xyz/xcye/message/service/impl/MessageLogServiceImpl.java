package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.MessageLogVO;
import xyz.xcye.message.dao.MessageLogDao;
import xyz.xcye.message.service.MessageLogService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public ModifyResult insertMessageLog(MessageLogDO messageLogDO) throws BindException {
        ValidationUtils.valid(messageLogDO, Insert.class);
        Assert.notNull(messageLogDO, "插入mq消息不能为null");
        //设置创建时间
        messageLogDO.setCreateTime(DateUtils.format(new Date()));
        return ModifyResult.operateResult(messageLogDao.insertMessageLog(messageLogDO),"插入消息投递日志",
                 ResponseStatusCodeEnum.SUCCESS.getCode(), messageLogDO.getUid());
    }

    @Override
    public ModifyResult deleteMessageLog(long uid) {
        return ModifyResult.operateResult(messageLogDao.deleteMessageLog(uid),"删除消息投递日志",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException {
        ValidationUtils.valid(messageLogDO, Update.class);
        //设置updateTime
        messageLogDO.setUpdateTime(DateUtils.format(new Date()));
        //如果修改成功，返回最新的数据
        return ModifyResult.operateResult(messageLogDao.updateMessageLog(messageLogDO),"修改消息投递日志",
                ResponseStatusCodeEnum.SUCCESS.getCode(), messageLogDO.getUid());
    }

    @Override
    public List<MessageLogVO> queryAllMessageLog(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        condition = condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        List<MessageLogDO> messageLogDOList = messageLogDao.queryAllMessageLog(condition);
        return BeanUtils.copyList(messageLogDOList,MessageLogVO.class);
    }

    @Override
    public MessageLogVO queryByUid(long uid) throws ReflectiveOperationException {
        ConditionDTO<Long> conditionDTO = new ConditionDTO<>();
        conditionDTO.setUid(uid);
        return BeanUtils.getSingleObjFromList(messageLogDao.queryAllMessageLog(conditionDTO),MessageLogVO.class);
    }
}
