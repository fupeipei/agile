package com.yusys.agile.requirement.enums;

public enum RelatedSystemEnum {
    TYPE_CRM("CRM",new Byte("1")),
    TYPE_NGBOSS("NGBOSS",new Byte("2")),
    TYPE_EB("电子商务系统",new Byte("3")), //Electronic Business 电商
    TYPE_ESOP("ESOP",new Byte("8")),
    TYPE_CHANNEL("渠道管理系统",new Byte("12")),//渠道管理系统
    TYPE_APP("APP客户端",new Byte("33"));

    public Byte CODE;
    public String NAME;


    private RelatedSystemEnum(String name, Byte code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (RelatedSystemEnum stateType : RelatedSystemEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static RelatedSystemEnum getByCode(Byte code) {
        for (RelatedSystemEnum stateType : RelatedSystemEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
