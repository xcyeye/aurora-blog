package com.example.webflux.test;

import java.util.function.Predicate;

/**
 * @author qsyyke
 */


public class MainTest2 {
    public static void main(String[] args) {
        Predicate<Integer> predicate = x -> x > 7;

        System.out.println(predicate.test(1));
        System.out.println(predicate.test(8));

        predicate = predicate.and(t -> t > 10);

        System.out.println(predicate.test(83));

        predicate = predicate.negate();

        System.out.println(predicate.test(8));

    }
}
