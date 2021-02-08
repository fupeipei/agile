package com.yusys.agile.requirement.enums;

public enum PlanStatesEnum {
    REQUIREMENT_CANCEL("需求取消","8880"),
    REQUIREMENT_SUSPEND("需求挂起","8881"),
    REQUIREMENT_SUSPEND_CANCEL("需求取消挂起","8882"),
    REQUIREMENT_DELAY("延期","8883"),
    REQUIREMENT_NO_DEPLOY("无需部署","8884"),
    REQUIREMENT_ON_TIME("按期","8888"),
    REQUIREMENT_BY_TIME("分期","8889");

    public String CODE;
    public String NAME;


    PlanStatesEnum(String name, String code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (PlanStatesEnum stateType : PlanStatesEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static PlanStatesEnum getByCode(String code) {
        for (PlanStatesEnum stateType : PlanStatesEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
