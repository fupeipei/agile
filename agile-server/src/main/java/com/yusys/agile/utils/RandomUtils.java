package com.yusys.agile.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {
    private static Random rand;

    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static String randomCode() {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {

            int a = rand.nextInt(10);

            code = code + a;
        }
        return code;
    }
}
