package xyz.xcye.admin.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

import java.time.Duration;

/**
 * 这是一个监听器，最开始想的是，在该应用启动之初，把角色权限信息加载到redis中，但是后面出现问题，就移到
 * AuroraAdminMain中了
 * @author qsyyke
 * @date Created in 2022/5/7 20:14
 */


@Order(3)
public class AuroraSpringApplicationRunListener implements SpringApplicationRunListener {

    private final String[] args;

    public AuroraSpringApplicationRunListener(SpringApplication sa, String[] args) {
        this.args = args;
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.ready(context,timeTaken);
        /*LoadRolePermissionInfo loadRolePermissionInfo = context.getBean(LoadRolePermissionInfo.class);
        loadRolePermissionInfo.storagePermissionInfoToRedis();
        // 将mysql中的白名单数据加载到redis中
        context.getBean(LoadWhiteUrlInfo.class).storageWhiteUrlInfoToRedis();*/
    }
}
