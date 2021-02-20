package com.yusys.agile.fault.enums;

import com.yusys.agile.issue.enums.IssueTypeEnum;

/**
 * 系统来源
 *
 * @create 2021-2-2 20:39
 */
public enum FaultSourceEnum {
    YuDO2("YuDO2", new Byte("0"));

    public Byte CODE;
    public String NAME;


    private FaultSourceEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
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

    public static Byte getId(String name) {
        for (IssueTypeEnum stateType : IssueTypeEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
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
}
