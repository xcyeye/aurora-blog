package com.example.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qsyyke
 */

@RestController
public class TestController {

    @GetMapping("/gate")
    public String test() {
        return "gateway test";
    }
}
