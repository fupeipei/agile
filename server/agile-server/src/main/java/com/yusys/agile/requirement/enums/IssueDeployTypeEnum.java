package com.yusys.agile.requirement.enums;

public enum IssueDeployTypeEnum {
    TYPE_HOT_DEPLOY("热部署",new Byte("0")),
    TYPE_TEMP_DEPLOY("临时部署",new Byte("1")),
    TYPE_NO_DEPLOY("无需部署",new Byte("2")),
    TYPE_NORMAL_DEPLOY("常规部署",new Byte("3"));

    public Byte CODE;
    public String NAME;


    private IssueDeployTypeEnum(String name, Byte code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueDeployTypeEnum stateType : IssueDeployTypeEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueDeployTypeEnum getByCode(Byte code) {
        for (IssueDeployTypeEnum stateType : IssueDeployTypeEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
