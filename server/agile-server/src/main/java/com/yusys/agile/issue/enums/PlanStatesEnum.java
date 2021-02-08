package com.yusys.agile.issue.enums;

public enum PlanStatesEnum {
    DEMAND_CANCEL("需求取消","8880"),
    DEMAND_SUSPENSION("需求挂起","8881"),
    DEMAND_CANCEL_SUSPENSION("需求取消挂起","8882"),
    DEMAND_DELAY("延期","8883"),
    NO_NEED_DEPLOY("无需部署","8884"),
    PERIODIZATION("分期","8889"),
    ON_TIME("按期","8888");

    public String CODE;
    public String NAME;


    private PlanStatesEnum(String name, String code){
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
