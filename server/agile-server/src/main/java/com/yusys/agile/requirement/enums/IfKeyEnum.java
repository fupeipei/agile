package com.yusys.agile.requirement.enums;

public enum IfKeyEnum {
    YSE("重点需求","1"),
    NO("非重点需求","0");

    public String CODE;
    public String NAME;


    IfKeyEnum(String name, String code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IfKeyEnum stateType : IfKeyEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IfKeyEnum getByCode(String code) {
        for (IfKeyEnum stateType : IfKeyEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
