package com.yusys.agile.issue.enums;
/**
 *  @Description: epic发起排期状态
 *  @author: zhao_yd
 *  @Date: 2021/7/21 9:41 上午
 *
 */


public enum StartScheduleStatusEnum {

    NO_TINITIATED ("未发起", (byte)0),
    INITIATED("已发起", (byte)1),
    FINISH ("结束", (byte)2);


    public Byte CODE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    private String NAME;


    StartScheduleStatusEnum(String name, Byte code) {
        this.CODE = code;
        this.NAME = name;
    }
}
