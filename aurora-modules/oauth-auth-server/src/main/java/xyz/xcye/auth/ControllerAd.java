package xyz.xcye.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 11:25
 */

@RestController
public class ControllerAd {

    @GetMapping("ser")
    public String he() {
        return "sdfhksdf";
    }
}
