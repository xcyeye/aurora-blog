package xyz.xcye.message.controller;

import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.common.vo.MessageLogVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import java.util.Enumeration;
import java.util.List;

/**
 * @author qsyyke
 */

@RequestMapping("/message/messageLog")
@RestController
public class MessageLogController {
    @Autowired
    private MessageLogService messageLogService;

    @ResponseResult
    @PostMapping("/insert")
    public ModifyResult insertMessageLog(@Validated({Insert.class,Default.class}) MessageLogDO messageLogDO, @RequestParam(value = "xid",required = false) String xid) throws BindException {
        return messageLogService.insertMessageLog(messageLogDO,xid);
    }

    @ResponseResult
    @PutMapping("/update")
    public ModifyResult updateMessageLog(@Validated(Update.class) MessageLogDO messageLogDO) throws BindException {
        return messageLogService.updateMessageLog(messageLogDO);
    }

    @ResponseResult
    @DeleteMapping("/delete/{uid}")
    public ModifyResult deleteMessageLog(@PathVariable("uid") long uid) {
        return messageLogService.deleteMessageLog(uid);
    }

    @ResponseResult
    @GetMapping("/queryAll")
    public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        return messageLogService.queryAllMessageLog(messageLogDO,paginationDTO);
    }

    @ResponseResult
    @GetMapping("/queryByUid/{uid}")
    public MessageLogDO queryMessageLogByUid(@PathVariable("uid") long uid) throws InstantiationException, IllegalAccessException {
        return messageLogService.queryByUid(uid);
    }

    @PostMapping("/seataTest")
    public void testMethod(@RequestParam(value = "xid",required = false) String xid, HttpServletRequest request) throws BindException {
        String transactionalXid = request.getHeader("transactionalXid");
        if (StringUtils.hasLength(transactionalXid)) {
            RootContext.bind(transactionalXid);
            System.out.println("从请求中获取到的: " + transactionalXid);
        }

        String s = messageLogService.seataTest(xid);
    }
}
