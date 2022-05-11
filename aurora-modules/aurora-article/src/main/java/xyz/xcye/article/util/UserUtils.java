package xyz.xcye.article.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.article.api.service.UserFeignService;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.lambda.AssertUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:19
 */


@Component
public class UserUtils {

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 查看是否存在此用户
     * @param userUid
     */
    public void userExists(Long userUid) {
        // 远程调用，查看此userUid是否正确
        UserVO userVO = (UserVO) userFeignService.queryUserByUid(userUid).getData();
        AssertUtils.stateThrow(userVO != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
    }
}
