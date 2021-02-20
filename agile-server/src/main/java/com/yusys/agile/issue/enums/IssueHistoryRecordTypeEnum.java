package com.yusys.agile.issue.enums;

public enum IssueHistoryRecordTypeEnum {
    TYPE_NORMAL_TEXT("普通类型",new Byte("0")),
    TYPE_RICH_TEXT("富文本类型",new Byte("1")),
    TYPE_ATTACHMENT("附件类型",new Byte("2"));

    public Byte CODE;
    public String NAME;


    private IssueHistoryRecordTypeEnum(String name, Byte code){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (IssueHistoryRecordTypeEnum stateType : IssueHistoryRecordTypeEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueHistoryRecordTypeEnum getByCode(Byte code) {
        for (IssueHistoryRecordTypeEnum stateType : IssueHistoryRecordTypeEnum.values()) {
            if (stateType.CODE.equals( code)) {
                return stateType;
            }
        }
        return null;
    }
}
