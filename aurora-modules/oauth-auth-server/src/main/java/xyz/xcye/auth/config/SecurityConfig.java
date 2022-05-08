package xyz.xcye.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.xcye.auth.handler.OauthServerAuthenticationFailureHandler;
import xyz.xcye.auth.handler.OauthServerAuthenticationSuccessHandler;
import xyz.xcye.auth.properties.SecurityProperties;
import xyz.xcye.auth.service.JwtTokenUserDetailsService;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:51
 */

@EnableConfigurationProperties({SecurityProperties.class})
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 使用自定义的验证逻辑，从数据库中查询用户信息
     */
    @Autowired
    private JwtTokenUserDetailsService jwtTokenUserDetailsService;

    /**
     * 数据库中，存储密码使用的加密算法，需要使用这个进行密码的验证
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置验证用户信息的自定义逻辑实现
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtTokenUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 此AuthenticationManager对象在oauth中会使用到
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(new OauthServerAuthenticationSuccessHandler())
                .failureHandler(new OauthServerAuthenticationFailureHandler())
                .and()
                .csrf()
                .disable();
    }
}
