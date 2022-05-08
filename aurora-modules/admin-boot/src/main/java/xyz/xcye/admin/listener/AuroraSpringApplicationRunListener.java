package xyz.xcye.admin.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import xyz.xcye.admin.manager.task.LoadRolePermissionInfo;

import java.time.Duration;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 20:14
 */


@Order(3)
public class AuroraSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;

    public AuroraSpringApplicationRunListener(SpringApplication sa, String[] args) {
        this.application = sa;
        this.args = args;
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.ready(context,timeTaken);
        LoadRolePermissionInfo loadRolePermissionInfo = context.getBean(LoadRolePermissionInfo.class);
        loadRolePermissionInfo.storagePermissionInfoToRedis();
    }
}
