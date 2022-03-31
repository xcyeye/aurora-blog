package xyz.xcye.common.sensitive.nanotation.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.common.sensitive.nanotation.InfoSensitive;

import java.util.List;

/**
 * @author qsyyke
 */

@NoArgsConstructor
@Data
public abstract class AbstractInfoSensitiveImpl implements InfoSensitive {
    /**
     * 默认的，替换敏感字符字段
     */
    public String convertSensitiveStr = "*";

    /**
     * 检查的信息
     */
    public String censorInfo;

    /**
     * 敏感信息集合
     */
    public List<String> sensitiveList;

    public AbstractInfoSensitiveImpl(String info, List<String> sensitiveList) {
        this.censorInfo = info;
        this.sensitiveList = sensitiveList;
    }

    public AbstractInfoSensitiveImpl(String info, List<String> sensitiveList,String convertSensitiveStr) {
        this.censorInfo = info;
        this.sensitiveList = sensitiveList;
        this.convertSensitiveStr = convertSensitiveStr;
    }

    /**
     * 待监测字符是否含有敏感字符
     * @return
     */
    @Override
    public boolean isSensitive() {
        boolean isSensitiveFlag = false;
        for (String sensitiveStr : sensitiveList) {
            //包含敏感字符
            if (censorInfo.contains(sensitiveStr)) {
                isSensitiveFlag = true;
                break;
            }
        }
        return isSensitiveFlag;
    }

    @Override
    public String convert() {
        if (!isSensitive()) {
            //不存在敏感信息，直接返回原字符
            return censorInfo;
        }

        String convertTempStr = censorInfo;

        //存在敏感信息
        for (String sensitiveStr : sensitiveList) {
            convertTempStr = convertTempStr.replaceAll(sensitiveStr,convertSensitiveStr);
        }
        return convertTempStr;
    }
}
