package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.pojo.SocialPojo;
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
    @PostMapping("/loginDeleteSocial")
    public int loginDeleteSocial(@RequestBody SocialPojo record) {
        return socialService.loginDeleteSocial(record.getUid());
    }

    @PostMapping("/physicalDeleteSocial")
    @Operation(summary = "物理删除此社交信息")
    @ModifyOperation
    public int physicalDeleteSocial(@RequestBody SocialPojo record) {
        return socialService.physicalDeleteSocial(record.getUid());
    }


    @PostMapping("/insertSocial")
    @Operation(summary = "插入新的社交信息")
    @ModifyOperation
    public void insertSocial(@Validated({Insert.class, Default.class}) @RequestBody SocialPojo record) {
        socialService.insertSocial(record);
    }

    @Operation(summary = "根据条件查询社交信息")
    @SelectOperation
    @PostMapping("/queryListSocialByCondition")
    public PageData<SocialVO> queryListSocialByCondition(@RequestBody Condition<Long> condition) {
        return socialService.queryListSocialByCondition(condition);
    }

    @PostMapping("/querySocialByUid")
    @SelectOperation
    @Operation(summary = "根据uid查询社交信息")
    public SocialVO querySocialByUid(@RequestBody SocialPojo record) {
        return socialService.querySocialByUid(record.getUid());
    }

    @PostMapping("/updateSocial")
    @ModifyOperation
    @Operation(summary = "修改社交信息")
    public int updateSocial(@Validated({Update.class, Default.class}) @RequestBody SocialPojo record) {
        return socialService.updateSocial(record);
    }
}