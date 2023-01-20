package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.xcye.admin.service.PermissionRelationService;
import xyz.xcye.admin.service.WhiteUrlService;
import xyz.xcye.data.entity.Condition;

/**
 * @author qsyyke
 */

@SpringBootApplication
@EnableFeignClients
public class AuroraAdminRun {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuroraAdminRun.class, args);

        // 将角色权限信息和白名单存入redis
        Condition<Long> condition = new Condition<>();
        condition.setPageSize(10000);
        run.getBean(PermissionRelationService.class).loadAllRolePermission(condition);
        run.getBean(WhiteUrlService.class).queryListWhiteUrlByCondition(new Condition<>(){{
            setPageSize(10000);
        }});
    }
}
