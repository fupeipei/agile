package com.yusys.agile.issue.enums;

public enum QueryTypeEnum {
    TYPE_VALID("不展开child", new Byte("1")),
    TYPE_INVALID("展开child", new Byte("2"));

    public Byte CODE;
    public String NAME;


    private QueryTypeEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (QueryTypeEnum stateType : QueryTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static QueryTypeEnum getByCode(Byte code) {
        for (QueryTypeEnum stateType : QueryTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
