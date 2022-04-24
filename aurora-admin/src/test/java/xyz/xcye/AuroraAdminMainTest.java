package xyz.xcye;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.common.vo.RoleVO;

/**
 * @author qsyyke
 */

@SpringBootTest
public class AuroraAdminMainTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void test1() throws InstantiationException, IllegalAccessException {
        RoleVO roleVO = roleService.queryByUid(1);
        System.out.println(roleVO);
    }
}
