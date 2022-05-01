package xyz.xcye.file.config;

import com.alibaba.druid.pool.DruidDataSource;
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
public class DatasourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setPassword(datasourcePassword);
        druidDataSource.setUsername(datasourceUsername);
        druidDataSource.setUrl(datasourceUrl);
        return druidDataSource;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        return RedisCommonConfig.redisTemplate(factory);
    }

    /*@Bean
    public Interceptor interceptor() {
        return new MybatisInterceptor();
    }*/
}
