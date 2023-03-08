package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.po.Site;
import xyz.xcye.admin.pojo.SitePojo;
import xyz.xcye.admin.vo.SiteVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
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
public class SiteService {

    @Autowired
    private AuroraSiteService auroraSiteService;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private UserService userService;

    public int logicDeleteSite(long uid) {
        Site site = Site.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraSiteService.updateById(site);
    }

    public int physicalDeleteSite(long uid) {
        return auroraSiteService.deleteById(uid);
    }

    public void insertSite(SitePojo record) {
        Assert.notNull(record, "站点信息不能为null");
        // 因为插入导航，一定要登录才能操作，所以userUid可以直接从认证对象中获取
        Long userUid = UserUtils.getCurrentUserUid();
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId());
        record.setUid(uid);
        record.setUserUid(userUid);
        record.setDelete(false);
        auroraSiteService.insert(BeanUtils.copyProperties(record, Site.class));
    }

    public PageData<SiteVO> queryListSiteByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraSiteService.queryListByCondition(condition), SiteVO.class);
    }

    public SiteVO querySiteByUid(long uid) {
        return BeanUtils.copyProperties(auroraSiteService.queryById(uid), SiteVO.class);
    }

    public int updateSite(SitePojo record) {
        Assert.notNull(record, "站点信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return auroraSiteService.updateById(BeanUtils.copyProperties(record, Site.class));
    }
}
