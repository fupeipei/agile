package com.yusys.agile.issue.enums;

public enum IssueFilterCodeEnum {
    /**
     * 表记录状态枚举
     */
    HANDLER("处理人","handler"),
    ENDDATE("结束日期","endDate"),
    COMPLETION("完成度","completion"),
    STAGEID("阶段/状态","stageId"),
    SPRINTID("迭代计划","sprintId"),
    ISCOLLECT("是否收藏","isCollect"),
    CREATEUID("创建人ID","createUid");

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    IssueFilterCodeEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
