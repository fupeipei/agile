package com.yusys.agile.projectmanager.enmu;

public enum StaticProjectDataEnum {
    PROJECT_STATUS(1,"项目状态"),
    PROJECT_TYPE(2,"项目类型"),
    PROJECT_MODE(3,"项目模式");

    private Integer CODE;

    private String NAME;

    public Integer getCODE() {
        return CODE;
    }

    public void setCODE(Integer CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    StaticProjectDataEnum(Integer CODE, String NAME) {
        this.CODE = CODE;
        this.NAME = NAME;
    }
}
