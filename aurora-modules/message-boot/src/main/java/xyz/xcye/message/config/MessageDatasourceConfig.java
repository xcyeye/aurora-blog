package xyz.xcye.message.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.xcye.aurora.config.RedisCommonConfig;


/**
 * @author qsyyke
 */

@Configuration
public class MessageDatasourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    /**
     * 配置mysql数据源
     * @return
     */
    @Bean(name = "messageDruidDataSource")
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setPassword(datasourcePassword);
        druidDataSource.setUsername(datasourceUsername);
        druidDataSource.setUrl(datasourceUrl);
        return druidDataSource;
    }

    /**
     * 配置redisTemplate模板
     * @return
     */
    @Bean(name = "messageRedisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean(name = "messageRedisTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("messageRedisConnectionFactory") RedisConnectionFactory factory){
        return RedisCommonConfig.redisTemplate(factory);
    }

    /*@Bean
    public Interceptor interceptor() {
        return new MybatisInterceptor();
    }*/
}
