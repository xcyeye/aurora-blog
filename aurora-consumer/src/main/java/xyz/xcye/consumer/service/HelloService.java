package xyz.xcye.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qsyyke
 */

@Component
@FeignClient(value = "aurora-gateway",fallback = MyFallBack.class)
public interface HelloService {

    @GetMapping("/hello/gateway")
    public String hello23();
}


@Component
class MyFallBack implements HelloService {
    @Override
    public String hello23() {
        return "出现异常了";
    }
}