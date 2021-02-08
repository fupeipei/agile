package com.yusys.agile.utils;

public class ObjectUtil {
    private ObjectUtil(){

    }

    public static boolean equals(Object a,Object b){
        if(a==null&&b==null){
            return true;
        }else if(a==null&&b!=null){
            return false;
        }else if (a!=null&&b==null){
            return false;
        }else {
            return a.equals(b);
        }
    }
}
