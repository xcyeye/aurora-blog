package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.Social;
import xyz.xcye.admin.service.SocialService;
import xyz.xcye.admin.vo.SocialVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/admin/social")
@Tag(name = "用户社交信息api")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @ModifyOperation
    @Operation(summary = "逻辑删除此社交信息")
    @DeleteMapping("/{uid}")
    public int deleteByPrimaryKey(@PathVariable("uid") long uid) {
        return socialService.deleteByPrimaryKey(uid);
    }

    @DeleteMapping("/physics/{uid}")
    @Operation(summary = "物理删除此社交信息")
    @ModifyOperation
    public int physicsDeleteSocial(@PathVariable("uid") long uid) {
        return socialService.physicsDeleteSocial(uid);
    }


    @PostMapping
    @Operation(summary = "插入新的社交信息")
    @ModifyOperation
    public int insertSelective(@Validated({Insert.class, Default.class}) Social record) {
        return socialService.insertSelective(record);
    }

    @Operation(summary = "根据条件查询社交信息")
    @SelectOperation
    @GetMapping
    public PageData<SocialVO> selectByCondition(Condition<Long> condition) {
        return socialService.selectByCondition(condition);
    }

    @GetMapping("/{uid}")
    @SelectOperation
    @Operation(summary = "根据uid查询社交信息")
    public SocialVO selectByUid(@PathVariable("uid") long uid) {
        return socialService.selectByUid(uid);
    }

    @PutMapping
    @ModifyOperation
    @Operation(summary = "修改社交信息")
    public int updateByPrimaryKeySelective(@Validated({Update.class, Default.class}) Social record) {
        return socialService.updateByPrimaryKeySelective(record);
    }
}