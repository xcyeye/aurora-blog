// package xyz.xcye.wg.filter;
//
// import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;
//
// /**
//  * @author xcye
//  * @description
//  * @date 2023-02-09 16:07:26
//  */
//
// @Component
// public class GlobalGatewayFilter implements GatewayFilter {
//
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         System.out.println("345");
//         return chain.filter(exchange);
//     }
// }
