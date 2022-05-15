package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author qsyyke
 */

@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"xyz.xcye.aurora.exception.*", "xyz.xcye.aurora.interceptor.*","xyz.xcye.aurora.manager.advice.*"})})
@SpringBootApplication
public class AuroraGateWayMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraGateWayMain.class, args);
    }
}
