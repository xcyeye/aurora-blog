package xyz.xcye.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.dao.TalkMapper;
import xyz.xcye.article.po.Talk;
import xyz.xcye.article.service.TalkService;
import xyz.xcye.article.vo.TalkVO;
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
 * @date Created in 2022/5/11 19:42
 */

@Service
public class TalkServiceImpl implements TalkService {

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private UserUtils userUtils;

    @Override
    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Talk talk = Talk.builder()
                .uid(uid)
                .delete(true)
                .updatedTime(DateUtils.format())
                .build();
        return talkMapper.updateByPrimaryKeySelective(talk);
    }

    @Override
    public int physicsDeleteByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return talkMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Talk record) {
        Assert.notNull(record, "说说信息不能为null");
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));

        JwtUserInfo jwtUserInfo = userUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        return talkMapper.insertSelective(record);
    }

    @Override
    public PageData<TalkVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition,t -> talkMapper.selectByCondition(condition), TalkVO.class);
    }

    @Override
    public TalkVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.getSingleObjFromList(talkMapper.selectByCondition(Condition.instant(uid, true)), TalkVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Talk record) {
        Assert.notNull(record, "说说信息不能为null");
        record.setUpdatedTime(DateUtils.format());
        return talkMapper.updateByPrimaryKeySelective(record);
    }
}
