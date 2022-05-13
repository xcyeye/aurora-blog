package xyz.xcye.article.magager.amqp.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.config.service.MistakeMessageSendService;
import xyz.xcye.article.po.Article;
import xyz.xcye.article.po.Talk;
import xyz.xcye.article.service.ArticleService;
import xyz.xcye.article.service.TalkService;
import xyz.xcye.article.vo.ArticleVO;
import xyz.xcye.article.vo.TalkVO;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.feign.config.service.MessageLogFeignService;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 这是一个消费从评论服务发送的评论消息，修改说说或者是文章的commentUidS值，根据PageUid的值，进行选择
 * @author qsyyke
 * @date Created in 2022/5/12 19:33
 */

@Component
public class ReceiveComment {

    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TalkService talkService;
    @Autowired
    private MessageLogFeignService.UpdateMessageLog updateMessageLog;

    @RabbitListener(queues = AmqpQueueNameConstant.PAGE_COMMENT_QUEUE,ackMode = "AUTO")
    private void receiveCommentConsumer(String msgJson, Channel channel, Message message) throws IOException, BindException {
        Comment comment = parseComment(msgJson, channel, message);
        if (comment == null || comment.getPageUid() == null) {
            return;
        }

        Long pageUid = comment.getPageUid();
        // 根据此pageUid查询article或者talk，看看是哪个的uid
        ArticleVO articleVO = articleService.selectByUid(pageUid);
        if (articleVO != null) {
            updateArticleData(articleVO, comment);
            ack(comment.getUid(), channel, message);
            return;
        }

        // 查询是否是说说的评论
        TalkVO talkVO = talkService.selectByUid(pageUid);
        if (talkVO != null) {
            // 是文章的评论uid
            updateTalkData(talkVO, comment);
            ack(comment.getUid(), channel, message);
            return;
        }

        // 运行到这里，说名此评论并不是说说和文章的评论，因为暂时没有加入其他的评论页面，所以也当错误消息处理
        mistakeMessageSendService.sendMistakeMessageToExchange(msgJson, channel, message,
                AmqpExchangeNameConstant.MISTAKE_MESSAGE_EXCHANGE, AmqpQueueNameConstant.MISTAKE_MESSAGE_ROUTING_KEY);
        updateMessageLog.updateMessageLogInfo(comment.getUid() + "",
                true, true, "此评论没有页面可以消费", message);
    }

    private Comment parseComment(String json, Channel channel, Message message) throws IOException {
        Comment comment = null;
        try {
            comment = JSON.parseObject(json, Comment.class);
        } catch (Exception e) {
            mistakeMessageSendService.sendMistakeMessageToExchange(json,channel,message,
                    AmqpExchangeNameConstant.MISTAKE_MESSAGE_EXCHANGE, AmqpQueueNameConstant.MISTAKE_MESSAGE_ROUTING_KEY);
            return null;
        }
        return comment;
    }

    private String setCommentUids(String commentUids, Long uid) {
        if (!StringUtils.hasLength(commentUids)) {
            return uid + "";
        }
        return Stream.concat(Stream.of(uid + ""), Arrays.stream(commentUids.split(",")))
                .distinct()
                .collect(Collectors.joining(","));
    }

    private void updateArticleData(ArticleVO articleVO, Comment comment) {
        // 是文章的评论uid
        articleVO.setCommentUids(setCommentUids(articleVO.getCommentUids(), comment.getUid()));
        articleService.updateByPrimaryKeySelective(BeanUtils.copyProperties(articleVO, Article.class));
    }

    private void updateTalkData(TalkVO talkVO, Comment comment) {
        // 是文章的评论uid
        talkVO.setCommentUids(setCommentUids(talkVO.getCommentUids(), comment.getUid()));
        talkService.updateByPrimaryKeySelective(BeanUtils.copyProperties(talkVO, Talk.class));
    }

    private void ack(Long uid, Channel channel, Message message) throws IOException, BindException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        updateMessageLog.updateMessageLogInfo(uid + "", true, true, null, message);
    }
}
