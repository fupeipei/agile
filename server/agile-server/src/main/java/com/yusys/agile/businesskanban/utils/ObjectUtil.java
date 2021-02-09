package com.yusys.agile.businesskanban.utils;

/**
 * @Date: 2021/2/1
 * @Description: 工具类
 */
public class ObjectUtil {

    public ObjectUtil() {

    }

    public static boolean equals(Object a, Object b) {
        if (null == a && null == b) {
            return true;
        } else if (null == a && null != b) {
            return false;
        } else if (null != a && null == b) {
            return false;
        } else {
            return a.equals(b);
        }
    }
}
