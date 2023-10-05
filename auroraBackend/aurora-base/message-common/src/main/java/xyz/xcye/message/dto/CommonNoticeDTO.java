package xyz.xcye.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这是一个发送普通通知的邮件dto
 *
 * @author qsyyke
 * @date Created in 2022/5/19 09:11
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonNoticeDTO {

    private String sendMessage;
}
