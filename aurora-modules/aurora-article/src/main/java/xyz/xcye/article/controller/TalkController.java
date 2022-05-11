package xyz.xcye.article.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Talk;
import xyz.xcye.article.service.TalkService;
import xyz.xcye.article.vo.TalkVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 20:39
 */

@Api("说说相关的控制类")
@RequestMapping("/blog/talk")
@RestController
public class TalkController {

    @Autowired
    private TalkService talkService;

    @ApiOperation("插入新的说说")
    @ModifyOperation
    @PostMapping
    public int insertTalk(@Validated({Insert.class, Default.class})Talk talk) {
        return talkService.insertSelective(talk);
    }

    @ApiOperation("逻辑删除说说")
    @ModifyOperation
    @DeleteMapping("/{uid}")
    public int deleteTalkByUid(@PathVariable("uid") long uid) {
        return talkService.deleteByPrimaryKey(uid);
    }

    @ModifyOperation
    @ApiOperation("物理删除说说")
    @DeleteMapping("/physics/{uid}")
    public int physicsDeleteTalk(@PathVariable("uid") long uid) {
        return talkService.physicsDeleteByUid(uid);
    }

    @ApiOperation("根据条件查询说说")
    @SelectOperation
    @GetMapping
    public PageData<TalkVO> selectAllTalk(Condition<Long> condition) {
        return talkService.selectByCondition(condition);
    }

    @ApiOperation("根据uid查询说说")
    @SelectOperation
    @GetMapping("/{uid}")
    public TalkVO selectByUid(@PathVariable("uid") long uid) {
        return talkService.selectByUid(uid);
    }

    @ApiOperation("修改说说内容")
    @PutMapping
    @ModifyOperation
    public int updateTalk(@Validated({Update.class, Default.class}) Talk talk) {
        return talkService.updateByPrimaryKeySelective(talk);
    }
}
