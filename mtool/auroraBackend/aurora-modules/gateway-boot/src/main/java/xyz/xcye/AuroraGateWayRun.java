package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author qsyyke
 */

@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"xyz.xcye.aurora.exception.*", "xyz.xcye.aurora.interceptor.*","xyz.xcye.aurora.manager.advice.*"})})
@SpringBootApplication
public class AuroraGateWayRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraGateWayRun.class, args);
    }

    // @Bean
    // public CorsWebFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.addAllowedOrigin("*");
    //     config.setAllowCredentials(false);
    //     config.addAllowedHeader("*");
    //     config.addAllowedMethod("*");
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsWebFilter(source);
    // }
}
