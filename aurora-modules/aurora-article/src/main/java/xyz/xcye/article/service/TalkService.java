package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.xcye.article.po.Talk;
import xyz.xcye.article.pojo.TalkPojo;
import xyz.xcye.article.vo.TalkVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
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
public class TalkService {

    @Autowired
    private AuroraTalkService auroraTalkService;

    @Autowired
    private AuroraProperties auroraProperties;

    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Talk talk = Talk.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraTalkService.updateById(talk);
    }

    public int physicsDeleteByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraTalkService.deleteById(uid);
    }

    @Transactional
    public void insertSelective(TalkPojo pojo) {
        Assert.notNull(pojo, "说说信息不能为null");
        Talk record = BeanUtils.copyProperties(pojo, Talk.class);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()));
        record.setCommentUids(null);
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        auroraTalkService.insert(record);
    }

    public PageData<TalkVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraTalkService.queryListByCondition(condition), TalkVO.class);
    }

    public TalkVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraTalkService.queryById(uid), TalkVO.class);
    }

    @Transactional
    public int updateByPrimaryKeySelective(TalkPojo record) {
        Assert.notNull(record, "说说信息不能为null");
        record.setUserUid(null);
        return auroraTalkService.updateById(BeanUtils.copyProperties(record, Talk.class));
    }
}
