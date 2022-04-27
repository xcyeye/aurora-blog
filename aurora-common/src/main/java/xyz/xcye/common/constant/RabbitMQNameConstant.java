package xyz.xcye.common.constant;

import lombok.Getter;

/**
 * 这是和rabbitmq相关的枚举
 * @author qsyyke
 */

@Getter
public class RabbitMQNameConstant {

    /** 发送html邮件的队列及routingKey **/
    public static final String SEND_HTML_MAIL_QUEUE_NAME = "send.html.mail.queue";
    public static final String SEND_HTML_MAIL_ROUTING_KEY = "send.html.amil.routing";

    /** 发送简单文本邮件的队列及routingKey **/
    public static final String SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME = "send.simple.text.mail.queue";
    public static final String SEND_SIMPLE_TEXT_MAIL_ROUTING_KEY = "send.simple.text.mail.routing";

    /** 发送html邮件的死信队列及routingKey **/
    public static final String SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME = "send.html.mail.dead.letter.queue";
    public static final String SEND_HTML_MAIL_DEAD_LETTER_ROUTING_KEY = "send.html.mail.dead.letter.routing";

    /** 发送简单文本邮件的死信队列及routingKey **/
    public static final String SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME = "send.simple.text.mail.dead.letter.queue";
    public static final String SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_ROUTING_KEY = "send.simple.text.mail.dead.letter.routing";


    /** 生产者生产的消息不合法的队列 **/
    public static final String MISTAKE_MESSAGE_QUEUE = "aurora.mistake.queue";
    /** 生产者生产的消息不合法的routingKey **/
    public static final String MISTAKE_MESSAGE_ROUTING_KEY = "aurora.mistake.routing.key";

    /** 绑定邮箱的队列 **/
    public static final String OPERATE_USER_BINDING_EMAIL_QUEUE = "aurora.operate.user.binding.email.queue";
    /** 绑定邮箱的路由key **/
    public static final String OPERATE_USER_BINDING_EMAIL_ROUTING_KEY = "aurora.operate.user.binding.email.routing";

    /** 绑定邮箱的死信队列 **/
    public static final String DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_QUEUE = "aurora.operate.user.binding.email.dead.letter.queue";
    /** 绑定邮箱的死信队列路由key **/
    public static final String DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_ROUTING_KEY = "aurora.operate.user.binding.email.dead.letter.routing";


    // =============> 发送邮件的交换机
    /** 发送邮件的普通交换机名称 **/
    public static final String AURORA_SEND_MAIL_EXCHANGE = "aurora.send.email.common.exchange";
    /** 发送邮件的死信交换机名称 **/
    public static final String AURORA_SEND_MAIL_DEAD_LETTER_EXCHANGE = "aurora.send.email.dead.letter.exchange";


    // =============> 发送错误消息的交换机
    /** 如果生产者生产的消息不合法，那么都会被该交换机接收 **/
    public static final String MISTAKE_MESSAGE_EXCHANGE = "aurora.send.mistake.exchange";


    // =============> 发送操作用户的交换机
    /** 发送操作邮件的交换机 **/
    public static final String AURORA_SEND_OPERATE_USER_EXCHANGE = "aurora.send.operate.user.exchange";
    /** 发送操作邮件的死信交换机 **/
    public static final String AURORA_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE = "aurora.send.operate.user.dead.letter.exchange";
}
