package com.yusys.agile.issue.enums;

/**
 * 工作项重要程度枚举
 *
 *     
 * @create 2020-06-09 16:18
 */
public enum IssueImportanceEnum {

    SERIOUS(new Byte("1"), "严重"),
    IMPORTANT(new Byte("2"), "重要"),
    NORMAL(new Byte("3"), "一般"),
    INFO(new Byte("4"), "信息");


    public Byte CODE;
    public String NAME;


    private IssueImportanceEnum(Byte code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueImportanceEnum stateType : IssueImportanceEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static Byte getCode(String name) {
        for (IssueImportanceEnum stateType : IssueImportanceEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
    }

}
