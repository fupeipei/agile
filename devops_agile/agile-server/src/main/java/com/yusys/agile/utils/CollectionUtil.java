package com.yusys.agile.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seemotions on 2019/5/14.
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     *  将集合类型转换
     * @param strs
     * @return
     */
    public static List<Integer> transformType(List<String> strs){
        List<Integer> result = new ArrayList<>();
        for(String str : strs){
            if(!str.matches("^([0-9])+$")){
                continue;
            }
            result.add(Integer.parseInt(str));
        }
        return result;
    }

    /**
     * 将Object转换为集合
     * @param obj
     * @param clazz
     * @param <T>
     * @return 转换后的集合结果
     */
    public static <T> List<T> castToList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 比较两个集合是否相等
     * @param l0
     * @param l1
     * @return
     */
    public static boolean isListEqual(List l0, List l1){
        if (l0 == l1)
            return true;
        if (l0 == null && l1 == null)
            return true;
        if (l0 == null || l1 == null)
            return false;
        if (l0.size() != l1.size())
            return false;
        for (Object o : l0) {
            if (!l1.contains(o))
                return false;
        }
        for (Object o : l1) {
            if (!l0.contains(o))
                return false;
        }
        return true;
    }
}
