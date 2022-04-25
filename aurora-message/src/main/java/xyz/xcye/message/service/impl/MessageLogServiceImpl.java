package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.MessageLogVO;
import xyz.xcye.message.dao.MessageLogDao;
import xyz.xcye.message.service.MessageLogService;

import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class MessageLogServiceImpl implements MessageLogService {

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

    @Autowired
    private MessageLogDao messageLogDao;

    @Override
    public ModifyResult insertMessageLog(MessageLogDO messageLogDO) throws BindException {
        ValidationUtils.valid(messageLogDO, Insert.class);
        //设置创建时间
        messageLogDO.setCreateTime(DateUtils.format(new Date()));
        if (messageLogDO.getUid() == null || messageLogDO.getUid() == 0) {
            messageLogDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));
        }
        int insertMessageLogNum = messageLogDao.insertMessageLog(messageLogDO);
        return ModifyResult.operateResult(insertMessageLogNum,"插入消息投递日志",
                 ResponseStatusCodeEnum.SUCCESS.getCode(), messageLogDO.getUid());
    }

    @Override
    public ModifyResult deleteMessageLog(long uid) {
        int deleteMessageLogNum = messageLogDao.deleteMessageLog(uid);
        return ModifyResult.operateResult(deleteMessageLogNum,"删除消息投递日志",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException {
        ValidationUtils.valid(messageLogDO, Update.class);
        //设置updateTime
        messageLogDO.setUpdateTime(DateUtils.format(new Date()));
        int updateMessageLogNum = messageLogDao.updateMessageLog(messageLogDO);

        //如果修改成功，返回最新的数据
        return ModifyResult.operateResult(updateMessageLogNum,"修改消息投递日志",
                ResponseStatusCodeEnum.SUCCESS.getCode(), messageLogDO.getUid());
    }

    @Override
    public List<MessageLogVO> queryAllMessageLog(ConditionDTO<Long> condition)
            throws InstantiationException, IllegalAccessException {
        condition = condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        List<MessageLogDO> messageLogDOList = messageLogDao.queryAllMessageLog(condition);
        return BeanUtils.copyList(messageLogDOList,MessageLogVO.class);
    }

    @Override
    public MessageLogVO queryByUid(long uid) throws InstantiationException, IllegalAccessException {
        ConditionDTO<Long> conditionDTO = new ConditionDTO<>();
        conditionDTO.setUid(uid);
        return BeanUtils.getSingleObjFromList(messageLogDao.queryAllMessageLog(conditionDTO),MessageLogVO.class);
    }
}
