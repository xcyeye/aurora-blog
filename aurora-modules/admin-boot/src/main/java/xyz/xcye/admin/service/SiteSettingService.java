package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.po.SiteSetting;
import xyz.xcye.admin.pojo.SiteSettingPojo;
import xyz.xcye.admin.vo.SiteSettingVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/16 16:16
 */

@Service
public class SiteSettingService {

    @Autowired
    private AuroraSiteSettingService auroraSiteSettingService;
    @Autowired
    private UserService userService;

    public int physicalDeleteSiteSetting(long uid) {
        return auroraSiteSettingService.deleteById(uid);
    }

    public void insertSiteSetting(SiteSettingPojo pojo) {
        // SiteSetting siteSetting = new SiteSetting();
        // siteSetting.setUserUid(pojo.getUserUid());
        // SiteSetting queriedSiteSetting = auroraSiteSettingService.queryOne(siteSetting);
        // AssertUtils.stateThrow(queriedSiteSetting == null, () -> new SettingException("站点设置每个用户只能存在一条记录"));
        auroraSiteSettingService.insert(BeanUtils.copyProperties(pojo, SiteSetting.class));
    }

    public PageData<SiteSettingVO> queryListSiteSettingByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraSiteSettingService.queryListByCondition(condition), SiteSettingVO.class);
    }

    public SiteSettingVO querySiteSettingByUid(long uid) {
        return BeanUtils.copyProperties(auroraSiteSettingService.queryById(uid), SiteSettingVO.class);
    }

    public SiteSettingVO querySiteSettingByUserUid(long userUid) {
        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setUserUid(userUid);
        return BeanUtils.copyProperties(auroraSiteSettingService.queryOne(siteSetting), SiteSettingVO.class);
    }

    public int updateSiteSetting(SiteSettingPojo pojo) {
        Optional.ofNullable(pojo.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return auroraSiteSettingService.updateById(BeanUtils.copyProperties(pojo, SiteSetting.class));
    }
}
