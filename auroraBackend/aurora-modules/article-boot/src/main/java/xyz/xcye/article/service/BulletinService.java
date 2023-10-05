package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.po.Bulletin;
import xyz.xcye.article.pojo.BulletinPojo;
import xyz.xcye.article.util.TimeUtils;
import xyz.xcye.article.vo.BulletinVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:07
 */

@Service
public class BulletinService {

    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private AuroraBulletinService auroraBulletinService;

    public int logicDeleteBulletin(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Bulletin bulletin = Bulletin.builder()
                .delete(true)
                .uid(uid)
                .build();
        return auroraBulletinService.updateById(bulletin);
    }

    public int physicalDeleteBulletin(Long uid) {
        return auroraBulletinService.deleteById(uid);
    }

    public void insertBulletin(BulletinPojo record) {
        Assert.notNull(record, "公告不能为null");
        record.setCreateTime(null);
        record.setDelete(false);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));
        setTimingPublishTime(record);
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        auroraBulletinService.insert(BeanUtils.copyProperties(record, Bulletin.class));
    }

    public PageData<BulletinVO> queryListBulletinByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraBulletinService.queryListByCondition(condition), BulletinVO.class);
    }

    public BulletinVO queryBulletinByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraBulletinService.queryById(uid), BulletinVO.class);
    }

    public int updateBulletin(BulletinPojo record) {
        Assert.notNull(record, "公告信息不能为null");
        record.setUserUid(null);
        setTimingPublishTime(record);
        return auroraBulletinService.updateById(BeanUtils.copyProperties(record, Bulletin.class));
    }

    /**
     * 判断公告对象中的定时发布时间是否规范，如果不规范，则设置为null
     *
     * @param bulletin
     */
    private void setTimingPublishTime(BulletinPojo bulletin) {
        if (bulletin.getTiming() == null || !bulletin.getTiming()) {
            bulletin.setTimingPublishTime(null);
            return;
        }
        if (!TimeUtils.isTimingPublishTime(bulletin.getTimingPublishTime())) {
            bulletin.setTiming(false);
            bulletin.setTimingPublishTime(null);
            return;
        }
        bulletin.setTimingPublishTime(DateUtils.parse(bulletin.getTimingPublishTime()));
    }

}
