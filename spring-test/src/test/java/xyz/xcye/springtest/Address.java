package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
    @Test
    public void test1() {
        String key = "k1";
        switch (key) {
            case "k1":
                System.out.println(1);
                break;
            case "k2":
                System.out.println(2);
                break;
            default:
                System.out.println("dea");
        }
    }

    @Test
    public void testsdf() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","23489634");
        map.put("k1","v1");

    }

}