package xyz.xcye.common.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * 这是增加，删除，修改数据表之后，返回的公用实体
 * <p>包含的字段有 影响的行数，message，原数据，修改之后的数据</p>
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
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
    private String message;

    /**
     * 修改之后的数据
     */
    private Object modifiedData;

    public static ModifyResult operateResult(int affectedRows, String prefix, Object returnData) {
        String msg = "";
        if (affectedRows == 1) {
            msg = prefix + "成功";
        }else {
            msg = prefix + "失败";
        }

        if (affectedRows != 1 || returnData == null) {
            // 如果affectedRows为0，则返回空数据
            returnData = new HashMap<>();
        }

        return new ModifyResult(affectedRows,affectedRows == 1,msg,returnData);
    }

    public static ModifyResult operateResult(String msg, Object returnData, int affectedRows ) {

        if (affectedRows != 1 || returnData == null) {
            // 如果affectedRows为0，则返回空数据
            returnData = new HashMap<>();
        }

        return new ModifyResult(affectedRows,affectedRows == 1,msg,returnData);
    }
}
