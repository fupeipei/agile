package com.yusys.agile.issue.enums;

public enum IssueTypeEnum {
    TYPE_EPIC("Epic", new Byte("1"), "业务需求"),
    TYPE_FEATURE("Feature", new Byte("2"), "研发需求"),
    TYPE_STORY("Story", new Byte("3"), "用户故事"),
    TYPE_TASK("Task", new Byte("4"), "任务"),
    TYPE_FAULT("Bug", new Byte("5"), "测试缺陷");

    public Byte CODE;
    public String NAME;
    public String DESC;


    private IssueTypeEnum(String name, Byte code, String desc) {
        this.CODE = code;
        this.NAME = name;
        this.DESC = desc;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueTypeEnum stateType : IssueTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueTypeEnum getByCode(Byte code) {
        for (IssueTypeEnum stateType : IssueTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    // 普通方法
    public static String getDesc(Byte code) {
        for (IssueTypeEnum stateType : IssueTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.DESC;
            }
        }
        return "";
    }
}
