package xyz.xcye.message.constant;

/**
 * 将html模板中的指定内容替换成传入的内容 这个枚举中就放置所涉及的"html中的指定内容"
 * <p>如存在一个模板 <span>这是模板{{content}}</span>，这里放置的就是"{{content}}部分，我们可以直接将{{content}}部分
 * 替换成对应内容"</p>
 * @author qsyyke
 */


public class MailTemplateConstant {
    /** 替换模板中字符前缀，比如{{，但是为了操作方便，不能使用{{,[[ **/
    public static final String REPLACE_CHARACTER_PREFIX = ":au";

    /** 替换模板中字符前缀，比如}}，但是为了操作方便，不能使用}},}} **/
    public static final String REPLACE_CHARACTER_SUFFIX = "ua:";
    /** 存放邮件发送相关的数据的key值，用于从json中提取该对象 **/
    public static final String STORAGE_SEND_MAIL_INFO_KEY = "storageSendMailInfo";

}
