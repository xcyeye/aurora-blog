package xyz.xcye.message.pojo;

import lombok.Data;

/**
 * @author xcye
 * @description
 * @date 2022-12-20 12:03:11
 */

@Data
public class SendMailPojo {

    private String receiverEmail;
    private String subject;
    private String content;
}
