package xyz.xcye.core.util;

import com.github.pagehelper.PageInfo;
import xyz.xcye.core.entity.PageData;

import java.util.List;

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
}
