package xyz.xcye.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.entity.R;
import xyz.xcye.entity.table.VerifyPath;
import xyz.xcye.enums.ResultStatusCode;
import xyz.xcye.service.VerifyPathService;

/**
 * @author qsyyke
 */

@Slf4j
@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private VerifyPathService verifyPathService;

    @GetMapping("/insert")
    public R insertPermission(@Validated VerifyPath verifyPath) {
        log.info(String.valueOf(verifyPath));
        //VerifyPath insertVerifyPath = verifyPathService.insertPermission(verifyPath);

        R success = R.success(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getMessage(), "insertVerifyPath");
        System.out.println(success);
        return success;
    }
}
