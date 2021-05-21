package com.yusys.agile.issuev3.enums;

/**
 * @ClassName IssueCompletionEnum
 * @Description 完成度枚举
 * @Date 2021/2/16 11:45
 * @Version 1.0
 */
public enum IssueCompletionEnum {

    /**
     * 对应数据库中的值
     */
    ONE("1", "20%"),
    TWO("2", "40%"),
    THREE("3", "60%"),
    FOUR("4", "80%"),
    FIVE("5", "100%");
    public String CODE;
    public String NAME;


    private IssueCompletionEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IssueCompletionEnum stateType : IssueCompletionEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }
}
