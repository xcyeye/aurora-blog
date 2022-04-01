package xyz.xcye.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.entity.result.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 */

@Api(tags = "管理类")
@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {

    @ApiOperation(value = "管理测试请求",notes = "测试")
    @GetMapping("/user/{name}/{habit}")
    public Object test(@PathVariable("name") String name,
                  @PathVariable("habit") String habit) {
        log.info("请求了{},{}",name,habit);


        Map map = new HashMap();
        map.put("v1",name);
        map.put("v2",habit);

        /*List list = new ArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        map.put("data",list);*/

        return R.success(200,"请求陈宫",map);
        //return map;
    }
}
