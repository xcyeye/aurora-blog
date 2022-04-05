package xyz.xcye.message.util;

import xyz.xcye.common.entity.email.EmailCommonNotice;
import xyz.xcye.common.entity.email.EmailVerifyAccount;
import xyz.xcye.common.entity.table.Comment;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.message.enums.EmailTemplateReplaceRegex;

import java.util.Date;

/**
 * 这是一个解析邮件模板的类
 * @author qsyyke
 */


public class ParseEmailTemplate {

    /**
     * 根据数据库中某个用户的邮件普通通知模板，把特定内容替换成emailCommonNotice中所存放的内容
     * @param emailCommonNotice
     * @param senderEmailInfo
     * @return 解析之后的邮件发送内容，包含html，content
     */
    public static String sendCommonNoticeMail(EmailCommonNotice emailCommonNotice, Email senderEmailInfo) {
        //获取通知html模板
        String noticeTemplate = senderEmailInfo.getNoticeTemplate();

        //使用正则表达式的方式进行替换模板中的内容
        //替换通知内容
        String content = noticeTemplate.replaceAll(EmailTemplateReplaceRegex.NOTICE_CONTENT,emailCommonNotice.getNoticeContent());
        //替换通知时间
        content = content.replaceAll(EmailTemplateReplaceRegex.NOTICE_TIME,emailCommonNotice.getNoticeTime());
        //替换发布者用户名
        content = content.replaceAll(EmailTemplateReplaceRegex.PUBLISH_NOTICE_USERNAME,emailCommonNotice.getPublishNoticeUsername());
        //替换此通知对应的地址
        content = content.replaceAll(EmailTemplateReplaceRegex.NOTICE_PATH,emailCommonNotice.getNoticePath());

        return content;
    };

    /**
     * 如果有用户回复某条评论，则会调用该解析方法进行解析，模板存放与数据库中
     * @param replyingCommentInfo
     * @param repliedCommentInfo
     * @param senderEmailInfo
     * @return 邮件发送的content，包含html，content
     */
    public static String sendReplyCommentMail(Comment replyingCommentInfo, Comment repliedCommentInfo, Email senderEmailInfo) {
        //回复评论的模板
        String replyCommentTemplate = senderEmailInfo.getReplyCommentTemplate();

        //进行内容替换 先设置被回复的评论信息

        //被回复的评论内容
        String content = replyCommentTemplate.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_CONTENT,repliedCommentInfo.getContent());
        //被回复的评论的发布者
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_USERNAME,repliedCommentInfo.getUsername());
        //被回复的评论的发布时间
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_CREATED_AT,repliedCommentInfo.getCreatedAt());
        //被回复的评论的用户站点地址
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_USER_SITE,repliedCommentInfo.getSite());

        //设置回复者的信息
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLYING_COMMENT_CONTENT,replyingCommentInfo.getContent());
        //回复者的用户名
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLYING_COMMENT_USERNAME,replyingCommentInfo.getUsername());
        //回复者的站点
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLYING_COMMENT_USER_SITE,replyingCommentInfo.getSite());
        //时间
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLYING_COMMENT_TIME,replyingCommentInfo.getCreatedAt());
        //替换此评论所对应的页面地址
        content = content.replaceAll(EmailTemplateReplaceRegex.COMMENT_PATH,repliedCommentInfo.getPath());
        return content;
    };

    /**
     * 如果有用户发布了评论，那么就使用该方法进行模板内容的替换
     * @param receiveCommentInfo
     * @param senderEmailInfo
     * @return 邮件发送的内容，包含html，content
     */
    public static String sendReceiveCommentMail(Comment receiveCommentInfo,Email senderEmailInfo) {
        //回复评论的模板
        String receiveCommentTemplate = senderEmailInfo.getReceiveCommentTemplate();

        //被回复的评论内容
        String content = receiveCommentTemplate.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_CONTENT,receiveCommentInfo.getContent());
        //被回复的评论的发布者
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_USERNAME,receiveCommentInfo.getUsername());
        //被回复的评论的发布时间
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_CREATED_AT,receiveCommentInfo.getCreatedAt());
        //被回复的评论的用户站点地址
        content = content.replaceAll(EmailTemplateReplaceRegex.REPLIED_COMMENT_USER_SITE,receiveCommentInfo.getSite());
        //替换此评论所对应的页面地址
        content = content.replaceAll(EmailTemplateReplaceRegex.COMMENT_PATH,receiveCommentInfo.getPath());
        return content;
    };

    /**
     * 邮件验证url的模板解析
     * @param verifyAccount
     * @param senderEmailInfo
     * @return 邮件发送的content，html
     */
    public static String sendVerifyAccountMail(EmailVerifyAccount verifyAccount, Email senderEmailInfo) {
        String verifyAccountTemplate = senderEmailInfo.getVerifyAccountTemplate();

        //替换内容
        String content = verifyAccountTemplate.replaceAll(EmailTemplateReplaceRegex.VERIFY_ACCOUNT_URL,verifyAccount.getVerifyAccountUrl());
        content = content.replaceAll(EmailTemplateReplaceRegex.VERIFY_ACCOUNT_EXPIRATION_TIME,verifyAccount.getExpirationTime());
        return content;
    };
}

