package xyz.xcye.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.comment.dao.CommentDao;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.manager.mq.RabbitMQSendService;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;

import java.util.*;

/**
 * @author qsyyke
 */

@Service
public class CommentServiceImpl implements CommentService {

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

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RabbitMQSendService rabbitMQSendService;

    // TODO 这里得设置事务
    @Override
    public ModifyResult insertComment(CommentDO commentDO) {
        // 不需要进行参数的校验，因为已经在controller中进行验证了 但是设置ip地址，得在controller中进行设置
        // 创建一个uid
        commentDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));
        // 设置创建的时间
        commentDO.setCreateTime(DateUtils.format(new Date()));

        // 设置删除状态
        commentDO.setDelete(false);
        commentDO.setShowComment(true);

        /*
         * 判断该条评论是回复评论还是单独的一条评论
         * 如果此条评论的replyCommentUid在数据库中不存在，则标记为新建的单独评论
         */
        CommentDO repliedCommentDOByUid = getCommentDOByUid(commentDO);

        boolean isReplyCommentFlag = repliedCommentDOByUid != null;

        // 使用rabbitmq发送邮件通知对方
        if (isReplyCommentFlag) {
            rabbitMQSendService.sendReplyCommentNotice(commentDO,repliedCommentDOByUid);
        }else {
            //不是回复评论 设置ReplyCommentUid标识
            commentDO.setReplyCommentUid(0L);
            //交换机发送消息 如果此commentDO.getUserUid()用户在au_email中不存在记录的话，会使用默认的模板进行邮件通知
            rabbitMQSendService.sendReceiveCommentNotice(commentDO);
        }

        // 设置email的发送状态，这里目前只能保证消息是否成功投递到rabbitmq交换机中，不能保证email是否发送成功
        commentDO.setEmailNotice(true);

        //向数据库中插入此条评论
        int insertCommentNum = commentDao.insertComment(commentDO);

        // 如果插入成功，并且是回复某条评论，则修改此被回复的评论的nextCommentUidArray值
        if (isReplyCommentFlag && insertCommentNum == 1) {
            CommentDO repliedCommonDO = commentDao.querySingleByUid(commentDO.getReplyCommentUid());

            repliedCommonDO.setNextCommentUidArray(getNextCommentUidArrayStr(repliedCommonDO,commentDO.getUid()));
            updateComment(repliedCommonDO);
        }

        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(commentDO,commentDTO);

        return getOperateResult(insertCommentNum,"插入评论",commentDTO);
    }

    @Override
    public ModifyResult setCommentDeleteStatus(Long uid) {
        // 直接修改
        int updateNum = commentDao.setCommentDeleteStatus(uid);
        return getOperateResult(updateNum,"修改" + uid + "对应的删除状态",null);
    }

    @Override
    public ModifyResult deleteComment(Long uid) {
        int deleteCommentNum = commentDao.deleteComment(uid);
        return getOperateResult(deleteCommentNum,"删除" + uid + "对应的评论",null);
    }

    @Override
    public ModifyResult updateComment(CommentDO commentDO) {
        //判断是否存在此uid
        boolean existsComment = isExistsComment(commentDO.getUid());
        if (!existsComment) {
            // 数据库中不存在此uid的记录，直接返回
            return getOperateResult(0,"此" + commentDO.getUid() + "不存在",null);
        }

        //设置最后修改时间
        commentDO.setUpdateTime(DateUtils.format(new Date()));
        int updateCommentNum = commentDao.updateComment(commentDO);
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(commentDO,commentDTO);
        return getOperateResult(updateCommentNum,"修改评论",commentDTO);
    }

    @Override
    public CommentVO queryArticleComments(Long[] arrayUid, Long articleUid, Integer pageType) {
        // 获取arrayUid中可用的uid
        List<Long> effectiveCommentUidList = getEffectiveCommentUid(arrayUid);
        //获取arrayUid所涉及的所有评论
        List<CommentDTO> allCommentDTOList = new ArrayList<>();

        for (Long uid : effectiveCommentUidList) {
            CommentDO commentDO = commentDao.querySingleByUid(uid);
            //没有发布或者已经删除的，也不展示
            if (commentDO == null || commentDO.getDelete() || !commentDO.getShowComment()) {
                continue;
            }
            CommentDTO copyCommentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentDO,copyCommentDTO);
            CommentDTO sonNode = getAllSingleParentNodeList(copyCommentDTO);
            allCommentDTOList.add(sonNode);
        }

        CommentVO commentVO = new CommentVO();
        commentVO.setCommentList(allCommentDTOList);
        commentVO.setArticleUid(articleUid);
        commentVO.setPageType(pageType);
        commentVO.setParentNodeNum(allCommentDTOList.size());
        return commentVO;
    }

    @Override
    public List<CommentDTO> queryAllComments(CommentDO commentDO, PaginationDTO pagination) {
        pagination = PaginationDTO.initPagination(pagination,defaultPageNum,defaultPageSize);
        PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize(),pagination.getOrderBy());

        List<CommentDO> commentDOList = commentDao.queryAllComment(commentDO);
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

    /**
     * 判断此commentDO中的ReplyCommentUid是不是回复某条评论
     * <p>如果此ReplyCommentUid在数据库中不存在，也标识单独新建的一条评论</p>
     * @param commentDO
     * @return true是回复评论
     */
    private boolean isReplyComment(CommentDO commentDO) {
        //此评论就是单独新建的一条评论
        if (commentDO.getReplyCommentUid() == 0) {
            return false;
        }
        //判断是否存在
        return commentDao.querySingleByUid(commentDO.getReplyCommentUid()) != null;
    }


    private CommentDO getCommentDOByUid(CommentDO commentDO) {
        //此评论就是单独新建的一条评论
        if (commentDO.getReplyCommentUid() == null || commentDO.getReplyCommentUid() == 0) {
            return null;
        }
        //判断是否存在
        return commentDao.querySingleByUid(commentDO.getReplyCommentUid());

    }

    private ModifyResult getOperateResult(int affectedRows, String prefix, Object returnData) {
        String msg = "";
        if (affectedRows == 1) {
            msg = prefix + "成功";
        }else {
            msg = prefix + "失败";
        }

        if (affectedRows != 1 || returnData == null) {
            // 如果affectedRows为0，则返回空数据
            returnData = new HashMap<>();
        }

        return new ModifyResult(affectedRows,affectedRows == 1,msg,returnData);
    }

    /**
     * 判断此uid在数据库中是否存在
     * @param uid
     * @return
     */
    private boolean isExistsComment(Long uid) {
        CommentDO commentDO = commentDao.querySingleByUid(uid);
        return commentDO != null;
    }

    private List<Long> getEffectiveCommentUid(Long[] arrayUid) {
        List<Long> listUid = new ArrayList<>();
        for (Long uid : arrayUid) {
            if (isExistsComment(uid)) {
                listUid.add(uid);
            }
        }
        return listUid;
    }

    private CommentDTO getAllSingleParentNodeList(CommentDTO commentDTO) {
        List<Long> uidList = parseUidArray(commentDTO.getNextCommentUidArray());
        if (uidList.isEmpty()) {
            //commentDTO下没有任何的子评论，直接返回
            return commentDTO;
        }

        //存在子评论，循环获取
        for (Long uid : uidList) {
            // 当前的uid对应评论数据
            CommentDO commentDO = commentDao.querySingleByUid(uid);
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
        String[] arraySplit = nextCommentUidArray.split(",");
        List<Long> uidList = new ArrayList<>();
        for (String uidStr : arraySplit) {
            try {
                uidList.add(new Long(uidStr));
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
}
