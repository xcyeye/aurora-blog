package xyz.xcye.common.sensitive.nanotation;

import java.util.List;

/**
 * 检查信息是否存在敏感字符接口
 * <p>两个方法，1.传入字符是否有敏感信息，2.有敏感信息的话，将返回转换后的字符</p>
 * @author qsyyke
 */

public interface InfoSensitive {

    /**
     * 判断指定字符是否存在敏感信息
     * @return true存在，false不存在
     */
    boolean isSensitive();

    /**
     * 如果存在对应的敏感字符，则调用此方法，返回没有敏感字符的信息
     * @return 将敏感字符替换成指定字符后的字符串
     */
    String convert();
}
