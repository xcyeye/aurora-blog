package xyz.xcye.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 10:37
 */


@RestController
public class ResourceController {

    @GetMapping("re1")
    public String hello1() {
        return "htllo";
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("re2")
    public String hello2() {
        return "htllo2 admin";
    }
}
