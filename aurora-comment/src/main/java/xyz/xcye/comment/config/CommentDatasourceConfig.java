package xyz.xcye.comment.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.xcye.common.config.RedisCommonConfig;

/**
 * @author qsyyke
 */

@Configuration
public class CommentDatasourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    //@ConditionalOnMissingBean
    @Bean(name = "commentDruidDataSource")
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setPassword(datasourcePassword);
        druidDataSource.setUsername(datasourceUsername);
        druidDataSource.setUrl(datasourceUrl);
        return druidDataSource;
    }

    @Bean(name = "commentRedisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean(name = "commentRedisTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("commentRedisConnectionFactory") RedisConnectionFactory factory){
        return RedisCommonConfig.redisTemplate(factory);
    }

    /*@Bean
    public Interceptor interceptor() {
        return new MybatisInterceptor();
    }*/
}
