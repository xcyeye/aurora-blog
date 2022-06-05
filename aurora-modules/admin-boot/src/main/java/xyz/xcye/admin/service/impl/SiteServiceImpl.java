package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.dao.SiteMapper;
import xyz.xcye.admin.po.Site;
import xyz.xcye.admin.service.SiteService;
import xyz.xcye.admin.service.UserService;
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
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private UserService userService;

    @Override
    public int deleteByPrimaryKey(long uid) {
        Site site = Site.builder()
                .uid(uid)
                .delete(true)
                .build();
        return siteMapper.updateByPrimaryKeySelective(site);
    }

    @Override
    public int physicsDeleteSite(long uid) {
        return siteMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Site record) {
        Assert.notNull(record, "站点信息不能为null");
        // 因为插入导航，一定要登录才能操作，所以userUid可以直接从认证对象中获取
        Long userUid = UserUtils.getCurrentUserUid();
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId());
        record.setUid(uid);
        record.setUserUid(userUid);
        record.setDelete(false);
        return siteMapper.insertSelective(record);
    }

    @Override
    public PageData<SiteVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> siteMapper.selectByCondition(condition), SiteVO.class);
    }

    @Override
    public SiteVO selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(siteMapper.selectByCondition(Condition.instant(uid, true)), SiteVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Site record) {
        Assert.notNull(record, "站点信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return siteMapper.updateByPrimaryKeySelective(record);
    }
}
