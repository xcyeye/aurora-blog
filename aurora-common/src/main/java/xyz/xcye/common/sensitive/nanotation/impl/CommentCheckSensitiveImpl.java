package xyz.xcye.common.sensitive.nanotation.impl;

import java.util.List;

/**
 * @author qsyyke
 */

public class CommentCheckSensitiveImpl extends AbstractInfoSensitiveImpl {

    public CommentCheckSensitiveImpl() {
    }

    public CommentCheckSensitiveImpl(String info, List<String> sensitiveList) {
        super(info, sensitiveList);
    }

    public CommentCheckSensitiveImpl(String info, List<String> sensitiveList, String convertSensitiveStr) {
        super(info, sensitiveList, convertSensitiveStr);
    }
}
