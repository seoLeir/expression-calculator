package io.seoLeir;

import io.seoLeir.util.ShuntingYard;

public class Main {
    public static void main(String[] args) {
        String test1 = "1+2/3";
        System.out.println(ShuntingYard.calculate(test1));
    }
}
