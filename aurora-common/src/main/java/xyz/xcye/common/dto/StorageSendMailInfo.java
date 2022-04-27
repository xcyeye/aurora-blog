package xyz.xcye.common.dto;

import lombok.Data;
import xyz.xcye.common.enums.SendHtmlMailKeyNameEnum;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 存储发送的邮件信息dto
 * @author qsyyke
 * @date Created in 2022/4/27 15:55
 */

@Data
public class StorageSendMailInfo {
    /**
     * 存放替换邮件模板中的key和value值，其中key不需要加上{{}},[[]]这些
     */
    private Map<String,String> replacedMap;

    /**
     * 额外的数据，比如回复评论，需要组装一个被回复的评论对象
     */
    private Map<String,Object> additionalData;

    /**
     * 使用哪个用户的模板，如果此模板不存在，则使用默认的
     */
    @NotNull
    private Long userUid;

    /**
     * 发送的标题
     */
    private String subject;

    /**
     * 收件人的邮箱号
     */
    private String receiverEmail;

    private SendHtmlMailKeyNameEnum sendType;

    /**
     * mq发送消息的唯一id
     */
    private String correlationDataId;

    /**
     * 如果发送的是简单文本，则需要对此属性赋值
     */
    private String simpleText;
}
