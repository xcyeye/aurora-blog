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
        String content = "深刻的减肥\n # 4534塑料袋咖啡机3485离\n开家8902老师快递费sdkj" +
                "看是两地分居森岛帆高345 435\n # 23i847lkjsd放阿斯拉达\n付款的双方各";

        String regStr = "# .+\n";

        Pattern pattern = Pattern.compile(regStr);

        Matcher matcher = pattern.matcher(content);
        boolean b = matcher.find();
        if (b) {
            System.out.println(matcher.group(1));
        }
        while (matcher.find()) {
            // System.out.println(matcher.group(0));
        }
    }
}
