package com.yusys.agile.issue.enums;

public enum ReturnFeatureEnum {
    RETURN_FEATURE("返回","1"),
    RETURN_STORYID("不返回","0");

    private String NAME;
    public String CODE;

    ReturnFeatureEnum(String name, String code) {
        this.CODE = code;
        this.NAME = name;
    }


    // 普通方法
    public static String getName(String code) {
        for (ReturnFeatureEnum stateType : ReturnFeatureEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static ReturnFeatureEnum getByCode(String code) {
        for (ReturnFeatureEnum stateType : ReturnFeatureEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }



}
