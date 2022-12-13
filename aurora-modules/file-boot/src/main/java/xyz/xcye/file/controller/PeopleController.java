package xyz.xcye.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.file.po.AuPeople;
import xyz.xcye.file.pojo.AuPeoplePojo;
import xyz.xcye.file.service.AuPeopleService;
import xyz.xcye.file.vo.AuPeopleVO;

/**
 * @author xcye
 * @description
 * @date 2022-12-13 17:07:35
 */

@RequestMapping("/people")
@RestController
public class PeopleController {

    @Autowired
    private AuPeopleService auPeopleService;

    @GetMapping("/test")
    public AuPeopleVO test(int a) {
        AuPeoplePojo pojo = new AuPeoplePojo();
        pojo.setName("xcye");
        pojo.setUid(1);
        AuPeople auPeople = BeanUtils.copyProperties(pojo, AuPeople.class);

        AuPeople insert = auPeopleService.insert(auPeople);
        System.out.println(insert);
        return BeanUtils.copyProperties(insert, AuPeopleVO.class);
    }
}
