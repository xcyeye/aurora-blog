package xyz.xcye.core.util;

/**
 * 命名工具类
 * @author qsyyke
 */


public class NameUtils {

    public static String getUnderlineName(String humpName) {
        return humpName.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
