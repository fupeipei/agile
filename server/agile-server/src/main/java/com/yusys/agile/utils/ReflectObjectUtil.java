package com.yusys.agile.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * : wsh
 *
 * @Description: 反射工具类
 * @modefy 添加属性类型的判断
 */
public class ReflectObjectUtil {

    private ReflectObjectUtil() {

    }

    /**
     * 获取类的属性，包含父类中的属性
     *
     * @param clazz
     * @return Field
     */
    public static Field[] getDeclaredFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        // 递归获取父类中的属性
        if (!clazz.getSuperclass().equals(Object.class)) {
            result.addAll(Arrays.asList(getDeclaredFields(clazz.getSuperclass())));
        }
        Field[] fields = new Field[result.size()];
        return result.toArray(fields);
    }


    /**
     * 对象之间的复制
     *
     * @param source      S类型对象
     * @param destination D类型对象
     * @param <S>         要转换的类型
     * @param <D>         转换的目标类型
     */
    public static <S, D> void copyProperties(S source, D destination) {
        Class sourceClass = source.getClass();
        Class destClass = destination.getClass();
        Field[] destDeclaredFields = getDeclaredFields(destClass);
        Field[] sourceDeclaredFields = getDeclaredFields(sourceClass);
        for (Field each : destDeclaredFields) {
            each.setAccessible(true);
            String fieldName = each.getName();
            String typeName = each.getType().getName();
            if (!"serialVersionUID".equals(fieldName)) {
                try {
                    for (Field item : sourceDeclaredFields) {
                        if (!StringUtils.equals(fieldName, item.getName()) || !typeName.equals(item.getType().getName())) {
                            continue;
                        }
                        item.setAccessible(true);
                        each.set(destination, item.get(source));
                    }
                } catch (IllegalAccessException e) {
                    //忽略
                }
            }
        }
    }

    public static <S, D> D copyProperties(S source, Class<D> clazz) {
        D destination = null;
        Class sourceClass = source.getClass();
        Field[] destDeclaredFields = getDeclaredFields(clazz);
        Field[] sourceDeclaredFields = getDeclaredFields(sourceClass);
        try {
            destination = clazz.newInstance();
            for (Field each : destDeclaredFields) {
                each.setAccessible(true);
                String fieldName = each.getName();
                String typeName = each.getType().getName();
                if (!"serialVersionUID".equals(fieldName)) {
                    for (Field item : sourceDeclaredFields) {
                        if (!StringUtils.equals(fieldName, item.getName()) || !typeName.equals(item.getType().getName())) {
                            continue;
                        } else {
                            item.setAccessible(true);
                            each.set(destination, item.get(source));
                        }
                    }

                }
            }
        } catch (Exception e) {
            //忽略
        }
        return destination;
    }


    public static <S, D> List<D> copyProperties4List(List<S> source, Class<D> clazz) {
        List<D> result = Lists.newArrayList();
        for (S each : source) {
            result.add(copyProperties(each, clazz));
        }
        return result;
    }
}
