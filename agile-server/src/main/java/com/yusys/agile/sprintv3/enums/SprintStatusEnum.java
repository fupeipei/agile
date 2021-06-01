package com.yusys.agile.sprintv3.enums;

/**
 * 迭代状态枚举
 *
 * @Date 2020/4/13
 */
public enum SprintStatusEnum {
    TYPE_INVALID_STAE("无效", new Byte("-1")),
    TYPE_CANCEL_STATE("已取消", new Byte("0")),
    TYPE_VALID_STATE("有效", new Byte("1")),
    TYPE_NO_START_STATE("未开始", new Byte("2")),
    TYPE_ONGOING_STATE("进行中", new Byte("3")),
    TYPE_FINISHED_STATE("已完成", new Byte("4"));


    public Byte CODE;
    private String NAME;


    SprintStatusEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (SprintStatusEnum stateType : SprintStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static SprintStatusEnum getByCode(Byte code) {
        for (SprintStatusEnum stateType : SprintStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
