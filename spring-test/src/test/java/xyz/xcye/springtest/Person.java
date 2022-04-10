package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {

    private String name;

    private int age;

    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

}