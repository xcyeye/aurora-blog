package xyz.xcye.message.enums;

import lombok.Getter;

/**
 * 和邮件发送模板相关的常量
 * @author qsyyke
 */

@Getter
public enum MailTemplateEnum {
    /** 类环境中 **/
    COMMON_NOTICE("notice.html","mailTemplate","你有一条普通通知"),
    RECEIVE_COMMENT("receiveComment.html","mailTemplate","你收到新评论了"),
    REPLY_COMMENT("replyComment.html","mailTemplate","你的评论有新回复了"),
    VERIFY_ACCOUNT("verifyAccount.html","mailTemplate","请验证你的账户"),
    DEFAULT("","",""),
    APPLICATION_MAIN_CONFIG("application.yml","","");

    /**
     * 模板的名字
     */
    private String templateName;

    /**
     * 类环境下，存放模板的文件夹路径
     */
    private String templateFolderPath;

    /**
     * 邮件发送的标题
     */
    private String subject;

    MailTemplateEnum(String templateName, String templateFolderPath, String subject) {
        this.templateName = templateName;
        this.templateFolderPath = templateFolderPath;
        this.subject = subject;
    }

    public static class DefaultValueConstant {
        /**
         * 普通通知的默认通知地址
         **/
        public static final String COMMON_NOTICE_PATH = "https://aurora.xcye.xyz";
    }
}

