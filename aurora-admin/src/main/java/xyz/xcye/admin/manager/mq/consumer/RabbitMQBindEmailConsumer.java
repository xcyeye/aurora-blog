package xyz.xcye.admin.manager.mq.consumer;

import org.springframework.validation.BindException;

/**
 * 消费者
 * @author qsyyke
 */

/*@Slf4j
@Component
public class RabbitMQBindEmailConsumer {

    @Resource
    private MessageLogFeignService messageLogFeignService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRedisService userRedisService;
    @Autowired
    private VerifyAccountSendService verifyAccountSendService;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;

    @Value("${aurora.admin.verify.account.expiration-time}")
    private int emailVerifyAccountExpirationTime;

    @Value("${aurora.admin.verify.account.email-prefix-path}")
    private String emailVerifyAccountPrefixPath;

    @RabbitListener(queues = RabbitMQNameConstant.OPERATE_USER_BINDING_EMAIL_QUEUE,ackMode = "MANUAL")
    public void bindingEmailConsumer(String msgJson, Channel channel, Message message) throws MessagingException, BindException, IOException, UserException {
        log.info("绑定邮箱mq消费者执行，接收到的消息:{}",msgJson);
        // 获取唯一id
        String correlationDataId = null;
        EmailDO emailDO = null;
        //String xid = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msgJson);
            correlationDataId = JSON.parseObject(jsonObject.getString("correlationDataId"), String.class);
            emailDO = JSON.parseObject(jsonObject.getString("emailDO"), EmailDO.class);
            *//*try {
                xid = JSON.parseObject(jsonObject.getString("xid"), String.class);
            } catch (Exception e) {
                String[] split = msgJson.split("\",\"correlationDataId\":\"")[0].split("\\{\"xid\":\"");
                xid = split[1];
            }*//*
        } catch (Exception e) {
            e.printStackTrace();
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            return;
        } finally {}

        try {
            ValidationUtils.valid(emailDO, Update.class);
        } catch (BindException e) {
            e.printStackTrace();
            // 属性验证失败
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson,channel,message);
            updateMessageLogInfo(correlationDataId,true,false,"commentDO对象中的属性字段不满足要求");

        }

        //RootContext.bind(xid);
        // 运行到此处，说明一切正常，将数据插入到数据库中 并且修改消息的消费状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        UserDO userDO = UserDO.builder().emailUid(emailDO.getUid()).uid(emailDO.getUserUid()).build();
        ModifyResult modifyResult = userService.updateUser(userDO);
        updateMessageLogInfo(correlationDataId,true,true,null);

        *//*if (modifyResult.isSuccess()) {
            // 如果更新成功，则发送验证账户的邮件
            String verifyAccountPath = AccountInfoUtils.generateVerifyAccountPath(modifyResult.getUid(), emailVerifyAccountPrefixPath);
            EmailVerifyAccountDTO verifyAccountDTO = EmailVerifyAccountDTO.builder().receiverEmail(emailDO.getEmail())
                    .verifyAccountUrl(verifyAccountPath)
                    .expirationTime((long) emailVerifyAccountExpirationTime)
                    .userUid(modifyResult.getUid()).build();
            verifyAccountSendService.sendVerifyAccount(verifyAccountDTO);
        }*//*
    }

    *//**
     * 更新数据库中的mq消息的信息
     * @param correlationDataId
     * @param ackStatus
     * @param consumeStatus
     * @param errorMessage
     * @throws BindException
     *//*
    private void updateMessageLogInfo(String correlationDataId, boolean ackStatus, boolean consumeStatus, String errorMessage) throws BindException {
        MessageLogDO messageLogDO = null;
        messageLogFeignService.queryMessageLogByUid(Long.parseLong(correlationDataId));

        if (messageLogDO == null) {
            return;
        }

        messageLogDO.setAckStatus(ackStatus);
        messageLogDO.setConsumeStatus(consumeStatus);
        messageLogDO.setErrorMessage(errorMessage);
        ModifyResult modifyResult = messageLogFeignService.updateMessageLog(messageLogDO);
        log.info("更新mq消息发送日志:{},结果:{}",messageLogDO,modifyResult.isSuccess());
    }
}*/
