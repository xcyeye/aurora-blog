package xyz.xcye.article;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xcye
 * @description
 * @date 2023-01-29 23:26:18
 */

public class Test {
    public static void main(String[] args) {
        String content = "因此，互联网是“网络的网络(Network of Networks)\n" +
                "\n" +
                "![image-20220628125857609](https://picture.xcye.xyz/image-20220628125857609.png)\n" +
                "\n" +
                "因特网 (Internet)是世界上最大的互连网络（用户数以亿计，互连的网络数以百万计)\n" +
                "\n" +
                "![image-20220628130023668](https://picture.xcye.xyz/image-20220628130023668.png)";

        String regStr = "!\\[[0-9a-zA-Z-~!@#$%^&*()._+]*]\\(((https|http)://.*)";

        Pattern pattern = Pattern.compile(regStr);

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            String group = matcher.group(0);
            group = group.replaceAll("!\\[[0-9a-zA-Z-~!@#$%^&*()._+]*]\\(", "");
            System.out.println(group.substring(0, group.length() - 1));
        }
    }
}
