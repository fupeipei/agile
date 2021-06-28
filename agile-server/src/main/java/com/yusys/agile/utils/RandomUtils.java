package com.yusys.agile.utils;

import lombok.extern.slf4j.Slf4j;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class RandomUtils {
    private static Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
           log.error("RandomUtils init rand error", e.getMessage());
        }
    }

    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static String randomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            int a = rand.nextInt(10);
            code = code + a;
        }
        return code;
    }
}
