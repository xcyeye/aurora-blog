package xyz.xcye.service.redis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.service.redis.annotation.CleanRedisData;
import xyz.xcye.service.redis.annotation.GetByRedis;
import xyz.xcye.service.redis.annotation.PutToRedis;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xcye
 * @description
 * @date 2023-01-01 22:36:43
 */

@Component
@Slf4j
@Aspect
public class RedisCacheAop {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(getByRedis)")
    public Object getDataFromRedis(ProceedingJoinPoint point, GetByRedis getByRedis) throws Throwable {

        // 默认情况下，key值有全限定名称:方法名:参数名组成
        String defaultRedisKey = getDefaultRedisKey(point);
        LogUtils.logCommonInfo("存储在redis中的默认key值为: " + defaultRedisKey);

        // 自定义key值
        String customKey = getByRedis.key();
        // 失效时间
        int expriedSecond = getByRedis.expriedSecond();
        // 存储类型
        GetByRedis.TYPE type = getByRedis.type();

        String redisKey = defaultRedisKey;
        if (StringUtils.hasLength(customKey)) {
            redisKey = customKey;
        }
        Object redisData = redisTemplate.opsForValue().get(redisKey);
        // 判断数据是否存在
        if (redisData != null) {
            return redisData;
        }
        // 数据不存在，重新放入redis中
        Object proceedData = point.proceed();
        execPutDataToRedis(proceedData, expriedSecond, redisKey);
        return proceedData;
    }

    @Around("@annotation(putToRedis)")
    public Object putDataToRedis(ProceedingJoinPoint point, PutToRedis putToRedis) throws Throwable {
        String defaultRedisKey = getDefaultRedisKey(point);

        // 自定义key值
        String customKey = putToRedis.key();
        // 失效时间
        int expriedSecond = putToRedis.expriedSecond();
        // 存储类型
        PutToRedis.TYPE type = putToRedis.type();

        String redisKey = defaultRedisKey;
        if (StringUtils.hasLength(customKey)) {
            redisKey = customKey;
        }

        Object proceedData = point.proceed();
        execPutDataToRedis(proceedData, expriedSecond, redisKey);
        return proceedData;
    }

    @Around("@annotation(cleanRedisData)")
    public Object cleanRedisData(ProceedingJoinPoint point, CleanRedisData cleanRedisData) throws Throwable {

        // 自定义key值
        String customKey = cleanRedisData.key();
        String[] otherKeyArr = cleanRedisData.otherKey();

        String prefixRedisKey = getPrefixRedisKey(point);
        String redisKey = prefixRedisKey + ":*";
        if (StringUtils.hasLength(customKey)) {
            redisKey = customKey;
        }
        Set<String> keys = redisTemplate.keys(redisKey);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

        // 清除其他key所对应的值
        if (otherKeyArr != null) {
            for (String otherKey : otherKeyArr) {
                Set<String> prefixOtherKeys = redisTemplate.keys(otherKey + ":*");
                if (prefixOtherKeys != null && !prefixOtherKeys.isEmpty()) {
                    redisTemplate.delete(prefixOtherKeys);
                }

                Set<String> otherKeys = redisTemplate.keys(otherKey);
                if (otherKeys != null && !otherKeys.isEmpty()) {
                    redisTemplate.delete(otherKeys);
                }
            }
        }
        return point.proceed();
    }

    private void execPutDataToRedis(Object data, long expriedSecond, String redisKey) {
        if (expriedSecond < 0) {
            redisTemplate.opsForValue().set(redisKey, data);
            return;
        }
        redisTemplate.opsForValue().set(redisKey, data, expriedSecond, TimeUnit.SECONDS);
    }

    private String getDefaultRedisKey(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        // 获取该方法的类的全限定名称
        String className = method.getDeclaringClass().getName();
        // 获取方法名
        String methodName = method.getName();
        Object[] args = point.getArgs();

        // 获取该方法的参数的toString字符串
        String parameterObjStr = "";
        for (Object arg : args) {
            parameterObjStr = parameterObjStr + arg.toString();
        }

        // 默认情况下，key值有全限定名称:方法名:参数名组成
        return className + ":" + methodName + ":" + parameterObjStr;
    }

    private String getPrefixRedisKey(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        // 获取该方法的类的全限定名称
        String className = method.getDeclaringClass().getName();
        // 获取方法名
        String methodName = method.getName();
        Object[] args = point.getArgs();

        // 获取该方法的参数的toString字符串
        String parameterObjStr = "";
        for (Object arg : args) {
            parameterObjStr = parameterObjStr + arg.toString();
        }

        // 默认情况下，key值有全限定名称:方法名:参数名组成
        return className;
    }
}
