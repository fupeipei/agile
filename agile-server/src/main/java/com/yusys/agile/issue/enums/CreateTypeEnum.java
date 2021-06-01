package com.yusys.agile.issue.enums;
/**
 *  @Description: 创建方式枚举
 *  @author: zhao_yd
 *  @Date: 2021/5/26 2:16 下午
 *
 */

public enum  CreateTypeEnum {

    KANBAN("从看板创建", 1),
    LIST("从列表创建", 2);


    public Integer CODE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    private String NAME;


    CreateTypeEnum(String name, Integer code) {
        this.CODE = code;
        this.NAME = name;
    }
}
