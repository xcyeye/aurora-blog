package xyz.xcye.api.mail.sendmail.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.message.dto.CommonNoticeDTO;
import xyz.xcye.message.pojo.EmailLogPojo;

import java.util.*;

import static xyz.xcye.core.util.ConvertObjectUtils.jsonToString;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 19:19
 */


public class StorageMailUtils {

    public static final String  SPACE_CHARACTER = ":";

    /**
     * 生成一个用户发送html的json字符串
     * @param mailInfo 存储html发送信息的对象
     * @param replacedObjList 需要将此对象添加到replacedMap map集合中的对象,如果存在多个相同的对象，比如obj1,obj2，
     * 在存储他们的key值(key就是属性名)的时候，会在obj1的所有key后面添加上存放此对象的map集合的键，obj2也是一样的道理
     * @return
     */
    public static String generateMailJson(StorageSendMailInfo mailInfo, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) {
        // replacedMap添加到mailInfo中
        if (replacedObjList != null) {
            StorageSendMailInfo sendMailInfo = new StorageSendMailInfo(createReplacedMap(replacedObjList));
            BeanUtils.copyProperties(mailInfo, sendMailInfo);
            return jsonToString(sendMailInfo);
        }else {
            return jsonToString(mailInfo);
        }
    }

    /**
     * 返回一个StorageSendMailInfo，并不是原来的对象
     * @param mailInfo
     * @param replacedObjList
     * @return
     */
    public static StorageSendMailInfo generateMailInfo(StorageSendMailInfo mailInfo, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) {
        // replacedMap添加到mailInfo中
        StorageSendMailInfo sendMailInfo = new StorageSendMailInfo(createReplacedMap(replacedObjList));
        BeanUtils.copyProperties(mailInfo, sendMailInfo);
        return sendMailInfo;
    }

    /**
     * 生成一个存放所有需要替换的值的map集合
     * @param replacedObjList
     * @return
     */
    public static Map<String,String> createReplacedMap(List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) {
        Map<String,String> replacedMap = new HashMap<>();

        if (replacedObjList == null) {
            return replacedMap;
        }
        for (Map<SendHtmlMailTypeNameEnum, Object> objectMap : replacedObjList) {
            // 因为replacedObjList集合中的map只会保存一个，所以不需要使用while进行迭代
            for (Map.Entry<SendHtmlMailTypeNameEnum, Object> next : objectMap.entrySet()) {
                String key = next.getKey().name();
                Object replacedObj = next.getValue();

                String replacedJson = jsonToString(replacedObj);
                Map<String, Object> tempMap = JSON.parseObject(replacedJson, Map.class);
                // 迭代的方式，获取key
                for (Map.Entry<String, Object> nextTemp : tempMap.entrySet()) {
                    String tempKey = key + SPACE_CHARACTER + nextTemp.getKey();
                    replacedMap.put(tempKey, nextTemp.getValue() + "");
                }
                System.out.println();
            }
        }
        return replacedMap;
    }

    public static List<Map<SendHtmlMailTypeNameEnum, Object>> generateReplacedMailObject(SendHtmlMailTypeNameEnum mailTypeNameEnum, Object obj) {
        List<Map<SendHtmlMailTypeNameEnum, Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum, Object> map = new HashMap<>();
        map.put(mailTypeNameEnum, obj);
        list.add(map);
        return list;
    }

    public static List<Map<SendHtmlMailTypeNameEnum, Object>> generateCommonNotice(String sendMessage) {
        CommonNoticeDTO commonNoticeDTO = CommonNoticeDTO.builder()
                .sendMessage(sendMessage)
                .build();
        return generateReplacedMailObject(SendHtmlMailTypeNameEnum.COMMON_NOTICE, commonNoticeDTO);
    }

    public static StorageSendMailInfo generateCommonNotice(String subject,String sendContent,String receiverEmail, Long userUid) {
        EmailLogPojo emailLogPojo = new EmailLogPojo();
        emailLogPojo.setContent(sendContent);
        emailLogPojo.setUserUid(userUid);
        emailLogPojo.setCreateTime(DateUtils.format(new Date()));
        List<Map<SendHtmlMailTypeNameEnum, Object>> maps = StorageMailUtils.generateReplacedMailObject(SendHtmlMailTypeNameEnum.COMMON_NOTICE, emailLogPojo);
        Map<String, String> replacedMap = StorageMailUtils.createReplacedMap(maps);
        return StorageSendMailInfo.builder()
                .sendType(SendHtmlMailTypeNameEnum.COMMON_NOTICE)
                .receiverEmail(receiverEmail)
                .subject(subject)
                .htmlContent(sendContent)
                .replacedMap(replacedMap)
                .userUid(userUid)
                .build();
    }
}
