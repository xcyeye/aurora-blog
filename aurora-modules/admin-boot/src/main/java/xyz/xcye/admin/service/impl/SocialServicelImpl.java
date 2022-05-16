package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.dao.SocialMapper;
import xyz.xcye.admin.po.Social;
import xyz.xcye.admin.service.SocialService;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.SocialVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
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
public class SocialServicelImpl implements SocialService {

    @Autowired
    private SocialMapper socialMapper;
    @Autowired
    private UserService userService;

    @Override
    public int deleteByPrimaryKey(long uid) {
        Social social = Social.builder()
                .uid(uid)
                .updateTime(DateUtils.format())
                .delete(true)
                .build();
        return socialMapper.updateByPrimaryKeySelective(social);
    }

    @Override
    public int physicsDeleteSocial(long uid) {
        return socialMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Social record) {
        Assert.notNull(record, "社交信息不能为null");
        // uid是自增的
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setShow(true);
        return socialMapper.insertSelective(record);
    }

    @Override
    public PageData<SocialVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> socialMapper.selectByCondition(condition), SocialVO.class);
    }

    @Override
    public SocialVO selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(socialMapper.selectByCondition(Condition.instant(uid, true)), SocialVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Social record) {
        Assert.notNull(record, "社交信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        record.setUpdateTime(DateUtils.format());
        return socialMapper.updateByPrimaryKeySelective(record);
    }
}
