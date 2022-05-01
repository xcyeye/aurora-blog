package xyz.xcye.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.service.amqp.SendMQMessageService;
import xyz.xcye.comment.dao.CommentDao;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.common.constant.RabbitMQNameConstant;
import xyz.xcye.common.dto.CommentDTO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.vo.CommentVO;

import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SendMQMessageService sendMQMessageService;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ModifyResult insertComment(CommentDO commentDO)
            throws Throwable {
        Assert.notNull(commentDO,"评论不能为null");
        // 初始化值
        commentDO.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));
        commentDO.setCreateTime(DateUtils.format(new Date()));
        commentDO.setShowComment(true);
        // 设置email的发送状态，这里目前只能保证消息是否成功投递到rabbitmq交换机中，不能保证email是否发送成功
        commentDO.setEmailNotice(false);
        commentDO.setDelete(false);

        // 判断该条评论是回复评论还是单独的一条评论
        // 如果此条评论的replyCommentUid在数据库中不存在，则标记为新建的单独评论
        CommentDO queriedRepliedCommentDO = getCommentDOByUid(commentDO.getReplyCommentUid());
        boolean isReplyCommentFlag = queriedRepliedCommentDO != null;

        //向数据库中插入此条评论
        int insertCommentNum = commentDao.insertComment(commentDO);

        // 使用rabbitmq发送邮件通知对方 因为使用rabbitmq发送消息到交换机的过程是同步的，并且我们已经开启了seata的全局事务功能
        // 所以如果在发送mq消息的过程中，出现异常，能够回滚，从而能够保证插入评论后，能够保证评论和mq发送到交换机的消息同时成功或者失败
        // 因为发布确认是异步的，如果能进入到发布确认代码中，那么前面插入评论和消息一定都成功保存到数据库中了
        if (isReplyCommentFlag) {
            sendMQMessageService.sendReplyMail(commentDO, queriedRepliedCommentDO,
                    RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                    "topic", RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY);
        }else {
            //不是回复评论 设置ReplyCommentUid标识
            commentDO.setReplyCommentUid(0L);
            //交换机发送消息 如果此commentDO.getUserUid()用户在au_email中不存在记录的话，会使用默认的模板进行邮件通知
            StorageSendMailInfo mailInfo = createReceiveCommentMailInfo(commentDO);
            sendMQMessageService.sendCommonMail(mailInfo, RabbitMQNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                    "topic", RabbitMQNameConstant.SEND_HTML_MAIL_ROUTING_KEY, createReceiveList(commentDO));
        }

        //如果运行到这里，不能确保邮件发送成功，但还是修改一下邮件发送状态
        commentDO.setEmailNotice(true);
        updateComment(commentDO);

        // 如果插入成功，并且是回复某条评论，则修改此被回复的评论的nextCommentUidArray值
        if (isReplyCommentFlag && insertCommentNum == 1) {
            queriedRepliedCommentDO.setNextCommentUidArray(getNextCommentUidArrayStr(queriedRepliedCommentDO,commentDO.getUid()));
            updateComment(queriedRepliedCommentDO);
        }
        return ModifyResult.operateResult(insertCommentNum,"插入评论",
                 ResponseStatusCodeEnum.SUCCESS.getCode(), commentDO.getUid());
    }

    @Override
    public ModifyResult deleteComment(Long uid) {
        return ModifyResult.operateResult(commentDao.deleteComment(uid),"删除" + uid + "对应的评论",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public ModifyResult updateComment(CommentDO commentDO) {
        // 设置最后修改时间
        commentDO.setUpdateTime(DateUtils.format());
        return ModifyResult.operateResult(commentDao.updateComment(commentDO),"修改评论",
                ResponseStatusCodeEnum.SUCCESS.getCode(), commentDO.getUid());
    }

    @Override
    public ModifyResult updateDeleteStatus(CommentDO commentDO) {
        Assert.notNull(commentDO, () -> "更新评论删除状态，不能为null");
        commentDO.setUpdateTime(DateUtils.format());
        commentDO.setDelete(Optional.ofNullable(commentDO.getDelete()).orElse(false));
        return ModifyResult.operateResult(commentDao.updateDeleteStatus(commentDO),"删除评论",
                ResponseStatusCodeEnum.SUCCESS.getCode(), commentDO.getUid());
    }

    @Override
    public CommentVO queryArticleComments(long[] arrayUid) throws ReflectiveOperationException {
        // 获取arrayUid中可用的uid
        List<Long> effectiveCommentUidList = getEffectiveCommentUid(arrayUid);

        // 获取arrayUid所涉及的所有评论
        List<CommentDTO> allCommentDTOList = new ArrayList<>();
        for (Long uid : effectiveCommentUidList) {
            CommentDO commentDO = getCommentDOByUid(uid);
            //没有发布或者已经删除的，也不展示
            if (commentDO == null || !commentDO.getShowComment()) {
                continue;
            }
            CommentDTO copyCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentDO,copyCommentDTO);
            CommentDTO sonNode = getAllSingleParentNodeList(copyCommentDTO);
            allCommentDTOList.add(sonNode);
        }

        CommentVO commentVO = new CommentVO();
        commentVO.setCommentList(allCommentDTOList);
        commentVO.setParentNodeNum(allCommentDTOList.size());
        return commentVO;
    }

    @Override
    public List<CommentDTO> queryAllComments(ConditionDTO<Long> condition) {
        condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());

        List<CommentDO> commentDOList = commentDao.queryAllComment(condition);
        List<CommentDTO> queriedCommentDTOList = new ArrayList<>();
        for (CommentDO queriedCommentDO : commentDOList) {
            CommentDTO queriedCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(queriedCommentDO,queriedCommentDTO);

            if (queriedCommentDTO.getSonCommentList() == null) {
                queriedCommentDTO.setSonCommentList(new ArrayList<>());
            }
            queriedCommentDTOList.add(queriedCommentDTO);
        }

        PageInfo<CommentDTO> commentDTOPageInfo = new PageInfo<>(queriedCommentDTOList);
        return commentDTOPageInfo.getList();
    }

    @Override
    public CommentDTO queryByUid(long uid) throws ReflectiveOperationException {
        ConditionDTO<Long> conditionDTO = new ConditionDTO();
        conditionDTO.setUid(uid);
        conditionDTO.init(conditionDTO);
        return xyz.xcye.common.util.BeanUtils.getSingleObjFromList(
                commentDao.queryAllComment(conditionDTO),CommentDTO.class);
    }

    /**
     * 判断此commentDO中的ReplyCommentUid是不是回复某条评论
     * <p>如果此ReplyCommentUid在数据库中不存在，也标识单独新建的一条评论</p>
     * @param commentDO
     * @return true是回复评论
     */
    private boolean isReplyComment(CommentDO commentDO) throws ReflectiveOperationException {
        //此评论就是单独新建的一条评论
        if (commentDO.getReplyCommentUid() == 0) {
            return false;
        }
        //判断是否存在
        return queryByUid(commentDO.getReplyCommentUid()) != null;
    }

    /**
     * 从数据库中查询uid所对应的评论数据，如果没有，返回null
     * @param commentUid
     * @return
     */
    private CommentDO getCommentDOByUid(Long commentUid) throws ReflectiveOperationException {
        //此评论就是单独新建的一条评论
        if (commentUid == null || commentUid == 0) {
            return null;
        }
        //判断是否存在
        ConditionDTO<Long> conditionDTO = new ConditionDTO<>();
        conditionDTO.setUid(commentUid);
        List<CommentDO> commentDOList = commentDao.queryAllComment(conditionDTO);
        return xyz.xcye.common.util.BeanUtils.getSingleObjFromList(commentDOList,CommentDO.class);
    }

    /**
     * 判断此uid在数据库中是否存在
     * @param uid
     * @return
     */
    private boolean isExistsComment(Long uid) throws ReflectiveOperationException {
        return getCommentDOByUid(uid) != null;
    }

    /**
     * 获取arrayUid中有效的uid
     * @param arrayUid
     * @return
     */
    private List<Long> getEffectiveCommentUid(long[] arrayUid) throws ReflectiveOperationException {
        List<Long> listUid = new ArrayList<>();
        for (Long uid : arrayUid) {
            if (isExistsComment(uid)) {
                listUid.add(uid);
            }
        }
        return listUid;
    }

    /**
     *
     * @param commentDTO
     * @return
     */
    private CommentDTO getAllSingleParentNodeList(CommentDTO commentDTO) throws ReflectiveOperationException {
        List<Long> uidList = parseUidArray(commentDTO.getNextCommentUidArray());
        if (uidList.isEmpty()) {
            //commentDTO下没有任何的子评论，直接返回
            return commentDTO;
        }

        //存在子评论，循环获取
        for (Long uid : uidList) {
            // 当前的uid对应评论数据
            CommentDO commentDO = getCommentDOByUid(uid);
            if (commentDO == null) {
                continue;
            }

            //将此commentDO添加到singleParentCommentDTOList中
            CommentDTO copyCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentDO,copyCommentDTO);
            if (commentDTO.getSonCommentList() == null) {
                commentDTO.setSonCommentList(new ArrayList<>());
            }
            commentDTO.getSonCommentList().add(copyCommentDTO);
            getAllSingleParentNodeList(copyCommentDTO);
        }
        return commentDTO;
    }

    /**
     * 根据传入的字符串返回按,分割后的List<Long>集合
     * @param nextCommentUidArray
     * @return
     */
    private List<Long> parseUidArray(String nextCommentUidArray) {
        if (!StringUtils.hasLength(nextCommentUidArray)) {
            return new ArrayList<>();
        }
        String[] arraySplit = nextCommentUidArray.split(",");
        List<Long> uidList = new ArrayList<>();
        for (String uidStr : arraySplit) {
            try {
                uidList.add(Long.valueOf(uidStr));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return uidList;
    }

    /**
     * 获取被回复的repliedCommonDO的NextCommentUid
     * @param repliedCommonDO
     * @param replyingUid
     * @return
     */
    private String getNextCommentUidArrayStr(CommentDO repliedCommonDO,Long replyingUid) {
        if ("".equals(repliedCommonDO.getNextCommentUidArray()) || repliedCommonDO.getNextCommentUidArray() == null) {
            return replyingUid + "";
        }else {
            return repliedCommonDO.getNextCommentUidArray() + "," + replyingUid;
        }
    }

    private StorageSendMailInfo createReceiveCommentMailInfo(CommentDO commentDO) {
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setUserUid(commentDO.getUserUid());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT);
        mailInfo.setCorrelationDataId(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()) + "");
        mailInfo.setSubject(commentDO.getContent());
        return mailInfo;
    }

    private List<Map<String,Object>> createReceiveList(CommentDO comment) {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT.getKeyName(),comment);
        list.add(map);
        return list;
    }
}
