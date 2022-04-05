package xyz.xcye.file.msgsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qsyyke
 */


@RestController
public class SendController {

    @Autowired
    private MessageSender messageSender;

    @GetMapping("/sender")
    public String sender() {
        String send = messageSender.send();
        return send;
    }
}
