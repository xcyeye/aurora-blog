package xyz.xcye.message.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author qsyyke
 */


@Component
public class SendMailRealize {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendSimpleMail(String to,String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(senderEmail);//发信人
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }
    }

    public void sendHtmlMail(String receiverEmail,String subject,String content) throws MessagingException {

        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

        //设置发送的内容
        helper.setFrom(senderEmail);
        //设置接收者
        helper.setTo(receiverEmail);
        helper.setSubject(subject);

        helper.setText(content, true);//true代表支持html
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new MessagingException(e.getMessage());
        }
    }
}
