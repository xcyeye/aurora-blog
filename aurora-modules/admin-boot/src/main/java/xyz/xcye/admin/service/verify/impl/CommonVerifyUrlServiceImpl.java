package xyz.xcye.admin.service.verify.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.verify.CommonVerifyUrlService;
import xyz.xcye.api.mail.sendmail.util.StorageEmailVerifyUrlUtil;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.lambda.AssertUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/17 12:08
 */

@Service
public class CommonVerifyUrlServiceImpl implements CommonVerifyUrlService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public boolean bindEmail(String incomingSecretKey) {
        boolean verifyUrl = StorageEmailVerifyUrlUtil.verifyUrl(incomingSecretKey);
        if (!verifyUrl) {
            return false;
        }

        // 验证成功，修改用户的邮箱绑定状态
        long userUid = StorageEmailVerifyUrlUtil.getUserUidFromVerifyPath(incomingSecretKey);
        User user = User.builder()
                .uid(userUid)
                .verifyEmail(true)
                .build();
        int updateUser = userService.updateUser(user);
        if (updateUser == 1) {
            boolean deleteKey = StorageEmailVerifyUrlUtil.deleteKey(incomingSecretKey);
            // 如果删除失败，则抛出异常，触发回滚
            AssertUtils.stateThrow(deleteKey, () -> new UserException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_FAIL_BINDING));
        }
        return updateUser == 1;
    }

    @Override
    public boolean removeAccountDisable(String incomingSecretKey) {
        return false;
    }
}
