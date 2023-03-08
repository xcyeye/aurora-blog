package xyz.xcye.article.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.amqp.comstant.AmqpExchangeNameConstant;
import xyz.xcye.amqp.comstant.AmqpQueueNameConstant;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.StorageMailUtils;
import xyz.xcye.article.api.service.ArticleUserFeignService;
import xyz.xcye.article.po.Link;
import xyz.xcye.article.pojo.CategoryPojo;
import xyz.xcye.article.pojo.LinkPojo;
import xyz.xcye.article.vo.CategoryVO;
import xyz.xcye.article.vo.LinkVO;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.link.LinkException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 19:38
 */

@Service
public class LinkService {

    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private AuroraLinkService auroraLinkService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleUserFeignService userFeignService;
    @Autowired
    private SendMQMessageService sendMQMessageService;

    public int physicalDeleteLink(LinkPojo linkPojo) throws BindException {
        Long uid = linkPojo.getUid();
        String replyMessage = linkPojo.getReplyMessage();
        Assert.notNull(uid, "uid不能为null");
        // 如果删除成功，通知对方
        LinkVO linkVO = queryLinkByUid(uid);
        int deleteNum = auroraLinkService.deleteById(uid);
        R r = userFeignService.queryUserByUid(new UserPojo(){{
            setUid(linkVO.getUserUid());
        }});
        UserVO userVO = JSONUtils.parseObjFromResult(r, "data", UserVO.class);
        if (deleteNum == 1) {
            String mailMessage = "<p>您在" + userVO.getUsername() + "用户处申请的友链被移除！┭┮﹏┭┮</p><p>原因:" + replyMessage + "</p>";
            StorageSendMailInfo mailInfo = getMailInfo(linkVO.getEmail(), "友链被移除",
                    mailMessage, BeanUtils.copyProperties(linkVO, Link.class), SendHtmlMailTypeNameEnum.COMMON_NOTICE);
            sendMail(mailInfo, StorageMailUtils.generateCommonNotice(mailMessage));
        }
        return deleteNum;
    }

    @GlobalTransactional
    public void insertLink(LinkPojo pojo) {
        Assert.notNull(pojo, "友情链接信息不能为null");
        Assert.notNull(pojo.getUserUid(), "用户uid不能为null");
        Link record = BeanUtils.copyProperties(pojo, Link.class);
        // 查看是否存在该用户
        R r = userFeignService.queryUserByUid(new UserPojo(){{
            setUid(record.getUserUid());
        }});
        UserVO userVO = JSONUtils.parseObjFromResult(r, "data", UserVO.class);
        AssertUtils.stateThrow(userVO != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));

