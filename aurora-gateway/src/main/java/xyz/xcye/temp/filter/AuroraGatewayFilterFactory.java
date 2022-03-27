/*
package xyz.xcye.xyz.xcye.temp.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

*/
/**
 * @author qsyyke
 *//*


//@Component
public class AuroraGatewayFilterFactory extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {
    public AuroraGatewayFilterFactory() {
        super(NameConfig.class);
    }

    public static final String NAME = "name";
    public static final String REGEX = "regex";
    public static final String SEX = "sex";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(NAME,REGEX,SEX);
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .headers(httpHeaders -> httpHeaders.remove(config.getName())).build();

                return chain.filter(exchange.mutate().request(request).build());
            }

            @Override
            public String toString() {
                return filterToStringCreator(AuroraGatewayFilterFactory.this)
                        .append("name", config.getName()).toString();
            }
        };
    }

    public static class Config {
        private String name;
        private String regex;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
*/
