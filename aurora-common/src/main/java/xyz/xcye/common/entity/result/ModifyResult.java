package xyz.xcye.common.entity.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 这是增加，删除，修改数据表之后，返回的公用实体
 * <p>包含的字段有 影响的行数，message，原数据，修改之后的数据</p>
 * @author qsyyke
 */

@Builder
@Data
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
    @JsonIgnore
    private String message;

    /**
     * 响应码
     */
    @JsonIgnore
    private int code;

    /**
     * 主键uid
     */
    private long uid;

    public static ModifyResult operateResult(int affectedRows, String prefix, int code, long... uid) {
        String msg = "";
        if (affectedRows == 1) {
            msg = prefix + "成功";
        }else {
            msg = prefix + "失败";
        }

        long insertUid = 0L;
        if (uid.length >= 1) {
            insertUid = uid[0];
        }

        return ModifyResult.builder()
                .affectedRows(affectedRows).message(msg).uid(insertUid)
                .success(affectedRows == 1).code(code).build();
    }

    public static ModifyResult operateResult(String msg, int affectedRows,int code, long... uid) {
        long insertUid = 0L;
        if (uid.length >= 1) {
            insertUid = uid[0];
        }
        return ModifyResult.builder()
                .code(code).affectedRows(affectedRows).uid(insertUid)
                .success(affectedRows == 1).message(msg).build();

    }
}