        setCategory(record);
        record.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(), auroraProperties.getSnowFlakeDatacenterId()));

        // 查看是否存在相似的链接
        Link link = new Link();
        link.setUserUid(pojo.getUserUid());
        link.setLinkUrl(pojo.getLinkUrl());
        Link queriedLink = auroraLinkService.queryOne(link);
        AssertUtils.stateThrow(queriedLink == null, () -> new LinkException("已存在相同友情链接，不能重复提交"));

        // 审核应该通过用户进行控制
        record.setPublish(false);
        // 插入
        auroraLinkService.insert(record);
        // 如果插入成功，则发送消息通知该用户
        String simpleText = "新友情链接信息: " + record.getLinkTitle();
        StorageSendMailInfo mailInfo = getMailInfo(null, "你有新的友情链接待审核",
                simpleText, record, SendHtmlMailTypeNameEnum.FRIEND_LINK_NOTICE);
        try {
            List<Map<SendHtmlMailTypeNameEnum, Object>> replacedMailObject =
                    StorageMailUtils.generateReplacedMailObject(SendHtmlMailTypeNameEnum.FRIEND_LINK_NOTICE, record);
            sendMail(mailInfo, replacedMailObject);
        } catch (Exception e) {
            // 如果消息入库失败，会抛出异常，不处理
            LogUtils.logExceptionInfo(e);
        }
    }

    public PageData<LinkVO> queryListLinkByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(auroraLinkService.queryListByCondition(condition), LinkVO.class);
    }

    public LinkVO queryLinkByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(auroraLinkService.queryById(uid), LinkVO.class);
    }

    @GlobalTransactional
    public int updateLink(LinkPojo pojo) throws BindException {
        Assert.notNull(pojo, "友情链接信息不能为null");
        Link record = BeanUtils.copyProperties(pojo, Link.class);
        // setCategory(record);
        Optional.ofNullable(UserUtils.getCurrentUserUid()).ifPresent(record::setUserUid);

        // 如果审核状态发生改变，则通知对方
        Link link = auroraLinkService.queryById(pojo.getUid());
        if (pojo.getPublish() != null && link.getPublish() != pojo.getPublish()) {
            updateLinkPublishStatus(pojo);
        }
        return auroraLinkService.updateById(record);
    }

    public int updateLinkPublishStatus(LinkPojo link) throws BindException {
        Long uid = link.getUid();
        Boolean publish = link.getPublish();
        String msg = link.getReplyMessage();
        LinkVO linkVO = queryLinkByUid(uid);
        AssertUtils.stateThrow(linkVO != null, () -> new LinkException("没有该条记录"));
        AssertUtils.stateThrow(linkVO.getPublish() != publish, () -> new LinkException("该条友情链接的发布状态并未改变"));
        // 修改
        Link linkTemp = new Link();
        linkTemp.setUid(uid);
        linkTemp.setPublish(publish);
        int updateNum = auroraLinkService.updateById(linkTemp);
        // 如果修改成功，发送消息通知对方
        linkVO.setPublish(publish);
        R r = userFeignService.queryUserByUid(new UserPojo(){{
            setUid(linkVO.getUserUid());
        }});
        UserVO userVO = JSONUtils.parseObjFromResult(r, "data", UserVO.class);
        if (updateNum == 1 && !publish) {
            String mailMessage = "您在" + userVO.getUsername() + "用户处申请的友链未通过审核！┭┮﹏┭┮<p>原因:" + msg + "</p>";
            StorageSendMailInfo sendMailInfo = StorageMailUtils.generateCommonNotice("友链未通过ε(┬┬﹏┬┬)3 ", mailMessage, linkVO.getEmail(), linkVO.getUserUid());
            sendMail(sendMailInfo, null);
            return updateNum;
        }

        // 通过审核，发送消息通知对方
        if (updateNum == 1 && publish) {
            String mailMessage = "您在" + userVO.getUsername() + "用户处申请的友链已通过审核！o(￣▽￣)ｄ ~<p>博主留言:" + msg + "</p>";
            StorageSendMailInfo sendMailInfo = StorageMailUtils.generateCommonNotice("友链审核通过 φ(゜▽゜*)♪ ", mailMessage, linkVO.getEmail(), linkVO.getUserUid());
            sendMail(sendMailInfo, null);
        }
        return updateNum;
    }

    public int queryTotalLinkCount(LinkPojo pojo) {
        return auroraLinkService.countByWhere(BeanUtils.copyProperties(pojo, Link.class));
    }

    /**
     * 设置类别，如果不存在，则插入
     * @param link
     */
    private void setCategory(Link link) {
        String categoryName = link.getCategoryName();
        if (!StringUtils.hasLength(categoryName)) {
            return;
        }
        // AssertUtils.stateThrow(StringUtils.hasLength(categoryName),
        //         () -> new LinkException(ResponseStatusCodeEnum.PARAM_NOT_COMPLETE.getMessage() + " categoryName"));
        CategoryPojo categoryPojo = new CategoryPojo();
        categoryPojo.setTitle(categoryName);
        categoryPojo.setUserUid(link.getUserUid());
        CategoryVO categoryVO = categoryService.queryOneCategory(categoryPojo);
        if (categoryVO == null) {
            // 插入
            CategoryPojo category = new CategoryPojo();
            category.setTitle(categoryName);
            try {
                categoryService.insertCategory(category);
            } catch (Exception e) {
                link.setCategoryName(null);
            }
        }

    }

    private StorageSendMailInfo getMailInfo(String receiverEmail, String subject, String simpleText,
                                            Link link, SendHtmlMailTypeNameEnum mailTypeNameEnum) {
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setReceiverEmail(receiverEmail);
        mailInfo.setSubject(subject);
        mailInfo.setSimpleText(simpleText);
        mailInfo.setUserUid(link.getUserUid());
        mailInfo.setSendType(mailTypeNameEnum);
        return mailInfo;
    }

    private void sendMail(StorageSendMailInfo mailInfo, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) throws BindException {
        sendMQMessageService.sendCommonMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, replacedObjList);
    }
}
