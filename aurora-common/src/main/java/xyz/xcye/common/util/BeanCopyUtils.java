package xyz.xcye.common.util;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qsyyke
 */


public class BeanCopyUtils {
    public static <T,S> List<T> copyList(List<S> sourceList,Class<T> target) throws InstantiationException, IllegalAccessException {
        if (sourceList.isEmpty() || target == null) {
            return new ArrayList<>();
        }
        PageInfo<S> pageInfo = new PageInfo<>(sourceList);
        List<S> sourcePageInfoList = pageInfo.getList();
        List<T> copyBeanList = new ArrayList<>();
        for (S sourceBean : sourcePageInfoList) {
            T t = target.newInstance();
            BeanUtils.copyProperties(sourceBean,t);
            copyBeanList.add(t);
        }
        return copyBeanList;
    }

    public static <T> T copyProperties(Object source,Class<T> target) throws InstantiationException, IllegalAccessException {
        if (source == null) {
            return null;
        }
        T t = target.newInstance();
        BeanUtils.copyProperties(source,t);
        return t;
    }
}
