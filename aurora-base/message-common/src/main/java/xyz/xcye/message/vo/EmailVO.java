package xyz.xcye.message.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 数据表 au_email
 * @author qsyyke
 */

@Data
public class EmailVO {
    /**
     * 唯一uid，不能为null，主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 此邮箱配置对应哪个用户 可以为null
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userUid;

    /**
     * 邮箱主机 可以为null
     * <p>length < 25</p>
     */
    private String emailHost;

    /**
     * 邮箱协议 可以为null
     * <p>length < 10</p>
     */
    private String protocol;

    /**
     * 邮件服务的端口
     */
    private Integer port;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
