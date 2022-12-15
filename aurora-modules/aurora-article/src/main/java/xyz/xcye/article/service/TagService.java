package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.article.po.Tag;
import xyz.xcye.article.pojo.TagPojo;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.article.ArticleException;
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
public class TagService {

    @Autowired
    private AuroraTagService auroraTagService;

    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Tag tag = Tag.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraTagService.updateById(tag);
    }

    public int physicsDeleteByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraTagService.deleteById(uid);
    }

    public void insertSelective(TagPojo pojo) {
        Assert.notNull(pojo, "标签信息不能为null");
        Tag record = BeanUtils.copyProperties(pojo, Tag.class);
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        auroraTagService.insert(record);
    }

    public PageData<TagVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraTagService.queryListByCondition(condition), TagVO.class);
    }

    public TagVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraTagService.queryById(uid), TagVO.class);
    }

    public TagVO selectByTitle(String title) {
        AssertUtils.stateThrow(StringUtils.hasLength(title), () -> new ArticleException("标签不能为null"));
        Tag tag = new Tag();
        tag.setTitle(title);
        return BeanUtils.copyProperties(auroraTagService.queryOne(tag), TagVO.class);
    }

    public int updateByPrimaryKeySelective(TagPojo record) {
        Assert.notNull(record, "标签信息不能为null");
        record.setUserUid(null);
        return auroraTagService.updateById(BeanUtils.copyProperties(record, Tag.class));
    }
}
