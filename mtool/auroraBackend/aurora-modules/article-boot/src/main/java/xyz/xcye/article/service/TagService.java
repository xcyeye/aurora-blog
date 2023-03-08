package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.po.Tag;
import xyz.xcye.article.pojo.TagPojo;
import xyz.xcye.article.vo.TagVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.article.ArticleException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Objects;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:32
 */

@Service
public class TagService {

    @Autowired
    private AuroraTagService auroraTagService;

    public int logicDeleteTag(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Tag tag = Tag.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraTagService.updateById(tag);
    }

    public int physicalDeleteTag(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraTagService.deleteById(uid);
    }

    public void insertTag(TagPojo pojo) {
        Assert.notNull(pojo, "标签信息不能为null");
        AssertUtils.stateThrow(pojo.getUserUid() != null, () -> new ArticleException("userUid不能为null"));
        judgeTag(pojo, true);
        Tag record = BeanUtils.copyProperties(pojo, Tag.class);
        // JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        // AssertUtils.stateThrow(jwtUserInfo != null,
        //         () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(pojo.getUserUid());
        auroraTagService.insert(record);
    }

    public PageData<TagVO> queryListTagByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraTagService.queryListByCondition(condition), TagVO.class);
    }

    public TagVO queryTagByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraTagService.queryById(uid), TagVO.class);
    }

    public TagVO queryOneTag(TagPojo pojo) {
        return BeanUtils.copyProperties(auroraTagService.queryOne(BeanUtils.copyProperties(pojo, Tag.class)), TagVO.class);
    }

    public int updateTag(TagPojo record) {
        Assert.notNull(record, "标签信息不能为null");
        judgeTag(record, false);
        record.setUserUid(null);
        return auroraTagService.updateById(BeanUtils.copyProperties(record, Tag.class));
    }

    private void judgeTag(TagPojo pojo, boolean isInsert) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        Tag tag = auroraTagService.queryOne(new Tag() {{
            setTitle(pojo.getTitle());
            setUserUid(currentUserUid);
        }});
        if (isInsert) {
            AssertUtils.stateThrow(tag == null, () -> new ArticleException("存在相同的标签"));
        }
        if (tag != null && !Objects.equals(tag.getUid(), pojo.getUid())) {
            throw new ArticleException("存在相同的标签");
        }
    }
}
