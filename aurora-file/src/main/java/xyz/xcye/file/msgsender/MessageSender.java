package xyz.xcye.file.msgsender;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.common.util.id.GenerateInfoUtil;
import org.springframework.integration.support.MessageBuilder;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author qsyyke
 */


@EnableBinding(value = Source.class)
public class MessageSender {

    @Autowired
    private MessageChannel output;

    public String send() {
        long l = GenerateInfoUtil.generateUid(1, 23);
        File file = new File();
        file.setSummary("sdfkljsdf");
        file.setFileName("文件的名称");
        file.setCreatedAt(DateUtil.format(new Date()));
        file.setUid(new BigInteger(GenerateInfoUtil.generateUid(1,2) + ""));
        boolean send1 = output.send(MessageBuilder.withPayload(file).build());
        //boolean send = output.send(MessageBuilder.withPayload(DateUtil.format(new Date()) + "随机生成的uid: " + l).build());
        return "是否发送成功" + send1;
    }


}
