package xyz.xcye.core.util;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qsyyke
 */


public class BeanUtils {
    public static <T,S> List<T> copyList(List<S> sourceList,Class<T> target)
            throws ReflectiveOperationException {
        if (sourceList.isEmpty() || target == null) {
            return new ArrayList<>();
        }
        PageInfo<S> pageInfo = new PageInfo<>(sourceList);
        List<S> sourcePageInfoList = pageInfo.getList();
        List<T> copyBeanList = new ArrayList<>();
        for (S sourceBean : sourcePageInfoList) {
            T t = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(sourceBean,t);
            copyBeanList.add(t);
        }
        return copyBeanList;
    }

    public static <T> T copyProperties(Object source,Class<T> target)
            throws ReflectiveOperationException {
        if (source == null) {
            return null;
        }
        T t = target.newInstance();
        org.springframework.beans.BeanUtils.copyProperties(source,t);
        return t;
    }

    public static <T,S> T getSingleObjFromList(List<S> objList, Class<T> target)
            throws ReflectiveOperationException {
        if (objList.isEmpty()) {
            return null;
        }

        // 返回第一个
        S s = objList.get(0);
        return BeanUtils.copyProperties(s,target);
    }
}
