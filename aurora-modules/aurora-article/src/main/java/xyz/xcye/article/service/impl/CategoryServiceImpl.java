package xyz.xcye.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.article.dao.CategoryMapper;
import xyz.xcye.article.po.Category;
import xyz.xcye.article.service.CategoryService;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:23
 */


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserUtils userUtils;

    @Override
    public int deleteByPrimaryKey(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Category category = Category.builder()
                .uid(uid)
                .delete(true)
                .updateTime(DateUtils.format())
                .build();
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int physicsDeleteByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return categoryMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(Category record) {
        Assert.notNull(record, "类别不能为null");
        record.setDelete(false);
        JwtUserInfo jwtUserInfo = userUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        return categoryMapper.insertSelective(record);
    }

    @Override
    public PageData<CategoryVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition,"查询条件不能为null");
        return PageUtils.pageList(condition, t -> categoryMapper.selectByCondition(condition), CategoryVO.class);
    }

    @Override
    public CategoryVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.getSingleObjFromList(categoryMapper.selectByCondition(Condition.instant(uid, true)), CategoryVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Category record) {
        Assert.notNull(record, "类别不能为null");
        record.setUpdateTime(DateUtils.format());
        return categoryMapper.updateByPrimaryKeySelective(record);
    }
}
