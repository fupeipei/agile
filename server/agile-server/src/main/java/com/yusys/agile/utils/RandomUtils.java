package com.yusys.agile.utils;

import java.util.Random;

public class RandomUtils {
    public static String randomCode(){
        String code = "";
        Random random = new Random();
        for(int i = 0 ; i < 6 ; i++){

            int a = random.nextInt(10);

            code = code + a;
        }
        return  code;
    }
}
