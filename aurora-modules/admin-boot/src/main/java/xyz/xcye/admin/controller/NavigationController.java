package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.dto.NavigationDTO;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.admin.service.NavigationService;
import xyz.xcye.admin.vo.NavigationVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@Api(tags = "操作前台导航的api")
@RequestMapping("/admin/navigation")
public class NavigationController {

    @Autowired
    private NavigationService navigationService;

    @ModifyOperation
    @DeleteMapping("/{uid}")
    @ApiOperation("根据uid逻辑删除导航")
    public int deleteByPrimaryKey(@PathVariable("uid") long uid) {
        return navigationService.deleteByPrimaryKey(uid);
    }

    @ApiOperation("物理删除")
    @DeleteMapping("/physics/{uid}")
    @ModifyOperation
    public int physicsDeleteNavigation(@PathVariable("uid") long uid) {
        return navigationService.physicsDeleteNavigation(uid);
    }

    @PostMapping
    @ModifyOperation
    @ApiOperation("插入新的导航信息")
    public int insertSelective(@Validated({Insert.class, Default.class}) Navigation record) {
        return navigationService.insertSelective(record);
    }

    @GetMapping
    @SelectOperation
    @ApiOperation("根据条件查询")
    public PageData<NavigationVO> selectByCondition(Condition<Long> condition) {
        return navigationService.selectByCondition(condition);
    }

    @ApiOperation("根据uid查询")
    @GetMapping("/{uid}")
    @SelectOperation
    public NavigationVO selectNavigationByUid(@PathVariable("uid") long uid) {
        return navigationService.selectNavigationByUid(uid);
    }

    /**
     * 根据用户名，获取该用户的所有导航信息
     * @param userUid
     * @return
     */
    @SelectOperation
    @ApiOperation("查询该用户所有的前台导航信息")
    @GetMapping("/userUid/{userUid}")
    public NavigationDTO selectAllNavigationByUserUid(@PathVariable("userUid") long userUid) {
        return navigationService.selectAllNavigationByUserUid(userUid);
    }

    @PutMapping
    @ModifyOperation
    @ApiOperation("修改导航信息")
    public int updateByPrimaryKeySelective(@Validated({Update.class, Default.class}) Navigation record) {
        return navigationService.updateByPrimaryKeySelective(record);
    }
}