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

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ModifyResult insertMessageLog(MessageLogDO messageLogDO,String xid) throws BindException {
        ValidationUtils.valid(messageLogDO, Insert.class);

        RootContext.bind(xid);
        boolean b = RootContext.inGlobalTransaction();
        //设置创建时间
        messageLogDO.setCreateTime(DateUtils.format(new Date()));
        if (messageLogDO.getUid() == null || messageLogDO.getUid() == 0) {
            messageLogDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));
        }
        int insertMessageLogNum = messageLogDao.insertMessageLog(messageLogDO);

        System.out.println(xid + ": " + b);

        int i = 10 /0;
        return ModifyResult.operateResult(insertMessageLogNum,"插入消息投递日志",messageLogDO);
    }

    @Override
    public ModifyResult deleteMessageLog(long uid) {
        int deleteMessageLogNum = messageLogDao.deleteMessageLog(uid);
        return ModifyResult.operateResult(deleteMessageLogNum,"删除消息投递日志",null);
    }

    @Override
    public ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException {
        ValidationUtils.valid(messageLogDO, Update.class);
        //设置updateTime
        messageLogDO.setUpdateTime(DateUtils.format(new Date()));
        int updateMessageLogNum = messageLogDao.updateMessageLog(messageLogDO);

        //如果修改成功，返回最新的数据
        return ModifyResult.operateResult(updateMessageLogNum,"修改消息投递日志",
                updateMessageLogNum == 1 ? queryByUid(messageLogDO.getUid()) : null);
    }

    @Override
    public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        PaginationDTO.initPagination(paginationDTO,defaultPageNum,defaultPageSize);
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

    @GlobalTransactional
    @Override
    public String seataTest(String xid) throws BindException {
        String xid1 = RootContext.getXID();


        System.out.println("获取到的: " + xid1);

        MessageLogDO messageLogDO = new MessageLogDO();
        messageLogDO.setUid(1514200870569951232L);
        messageLogDO.setExchange("chucehnExchange");
        messageLogDO.setAckStatus(false);
        messageLogDO.setConsumeStatus(false);
        messageLogDO.setTryCount(24345);
        ModifyResult modifyResult = updateMessageLog(messageLogDO);

        if (new Random().nextInt(6) % 2 == 0) {
            throw new RuntimeException();
        }
        return null;
    }
}
