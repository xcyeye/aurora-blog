package xyz.xcye.common.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
