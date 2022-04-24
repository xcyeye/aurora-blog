package xyz.xcye.message.service.impl;

import com.github.pagehelper.PageHelper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanCopyUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.dao.MessageLogDao;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author qsyyke
 */

@Service
public class MessageLogServiceImpl implements MessageLogService {

    /**
     * 查询时默认的初始页数
     */
    @Value("${aurora.pagination.pageNum}")
    private int defaultPageNum;

    /**
     * 查询时默认的返回数目
     */
    @Value("${aurora.pagination.pageSize}")
    private int defaultPageSize;

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

        System.out.println("全局xid" + RootContext.getXID() + "是否绑定" + RootContext.inGlobalTransaction());

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
    public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        paginationDTO = PaginationDTO.initPagination(paginationDTO,defaultPageNum,defaultPageSize);
        PageHelper.startPage(paginationDTO.getPageNum(),paginationDTO.getPageSize(),paginationDTO.getOrderBy());
        List<MessageLogDO> messageLogDOList = messageLogDao.queryAllMessageLog(messageLogDO);
        return BeanCopyUtils.copyList(messageLogDOList,MessageLogVO.class);
    }

    @Override
    public MessageLogDO queryByUid(long uid) {
        if (uid == 0) {
            return null;
        }
        return messageLogDao.queryByUid(uid);
    }
}
