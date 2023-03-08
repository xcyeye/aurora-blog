package xyz.xcye.core.enums;

/**
 * 常见的格式枚举，包括时间格式等
 * @author qsyyke
 */

public enum CommonFormatEnum {
    /**
     * 全局日期格式，如果在其他地方没有自定义日期格式，那么博客所有与时间相关的日期字符串都将是这个
     * 没有特殊要求，在将日期转换成字符串的时候，就不要转换成其他格式了，方便后续操作
     */
    DATE_FORMAT("yyyy-MM-dd HH:mm:ss");

    private String format;

    private CommonFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
