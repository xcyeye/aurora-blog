package xyz.xcye.common.sensitive.nanotation.impl;

import java.util.List;

/**
 * @author qsyyke
 */


public class identityCheckSensitiveImpl extends AbstractInfoSensitiveImpl {
    public identityCheckSensitiveImpl() {
    }

    public identityCheckSensitiveImpl(String info, List<String> sensitiveList) {
        super(info, sensitiveList);
    }

    public identityCheckSensitiveImpl(String info, List<String> sensitiveList, String convertSensitiveStr) {
        super(info, sensitiveList, convertSensitiveStr);
    }
}
