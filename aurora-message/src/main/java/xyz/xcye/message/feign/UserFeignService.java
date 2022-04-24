package xyz.xcye.message.feign;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dos.UserAccountDO;
import xyz.xcye.common.dos.UserDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.UserVO;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author qsyyke
 */

@FeignClient(value = "aurora-admin")
public interface UserFeignService {

    @GetMapping("/admin/user/{uid}")
    R queryUserByUid(@PathVariable("uid") long uid) throws InstantiationException, IllegalAccessException;

    @PutMapping("/admin/user")
    ModifyResult updateUser(@SpringQueryMap UserDO userDO) throws UserException;

    @PutMapping("/admin/user/bindingEmail")
    R bindingEmail(@SpringQueryMap EmailDO emailDO) throws BindException;
}
