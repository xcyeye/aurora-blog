package xyz.xcye.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.EmailLog;
import xyz.xcye.common.enums.RegexEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.service.EmailLogService;

import javax.validation.groups.Default;
import java.math.BigInteger;
import java.util.List;

/**
 * 操作au_email_log表的controller
 * @author qsyyke
 */

@RequestMapping("/message/emailLog")
@RestController
public class EmailLogController {

    @Autowired
    private EmailLogService emailLogService;

    @ResponseResult
    @PostMapping("/insert")
    public ModifyResult insertEmailLog(@Validated({Insert.class, Default.class}) EmailLog emailLog) {
        return emailLogService.insertEmailLog(emailLog);
    }

    @ResponseResult
    @PutMapping("/update")
    public ModifyResult updateEmailLog(@Validated({Update.class}) EmailLog emailLog) throws BindException {
        return emailLogService.updateEmailLog(emailLog);
    }

    @ResponseResult
    @DeleteMapping("/delete/{uid}")
    public ModifyResult deleteEmailLog(@PathVariable("uid") String uid) {
        return emailLogService.deleteEmailLog(new BigInteger(uid));
    }

    @GetMapping("/queryAll")
    public List<EmailLog> queryAllEmailLog(EmailLog emailLog, Pagination pagination) {
        if (emailLog == null) {
            emailLog = new EmailLog();
        }

        if (pagination == null) {
            pagination = new Pagination();
        }

        return emailLogService.queryAll(emailLog,pagination);
    }
}