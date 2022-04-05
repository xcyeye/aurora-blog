package xyz.xcye.message.enums;

/**
 * 将html模板中的指定内容替换成传入的内容 这个枚举中就放置所涉及的"html中的指定内容"
 * <p>如存在一个模板 <span>这是模板{{content}}</span>，这里放置的就是"{{content}}部分，我们可以直接将{{content}}部分
 * 替换成对应内容"</p>
 * @author qsyyke
 */


public class EmailTemplateReplaceRegex {
    //=================================发布普通通知
    /** 通知的内容 **/
    public static final String NOTICE_CONTENT = "\\{\\{content}}";
    /** 通知的时间 **/
    public static final String NOTICE_TIME = "\\{\\{time}}";
    /** 此通知对应的地址 **/
    public static final String NOTICE_PATH = "\\{\\{noticePath}}";
    /** 发布此通知的用户 **/
    public static final String PUBLISH_NOTICE_USERNAME = "\\{\\{username}}";

    //=================================回复评论和有评论通知 （因为用户回复某条评论和有评论通知都差不多，所以他们基本上都使用同一个）
    /** 正在回复评论的用户名 **/
    public static final String REPLYING_COMMENT_USERNAME = "\\{\\{replyingCommentUsername}}";
    /** 正在回复评论的内容 **/
    public static final String REPLYING_COMMENT_CONTENT = "\\{\\{replyingCommentContent}}";
    /** 回复评论的时间 **/
    public static final String REPLYING_COMMENT_TIME = "\\{\\{replyingCommentTime}}";
    /** 正在回复评论的用户的站点 **/
    public static final String REPLYING_COMMENT_USER_SITE = "\\{\\{replyingCommentUserSite}}";

    /** 被回复评论的用户名 **/
    public static final String REPLIED_COMMENT_USERNAME = "\\{\\{repliedCommentUsername}}";
    /** 被回复的评论内容 **/
    public static final String REPLIED_COMMENT_CONTENT = "\\{\\{repliedCommentContent}}";
    /** 被回复的评论的创建时间 **/
    public static final String REPLIED_COMMENT_CREATED_AT = "\\{\\{repliedCommentCreatedAt}}";
    /** 被回复评论的评论发布者的站点 **/
    public static final String REPLIED_COMMENT_USER_SITE = "\\{\\{repliedCommentUserSite}}";
    /** 在哪个地址发布的评论 **/
    public static final String COMMENT_PATH = "\\{\\{commentPath}}";

    //=================================发布普通通知

    //=================================验证账户信息
    /** 验证账户的地址 **/
    public static final String VERIFY_ACCOUNT_URL = "\\{\\{verifyAccountUrl}}";
    /** 此链接的过期时间 **/
    public static final String VERIFY_ACCOUNT_EXPIRATION_TIME = "\\{\\{verifyUrlExpirationTime}}";
}
