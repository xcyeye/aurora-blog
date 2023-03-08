package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.pojo.SiteSettingPojo;
import xyz.xcye.admin.service.SiteSettingService;
import xyz.xcye.admin.vo.SiteSettingVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/admin/siteSetting")
@Tag(name = "用户前端站点配置api")
public class SiteSettingController {

    @Autowired
    private SiteSettingService siteSettingService;

    @PostMapping("/physicalDeleteSiteSetting")
    @Operation(summary = "物理删除站点配置")
    @ModifyOperation
    public int physicalDeleteSiteSetting(@RequestBody SiteSettingPojo pojo) {
        return siteSettingService.physicalDeleteSiteSetting(pojo.getUid());
    }

    @PostMapping("/insertSiteSetting")
    @ModifyOperation
    @Operation(summary = "插入站点配置")
    public void insertSiteSetting(@Validated({Insert.class, Default.class}) @RequestBody SiteSettingPojo pojo) {
        siteSettingService.insertSiteSetting(pojo);
    }

    /**
     * 通过条件查询
     * @param condition
     * @return object by primary key
     */
    @PostMapping("/queryListSiteSettingByCondition")
    @SelectOperation
    @Operation(summary = "根据条件查询站点配置")
    public PageData<SiteSettingVO> queryListSiteByCondition(@RequestBody Condition<Long> condition) {
        return siteSettingService.queryListSiteSettingByCondition(condition);
    }

    /**
     * 通过userUid查询
     * @param pojo
     * @return
     */
    @PostMapping("/querySiteSettingByUid")
    @SelectOperation
    @Operation(summary = "根据uid查询站点配置")
    public SiteSettingVO querySiteByUid(@RequestBody SiteSettingPojo pojo) {
        return siteSettingService.querySiteSettingByUid(pojo.getUid());
    }

    @PostMapping("/querySiteSettingByUserUid")
    @SelectOperation
    @Operation(summary = "根据uid查询站点配置")
    public SiteSettingVO querySiteSettingByUserUid(@RequestBody SiteSettingPojo pojo) {
        return siteSettingService.querySiteSettingByUserUid(pojo.getUserUid());
    }

    @PostMapping("/updateSiteSetting")
    @ModifyOperation
    @Operation(summary = "根据uid修改站点配置")
    public int updateSiteSetting(@RequestBody SiteSettingPojo pojo) {
        return siteSettingService.updateSiteSetting(pojo);
    }
}