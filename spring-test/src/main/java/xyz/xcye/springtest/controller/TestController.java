package xyz.xcye.springtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.springtest.exception.MyException;

/**
 * @author qsyyke
 */

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(int i) throws MyException {
        if (i < 10) {
            throw new MyException("自定义异常");
        }
        return "sdflj";
    }
}
