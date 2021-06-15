package com.yusys.agile.set.stage.enums;

/**
 *  @Description:
 *  @author: zhao_yd
 *  @Date: 2021/6/11 3:05 下午
 *
 */

public enum StageTypeEnum {
    TYPE_INVALID_STAE("无效", new Byte("-1")),
    TYPE_CANCEL_STATE("已取消", new Byte("0")),
    TYPE_VALID_STATE("有效", new Byte("1")),
    TYPE_NO_START_STATE("未开始", new Byte("2")),
    TYPE_ONGOING_STATE("进行中", new Byte("3")),
    TYPE_FINISHED_STATE("已完成", new Byte("4"));


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
