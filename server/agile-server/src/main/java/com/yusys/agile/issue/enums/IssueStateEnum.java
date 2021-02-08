package com.yusys.agile.issue.enums;

public enum IssueStateEnum {
    TYPE_VALID("有效","U"),
    TYPE_INVALID("失效","E");

    public String CODE;
    public String NAME;


    private IssueStateEnum(String name, String code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IssueStateEnum stateType : IssueStateEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueStateEnum getByCode(String code) {
        for (IssueStateEnum stateType : IssueStateEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
