package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.article.pojo.TalkPojo;
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

@Tag(name = "说说相关的控制类")
@RequestMapping("/blog/talk")
@RestController
@RefreshScope
public class TalkController {

    @Autowired
    private TalkService talkService;

    @Operation(summary = "插入新的说说")
    @ModifyOperation
    @PostMapping("/insertTalk")
    public void insertTalk(@Validated({Insert.class, Default.class}) @RequestBody TalkPojo talk) {
        talkService.insertTalk(talk);
    }

    @Operation(summary = "逻辑删除说说")
    @ModifyOperation
    @PostMapping("/logicDeleteTalk")
    public int logicDeleteTalk(@RequestBody TalkPojo talk) {
        return talkService.logicDeleteTalk(talk.getUid());
    }

    @ModifyOperation
    @Operation(summary = "物理删除说说")
    @PostMapping("/physicalDeleteTalk")
    public int physicalDeleteTalk(@RequestBody TalkPojo talk) {
        return talkService.physicalDeleteTalk(talk.getUid());
    }

    @Operation(summary = "修改说说点赞数")
    @ModifyOperation
    @PostMapping("/updateTalkLikeNum")
    public void updateTalkLikeNum(@Validated({Update.class, Default.class}) @RequestBody TalkPojo talkPojo) {
        talkService.updateTalkLikeNum(talkPojo);
    }

    @Operation(summary = "根据条件查询说说")
    @SelectOperation
    @PostMapping("/queryListTalkByCondition")
    public PageData<TalkVO> queryListTalkByCondition(@RequestBody Condition<Long> condition) {
        return talkService.queryListTalkByCondition(condition);
    }

    @Operation(summary = "根据uid查询说说")
    @SelectOperation
    @PostMapping("/queryTalkByUid")
    public TalkVO queryTalkByUid(@RequestBody TalkPojo talk) {
        return talkService.queryTalkByUid(talk.getUid());
    }

    @Operation(summary = "修改说说内容")
    @PostMapping("/updateTalk")
    @ModifyOperation
    public int updateTalk(@Validated({Update.class, Default.class}) @RequestBody TalkPojo talk) {
        return talkService.updateTalk(talk);
    }

    @PostMapping("/queryTotalTalkCount")
    @ModifyOperation
    @Operation(summary = "查询说说数")
    public Integer queryTotalTalkCount(@RequestBody TalkPojo pojo) {
        return talkService.queryTotalTalkCount(pojo);
    }
}
