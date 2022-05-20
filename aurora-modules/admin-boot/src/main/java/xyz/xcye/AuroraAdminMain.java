package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.xcye.admin.manager.task.LoadRolePermissionInfo;
import xyz.xcye.admin.manager.task.LoadWhiteUrlInfo;

/**
 * @author qsyyke
 */

@SpringBootApplication
@EnableFeignClients
public class AuroraAdminMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuroraAdminMain.class, args);

        LoadRolePermissionInfo loadRolePermissionInfo = run.getBean(LoadRolePermissionInfo.class);
        loadRolePermissionInfo.storagePermissionInfoToRedis();
        // 将mysql中的白名单数据加载到redis中
        run.getBean(LoadWhiteUrlInfo.class).storageWhiteUrlInfoToRedis();
    }
}
