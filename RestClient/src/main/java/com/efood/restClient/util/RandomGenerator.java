package com.efood.restClient.util;

import java.util.Random;

public class RandomGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHA_NUMERIC_SPECIAL_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!£$%&/()=?'ìèé*+][òç@#°àù§,;.:-_|";

    public static String randomUsername(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomPassword(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_SPECIAL_STRING.length());
            builder.append(ALPHA_NUMERIC_SPECIAL_STRING.charAt(character));
        }
        return builder.toString();
    }


    public static String randomName(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*RANDOM_STRING.length());
            builder.append(RANDOM_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static int randomNumber(int init, int end){
        Random random = new Random();
        return random.nextInt(end)+init;
    }
}
