package xyz.xcye.core.entity;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

/**
 * 这是增加，删除，修改数据表之后，返回的公用实体
 * <p>包含的字段有 影响的行数，message，原数据，修改之后的数据</p>
 *
 * @author qsyyke
 */

//@JsonIgnoreProperties(value = {"code","message","success"})
@Data
@Builder
public class ModifyResult {
    /**
     * 影响的行数
     */
    private int affectedRows;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 成功或者失败的消息
     */
    //@JsonIgnore
    private String message;

    /**
     * 响应码
     */
    private int code;

    /**
     * 主键uid
     */
    private long uid;

    public static ModifyResult operateResult(int affectedRows, String prefix, int code, long uid) {
        String msg = "";
        if (affectedRows == 1) {
            msg = prefix + "成功";
        } else {
            msg = prefix + "失败";
        }

        return ModifyResult.builder()
                .affectedRows(affectedRows).message(msg).uid(uid)
                .success(affectedRows == 1).code(code).build();
    }

    public static ModifyResult operateResult(String msg, int affectedRows, int code, long uid) {
        return ModifyResult.builder()
                .code(code).affectedRows(affectedRows).uid(uid)
                .success(affectedRows == 1).message(msg).build();

    }
}
