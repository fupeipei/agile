package com.yusys.agile.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.LinkedHashSet;

public class StringTool {

    public static boolean isChanged(String newVal, String oldVal) {
        if (StringUtils.isNotBlank(newVal) && !StringUtils.equals(newVal,oldVal)) {
            return true;
        }
        return false;
    }

    public static String convertCollectionToString(Collection<?> collection) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(collection)) {
            for (Object obj : collection) {
                builder.append(obj).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        testConvertCollectionToString();
    }

    private static void testConvertCollectionToString() {
        LinkedHashSet<Long> idHashSet = new LinkedHashSet<>();
        idHashSet.add(10L);
        idHashSet.add(12L);
        idHashSet.add(14L);
        idHashSet.add(10L);
        idHashSet.add(16L);
        String idStr = convertCollectionToString(idHashSet);
        System.out.println(idStr);
    }
}
