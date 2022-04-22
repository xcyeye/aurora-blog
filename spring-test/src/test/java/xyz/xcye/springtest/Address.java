package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Address {

    public static void main(String[] args) {
        test(1,2,3,4,5);
        test(1);
    }

    private static void test(int... uid) {
        System.out.println(uid.length);
        if (uid.length > 1) {
            System.out.println(uid[0]);
        }
    }

}