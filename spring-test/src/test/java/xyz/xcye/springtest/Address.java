package xyz.xcye.springtest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Address {

    private String city;

    public Address() {
    }

    public Address(String city) {
        this.city = city;
    }

}