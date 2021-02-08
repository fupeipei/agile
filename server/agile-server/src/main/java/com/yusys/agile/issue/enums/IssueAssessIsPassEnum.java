package com.yusys.agile.issue.enums;

public enum IssueAssessIsPassEnum {
    NOT_PASS("未通过", new Byte("0")),
    PASS("通过", new Byte("1"));

    public Byte CODE;
    public String NAME;


    private IssueAssessIsPassEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueAssessIsPassEnum stateType : IssueAssessIsPassEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueAssessIsPassEnum getByCode(Byte code) {
        for (IssueAssessIsPassEnum stateType : IssueAssessIsPassEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
