package xyz.xcye.core.util.id;

import xyz.xcye.core.util.id.generator.SnowFlakeGenerator;
import xyz.xcye.core.util.id.generator.UsernameGenerator;

import java.util.Random;

/**
 * 这是一个id生成器 包括雪花算法，用户名
 * @author qsyyke
 */


public class GenerateInfoUtils {

    /**
     * 根据终端id和数据中心id生成一个id
     * @param workerId 终端id
     * @param datacenterId 数据中心id
     * @return 返回19位长度的long型id
     */
    public static long generateUid(int workerId,int datacenterId) {
        if (datacenterId > 31) {
            datacenterId = new Random().nextInt(31);
        }
        return SnowFlakeGenerator.getSnowFlakeUid(workerId,datacenterId);
    }

    /**
     * 生成一个随机用户名，此生成的用户名不能保证数据库中唯一，因为这是工具类，不涉及操作数据库
     * 所以需要验证是否唯一的话，在调用那里进行判断
     * @return 用户名
     */
    public static String generateUsername(String usernamePrefix) {
        return UsernameGenerator.getUsername(usernamePrefix);
    }
}
