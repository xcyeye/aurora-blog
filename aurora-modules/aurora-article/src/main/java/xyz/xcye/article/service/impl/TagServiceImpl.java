package xyz.xcye.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.dao.TagMapper;
import xyz.xcye.article.po.Tag;
import xyz.xcye.article.service.TagService;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:32
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Tag tag = Tag.builder()
                .uid(uid)
                .delete(true)
                .build();
        return tagMapper.updateByPrimaryKeySelective(tag);
    }

    @Override
    public int physicsDeleteByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return tagMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Tag record) {
        Assert.notNull(record, "标签信息不能为null");
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        return tagMapper.insertSelective(record);
    }

    @Override
    public PageData<TagVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> tagMapper.selectByCondition(condition), TagVO.class);
    }

    @Override
    public TagVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.getSingleObjFromList(tagMapper.selectByCondition(Condition.instant(uid, true)), TagVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Tag record) {
        Assert.notNull(record, "标签信息不能为null");
        record.setUserUid(null);
        return tagMapper.updateByPrimaryKeySelective(record);
    }
}
