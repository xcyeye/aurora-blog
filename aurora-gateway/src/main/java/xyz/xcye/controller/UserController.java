package xyz.xcye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.dao.UserDao;
import xyz.xcye.entity.table.User;
import xyz.xcye.service.UserService;

/**
 * 操作用户类
 * @author qsyyke
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public void test() {
        User qsyyke = userService.queryUserByUsername("qsyyke");
        System.out.println(qsyyke);

    }
}
