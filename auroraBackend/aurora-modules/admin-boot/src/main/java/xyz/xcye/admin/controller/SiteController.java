package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.pojo.SitePojo;
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
@Tag(name = "用户站点信息api")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping("/logicDeleteSite")
    @ModifyOperation
    @Operation(summary = "逻辑删除此uid对应的站点信息")
    public int logicDeleteSite(@RequestBody SitePojo record) {
        return siteService.logicDeleteSite(record.getUid());
    }

    @PostMapping("/physicalDeleteSite")
    @Operation(summary = "物理删除站点信息")
    @ModifyOperation
    public int physicalDeleteSite(@RequestBody SitePojo record) {
        return siteService.physicalDeleteSite(record.getUid());
    }

    @PostMapping("/insertSite")
    @ModifyOperation
    @Operation(summary = "插入站点信息")
    public void insertSite(@Validated({Insert.class, Default.class}) @RequestBody SitePojo record) {
        siteService.insertSite(record);
    }

    /**
     * 通过条件查询
     *
     * @param condition 查询参数，keyword->logo_title(不是模糊查询)
     * @return object by primary key
     */
    @PostMapping("/queryListSiteByCondition")
    @SelectOperation
    @Operation(summary = "根据条件查询站点信息")
    public PageData<SiteVO> queryListSiteByCondition(@RequestBody Condition<Long> condition) {
        return siteService.queryListSiteByCondition(condition);
    }

    /**
     * 通过userUid查询
     *
     * @param record
     * @return
     */
    @PostMapping("/querySiteByUid")
    @SelectOperation
    @Operation(summary = "根据uid查询站点信息")
    public SiteVO querySiteByUid(@RequestBody SitePojo record) {
        return siteService.querySiteByUid(record.getUid());
    }

    @PostMapping("/updateSite")
    @ModifyOperation
    @Operation(summary = "根据uid修改站点信息")
    public int updateSite(@RequestBody SitePojo record) {
        return siteService.updateSite(record);
    }
}