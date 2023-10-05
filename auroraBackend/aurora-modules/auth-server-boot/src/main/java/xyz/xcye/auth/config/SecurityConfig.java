package xyz.xcye.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.auth.handler.OauthServerAuthenticationFailureHandler;
import xyz.xcye.auth.handler.OauthServerAuthenticationSuccessHandler;
import xyz.xcye.auth.manager.cache.AuroraUserDetailsCache;
import xyz.xcye.auth.manager.security.CustomAuthServerAccess;
import xyz.xcye.auth.service.AuthServerRememberMeServices;
import xyz.xcye.auth.service.JwtTokenUserDetailsService;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:51
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 使用自定义的验证逻辑，从数据库中查询用户信息，有就是根据用户名，查看此用户是否存在，存在的话，存储用户的信息
     */
    @Autowired
    private JwtTokenUserDetailsService jwtTokenUserDetailsService;
    @Autowired
    private OauthServerAuthenticationSuccessHandler oauthServerAuthenticationSuccessHandler;
    @Autowired
    private OauthServerAuthenticationFailureHandler oauthServerAuthenticationFailureHandler;
    @Autowired
    private AuroraUserDetailsCache auroraUserDetailsCache;
    @Autowired
    private AuthServerRememberMeServices authServerRememberMeServices;
    @Autowired
    private AuroraProperties.AuroraAuthProperties auroraAuthProperties;

    private final CustomAuthServerAccess serverAccess = new CustomAuthServerAccess();

    /**
     * 数据库中，存储密码使用的加密算法，需要使用这个进行密码的验证
     *
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
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtTokenUserDetailsService).passwordEncoder(passwordEncoder());
    }*/

    /**
     * 此AuthenticationManager对象在oauth中会使用到
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(jwtTokenUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserCache(auroraUserDetailsCache);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .access("@customAuthServerAccess.hasPermission(request)")
                .and()
                .rememberMe()
                .rememberMeServices(authServerRememberMeServices)
                //.tokenValiditySeconds(auroraAuthProperties.getTokenValiditySeconds())
                .and()
                .formLogin()
                .loginProcessingUrl(OauthJwtConstant.LOGIN_PROCESS_URL)
                .permitAll()
                .successHandler(oauthServerAuthenticationSuccessHandler)
                .failureHandler(oauthServerAuthenticationFailureHandler)
                .and()
                //.authenticationProvider(authenticationProvider())
                .csrf()
                .disable();
    }
}

