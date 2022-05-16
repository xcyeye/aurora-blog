package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import xyz.xcye.core.exception.comment.CommentException;
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
import java.util.concurrent.atomic.AtomicReference;
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

    @Transactional
    @Override
    public int insertSelective(Navigation record) {
        Assert.notNull(record, "导航信息不能为null");
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setDelete(false);
        record.setExternal(false);
        record.setShow(true);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));

        // 插入新的导航，只能将这个新添加的导航添加到已存在的导航上，不能连带着添加该导航的子导航
        record.setSonNavUids(null);
        setEffectiveNavigationUid(record, true);
        int insertNum = navigationMapper.insertSelective(record);
        // 如果增加成功，并且存在父导航，则修改父导航的子导航数据
        if (insertNum == 1 && record.getParentNavUid() != null) {
            addSonNavigationUids(record.getParentNavUid(), record.getUid());
        }
        return insertNum;
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

    /**
     * 设置该导航对象中，有效的子导航或者父导航，如果传入一个不存在的导航uid，那么会将该导航对象的父/子导航uid设置为null
     * @param navigation
     * @param setParentNav
     */
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

    /**
     * 获取一个导航中，有效的子导航字符串
     * @param navigation
     * @return
     */
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

    private String getEffectiveSonNavigationUids(Navigation parentNavigation) {
        if (parentNavigation == null) {
            return null;
        }

        NavigationVO navigationVO = selectNavigationByUid(parentNavigation.getUid());
        if (navigationVO == null) {
            return null;
        }

        // 查看该父导航的可用子导航
        if (!StringUtils.hasLength(navigationVO.getSonNavUids())) {
            return null;
        }

        return Arrays.stream(navigationVO.getSonNavUids().split(","))
                .map(Long::parseLong)
                .filter(uid -> selectNavigationByUid(uid) != null)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * 获取该导航对象中的子导航字符串，不会验证该导航中的子导航uid是否有效
     * @param navigation
     * @return
     */
    private String getSonNavigationUids(Navigation navigation) {
        if (navigation == null) {
            return null;
        }

        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            return null;
        }
        return String.join(",", navigation.getSonNavUids().split(","));
    }

    /**
     * 为parentNavUid对对象的父导航，增加一个新的子导航，newSonNavUid是一个有效的uid，不会验证该newSonNavUid是否有效，但是
     * 会验证parentNavUid是否有效
     * @param parentNavUid
     * @param newSonNavUid
     */
    private void addSonNavigationUids(long parentNavUid, long newSonNavUid) {
        NavigationVO navigationVO = selectNavigationByUid(parentNavUid);
        AssertUtils.stateThrow(navigationVO != null, () -> new CommentException("该" + parentNavUid + "不存在"));

        // 获取有效的子导航字符串
        String effectiveSonNavigationUids =
                getEffectiveSonNavigationUids(BeanUtils.copyProperties(navigationVO, Navigation.class));

        // 修改
        AtomicReference<String> sonNavigationStrAtomicReference = new AtomicReference<>("");
        Optional.ofNullable(effectiveSonNavigationUids)
                .ifPresentOrElse(uidStr -> sonNavigationStrAtomicReference.set(uidStr + "," + newSonNavUid),
                        () -> sonNavigationStrAtomicReference.set(newSonNavUid + ""));
        Navigation navigation = Navigation.builder()
                .uid(parentNavUid)
                .sonNavUids(sonNavigationStrAtomicReference.get())
                .build();
        updateByPrimaryKeySelective(navigation);
    }
}
