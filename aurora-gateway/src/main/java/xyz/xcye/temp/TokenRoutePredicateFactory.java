/*
package xyz.xcye.xyz.xcye.temp;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

*/
/**
 * @author qsyyke
 *//*


//@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {
    public static final String PARAM_KEY = "param";

    */
/**
     * Regexp key.
     *//*

    public static final String REGEXP_KEY = "regexp";
    public static final String NAME = "name";

    public TokenRoutePredicateFactory() {
        super(TokenRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_KEY, REGEXP_KEY,NAME);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TokenRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                System.out.println(config);
                System.out.println(exchange);
                return true;
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Query: param=%s regexp=%s", config.getParam(), config.getRegexp());
            }
        };
    }

    @Validated
    public static class Config {

        @NotEmpty
        private String param;

        private String regexp;

        private String name;

        public void setParam(String param) {
            this.param = param;
        }

        public void setRegexp(String regexp) {
            this.regexp = regexp;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParam() {
            return param;
        }

        //public TokenRoutePredicateFactory.Config setParam(String param) {
        //    this.param = param;
        //    return this;
        //}

        public String getRegexp() {
            return regexp;
        }

        //public TokenRoutePredicateFactory.Config setRegexp(String regexp) {
        //    this.regexp = regexp;
        //    return this;
        //}

        public String getName() {
            return name;
        }

        //public TokenRoutePredicateFactory.Config setName(String name) {
        //    this.name = name;
        //    return this;
        //}

    }
}
*/
