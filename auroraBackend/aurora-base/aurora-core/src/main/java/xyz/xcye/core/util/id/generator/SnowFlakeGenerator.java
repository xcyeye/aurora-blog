package xyz.xcye.core.util.id.generator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 获取雪花算法生成的id
 * @author qsyyke
 */


public class SnowFlakeGenerator {
    /**
     * 根据终端id和数据中心id生成一个id
     * @param workerId 终端id
     * @param datacenterId 数据中心id
     * @return 返回19位长度的long型id
     */
    public static long getSnowFlakeUid(int workerId,int datacenterId) {
        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
}
