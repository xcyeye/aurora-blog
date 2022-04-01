package xyz.xcye.springtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author qsyyke
 */

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(int i) throws Exception {
        if (i < 10) {
            throw new IOException();
        }
        return "sdflj";
    }
}
