package xyz.xcye.article.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Tag;
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

@Api("操作文章标签的控制类")
@RequestMapping("/blog/tag")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("插入新标签")
    @PostMapping
    @ModifyOperation
    public int insertTag(@Validated({Insert.class, Default.class})Tag tag) {
        return tagService.insertSelective(tag);
    }

    @ApiOperation("逻辑删除标签信息")
    @ModifyOperation
    @DeleteMapping("/{uid}")
    public int deleteTagByUid(@PathVariable("uid") long uid) {
        return tagService.deleteByPrimaryKey(uid);
    }

    @ApiOperation("物理删除标签数据")
    @ModifyOperation
    @DeleteMapping("/physics/{uid}")
    public int physicsDeleteTag(@PathVariable("uid") long uid) {
        return tagService.physicsDeleteByUid(uid);
    }

    @ApiOperation("根据条件查询标签")
    @SelectOperation
    @GetMapping
    public PageData<TagVO> selectAllTag(Condition<Long> condition) {
        return tagService.selectByCondition(condition);
    }

    @ApiOperation("根据uid查询标签")
    @SelectOperation
    @GetMapping("/{uid}")
    public TagVO selectTagByUid(@PathVariable("uid") long uid) {
        return tagService.selectByUid(uid);
    }

    @ApiOperation("修改标签信息")
    @ModifyOperation
    @PutMapping
    public int updateTagByUid(@Validated({Update.class, Default.class}) Tag tag) {
        return tagService.updateByPrimaryKeySelective(tag);
    }
}
