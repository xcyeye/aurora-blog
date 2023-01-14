package xyz.xcye.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qsyyke
 */

@Slf4j
public class BeanUtils {
    public static <T,S> List<T> copyList(List<S> sourceList, Class<T> target) {
        if (sourceList.isEmpty() || target == null) {
            return new ArrayList<>();
        }
        List<T> copyBeanList = new ArrayList<>();
        sourceList.forEach(source -> {
            T t = null;
            try {
                t = target.newInstance();
                org.springframework.beans.BeanUtils.copyProperties(source,t);
                copyBeanList.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("拷贝bean出现异常{}",e.getMessage(),e);
            }
        });
        return copyBeanList;
    }

    public static <T> T copyProperties(Object source,Class<T> target) {
        if (source == null) {
            return null;
        }
        T t = null;
        try {
            t = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source,t);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("拷贝bean出现异常{}",e.getMessage(),e);
        }
        return t;
    }

    public static <T,S> T getSingleObjFromList(List<S> objList, Class<T> target) {
        if (objList.isEmpty()) {
            return null;
        }

        // 返回第一个
        S s = objList.get(0);
        return BeanUtils.copyProperties(s, target);
    }
}
