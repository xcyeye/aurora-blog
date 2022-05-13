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
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.comment.dao.CommentMapper;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.enums.CommentPageTypeEnum;
import xyz.xcye.comment.manager.amqp.send.SendCommentToExchange;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.comment.CommentException;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;

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
    private CommentMapper commentMapper;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private SendMQMessageService sendMQMessageService;
    @Autowired
    private SendCommentToExchange sendCommentToExchange;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int insertComment(Comment comment) throws Throwable {
        Assert.notNull(comment,"评论不能为null");
        AssertUtils.stateThrow(comment.getPageUid() != null, () -> new CommentException("文章uid不能为null"));
        setDefaultProperty(comment);
        setPageType(comment, false);

        // 判断该条评论是回复评论还是单独的一条评论
        // 如果此条评论的replyCommentUid在数据库中不存在，则标记为新建的单独评论
        Comment queriedRepliedCommentDO = getCommentByUid(comment.getReplyCommentUid());
        boolean isReplyCommentFlag = queriedRepliedCommentDO != null;

        // 向数据库中插入此条评论
        int insertCommentNum = commentMapper.insertComment(comment);

        // 使用rabbitmq发送邮件通知对方 因为使用rabbitmq发送消息到交换机的过程是同步的，并且我们已经开启了seata的全局事务功能
        // 所以如果在发送mq消息的过程中，出现异常，能够回滚，从而能够保证插入评论后，能够保证评论和mq发送到交换机的消息同时成功或者失败
        // 因为发布确认是异步的，如果能进入到发布确认代码中，那么前面插入评论和消息一定都成功保存到数据库中了
        if (isReplyCommentFlag) {
            sendMQMessageService.sendReplyMail(comment, queriedRepliedCommentDO,
                    AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                    "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY);
        }else {
            //不是回复评论 设置ReplyCommentUid标识
            comment.setReplyCommentUid(0L);
            //交换机发送消息 如果此commentDO.getUserUid()用户在au_email中不存在记录的话，会使用默认的模板进行邮件通知
            StorageSendMailInfo mailInfo = createReceiveCommentMailInfo(comment);
            sendMQMessageService.sendCommonMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                    "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, createReceiveList(comment));
        }

        //如果运行到这里，不能确保邮件发送成功，但还是修改一下邮件发送状态
        comment.setEmailNotice(true);
        updateComment(comment);

        // 发送此评论到交换机，说说或者是文章修改CommentUidS的值
        sendCommentToExchange.sendCommentToMQ(comment);

        // 如果插入成功，并且是回复某条评论，则修改此被回复的评论的nextCommentUidArray值
        if (isReplyCommentFlag && insertCommentNum == 1) {
            queriedRepliedCommentDO.setNextCommentUidArray(getNextCommentUidArrayStr(queriedRepliedCommentDO,comment.getUid()));
            updateComment(queriedRepliedCommentDO);
        }
        return insertCommentNum;
    }

    @Override
    public int deleteComment(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return commentMapper.deleteComment(uid);
    }

    @Override
    public int updateComment(Comment comment) {
        Assert.notNull(comment, "评论信息不能为null");
        setPageType(comment, true);
        // 设置最后修改时间
        comment.setUpdateTime(DateUtils.format());
        Optional.ofNullable(userUtils.getCurrentUserUid()).ifPresent(comment::setUserUid);
        return commentMapper.updateComment(comment);
    }

    @Override
    public int updateDeleteStatus(Comment comment) {
        Assert.notNull(comment, () -> "更新评论删除状态，不能为null");
        Optional.ofNullable(userUtils.getCurrentUserUid()).ifPresent(comment::setUserUid);
        comment.setUpdateTime(DateUtils.format());
        comment.setDelete(Optional.ofNullable(comment.getDelete()).orElse(false));
        return commentMapper.updateDeleteStatus(comment);
    }

    @Override
    public CommentVO queryArticleComments(long[] arrayUid) {
        // 获取arrayUid中可用的uid
        List<Long> effectiveCommentUidList = getEffectiveCommentUid(arrayUid);

        // 获取arrayUid所涉及的所有评论
        List<CommentDTO> allCommentDTOList = new ArrayList<>();
        for (Long uid : effectiveCommentUidList) {
            Comment comment = getCommentByUid(uid);
            //没有发布或者已经删除的，也不展示
            if (comment == null || !comment.getShowComment()) {
                continue;
            }
            CommentDTO copyCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,copyCommentDTO);
            CommentDTO sonNode = getAllSingleParentNodeList(copyCommentDTO);
            allCommentDTOList.add(sonNode);
        }

        CommentVO commentVO = new CommentVO();
        commentVO.setCommentList(allCommentDTOList);
        commentVO.setParentNodeNum(allCommentDTOList.size());
        return commentVO;
    }

    @Override
    public List<CommentDTO> queryAllComments(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        List<Comment> commentDOList = commentMapper.queryAllComment(condition);
        List<CommentDTO> queriedCommentDTOList = new ArrayList<>();
        for (Comment queriedCommentDO : commentDOList) {
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
    public CommentDTO queryByUid(long uid) {
        Condition<Long> condition = new Condition();
        condition.setUid(uid);
        return xyz.xcye.core.util.BeanUtils.getSingleObjFromList(
                commentMapper.queryAllComment(condition),CommentDTO.class);
    }

    private void setDefaultProperty(Comment comment) {
        // 初始化值
        comment.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));
        comment.setCreateTime(DateUtils.format(new Date()));
        comment.setShowComment(true);
        // 设置email的发送状态，这里目前只能保证消息是否成功投递到rabbitmq交换机中，不能保证email是否发送成功
        comment.setEmailNotice(false);
        comment.setDelete(false);
    }

    /**
     * 设置此评论是在哪种类型的页面上发布的，有文章，说说
     * @param comment
     */
    private void setPageType(Comment comment, boolean isUpdate) {
        if (isUpdate) {
            if (comment.getPageType() == null) {
                return;
            }
        }

        AssertUtils.stateThrow(comment.getPageType() != null,
                () -> new CommentException(ResponseStatusCodeEnum.PARAM_COMMENT_NOT_SUPPORT_PAGE_TYPE));

        CommentPageTypeEnum commentPageTypeEnum = null;
        try {
            commentPageTypeEnum = CommentPageTypeEnum.valueOf(comment.getPageType());
        } catch (IllegalArgumentException e) {
            // 不支持的文章类型
            throw new CommentException(ResponseStatusCodeEnum.PARAM_COMMENT_NOT_SUPPORT_PAGE_TYPE);
        }
        comment.setPageType(commentPageTypeEnum.toString());
    }

    /**
     * 判断此commentDO中的ReplyCommentUid是不是回复某条评论
     * <p>如果此ReplyCommentUid在数据库中不存在，也标识单独新建的一条评论</p>
     * @param comment
     * @return true是回复评论
     */
    private boolean isReplyComment(Comment comment) throws ReflectiveOperationException {
        //此评论就是单独新建的一条评论
        if (comment.getReplyCommentUid() == 0) {
            return false;
        }
        //判断是否存在
        return queryByUid(comment.getReplyCommentUid()) != null;
    }

    /**
     * 从数据库中查询uid所对应的评论数据，如果没有，返回null
     * @param commentUid
     * @return
     */
    private Comment getCommentByUid(Long commentUid) {
        //此评论就是单独新建的一条评论
        if (commentUid == null || commentUid == 0) {
            return null;
        }
        //判断是否存在
        Condition<Long> condition = new Condition<>();
        condition.setUid(commentUid);
        List<Comment> commentDOList = commentMapper.queryAllComment(condition);
        return xyz.xcye.core.util.BeanUtils.getSingleObjFromList(commentDOList,Comment.class);
    }

    /**
     * 判断此uid在数据库中是否存在
     * @param uid
     * @return
     */
    private boolean isExistsComment(Long uid) {
        return getCommentByUid(uid) != null;
    }

    /**
     * 获取arrayUid中有效的uid
     * @param arrayUid
     * @return
     */
    private List<Long> getEffectiveCommentUid(long[] arrayUid) {
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
    private CommentDTO getAllSingleParentNodeList(CommentDTO commentDTO) {
        List<Long> uidList = parseUidArray(commentDTO.getNextCommentUidArray());
        if (uidList.isEmpty()) {
            //commentDTO下没有任何的子评论，直接返回
            return commentDTO;
        }

        //存在子评论，循环获取
        for (Long uid : uidList) {
            // 当前的uid对应评论数据
            Comment comment = getCommentByUid(uid);
            if (comment == null) {
                continue;
            }

            //将此commentDO添加到singleParentCommentDTOList中
            CommentDTO copyCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,copyCommentDTO);
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
    private String getNextCommentUidArrayStr(Comment repliedCommonDO,Long replyingUid) {
        if ("".equals(repliedCommonDO.getNextCommentUidArray()) || repliedCommonDO.getNextCommentUidArray() == null) {
            return replyingUid + "";
        }else {
            return repliedCommonDO.getNextCommentUidArray() + "," + replyingUid;
        }
    }

    /**
     * 创建一个发送邮件的对象
     * @param comment
     * @return
     */
    private StorageSendMailInfo createReceiveCommentMailInfo(Comment comment) {
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setUserUid(comment.getUserUid());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT);
        mailInfo.setSubject(comment.getContent());
        return mailInfo;
    }

    /**
     *
     * @param comment
     * @return
     */
    private List<Map<SendHtmlMailTypeNameEnum,Object>> createReceiveList(Comment comment) {
        List<Map<SendHtmlMailTypeNameEnum,Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum,Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT,comment);
        list.add(map);
        return list;
    }
}
