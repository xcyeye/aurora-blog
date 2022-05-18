package xyz.xcye.core.enums;

/**
 * @author qsyyke
 */

public enum ResponseStatusCodeEnum {

    //----------------------成功相关的响应码
    SUCCESS(21000,"成功"),
    FAILURE(24000,"操作失败"),
    COMMON_RECORD_NOT_EXISTS(22000,"记录不存在"),
    EXCEPTION_NULL_POINTER(223000,"空指针异常"),


    //----------------------参数相关的响应码
    PARAM_TYPE_ERROR(31000,"参数类型错误"),
    PARAM_IS_INVALID(32000,"参数无效"),
    PARAM_IS_BLANK(33000,"参数为空"),
    PARAM_NOT_COMPLETE(34000,"参数缺失"),
    PARAM_COMMENT_NOT_SUPPORT_PAGE_TYPE(34567, "不支持的评论页面"),


    //----------------------异常相关的响应码
    //----------------------异常相关的响应码--------上传，更新，修改，删除，下载文件相关
    EXCEPTION_FILE_FAIL_UPLOAD(11110,"文件上传失败"),
    EXCEPTION_FILE_FAIL_UPDATE(11120,"修改文件失败"),
    EXCEPTION_FILE_FAIL_DELETE(11130,"删除文件失败"),
    EXCEPTION_FILE_FAIL_DOWNLOAD(11140,"下载文件失败"),
    EXCEPTION_FILE_FAIL_CREATE(11150,"未能在指定路径下创建新文件或者文件夹"),
    EXCEPTION_FILE_FAIL_HAD_DELETED(11160,"文件已被删除"),
    EXCEPTION_FILE_PERMISSION(11200,"没有权限操作文件"),
    EXCEPTION_FILE_NOT_FOUND(11300,"未发现该文件"),
    EXCEPTION_FILE_ALREADY_EXIST(11400,"已经存在该文件"),
    EXCEPTION_FILE_FAIL_PROPERTY(11500,"获取文件属性失败"),
    EXCEPTION_FILE_EXCEED_MAX_SIZE(11600,"上传文件超过最大上传大小"),


    //----------------------异常相关的响应码--------未知异常
    EXCEPTION_UNKNOWN(12000,"未知异常"),


    //----------------------异常相关的响应码--------邮件相关异常
    EXCEPTION_EMAIL_SEND_PASSWORD_MISTAKE(13110,"发件者邮箱密码错误"),
    EXCEPTION_EMAIL_SEND_HOST_MISTAKE(13120,"发件者邮箱主机错误"),
    EXCEPTION_EMAIL_SEND_PROTOCOL_MISTAKE(13130,"发件者邮箱协议错误"),
    EXCEPTION_EMAIL_RECEIVE_SEND_FAILURE(13210,"邮件发送失败"),
    EXCEPTION_EMAIL_SEND_CONTENT_TO_LONG(13220,"发送的邮件内容太长"),
    EXCEPTION_EMAIL_EXISTS(13310,"邮箱已存在"),
    EXCEPTION_EMAIL_MISSING_RECEIVER(13310,"缺失收件人邮箱"),
    EXCEPTION_EMAIL_NOT_EXISTS(13310,"邮箱未存在"),
    EXCEPTION_EMAIL_FAIL_BINDING(13410,"邮箱绑定失败"),
    EXCEPTION_EMAIL_HAD_BINDING(13510,"邮箱已经绑定"),
    EXCEPTION_EMAIL_MISTAKE(13510,"传入的不是一个邮箱号"),


    //----------------------异常相关的响应码--------超时异常
    EXCEPTION_TIMEOUT(14000,"超时啦♪(^∇^*)"),


    //----------------------异常相关的响应码--------转换异常
    EXCEPTION_CONVERT_JSON_FAILURE(15100,"json转换失败了"),


    //----------------------权限相关的响应码
    PERMISSION_DENIED(41000,"用户权限不足"),
    PERMISSION_UNAUTHORIZED(42000,"用户未认证"),
    PERMISSION_AUTHORIZED_FAIL(42100,"认证失败"),
    PERMISSION_TOKEN_CREATE_FAILURE(43100,"token创建失败"),
    PERMISSION_TOKEN_EXPIRATION(43200,"token过期或者无效"),
    PERMISSION_USER_NOT_LOGIN(44100,"未登录"),
    PERMISSION_USER_HAD_LOGIN(44100,"已登录，不能重复登录"),
    PERMISSION_USER_HAD_LOGOUT(44100,"已注销，不能重复操作"),
    PERMISSION_USER_MISTAKE(44200,"账号不存在或者密码错误"),
    PERMISSION_USER_IS_DISABLE(44300,"账户已被禁用"),
    PERMISSION_USER_NOT_EXIST(44400,"用户不存在"),
    PERMISSION_USER_NOT_DELETE(44400,"用户已被删除"),
    PERMISSION_USER_EXIST(44500,"用户已存在"),
    PERMISSION_USER_NOT_EMAIL_UNAUTHORIZED(44610,"邮箱未验证"),
    PERMISSION_USER_IS_LOCKED(44710,"用户已被锁住"),
    PERMISSION_USER_FAIL_ADD(44800,"添加用户失败"),
    PERMISSION_USER_FAIL_DELETE(44900,"删除用户失败"),
    PERMISSION_RESOURCE_NOT_RIGHT(44900,"资源路径不正确"),
    PERMISSION_ROLE_NOT_EXISTS(44900,"角色不存在"),
    PERMISSION_ROLE_HAD_EXISTS(44900,"角色已存在"),
    PERMISSION_ROLE_HAD_DISABLED(44900,"角色被禁用"),

    //----------------------未知错误相关的响应码
    OAUTH_CLIENT_SECRET_MISTAKE(444444, "client_id或者secret错误"),
    OAUTH_NOT_SUPPORT_AUTH_TYPE(444444, "不支持的认证方式"),


    //----------------------sentinel相关的响应码
    REQUEST_BUSY(23746, "请求太快了,请稍后在试┭┮﹏┭┮"),


    //----------------------未知错误相关的响应码
    UNKNOWN(50000,"未知错误"),
    SYSTEM_ERROR(50000,"系统错误"),

    //----------------------网关处的响应码
    SERVICE_INSTANCE_NOT_FOUND(53000, "服务未启动"),

    //----------------------和消息中间件相关的异常

    //----------------------数据相关的响应码
    SEATA_GLOBAL_TRANSACTIONAL_NOT_BINDING(60000,"seata全局事务未绑定");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应码描述
     */
    private final String message;

    ResponseStatusCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
