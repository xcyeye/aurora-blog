package xyz.xcye;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.xcye.admin.service.RoleService;

/**
 * @author qsyyke
 */

@SpringBootTest
public class AuroraAdminMainTest {

    @Autowired
    private RoleService roleService;
}
