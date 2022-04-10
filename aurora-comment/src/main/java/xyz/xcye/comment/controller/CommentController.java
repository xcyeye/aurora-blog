package xyz.xcye.comment.controller;

import org.apache.http.protocol.RequestUserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.dto.QueryAllCommentByArrayUidDTO;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.util.NetWorkUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qsyyke
 */

//TODO 插入数据时，获取操作系统信息没做
@RequestMapping("/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 根据多个uid，返回这些uid所对应的记录以及他们的子评论数据 是所有，没有做分页操作，其中uid是在文章中获取的
     * @param queryAllCommentByArrayUidDTO
     * @return
     */

    @ResponseResult
    @GetMapping("/queryArticleComments")
    public CommentVO queryAllComment(@Validated QueryAllCommentByArrayUidDTO queryAllCommentByArrayUidDTO) {
        if (queryAllCommentByArrayUidDTO.getArrayCommentUid() == null) {
            CommentVO commentVO = new CommentVO();
            commentVO.setCommentList(new ArrayList<>());
            return commentVO;
        }
        return commentService.queryArticleComments(queryAllCommentByArrayUidDTO.getArrayCommentUid(), queryAllCommentByArrayUidDTO.getArticleUid(), queryAllCommentByArrayUidDTO.getPageType());
    }

    @ResponseResult
    @GetMapping("/queryAll")
    public List<CommentDTO> queryAllCommentByCondition(CommentDO commentDO, PaginationDTO paginationDTO) {
        return commentService.queryAllComments(commentDO, paginationDTO);
    }

    @ResponseResult
    @PutMapping("/update")
    public ModifyResult updateComment(@Validated({Update.class}) CommentDO commentDO) {
        return commentService.updateComment(commentDO);
    }

    @ResponseResult
    @PostMapping("/insert")
    public ModifyResult insertComment(@Validated({Default.class, Insert.class}) CommentDO commentDO,
                                      HttpServletRequest request) {
        commentDO.setCommentIp(NetWorkUtils.getIpAddr(request));
        commentDO.setOperationSystemInfo(NetWorkUtils.getOperationInfo(request));
        return commentService.insertComment(commentDO);
    }

    @ResponseResult
    @PutMapping("/setDeleteStatus/{uid}")
    public ModifyResult setCommentDeleteStatus(@PathVariable("uid") Long uid) {
        return commentService.setCommentDeleteStatus(uid);
    }

    @ResponseResult
    @DeleteMapping("/delete/{uid}")
    public ModifyResult deleteComment(@PathVariable("uid") Long uid) {
        return commentService.deleteComment(uid);
    }
}
