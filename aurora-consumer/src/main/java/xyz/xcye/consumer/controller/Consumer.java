package xyz.xcye.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.xcye.consumer.service.HelloService;


/**
 * @author qsyyke
 */

@RequestMapping("/consumer")
@RestController
public class Consumer {

    @Autowired
    private HelloService helloService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello/gateway")
    public String hello1() {
        String gateway = helloService.hello23();
        System.out.println(gateway);
        return gateway;
    }
}
