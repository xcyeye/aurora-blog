package xyz.xcye.test;

import lombok.Data;
import xyz.xcye.common.valid.validator.ValidateString;
import xyz.xcye.common.valid.validator.Status;

/**
 * @author qsyyke
 */

@Data
public class UserInfo {

    @Status(value = "删除")
    private int age;

    @ValidateString(min = 1,max = 12,value = "姓名")
    private String name;
}
