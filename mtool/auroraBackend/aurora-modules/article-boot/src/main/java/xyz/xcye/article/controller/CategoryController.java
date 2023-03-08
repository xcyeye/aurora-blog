package xyz.xcye.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.article.po.Category;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.service.CategoryService;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 20:38
 */

@Tag(name = "操作文章类别的控制类")
@RestController
@RefreshScope
@RequestMapping("/blog/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "插入类别信息")
    @ModifyOperation
    @PostMapping("/insertCategory")
    public void insertCategory(@Validated({Insert.class, Default.class}) @RequestBody CategoryPojo category) {
        categoryService.insertCategory(category);
    }

    @Operation(summary = "逻辑删除类别")
    @ModifyOperation
    @PostMapping("/logicDeleteCategory")
    public int logicDeleteCategory(@RequestBody CategoryPojo category) {
        return categoryService.logicDeleteCategory(category.getUid());
    }

    @Operation(summary = "物理删除类别信息")
    @ModifyOperation
    @PostMapping("/physicalDeleteCategory")
    public int physicalDeleteCategory(@RequestBody CategoryPojo category) {
        return categoryService.physicalDeleteCategory(category.getUid());
    }

    @Operation(summary = "根据条件查询类别信息")
    @SelectOperation
    @PostMapping("/queryListCategoryByCondition")
    public PageData<CategoryVO> queryListCategoryByCondition(@RequestBody Condition<Long> condition) {
        return categoryService.queryListCategoryByCondition(condition);
    }

    @Operation(summary = "根据uid查询类别信息")
    @SelectOperation
    @PostMapping("/queryCategoryByUid")
    public CategoryVO queryCategoryByUid(@RequestBody CategoryPojo category) {
        return categoryService.queryCategoryByUid(category.getUid());
    }

    @Operation(summary = "修改类别信息")
    @ModifyOperation
    @PostMapping("/updateCategory")
    public int updateCategory(@Validated({Update.class, Default.class}) @RequestBody CategoryPojo category) {
        return categoryService.updateCategory(category);
    }
}
