package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Link;
import xyz.xcye.article.service.LinkService;
import xyz.xcye.article.vo.LinkVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 20:37
 */

@Tag(name = "友情链接相关的操作")
@RestController
@RequestMapping("/blog/link")
@RefreshScope
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Operation(summary = "根据uid删除对应的友情链接")
    @PutMapping("/delete")
    @ModifyOperation
    public int deleteByPrimaryKey(Long uid, @RequestParam("msg") String replyMessage) throws BindException {
        return linkService.deleteByPrimaryKey(uid, replyMessage);
    }

    @ModifyOperation
    @Operation(summary = "插入新友情链接")
    @PostMapping
    public int insertSelective(@Validated({Insert.class, Default.class}) Link link) throws BindException {
        return linkService.insertSelective(link);
    }

    @GetMapping
    @Operation(summary = "根据条件查询")
    @SelectOperation
    public PageData<LinkVO> selectByCondition(Condition<Long> condition) {
        return linkService.selectByCondition(condition);
    }

    @Operation(summary = "根据uid查询")
    @SelectOperation
    @GetMapping("/{uid}")
    public LinkVO selectByUid(@PathVariable("uid") Long uid) {
        return linkService.selectByUid(uid);
    }

    @PutMapping
    @ModifyOperation
    @Operation(summary = "修改友情链接信息")
    public int updateByPrimaryKeySelective(@Validated({Update.class, Default.class}) Link link) throws BindException {
        return linkService.updateByPrimaryKeySelective(link);
    }

    @PutMapping("/publish")
    @ModifyOperation
    @Operation(summary = "修改友情链接的发布状态")
    public int updateLinkPublishStatus(long uid, boolean publish, @RequestParam("msg") String replyMessage) throws BindException {
        return linkService.updateLinkPublishStatus(uid, publish, replyMessage);
    }
}
