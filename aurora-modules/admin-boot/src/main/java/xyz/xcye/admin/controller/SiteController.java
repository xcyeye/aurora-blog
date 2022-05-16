package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.Site;
import xyz.xcye.admin.service.SiteService;
import xyz.xcye.admin.vo.SiteVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/admin/site")
@Api(tags = "用户站点信息api")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @DeleteMapping("/{uid}")
    @ModifyOperation
    @ApiOperation("逻辑删除此uid对应的站点信息")
    public int deleteByPrimaryKey(@PathVariable("uid") long uid) {
        return siteService.deleteByPrimaryKey(uid);
    }

    @DeleteMapping("/physics/{uid}")
    @ApiOperation("物理删除站点信息")
    @ModifyOperation
    public int physicsDeleteSiteInfo(@PathVariable("uid") long uid) {
        return siteService.physicsDeleteSite(uid);
    }

    @PostMapping
    @ModifyOperation
    @ApiOperation("插入站点信息")
    public int insertSelective(@Validated({Insert.class, Default.class}) Site record) {
        return siteService.insertSelective(record);
    }

    /**
     * 通过条件查询
     * @param condition 查询参数，keyword->logo_title(不是模糊查询)
     * @return object by primary key
     */
    @GetMapping
    @SelectOperation
    @ApiOperation("根据条件查询站点信息")
    public PageData<SiteVO> selectByCondition(Condition<Long> condition) {
        return siteService.selectByCondition(condition);
    }

    /**
     * 通过userUid查询
     * @param uid
     * @return
     */
    @GetMapping("/{uid}")
    @SelectOperation
    @ApiOperation("根据uid查询站点信息")
    public SiteVO selectByUid(@PathVariable("uid") long uid) {
        return siteService.selectByUid(uid);
    }

    @PutMapping
    @ModifyOperation
    @ApiOperation("根据uid修改站点信息")
    public int updateByPrimaryKeySelective(Site record) {
        return siteService.updateByPrimaryKeySelective(record);
    }
}