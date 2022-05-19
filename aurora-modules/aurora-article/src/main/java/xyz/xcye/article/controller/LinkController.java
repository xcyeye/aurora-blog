package xyz.xcye.article.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
import xyz.xcye.feign.config.service.MessageLogFeignService;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 20:37
 */

@Api(tags = "友情链接相关的操作")
@RestController
@RequestMapping("/blog/link")
public class LinkController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private MessageLogFeignService messageLogFeignService;

    @ApiOperation("根据uid删除对应的友情链接")
    @DeleteMapping
    @ModifyOperation
    public int deleteByPrimaryKey(Long uid, @RequestParam("msg") String replyMessage) throws BindException {
        return linkService.deleteByPrimaryKey(uid, replyMessage);
    }

    @ModifyOperation
    @ApiOperation("插入新友情链接")
    @PostMapping
    public int insertSelective(@Validated({Insert.class, Default.class}) Link record) throws BindException {
        return linkService.insertSelective(record);
    }

    @GetMapping
    @ApiOperation("根据条件查询")
    @SelectOperation
    public PageData<LinkVO> selectByCondition(Condition<Long> condition) {
        return linkService.selectByCondition(condition);
    }

    @ApiOperation("根据uid查询")
    @SelectOperation
    @GetMapping("/{uid}")
    public LinkVO selectByUid(@PathVariable("uid") Long uid) {
        return linkService.selectByUid(uid);
    }

    @PutMapping
    @ModifyOperation
    @ApiOperation("修改友情链接信息")
    public int updateByPrimaryKeySelective(@Validated({Update.class, Default.class}) Link record,@RequestParam("msg") String replyMessage) throws BindException {
        return linkService.updateByPrimaryKeySelective(record, replyMessage);
    }
}
