package xyz.xcye.aurora.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 这是redis序列化的通用配置类
 * @author qsyyke
 */

public class RedisCommonConfig {

    public static RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();

        //指定要序列化的域,ANY是包括public和private的
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        //指定序列化输入的类型,类必须是非final类型的,否则会报错
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //设置key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置hash的key采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置value采用jackson2的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //设置hash的value采用jackson2的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
