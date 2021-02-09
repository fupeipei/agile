package com.yusys.agile.issue.enums;

/**
 * @Date 2021/2/11
 * @Description 任务和缺陷阻塞状态枚举
 */
public enum IssueBlockStateEnum {
    NOT_BLOCK("未阻塞", new Byte("0")),
    BLOCK("阻塞中", new Byte("1"));

    public Byte CODE;
    public String NAME;


    private IssueBlockStateEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueBlockStateEnum stateType : IssueBlockStateEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueBlockStateEnum getByCode(Byte code) {
        for (IssueBlockStateEnum stateType : IssueBlockStateEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
