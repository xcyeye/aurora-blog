package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.article.pojo.LinkPojo;
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
    @PostMapping("/physicalDeleteLink")
    @ModifyOperation
    public int physicalDeleteLink(@RequestBody LinkPojo link) throws BindException {
        return linkService.physicalDeleteLink(link);
    }

    @ModifyOperation
    @Operation(summary = "插入新友情链接")
    @PostMapping("/insertLink")
    public void insertLink(@Validated({Insert.class, Default.class}) @RequestBody LinkPojo link) throws BindException {
        linkService.insertLink(link);
    }

    @PostMapping("/queryListLinkByCondition")
    @Operation(summary = "根据条件查询")
    @SelectOperation
    public PageData<LinkVO> queryListLinkByCondition(@RequestBody Condition<Long> condition) {
        return linkService.queryListLinkByCondition(condition);
    }

    @Operation(summary = "根据uid查询")
    @SelectOperation
    @PostMapping("/queryLinkByUid")
    public LinkVO queryLinkByUid(@RequestBody LinkPojo link) {
        return linkService.queryLinkByUid(link.getUid());
    }

    @PostMapping("/updateLink")
    @ModifyOperation
    @Operation(summary = "修改友情链接信息")
    public int updateLink(@Validated({Update.class, Default.class}) @RequestBody LinkPojo link) throws BindException {
        return linkService.updateLink(link);
    }

    @PostMapping("/updateLinkPublishStatus")
    @ModifyOperation
    @Operation(summary = "修改友情链接的发布状态")
    public int updateLinkPublishStatus(@RequestBody LinkPojo link) throws BindException {
        return linkService.updateLinkPublishStatus(link);
    }

    @PostMapping("/queryTotalLinkCount")
    @ModifyOperation
    @Operation(summary = "查询友情链接数")
    public Integer queryTotalLinkCount(@RequestBody LinkPojo linkPojo) {
        return linkService.queryTotalLinkCount(linkPojo);
    }
}
