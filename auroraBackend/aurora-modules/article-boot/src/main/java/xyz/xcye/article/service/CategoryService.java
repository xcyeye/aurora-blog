package xyz.xcye.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.po.Category;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.vo.CategoryVO;
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
 * @date Created in 2022/5/11 19:23
 */


@Service
public class CategoryService {

    @Autowired
    private AuroraCategoryService auroraCategoryService;

    public int logicDeleteCategory(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Category category = Category.builder()
                .uid(uid)
                .delete(true)
                .build();
        return auroraCategoryService.updateById(category);
    }

    public int physicalDeleteCategory(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return auroraCategoryService.deleteById(uid);
    }

    public void insertCategory(CategoryPojo record) {
        Assert.notNull(record, "类别不能为null");
        record.setDelete(false);
        // JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        // AssertUtils.stateThrow(jwtUserInfo != null,
        //         () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        AssertUtils.stateThrow(record.getUserUid() != null, () -> new ArticleException("userUid不能为null"));
        judgeCategory(record, true);
        record.setUserUid(record.getUserUid());
        auroraCategoryService.insert(BeanUtils.copyProperties(record, Category.class));
    }

    public PageData<CategoryVO> queryListCategoryByCondition(Condition<Long> condition) {
        Assert.notNull(condition,"查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraCategoryService.queryListByCondition(condition), CategoryVO.class);
    }

    public CategoryVO queryCategoryByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraCategoryService.queryById(uid), CategoryVO.class);
    }

    public CategoryVO queryOneCategory(CategoryPojo pojo) {
        return BeanUtils.copyProperties(auroraCategoryService.queryOne(BeanUtils.copyProperties(pojo, Category.class)), CategoryVO.class);
    }

    public int updateCategory(CategoryPojo record) {
        Assert.notNull(record, "类别不能为null");
        record.setUserUid(null);
        judgeCategory(record, false);
        return auroraCategoryService.updateById(BeanUtils.copyProperties(record, Category.class));
    }

    private void judgeCategory(CategoryPojo pojo, boolean isInsert) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        CategoryPojo categoryPojo = new CategoryPojo();
        categoryPojo.setUserUid(currentUserUid);
        categoryPojo.setTitle(pojo.getTitle());
        Category category = auroraCategoryService.queryOne(BeanUtils.copyProperties(categoryPojo, Category.class));
        if (isInsert) {
            AssertUtils.stateThrow(category == null, () -> new ArticleException("存在相同的类别"));
        }
        if (category != null && !Objects.equals(category.getUid(), pojo.getUid())) {
            throw new ArticleException("存在相同的类别");
        }
    }
}
