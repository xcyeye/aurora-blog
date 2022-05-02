package xyz.xcye.comment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.aurora.feign.MessageLogFeignService;
import xyz.xcye.comment.service.CommentService;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dto.CommentDTO;
import xyz.xcye.core.dto.Condition;
import xyz.xcye.core.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.core.util.NetWorkUtils;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.common.vo.CommentVO;

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
    @ResponseResult
    @PutMapping("")
    public ModifyResult updateComment(@Validated({Update.class}) CommentDO commentDO) {
        return commentService.updateComment(commentDO);
    }

    @ApiOperation(value = "插入新评论")
    @ResponseResult
    @PostMapping("")
    public ModifyResult insertComment(@Validated({Default.class, Insert.class}) CommentDO commentDO,
                                      HttpServletRequest request)
            throws Throwable {
        commentDO.setCommentIp(NetWorkUtils.getIpAddr(request));
        commentDO.setOperationSystemInfo(NetWorkUtils.getOperationInfo(request));
        return commentService.insertComment(commentDO);
    }

    @ApiOperation(value = "删除单条评论")
    @ResponseResult
    @DeleteMapping("/{uid}")
    public ModifyResult deleteComment(@PathVariable("uid") Long uid) {
        return commentService.deleteComment(uid);
    }

    /**
     * 根据多个uid，返回这些uid所对应的记录以及他们的子评论数据 是所有，没有做分页操作，其中uid是在文章中获取的
     * @param commentUidArr
     * @return
     */
    @ApiOperation(value = "查询所有满足要求的所有评论")
    @ResponseResult
    @GetMapping("/queryArticleComments")
    public CommentVO queryAllComment(@RequestParam(value = "uidArr") long[] commentUidArr)
            throws ReflectiveOperationException {
        return commentService.queryArticleComments(commentUidArr);
    }

    @ApiOperation(value = "根据自定义条件查询所有评论")
    @ResponseResult
    @GetMapping
    public List<CommentDTO> queryAllCommentByCondition(Condition<Long> condition) {
        return commentService.queryAllComments(condition);
    }

    @ApiOperation(value = "根据uid查询评论")
    @ResponseResult
    @GetMapping("/{uid}")
    public CommentDTO queryCommentByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return commentService.queryByUid(uid);
    }
}
