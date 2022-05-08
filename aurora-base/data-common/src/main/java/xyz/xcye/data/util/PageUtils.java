package xyz.xcye.data.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import java.util.List;
import java.util.function.Consumer;

/**
 * 这是和分页相关的工具类
 * @author qsyyke
 * @date Created in 2022/5/3 09:09
 */


public class PageUtils {
    public static <T> PageData<T> pageList(List<T> queriedResult) {
        PageInfo<T> pageInfo = new PageInfo<>(queriedResult);
        PageData<T> pageData = new PageData<>();
        pageData.setPages(pageInfo.getPages());
        pageData.setResult(queriedResult);
        pageData.setTotal(pageInfo.getTotal());
        return pageData;
    }

    public static <T,I> PageData<T> pageList(Condition<I> condition, Consumer<Condition<I>> consumer) {
        Page<T> page = PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy()).doSelectPage(() -> {
            consumer.accept(condition);
        });
        PageData<T> pageData = new PageData<>();
        pageData.setResult(page.getResult());
        pageData.setTotal(page.getTotal());
        pageData.setPages(page.getPages());
        return pageData;
    }
}
