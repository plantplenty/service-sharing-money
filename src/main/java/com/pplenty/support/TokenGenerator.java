package com.pplenty.support;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by yusik on 2020/10/11.
 */
@Component
public class TokenGenerator {

    public static String UPPER_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String LOWER_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    public static String NUMBERS = "0123456789";
    public static String BASE = UPPER_ALPHABETS + LOWER_ALPHABETS + NUMBERS;
    public static int TOKEN_LENGTH = 3;
    public static Random RANDOM = new Random();

    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(BASE.charAt(RANDOM.nextInt(BASE.length())));
        }

        return sb.toString();
    }
}
