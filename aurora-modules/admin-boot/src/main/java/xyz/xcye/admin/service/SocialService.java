package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.po.Social;
import xyz.xcye.admin.pojo.SocialPojo;
import xyz.xcye.admin.vo.SocialVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.aurora.util.UserUtils;
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
 * @date Created in 2022/5/16 16:17
 */

@Service
public class SocialService {

    @Autowired
    private AuroraSocialService auroraSocialService;
    @Autowired
    private UserService userService;

    public int deleteByPrimaryKey(long uid) {
        Social social = Social.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraSocialService.updateById(social);
    }

    public int physicsDeleteSocial(long uid) {
        return auroraSocialService.deleteById(uid);
    }

    public void insertSelective(SocialPojo record) {
        Assert.notNull(record, "社交信息不能为null");
        // uid是自增的
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setShow(true);
       auroraSocialService.insert(BeanUtils.copyProperties(record, Social.class));
    }

    public PageData<SocialVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> auroraSocialService.queryListByCondition(condition), SocialVO.class);
    }

    public SocialVO selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(auroraSocialService.queryListByCondition(Condition.instant(uid, true)), SocialVO.class);
    }

    public int updateByPrimaryKeySelective(SocialPojo record) {
        Assert.notNull(record, "社交信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return auroraSocialService.updateById(BeanUtils.copyProperties(record, Social.class));
    }
}
