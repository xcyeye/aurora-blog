package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {

    private String name;

    private int age;

    private Address address;

    public Student() {

    }

    public Student(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}