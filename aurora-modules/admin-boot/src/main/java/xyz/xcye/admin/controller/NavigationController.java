package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.dto.NavigationDTO;
import xyz.xcye.admin.pojo.NavigationPojo;
import xyz.xcye.admin.service.NavigationService;
import xyz.xcye.admin.vo.NavigationVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@Tag(name = "操作前台导航的api")
@RequestMapping("/admin/navigation")
public class NavigationController {

    @Autowired
    private NavigationService navigationService;

    @ModifyOperation
    @PostMapping("/delete")
    @Operation(summary = "根据uid逻辑删除导航")
    public int deleteByPrimaryKey(@Validated({Delete.class}) @RequestBody NavigationPojo pojo) {
        return navigationService.deleteByPrimaryKey(pojo);
    }

    @Operation(summary = "物理删除")
    @PostMapping("/physics/delete")
    @ModifyOperation
    public int physicsDeleteNavigation(@Validated({Delete.class}) @RequestBody NavigationPojo pojo) {
        return navigationService.physicsDeleteNavigation(pojo);
    }

    @PostMapping("/insert")
    @ModifyOperation
    @Operation(summary = "插入新的导航信息")
    public void insertSelective(@Validated({Insert.class, Default.class}) @RequestBody NavigationPojo record) {
        navigationService.insertSelective(record);
    }

    @PostMapping("queryAllByCondition")
    @SelectOperation
    @Operation(summary = "根据条件查询")
    public PageData<NavigationVO> selectByCondition(@RequestBody Condition<Long> condition) {
        return navigationService.selectByCondition(condition);
    }

    @Operation(summary = "根据uid查询")
    @PostMapping("/queryByUid")
    @SelectOperation
    public NavigationVO selectNavigationByUid(@RequestBody NavigationPojo pojo) {
        return navigationService.selectNavigationByUid(pojo);
    }

    /**
     * 根据用户名，获取该用户的所有导航信息
     * @param pojo
     * @return
     */
    @SelectOperation
    @Operation(summary = "查询该用户所有的前台导航信息")
    @PostMapping("queryByUserUid")
    public NavigationDTO selectAllNavigationByUserUid(@RequestBody NavigationPojo pojo) {
        return navigationService.selectAllNavigationByUserUid(pojo);
    }

    @PostMapping("/update")
    @ModifyOperation
    @Operation(summary = "修改导航信息")
    public int updateByPrimaryKeySelective(@Validated({Update.class, Default.class}) @RequestBody NavigationPojo record) {
        return navigationService.updateByPrimaryKeySelective(record);
    }
}