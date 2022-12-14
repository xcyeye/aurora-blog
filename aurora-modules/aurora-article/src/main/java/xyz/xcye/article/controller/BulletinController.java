package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.article.pojo.BulletinPojo;
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
@Tag(name = "和公告相关的控制类")
@RestController
@RefreshScope
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @Operation(summary = "插入公告")
    @ModifyOperation
    @PostMapping
    public void insertBulletin(@Validated({Insert.class, Default.class}) BulletinPojo bulletin) {
        bulletinService.insertSelective(bulletin);
    }

    @ModifyOperation
    @Operation(summary = "逻辑删除公告")
    @DeleteMapping("/{uid}")
    public int deleteBulletin(@PathVariable("uid") long uid) {
        return bulletinService.deleteByPrimaryKey(uid);
    }

    @Operation(summary = "物理删除公告")
    @ModifyOperation
    @DeleteMapping("/physics/{uid}")
    public int physicsDeleteBulletin(@PathVariable("uid") long uid) {
        return bulletinService.physicsDeleteByUid(uid);
    }

    @Operation(summary = "修改公告内容")
    @ModifyOperation
    @PutMapping
    public int updateBulletin(@Validated({Update.class, Default.class}) BulletinPojo bulletin) {
        return bulletinService.updateByPrimaryKeySelective(bulletin);
    }

    @Operation(summary = "根据条件，查询满足要求的公告")
    @SelectOperation
    @GetMapping
    public PageData<BulletinVO> selectAllBulletin(Condition<Long> condition) {
        return bulletinService.selectByCondition(condition);
    }

    @Operation(summary = "根据uid查询公告")
    @SelectOperation
    @GetMapping("/{uid}")
    public BulletinVO selectByUid(@PathVariable("uid") long uid) {
        return bulletinService.selectByUid(uid);
    }
}
