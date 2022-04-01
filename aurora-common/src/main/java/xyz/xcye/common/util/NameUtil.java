package xyz.xcye.common.util;

/**
 * 命名工具类
 * @author qsyyke
 */


public class NameUtil {

    public static String getUnderlineName(String humpName) {
        return humpName.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
