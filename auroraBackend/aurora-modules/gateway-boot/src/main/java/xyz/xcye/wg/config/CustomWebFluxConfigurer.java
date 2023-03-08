// package xyz.xcye.wg.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpHeaders;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsWebFilter;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
// import org.springframework.web.reactive.config.CorsRegistry;
// import org.springframework.web.reactive.config.WebFluxConfigurer;
//
// /**
//  * @author xcye
//  * @description
//  * @date 2023-02-09 14:08:08
//  */
//
// @Configuration
// public class CustomWebFluxConfigurer implements WebFluxConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowCredentials(true)
//                 .allowedOriginPatterns("*")
//                 .allowedHeaders("*")
//                 .allowedMethods("*")
//                 .exposedHeaders(HttpHeaders.SET_COOKIE);
//     }
//
//     @Bean
//     public CorsWebFilter corsWebFilter() {
//         CorsConfiguration corsConfiguration = new CorsConfiguration();
//         corsConfiguration.setAllowCredentials(true);
//         corsConfiguration.addAllowedHeader("*");
//         corsConfiguration.addAllowedMethod("*");
//         corsConfiguration.addAllowedOriginPattern("*");
//         corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
//         UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//         corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//         return new CorsWebFilter(corsConfigurationSource);
//     }
// }
