package xyz.xcye.message;

import cn.hutool.extra.template.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.xcye.message.mail.MailServiceImpl;



@SpringBootTest
public class MailT {


    @Autowired
    private MailServiceImpl mailService;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("2604400276@qq.com", "发送邮件测试", "大家好，这是我用springboot进行发送邮件测试");
    }

    @Test
    public void sendHtmlMail() {
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "    <!-- IMPORTANT -->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Account Activation</title>\n" +
                "\n" +
                "    <!-- <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheets/email.css\" /> -->\n" +
                "    <style>\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#FFFFFF\">\n" +
                "\n" +
                "<!-- HEADER -->\n" +
                "<table class=\"head-wrap\" bgcolor=\"#FFFFFF\">\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td class=\"header container\">\n" +
                "\n" +
                "            <div class=\"content\">\n" +
                "                <table bgcolor=\"#FFFFFF\">\n" +
                "                    <tr>\n" +
                "                        <td>\n" +
                "\n" +
                "                        </td>\n" +
                "\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "\n" +
                "        </td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<!-- /HEADER -->\n" +
                "\n" +
                "\n" +
                "<!-- BODY -->\n" +
                "<table class=\"body-wrap\">\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td class=\"container\" bgcolor=\"#FFFFFF\">\n" +
                "\n" +
                "            <div class=\"content\">\n" +
                "                <table>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\">\n" +
                "                            <p><img  style=\"max-width: 100%; \" src=\"https://s23.postimg.org/5nersjxmf/Screen_Shot_2016_04_17_at_2_08_36_PM.png\" alt=\"Brewplanner.com\" /></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>\n" +
                "                            <br/>\n" +
                "                            <h2>Hi ${username},</h3>\n" +
                "\n" +
                "                                <!-- You may like to include a Hero Image -->\n" +
                "\n" +
                "                                <!-- /Hero Image -->\n" +
                "\n" +
                "                                <br/>\n" +
                "\n" +
                "                                <h4>One more step to start planning your brews!</h4>\n" +
                "                                <br>\n" +
                "\n" +
                "                                <p style=\"text-align:center;\">\n" +
                "                                    <a class=\"btn\" style=\"background-color: #1da1db; border-radius: 3px;\n" +
                "        box-shadow: 3px 3px 10px 3px #B7B7B7;text-decoration:none;\n" +
                "    \t\tcolor:#FFF;\n" +
                "    \t\tbackground-color: #1da1db;\n" +
                "    \t\twidth:80%;\n" +
                "    \t\tpadding:15px 10%;\n" +
                "    \t\tfont-weight:bold;\n" +
                "    \t\ttext-align:center;\n" +
                "    \t\tcursor:pointer;\n" +
                "    \t\tdisplay:inline-block;\n" +
                "        border-radius: 5px;\n" +
                "        box-shadow: 3px 3px 3px 1px #EBEBEB;\" href=\"${confirmationUrl}\">Activate Account &raquo;</a>\n" +
                "                                </p>\n" +
                "                                <br/>\n" +
                "                                <p style=\"text-align: left\">Trouble activating? Contact us at <a href=\"mailto:support@brewplanner.com?Subject=Need%20help\" target=\"_top\">support@brewplanner.com</a></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                </table>\n" +
                "            </div>\n" +
                "\n" +
                "        </td>\n" +
                "\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<!-- /BODY -->\n" +
                "\n" +
                "<!-- FOOTER -->\n" +
                "<table class=\"footer-wrap\" bgcolor=\"#FFFFFF\">\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td class=\"container\">\n" +
                "\n" +
                "            <!-- content -->\n" +
                "            <div class=\"content\" style=\"margin-top: -15px\">\n" +
                "                <table>\n" +
                "                    <tr>\n" +
                "                        <br/>\n" +
                "                        <td >\n" +
                "                            <p>\n" +
                "\n" +
                "                                <a style=\"color:#9E9D9D\" href=\"www.brewplanner.com\"><small >Brewplanner.com</a></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "            <!-- /content -->\n" +
                "\n" +
                "        </td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<!-- /FOOTER -->\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        mailService.sendHtmlMail("2604400276@qq.com", "发送邮件测试", content);
    }

    @Test
    public void sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "/Users/aurora/Pictures/壁纸/wallhaven-q2vr3d.png";
        mailService.sendAttachmentMail("2604400276@qq.com", "发送邮件测试", content, filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String rscPath = "/Users/aurora/Pictures/壁纸/wallhaven-q2vr3d.png";
        String rscId = "001";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        mailService.sendInlineResourceMail("2604400276@qq.com", "发送邮件测试", content, rscPath, rscId);
    }
}
