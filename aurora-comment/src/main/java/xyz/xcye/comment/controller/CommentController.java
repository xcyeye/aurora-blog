package xyz.xcye.comment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dto.comment.CommentDTO;
import xyz.xcye.common.dto.comment.QueryAllCommentByArrayUidDTO;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.common.vo.CommentVO;
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

@Api(tags = "评论相关操作接口")
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
    @ApiOperation(value = "查询所有满足要求的所有评论")
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

    @ApiOperation(value = "根据自定义条件查询所有评论")
    @ResponseResult
    @GetMapping("/queryAll")
    public List<CommentDTO> queryAllCommentByCondition(CommentDO commentDO, PaginationDTO paginationDTO) {
        return commentService.queryAllComments(commentDO, paginationDTO);
    }

    @ApiOperation(value = "更新评论")
    @ResponseResult
    @PutMapping("")
    public ModifyResult updateComment(@Validated({Update.class}) CommentDO commentDO) {
        return commentService.updateComment(commentDO);
    }

    @ApiOperation(value = "插入新评论")
    @ResponseResult
    @PostMapping("")
    public ModifyResult insertComment(@Validated({Default.class, Insert.class}) CommentDO commentDO,
                                      HttpServletRequest request) throws BindException {
        commentDO.setCommentIp(NetWorkUtils.getIpAddr(request));
        commentDO.setOperationSystemInfo(NetWorkUtils.getOperationInfo(request));
        return commentService.insertComment(commentDO);
    }

    @ApiOperation(value = "修改评论的删除状态")
    @ResponseResult
    @PutMapping("/deleteStatus/{uid}")
    public ModifyResult setCommentDeleteStatus(@PathVariable("uid") Long uid) {
        return commentService.setCommentDeleteStatus(uid);
    }

    @ApiOperation(value = "删除单条评论")
    @ResponseResult
    @DeleteMapping("/{uid}")
    public ModifyResult deleteComment(@PathVariable("uid") Long uid) {
        return commentService.deleteComment(uid);
    }
}
