package xyz.xcye.article.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.article.api.service.ArticleUserFeignService;
import xyz.xcye.article.dao.LinkMapper;
import xyz.xcye.article.po.Link;
import xyz.xcye.article.service.CategoryService;
import xyz.xcye.article.service.LinkService;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.article.vo.LinkVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.link.LinkException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 19:38
 */

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleUserFeignService userFeignService;
    @Autowired
    private SendMQMessageService sendMQMessageService;

    @Override
    public int deleteByPrimaryKey(Long uid, String replyMessage) throws BindException {
        Assert.notNull(uid, "uid不能为null");
        // 如果删除成功，通知对方
        LinkVO linkVO = selectByUid(uid);
        int deleteNum = linkMapper.deleteByPrimaryKey(uid);
        if (deleteNum == 1) {
            StorageSendMailInfo mailInfo = getMailInfo(linkVO.getEmail(), "你的友情链接被删除了", replyMessage,
                    BeanUtils.copyProperties(linkVO, Link.class));
            sendMail(mailInfo);
        }
        return deleteNum;
    }

    @GlobalTransactional
    @Override
    public int insertSelective(Link record) throws BindException {
        Assert.notNull(record, "友情链接信息不能为null");
        Assert.notNull(record.getUserUid(), "用户uid不能为null");
        // 查看是否存在该用户
        R r = userFeignService.queryUserByUid(record.getUserUid());
        UserVO userVO = JSONUtils.parseObjFromResult(r, "data", UserVO.class);
        AssertUtils.stateThrow(userVO != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));

        setEffectiveCategory(record, false);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(), auroraProperties.getSnowFlakeDatacenterId()));

        // 查看是否存在相似的链接
        List<LinkVO> result = selectByCondition(Condition.instant(record.getLinkUrl())).getResult();
        AssertUtils.stateThrow(result.isEmpty(), () -> new LinkException("已存在相同友情链接，不能重复提交"));

        // 插入
        int insertNum = linkMapper.insertSelective(record);
        // 如果插入成功，则发送消息通知该用户
        if (insertNum == 1) {
            StorageSendMailInfo mailInfo = getMailInfo(null, "你有新的友情链接待审核", "新友情链接信息: " + record, record);
            sendMail(mailInfo);
        }
        return insertNum;
    }

    @Override
    public PageData<LinkVO> selectByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.pageList(condition, t -> linkMapper.selectByCondition(condition), LinkVO.class);
    }

    @Override
    public LinkVO selectByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.getSingleObjFromList(linkMapper.selectByCondition(Condition.instant(uid, true)), LinkVO.class);
    }

    @Override
    public int updateByPrimaryKeySelective(Link record, String replyMessage) throws BindException {
        Assert.notNull(record, "友情链接信息不能为null");
        setEffectiveCategory(record, true);
        record.setUpdateTime(DateUtils.format());
        Optional.ofNullable(UserUtils.getCurrentUserUid()).ifPresent(record::setUserUid);
        int updateNum = linkMapper.updateByPrimaryKeySelective(record);

        // 如果修改成功，查询完整的友情链接信息，用于发送
        LinkVO linkVO = selectByUid(record.getUid());
        if (updateNum == 1 && !record.getPublish()) {
            // 没有审核通过，则把message发送给该站长
            StorageSendMailInfo mailInfo = getMailInfo(linkVO.getEmail(), "你申请的友情链接未通过审核", "原因: " + replyMessage, record);
            sendMail(mailInfo);
            return updateNum;
        }

        // 通过审核，发送消息通知对方
        if (updateNum == 1 && record.getPublish()) {
            StorageSendMailInfo mailInfo = getMailInfo(linkVO.getEmail(), "你申请的友情链接已通过审核", replyMessage, record);
            sendMail(mailInfo);
        }
        return updateNum;
    }

    /**
     * 查看传入的类别是否有效
     * @param link
     */
    private void setEffectiveCategory(Link link, boolean isUpdate) {
        Long categoryUid = link.getCategoryUid();
        if (!isUpdate) {
            // 插入，必须要有类别
            AssertUtils.stateThrow(categoryUid != null,
                    () -> new LinkException(ResponseStatusCodeEnum.PARAM_NOT_COMPLETE.getMessage() + categoryUid));
        }

        CategoryVO categoryVO = null;
        if (categoryUid != null) {
            categoryVO = categoryService.selectByUid(categoryUid);
        }
        // 查看此categoryUid是否有效
        if (!isUpdate) {
            // 插入，必须要有类别
            AssertUtils.stateThrow(categoryVO != null,
                    () -> new LinkException(ResponseStatusCodeEnum.PARAM_NOT_COMPLETE.getMessage() + categoryUid));
        }

        if (categoryVO == null) {
            link.setCategoryUid(null);
        }
    }

    private StorageSendMailInfo getMailInfo(String receiverEmail, String subject, String simpleText, Link link) {
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setReceiverEmail(receiverEmail);
        mailInfo.setSubject(subject);
        mailInfo.setSimpleText(simpleText);
        mailInfo.setUserUid(link.getUserUid());
        return mailInfo;
    }

    private void sendMail(StorageSendMailInfo mailInfo) throws BindException {
        sendMQMessageService.sendSimpleTextMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_SIMPLE_TEXT_MAIL_ROUTING_KEY);
    }
}
