package com.yusys.agile.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *   :
 * @Date: 2021/2/6
 * @Description: TODO
 */
public class StringUtil {
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {

    }

    public static List<Long> StringtoListLong(String string) {

        List<String> list = Lists.newArrayList(string.split(","));
        List<Long> longList = Lists.newArrayList();
        try {
            if (list.size() > 0) {
                for (String str : list) {
                    longList.add(Long.parseLong(str));
                }
            }
        } catch (Exception e) {
            log.error("StringUtil异常：" + e);
        }
        return longList;
    }

    public static Long StringtoLong(String string) {

        try {
            return Long.parseLong(string);
        } catch (Exception e) {
            log.error("StringUtil异常：" + e);
            return null;
        }
    }

    public static Integer StringtoInteger(String string) {

        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            log.error("StringUtil异常：" + e);
            return null;
        }
    }

    /**
     * 功能描述:判断字符串是否是正整数
     *
     * @date 2021/2/10
     * @param string
     * @return boolean
     */
    public static boolean isPureDigital(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String regEx1 = "^[1-9]\\d*$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @description 判断字符串是否修改
     * @param newVal
     * @param oldVal
     * @return boolean
     *    HaoXU
     * @date 2020/9/30
     **/
    public static boolean isChanged(String newVal, String oldVal) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(newVal) && !org.apache.commons.lang.StringUtils.equals(newVal,oldVal)) {
            return true;
        }
        return false;
    }

    /**
     * @description 将集合转换成字符串，字符串之间逗号间隔
     * @param collection
     * @return java.lang.String
     *    HaoXU
     * @date 2020/9/30
     **/
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

//    public static void main(String[] args){
//        System.out.println(isPureDigital("0"));
//    }
    /**
     * String字符串转成List<Byte>数据格式
     * String str = "1,2,3,4,5,6" -> List<Long> listLong [1,2,3,4,5,6];
     *
     * @param
     * @returnstrArr
     */
    public static List<Byte> stringToByteList(List<String> stringList) {
        return   stringList.stream()
                .map(s -> Byte.parseByte(s.trim()))
                .collect(Collectors.toList());
    }
    public static List<Date> stringToDateList(List<String> stringList) {
        return  stringList.stream()
                .map(s -> new Date(s.trim()))
                .collect(Collectors.toList());
    }
    public static List<Integer> stringToIntegerList(List<String> stringList) {
        return  stringList.stream()
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }
    public static List<Long> stringToLongrList(List<String> stringList) {
        return  stringList.stream()
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
    }

    /**
     * 判断某个字符串是否存在于数组中
     *
     * @param stringArray 原数组
     * @param source 查找的字符串
     * @return 是否找到
     */
    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);
        // 利用list的包含方法,进行判断
        if(tempList.contains(source)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除数组中某个字符串
     *
     * @param substring 某字符串
     * @param source 源字符串数组
     * @return 包含则返回true，否则返回false
     */
    public static List<String> removeNULL(String substring, String[] source) {
        List<String> sourceList = new ArrayList<>(Arrays.asList(source));
        for (int i = 0; i < sourceList.size(); i++) {
            if (sourceList.get(i).equals(substring)) {
                sourceList.remove(i);
            }
        }
        return sourceList;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
