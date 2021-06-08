package com.yusys.agile.issue.enums;

public enum IsAchiveEnum {
    ACHIVEA_TRUE("已归档", new Byte("1")),
    ACHIVEA_FALSE("未归档", new Byte("0"));

    public Byte CODE;
    private String NAME;


    IsAchiveEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IsAchiveEnum stateType : IsAchiveEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IsAchiveEnum getByCode(Byte code) {
        for (IsAchiveEnum stateType : IsAchiveEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
