package com.yusys.agile.sysextendfield.enums;

public enum ReqGroupEnum {
    PERSONAL_BUSINESS_GROUP("个人业务组","001"),
    GROUP_BUSINESS_GROUP("集团业务组","002"),
    BASIC_SERVICES_GROUP("基础服务组","003"),
    ELECTRONIC_AUDIT_GROUP("电子稽核组","004"),
    PLANNING_GROUP("规划建设组","005"),
    IT_SUPPORT_DEMAND_GROUP_ONE("IT支撑室需求组1组","012"),
    SYSTEM_MAINTENANCE_GROUP("系统运维组","013"), //Electronic Business 电商
    IT_SUPPORT_DEMAND_GROUP_TWO("IT支撑室需求组2组","023"),
    BUSINESS_MAINTENANCE_GROUP("业务运维组","024"),//渠道管理系统
    SERVICE_SUPPORT_GROUP("服务支撑组","025"),
    CALL_MANAGEMENT_DEMAND_GROUP ("话管需求组","050");

    public String CODE;
    public String NAME;


    private ReqGroupEnum(String name, String code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (ReqGroupEnum stateType : ReqGroupEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static ReqGroupEnum getByCode(String code) {
        for (ReqGroupEnum stateType : ReqGroupEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
