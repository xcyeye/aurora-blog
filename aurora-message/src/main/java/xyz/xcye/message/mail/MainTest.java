package xyz.xcye.message.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author qsyyke
 */


public class MainTest {
    public static void main(String[] args) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setPort(465);
        sender.setPassword("uutqhjyodxtrdiea");
        sender.setUsername("2291308094@qq.com");
        sender.setHost("smtp.qq.com");
        sender.setProtocol("smtps");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("2604400276@qq.com");//收信人
        message.setSubject("subject");//主题
        message.setText("content");//内容
        message.setFrom("2291308094@qq.com");//发信人

        sender.send(message);

    }
}
