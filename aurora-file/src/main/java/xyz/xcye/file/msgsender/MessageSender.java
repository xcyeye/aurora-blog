package xyz.xcye.file.msgsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import xyz.xcye.common.dos.FileDO;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import org.springframework.integration.support.MessageBuilder;

import java.util.Date;

/**
 * @author qsyyke
 */


@EnableBinding(value = Source.class)
public class MessageSender {

    @Autowired
    private MessageChannel output;

    public String send() {
        long l = GenerateInfoUtils.generateUid(1, 23);
        FileDO file = new FileDO();
        file.setSummary("sdfkljsdf");
        file.setFileName("文件的名称");
        file.setCreateTime(DateUtils.format(new Date()));
        file.setUid(GenerateInfoUtils.generateUid(1,2));
        boolean send1 = output.send(MessageBuilder.withPayload(file).build());
        //boolean send = output.send(MessageBuilder.withPayload(DateUtils.format(new Date()) + "随机生成的uid: " + l).build());
        return "是否发送成功" + send1;
    }


}
