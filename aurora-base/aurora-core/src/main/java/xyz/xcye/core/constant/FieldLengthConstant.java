package xyz.xcye.core.constant;

/**
 * 定义数据库中字段的长度
 * @author qsyyke
 */

public class FieldLengthConstant {
    //-----------------------------------网站导航相关长度
    /** 导航栏的标题 **/
    public static final int NAVIGATION_TITLE = 12;
    /** css类名长度 **/
    public static final int CLASS_NAME = 15;


    //-----------------------------------邮件相关
    /** 邮件主机 **/
    public static final int EMAIL_HOST = 30;
    /** 邮件协议 **/
    public static final int EMAIL_PROTOCOL = 15;
    /** 邮件标题 **/
    public static final int EMAIL_SUBJECT = 50;
    /** 邮箱号 **/
    public static final int EMAIL_NUMBER = 254;
    /** 邮件模板 **/
    public static final int EMAIL_TEMPLATE = 1000000;
    /** 邮件授权码 **/
    public static final int EMAIL_PASSWORD = 60;
    /** 替换模板中的特殊字符的集合疮毒 **/
    public static final int EMAIL_MAX_REPLACE = 100000;
    /** 邮件模板的类型 **/
    public static final int EMAIL_TEMPLATE_TYPE = 25;


    //-----------------------------------用户信息相关
    /** 用户名 **/
    public static final int USERNAME = 15;
    /** 用户昵称 **/
    public static final int NICKNAME = 15;
    /** 性别 **/
    public static final int GENDER = 10;
    /** 登录密码 **/
    public static final int PASSWORD = 100;
    /** 专业 **/
    public static final int PROFESSION = 100;

    /** 用户权限长度 **/
    public static final int USER_PERMISSION = 50;

    public static final int USER_ROLE = 15;


    //-----------------------------------文章相关
    /** 集合转字符串存储 **/
    public static final int STRING_ARRAY = 500;
    /** 标题 **/
    public static final int TITLE = 100;
    /** 内容 **/
    public static final int CONTENT = 1000000;
    /** 简介 **/
    public static final int SUMMARY = 500;
    /** url地址 **/
    public static final int URL = 255;


    //-----------------------------------社交信息相关
    /** 社交名称 **/
    public static final int SOCIAL_NAME = 10;


    //-----------------------------------网站信息相关
    /** 站点关键词 **/
    public static final int KEYWORD = 200;
    /** 站点描述 **/
    public static final int DESCRIPTION = 200;


    //-----------------------------------其他
    /** ip地址长度 **/
    public static final int IP = 12;
    /** 记录操作系统长度 **/
    public static final int OPERATION_INFO = 255;
    public static final int LOGIN_LOCATION = 200;


    //-----------------------------------友情链接相关
    /** 友情链接标题 **/
    public static final int LINK_TITLE = 50;
    /** 友情链接描述 **/
    public static final int LINK_DESCRIPTION = 150;


    //-----------------------------------时间相关
    public static final int TIME_FORMAT = 19;


    //-----------------------------------文件相关
    public static final int FILE_NAME = 50;
    public static final int FILE_PATH = 255;


    //-----------------------------------时间相关
    /** token默认失效时间，毫秒 默认为30分钟 **/
    public static final long TOKEN_EXPIRATION_Time = 1800000L;
}
