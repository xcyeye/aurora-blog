package xyz.xcye.admin.manager.mq.consumer;

import com.rabbitmq.client.Channel;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.amqp.config.service.MistakeMessageSendService;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.AccountInfoUtils;
import xyz.xcye.api.mail.sendmail.util.StorageEmailVerifyUrlUtil;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.feign.config.service.MessageLogFeignService;

/**
 * @author qsyyke
 * @date Created in 2022/5/17 16:30
 */


@Component
public class OperateUserConsumer {

    private final String enableAccountKey = "enableAccount";

    @Autowired
    private MessageLogFeignService messageLogFeignService;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuroraProperties.AuroraAccountProperties auroraAccountProperties;
    @Autowired
    private SendMQMessageService sendMQMessageService;

    @GlobalTransactional
    @RabbitListener(queues = AmqpQueueNameConstant.OPERATE_USER_LOCK_ACCOUNT_QUEUE, ackMode = "MANUAL")
    public void lockAccountConsumer(String msgJson, Channel channel, Message message) throws Exception {
        // 待替换的key和value的map
        if (msgJson == null) {
            // 错误消息
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson, channel, message);
        }

        // 根据用户名查询用户信息
        UserVO userVO = userService.queryByUsername(msgJson);
        if (userVO == null || !userVO.getVerifyEmail()) {
            // 不做处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 如果账户已经被锁住，则不处理
        if (userVO.getAccountLock()) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 用户存在 修改锁住状态
        User user = User.builder()
                .uid(userVO.getUid())
                .accountLock(true)
                .build();
        int updateUserNum = userService.updateUser(user);
        if (updateUserNum == 1) {
            boolean sendStatus = sendEnableAccountEmail(userVO);
            AssertUtils.stateThrow(sendStatus,
                    () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_RECEIVE_SEND_FAILURE));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 消费失败
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }

    /**
     * 发送一封邮件到该用户所对应的邮箱中，用户点击发送的链接进行解除账户锁住状态
     * @param userVO
     * @return
     */
    private boolean sendEnableAccountEmail(UserVO userVO) throws BindException {
        String verifyUrl = AccountInfoUtils.generateVerifyUrl(userVO.getUid(),
                enableAccountKey, userVO.hashCode(), auroraAccountProperties.getEnableAccountPrefixPath());

        // 将验证的信息存入redis
        boolean storageVerifyUrlToRedis = StorageEmailVerifyUrlUtil.storageVerifyUrlToRedis(enableAccountKey, userVO.hashCode(),
                auroraAccountProperties.getMailVerifyAccountExpirationTime(), userVO.getUid());
        // 如果存储成功，则发送邮件
        if (!storageVerifyUrlToRedis) {
            return false;
        }

        StorageSendMailInfo mailInfo = StorageSendMailInfo.builder()
                .userUid(userVO.getUid())
                .subject("点击此链接，解除账号锁定状态")
                .htmlContent("<a>请点击此链接，重新启动账号" + verifyUrl + "</a>")
                .sendType(SendHtmlMailTypeNameEnum.CUSTOM_HTML)
                .build();
        sendMQMessageService.sendCommonMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, null);
        return true;
    }

}
