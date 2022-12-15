package xyz.xcye.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.comment.pojo.CommentPojo;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.comment.vo.ShowCommentVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.util.NetWorkUtils;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.GetList;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@Tag(name = "评论相关操作接口")
@RequestMapping("/comment")
@RestController
@RefreshScope
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "更新评论")
    @ModifyOperation
    @PostMapping("/update")
    public void updateComment(@Validated({Update.class}) @RequestBody CommentPojo comment) {
        commentService.updateComment(comment);
    }

    @Operation(summary = "插入新评论")
    @ModifyOperation
    @PostMapping("/insert")
    public void insertComment(@Validated({Default.class, Insert.class}) @RequestBody CommentPojo comment,
                             HttpServletRequest request) throws Throwable {
        comment.setCommentIp(NetWorkUtils.getIpAddr(request));
        comment.setOperationSystemInfo(NetWorkUtils.getOperationInfo(request));
        commentService.insertComment(comment);
    }

    @Operation(summary = "删除单条评论")
    @ModifyOperation
    @PostMapping("/delete")
    public int deleteComment(@Validated({Delete.class}) @RequestBody CommentPojo comment) {
        return commentService.deleteComment(comment);
    }

    /**
     * 根据多个uid，返回这些uid所对应的记录以及他们的子评论数据 是所有，没有做分页操作，其中uid是在文章中获取的
     * @param comment
     * @return
     */
    @Operation(summary = "查询所有满足要求的所有评论")
    @SelectOperation
    @GetMapping("/queryArticleComments")
    public ShowCommentVO queryAllComment( @RequestBody CommentPojo comment) {
        return commentService.queryArticleComments(comment);
    }

    @Operation(summary = "根据自定义条件查询所有评论")
    @SelectOperation
    @PostMapping("/queryAllCommentByCondition")
    public PageData<CommentVO> queryAllCommentByCondition(@RequestBody Condition<Long> condition) {
        return commentService.queryAllComments(condition);
    }

    @Operation(summary = "根据uid查询评论")
    @SelectOperation
    @PostMapping("/queryCommentByUid")
    public CommentDTO queryCommentByUid(@RequestBody long uid) {
        return commentService.queryByUid(uid);
    }

    @Operation(summary = "重新发送评论的邮件通知")
    @ModifyOperation
    @PostMapping("/resendEmail")
    public int resendEmailNotice(@RequestBody long uid) throws BindException {
        return commentService.resendEmailNotice(uid);
    }
}
