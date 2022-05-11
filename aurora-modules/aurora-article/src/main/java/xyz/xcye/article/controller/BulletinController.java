package xyz.xcye.article.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.article.service.BulletinService;
import xyz.xcye.article.vo.BulletinVO;
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

@RequestMapping("/blog/bulletin")
@Api(tags = "和公告相关的控制类")
@RestController
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @ApiOperation("插入公告")
    @ModifyOperation
    @PostMapping
    public int insertBulletin(@Validated({Insert.class, Default.class}) Bulletin bulletin) {
        return bulletinService.insertSelective(bulletin);
    }

    @ModifyOperation
    @ApiOperation("逻辑删除公告")
    @DeleteMapping("/{uid}")
    public int deleteBulletin(@PathVariable("uid") long uid) {
        return bulletinService.deleteByPrimaryKey(uid);
    }

    @ApiOperation("物理删除公告")
    @ModifyOperation
    @DeleteMapping("/physics/{uid}")
    public int physicsDeleteBulletin(@PathVariable("uid") long uid) {
        return bulletinService.physicsDeleteByUid(uid);
    }

    @ApiOperation("修改公告内容")
    @ModifyOperation
    @PutMapping
    public int updateBulletin(@Validated({Update.class, Default.class}) Bulletin bulletin) {
        return bulletinService.updateByPrimaryKeySelective(bulletin);
    }

    @ApiOperation("根据条件，查询满足要求的公告")
    @SelectOperation
    @GetMapping
    public PageData<BulletinVO> selectAllBulletin(Condition<Long> condition) {
        return bulletinService.selectByCondition(condition);
    }

    @ApiOperation("根据uid查询公告")
    @SelectOperation
    @GetMapping("/{uid}")
    public BulletinVO selevtByUid(@PathVariable("uid") long uid) {
        return bulletinService.selectByUid(uid);
    }
}
