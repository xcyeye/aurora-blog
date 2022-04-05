package xyz.xcye.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.service.EmailService;

import javax.validation.groups.Default;
import java.math.BigInteger;
import java.util.List;

/**
 * 操作au_email表的controller
 * @author qsyyke
 */

@RestController
@RequestMapping("/message/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ResponseResult
    @PostMapping("/insert")
    public ModifyResult insertEmail(@Validated({Insert.class,Default.class}) Email email) {
        return emailService.insertEmail(email);
    }

    @ResponseResult
    @DeleteMapping("/delete/{uid}")
    public ModifyResult deleteEmailByUid(@PathVariable(value = "uid") String uid) {
        Email email = new Email();
        email.setUid(new BigInteger(uid));
        return emailService.deleteEmailByUid(email);
    }

    @ResponseResult
    @PutMapping("/update")
    public ModifyResult updateEmailByUid(@Validated({Update.class, Default.class}) Email email) {
        return emailService.updateEmailByUid(email);
    }

    @ResponseResult
    @GetMapping("/queryAll")
    public List<Email> queryAll(@RequestParam(required = false) Email email, Pagination pagination) {
        if (email == null) {
            email = new Email();
        }
        return emailService.queryAllEmail(email,pagination);
    }
}
