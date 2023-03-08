package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.article.pojo.TagPojo;
import xyz.xcye.article.service.TagService;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 20:38
 */

@io.swagger.v3.oas.annotations.tags.Tag(name = "操作文章标签的控制类")
@RequestMapping("/blog/tag")
@RestController
@RefreshScope
public class TagController {

    @Autowired
    private TagService tagService;

    @Operation(summary = "插入新标签")
    @PostMapping("/insertTag")
    @ModifyOperation
    public void insertTag(@Validated({Insert.class, Default.class}) @RequestBody TagPojo tag) {
        tagService.insertTag(tag);
    }

    @Operation(summary = "逻辑删除标签信息")
    @ModifyOperation
    @PostMapping("/logicDeleteTag")
    public int logicDeleteTag(@RequestBody TagPojo tag) {
        return tagService.logicDeleteTag(tag.getUid());
    }

    @Operation(summary = "物理删除标签数据")
    @ModifyOperation
    @PostMapping("/physicalDeleteTag")
    public int physicalDeleteTag(@RequestBody TagPojo tag) {
        return tagService.physicalDeleteTag(tag.getUid());
    }

    @Operation(summary = "根据条件查询标签")
    @SelectOperation
    @PostMapping("/queryListTagByCondition")
    public PageData<TagVO> queryListTagByCondition(@RequestBody Condition<Long> condition) {
        return tagService.queryListTagByCondition(condition);
    }

    @Operation(summary = "根据uid查询标签")
    @SelectOperation
    @PostMapping("/queryTagByUid")
    public TagVO queryTagByUid(@RequestBody TagPojo tag) {
        return tagService.queryTagByUid(tag.getUid());
    }

    @Operation(summary = "修改标签信息")
    @ModifyOperation
    @PostMapping("/updateTag")
    public int updateTag(@Validated({Update.class, Default.class}) @RequestBody TagPojo tag) {
        return tagService.updateTag(tag);
    }
}
