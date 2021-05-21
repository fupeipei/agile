package com.yusys.agile.issuev3.enums;

/**
 * 工作项优先级枚举
 *
 * @create 2020-06-09 16:25
 */
public enum IssuePriorityEnum {

    HIGH(new Byte("1"), "高"),
    MIDDLE(new Byte("2"), "中"),
    LOW(new Byte("3"), "低");


    public Byte CODE;
    public String NAME;


    private IssuePriorityEnum(Byte code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssuePriorityEnum stateType : IssuePriorityEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static Byte getCode(String name) {
        for (IssuePriorityEnum stateType : IssuePriorityEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
    }
}
