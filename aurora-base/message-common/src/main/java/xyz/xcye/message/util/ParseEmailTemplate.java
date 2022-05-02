package xyz.xcye.message.util;

import lombok.extern.slf4j.Slf4j;
import xyz.xcye.message.constant.MailTemplateConstant;

import java.util.Iterator;
import java.util.Map;

/**
 * 这是一个解析邮件模板的类
 * @author qsyyke
 */

@Slf4j
public class ParseEmailTemplate {

    /**
     * 根据传入的map集合中的key和value值，解析模板，比如模板<span>{{key1}}<span/>，一个map集合中的key为key1，vlaue为v1，那么就将v1替换到{{key1}}中
     * @param replacedKV 存放key和value的键值map
     * @param mailTemplate 待替换的模板
     * @return
     */
    public static String parseHtmlMailTemplate(Map<String,String> replacedKV, String mailTemplate) {

        Iterator<Map.Entry<String, String>> iterator = replacedKV.entrySet().iterator();
        while (iterator.hasNext()) {
            // 获取键值
            Map.Entry<String, String> replaceKVMap = iterator.next();
            // key就是模板中使用前缀和后缀包裹起来的字符，比如{{key}}的key部分
            String key = replaceKVMap.getKey();
            // value就是需要将模板中的特定字符，替换成此值，比如模板<span>{{key}}</span>,会被替换成<span>value</span>
            String value = replaceKVMap.getValue();

            // 切结替换的正则表达式，不能使用{{ [[等特殊符号
            String replacingRegex = MailTemplateConstant.REPLACE_CHARACTER_PREFIX + key + MailTemplateConstant.REPLACE_CHARACTER_SUFFIX;
            mailTemplate = mailTemplate.replaceAll(replacingRegex, value);
            log.info(value);
        }
        // 从迭代器中，获取每一个
        //替换内容
        return mailTemplate;
    }
}

