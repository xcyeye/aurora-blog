package xyz.xcye.admin.service.verify;

/**
 * 通用的验证账户信息的service
 * @author qsyyke
 * @date Created in 2022/5/17 11:26
 */

public interface CommonVerifyUrlService {

    boolean bindEmail(String incomingSecretKey);

    boolean removeAccountDisable(String incomingSecretKey);
}
