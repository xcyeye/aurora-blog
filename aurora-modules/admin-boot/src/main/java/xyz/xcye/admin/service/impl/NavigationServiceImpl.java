package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.dao.NavigationMapper;
import xyz.xcye.admin.dto.NavigationDTO;
import xyz.xcye.admin.po.Navigation;
import xyz.xcye.admin.service.NavigationService;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.NavigationVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author qsyyke
 * @date Created in 2022/5/16 16:16
 */

@Service
public class NavigationServiceImpl implements NavigationService {

    @Autowired
    private NavigationMapper navigationMapper;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private UserService userService;

    @Override
    public int deleteByPrimaryKey(long uid) {
        Navigation navigation = Navigation.builder()
                .uid(uid)
                .updateTime(DateUtils.format())
                .delete(true)
                .build();
        return navigationMapper.updateByPrimaryKeySelective(navigation);
    }

    @Override
    public int physicsDeleteNavigation(long uid) {
        return navigationMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Navigation record) {
        Assert.notNull(record, "导航信息不能为null");
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setDelete(false);
        record.setExternal(false);
        record.setShow(true);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));
        setEffectiveNavigationUid(record, true);
        setEffectiveNavigationUid(record, false);
        return navigationMapper.insertSelective(record);
    }

    @Override
    public PageData<NavigationVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> navigationMapper.selectByCondition(condition), NavigationVO.class);
    }

    @Override
    public NavigationVO selectNavigationByUid(long uid) {
        return BeanUtils.getSingleObjFromList(navigationMapper.selectByCondition(Condition.instant(uid, true)), NavigationVO.class);
    }

    @Override
    public NavigationDTO selectAllNavigationByUserUid(long userUid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Navigation record) {
        Assert.notNull(record, "导航信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        record.setUpdateTime(DateUtils.format());
        setEffectiveNavigationUid(record, true);
        setEffectiveNavigationUid(record, false);
        return navigationMapper.updateByPrimaryKeySelective(record);
    }

    private void setEffectiveNavigationUid(Navigation navigation, boolean setParentNav) {
        if (setParentNav) {
            if (navigation.getParentNavUid() == null) {
                return;
            }

            NavigationVO navigationVO = selectNavigationByUid(navigation.getParentNavUid());
            if (navigationVO == null) {
                navigation.setParentNavUid(null);
                return;
            }
            return;
        }

        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            return;
        }
        try {
            navigation.setSonNavUids(getEffectiveSonNavigationUidStr(navigation));
        } catch (Exception e) {
            throw new RuntimeException("类型错误");
        }
    }

    private String getEffectiveSonNavigationUidStr(Navigation navigation) {
        Assert.notNull(navigation, "导航信息不能为null");
        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            return null;
        }

        // 获取有效的导航信息
        return Arrays.stream(navigation.getSonNavUids().split(","))
                .map(Long::parseLong)
                .filter(uid -> selectNavigationByUid(uid) != null)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
