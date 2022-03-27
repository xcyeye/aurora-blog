package xyz.xcye.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Insert;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.ValidateString;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_logo_info
 * @author qsyyke
 */

@Data
public class LoginInfo {
    /**
     * 唯一uid 主键 不能为null
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 哪个用户 可以为null
     */
    private BigInteger userUid;

    /**
     * 登录地点 可以为null
     * <p>长度<255</p>
     */
    @Length(max = FieldLengthEnum.LOGIN_LOCATION,message = "登录日志-登录地点不能超过{max}")
    private String loginLocation;

    /**
     * 登录地点ip地址 不能为null
     * <p>长度<12</p>
     */
    @ValidateString(value = "登录日志-登录地点的ip地址",max = FieldLengthEnum.IP)
    private String loginIp;

    /**
     * 登录时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "登录日志-登录时间",max = FieldLengthEnum.TIME_FORMAT)
    private String loginTime;

    /**
     * 登录的操作系统信息 可以为null
     * <p>长度<200</p>
     */
    @Length(max = FieldLengthEnum.OPERATION_INFO,message = "登录日志-操作系统信息不能超过{max}")
    private String operationSystem;
}
