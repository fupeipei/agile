package com.yusys.agile.set.stage.enums;

/**
 *  @Description:
 *  @author: zhao_yd
 *  @Date: 2021/6/11 3:05 下午
 *
 */

public enum StageTypeEnum {

    AGILE("敏捷", new Byte("1")),
    LEAN("精益", new Byte("2"));


    public Byte CODE;
    private String NAME;


    StageTypeEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (StageTypeEnum stateType : StageTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static StageTypeEnum getByCode(Byte code) {
        for (StageTypeEnum stateType : StageTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
