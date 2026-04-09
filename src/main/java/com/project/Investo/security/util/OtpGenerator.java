package com.project.Investo.security.util;



import java.util.Random;

public class OtpGenerator {

    public static String generate() {

        Random random = new Random();

        int number = 100000 + random.nextInt(900000);

        return String.valueOf(number);
    }
}