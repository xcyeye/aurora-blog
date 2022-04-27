package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
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
        Map<String,Object> map = new HashMap<>();
        map.put("s1","v1");
        map.put("s2","v2");
        map.put("s3","v3");
        map.put("s4","v4");

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println(key + "--->" + value);
        }
    }

}