package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.admin.service.WhiteUrlService;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/10 21:17
 */

@Api(tags = "和白名单相关的控制器")
@RestController
@RequestMapping("/admin/whiteUrl")
public class WhiteUrlController {

    @Autowired
    private WhiteUrlService whiteUrlService;

    @DeleteMapping("/{uid}")
    @ApiOperation("根据uid删除白名单")
    public int deleteByUid(@PathVariable("uid") Long uid) {
        return whiteUrlService.deleteByUid(uid);
    }

    @ApiOperation("插入白名单记录")
    @PostMapping
    public int insert(@Validated({Insert.class, Default.class}) WhiteUrl record) {
        return whiteUrlService.insert(record);
    }

    /**
     * 条件查询，其中keyword为url
     * @param condition
     * @return
     */
    @ApiOperation("根据查询条件获取所有的白名单数据")
    @GetMapping
    public List<WhiteUrl> selectByCondition(@Param("condition") Condition<Integer> condition) {
        return whiteUrlService.selectByCondition(condition);
    }

    @PutMapping
    @ApiOperation("修改白名单数据")
    public int updateByPrimaryKeySelective(@Validated({Update.class}) WhiteUrl record) {
        return whiteUrlService.updateByPrimaryKeySelective(record);
    }
}
