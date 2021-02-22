package com.yusys.agile.issue.enums;


public enum IssueField {
    /**
     *
     */
    ISSUEID("issueId", "工作项编号"),
    TITLE("title", "标题"),
    DESCRIPTION("description", "描述"),
    TYPE("issueType", "类型"),
    MODULE("moduleId", "模块"),
    SYSTEM("systemId", "系统"),
    priority("priority", "优先级"),
    BEGINDATE("beginDate", "开始日期"),
    ENDDATE("endDate", "结束日期"),
    PLANWORKLOAD("planWorkload", "预计工时"),
    REMAINWORKLOAD("remainWorkload", "剩余工时"),
    REALLYWORKLOAD("reallyWorkload", "实际工时"),
    STATE("state", "状态"),
    SPRINT("sprintId", "迭代计划"),
    HANDLER("handler", "处理人"),
    ATTACHMENT("attachments", "附件"),
    CREATENAME("createUid", "创建人"),
    CREATETIME("createTime", "创建时间"),
    ISCOLLECTION("isCollection", "是否收藏"),
    COMPLETION("completion", "完成情况"),
    IMPORTANCE("importance", "重要程度"),
    STAGEID("stageId", "阶段id"),
    LANEID("laneId", "二阶段状态id"),
    BLOCKSTATE("blockState", "阻塞状态"),
    ASSESSISPASS("assessIsPass", "故事验收状态"),
    ASSESSREMARKS("assessRemarks", "评审备注"),
    PARENTID("parentId", "父工作项ID"),
    ORDER("order", "业务价值"),
    EXTERNALHANDLERID("externalHandlerId", "测试负责人"),
    DEVLOPMANAGER("devlopManager", "开发负责人");

    private String key;
    private String desc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    IssueField(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}