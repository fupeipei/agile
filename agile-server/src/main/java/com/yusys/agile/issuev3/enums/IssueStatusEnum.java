package com.yusys.agile.issuev3.enums;

public enum IssueStatusEnum {
    TYPE_VALID("Epic", new Byte("0")),
    TYPE_INVALID("Feature", new Byte("1"));

    public Byte CODE;
    public String NAME;


    private IssueStatusEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueStatusEnum stateType : IssueStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueStatusEnum getByCode(Byte code) {
        for (IssueStatusEnum stateType : IssueStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
