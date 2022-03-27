package com.example.webflux.test;

/**
 * @author qsyyke
 */


public class User {

    private String name;
    private String sex;

    public static User re(String f) {
        User user = new User();
        user.setName(f);
        return user;
    }

    public static String get(String a) {
        return "这是有参" + a;
    }

    public static String set() {
        return "除尘";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static User re() {
        return new User("sdf","sadf");
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public User() {
    }
}
