package xyz.xcye.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @NotNull
    private String name;

    private int age;
    private String email;
}

class Test {
    public static void main(String[] args) {
        System.out.println(Pattern.matches("^[0-9]?\\d*$", "1df"));
    }
}