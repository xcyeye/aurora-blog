package xyz.xcye.comment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.comment.dto.CommentDTO;
import xyz.xcye.comment.manager.amqp.send.SendCommentToExchange;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.comment.vo.CommentVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.feign.config.service.MessageLogFeignService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
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

    @Autowired
    private MessageLogFeignService messageLogFeignService;

    @ApiOperation(value = "更新评论")
    @ModifyOperation
    @PutMapping("")
    public int updateComment(@Validated({Update.class}) Comment comment) {
        return commentService.updateComment(comment);
    }

    @Autowired
    private SendCommentToExchange sendCommentToExchange;

    @ApiOperation(value = "插入新评论")
    @ModifyOperation
    @PostMapping("")
    public int insertComment(@Validated({Default.class, Insert.class}) Comment comment,
                             HttpServletRequest request) throws Throwable {
        /*comment.setCommentIp(NetWorkUtils.getIpAddr(request));
        comment.setOperationSystemInfo(NetWorkUtils.getOperationInfo(request));
        return commentService.insertComment(comment);*/
        comment.setUid(GenerateInfoUtils.generateUid(1,2));
        sendCommentToExchange.sendCommentToMQ(comment);


        return 1;
    }

    @ApiOperation(value = "删除单条评论")
    @ModifyOperation
    @DeleteMapping("/{uid}")
    public int deleteComment(@PathVariable("uid") Long uid) {
        return commentService.deleteComment(uid);
    }

    /**
     * 根据多个uid，返回这些uid所对应的记录以及他们的子评论数据 是所有，没有做分页操作，其中uid是在文章中获取的
     * @param commentUidArr
     * @return
     */
    @ApiOperation(value = "查询所有满足要求的所有评论")
    @SelectOperation
    @GetMapping("/queryArticleComments")
    public CommentVO queryAllComment(@RequestParam(value = "uidArr") long[] commentUidArr) {
        return commentService.queryArticleComments(commentUidArr);
    }

    @ApiOperation(value = "根据自定义条件查询所有评论")
    @SelectOperation
    @GetMapping
    public List<CommentDTO> queryAllCommentByCondition(Condition<Long> condition) {
        return commentService.queryAllComments(condition);
    }

    @ApiOperation(value = "根据uid查询评论")
    @SelectOperation
    @GetMapping("/{uid}")
    public CommentDTO queryCommentByUid(@PathVariable("uid") long uid) {
        return commentService.queryByUid(uid);
    }
}
