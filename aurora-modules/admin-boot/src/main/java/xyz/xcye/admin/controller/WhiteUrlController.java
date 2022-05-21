package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.admin.service.WhiteUrlService;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 21:17
 */

@Tag(name = "和白名单相关的控制器")
@RestController
@RequestMapping("/admin/whiteUrl")
public class WhiteUrlController {

    @Autowired
    private WhiteUrlService whiteUrlService;

    @DeleteMapping("/{uid}")
    @ModifyOperation
    @Operation(summary = "根据uid删除白名单")
    public int deleteByUid(@PathVariable("uid") Long uid) {
        return whiteUrlService.deleteByUid(uid);
    }

    @Operation(summary = "插入白名单记录")
    @ModifyOperation
    @PostMapping
    public int insert(@Validated({Insert.class, Default.class}) WhiteUrl record) {
        return whiteUrlService.insert(record);
    }

    /**
     * 条件查询，其中keyword为url
     * @param condition
     * @return
     */
    @Operation(summary = "根据查询条件获取所有的白名单数据")
    @GetMapping
    @SelectOperation
    public PageData<WhiteUrl> selectByCondition(@Param("condition") Condition<Integer> condition) {
        return whiteUrlService.selectByCondition(condition);
    }

    @PutMapping
    @Operation(summary = "修改白名单数据")
    @ModifyOperation
    public int updateByPrimaryKeySelective(@Validated({Update.class}) WhiteUrl record) {
        return whiteUrlService.updateByPrimaryKeySelective(record);
    }
}
