package xyz.xcye.entity.table;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author qsyyke
 */

@Data
public class LoginInfo {
    /**
     * 唯一uid 主键 不能为null
     */
    private BigInteger uid;

    /**
     * 哪个用户 可以为null
     */
    private BigInteger userUid;

    /**
     * 登录地点 可以为null
     * <p>长度<255</p>
     */
    private String loginLocation;

    /**
     * 登录地点ip地址 不能为null
     * <p>长度<12</p>
     */
    private String loginIp;

    /**
     * 登录时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String loginTime;

    /**
     * 登录的操作系统信息 可以为null
     * <p>长度<200</p>
     */
    private String operationSystem;
}
