package com.yusys.agile.businesskanban.enums;

public enum BusinessField {
    BUSINESSID("businessId","事务ID"),BUSINESSNAME("businessName","事务标题"),BUSINESSDESC("businessDesc","描述信息"),
    BUSINESSTYPE("businessType","事务类型"),BUSINESSTATE("businessState","事务状态"),ISVISIBLE("isVisible","是否归档"),
    BUSINESSOWNER("businessOwner","领取人"),BUSINESSOWNERNAME("businessOwnerName","领取人"),
    BUSINESSLEVEL("businessLevel","优先级"),IMPORTANCE("businessImportance","重要程度"),
    STARTTIME("startTime","实际开始日期"),ENDTIME("endTime","实际结束日期"),PLANWORKLOAD("planWorkload","预计工时"),
    ACTUALWORKLOAD("actualWorkload","实际工时"),PLANSTARTTIME("planStartTime","预计开始日期"),
    PLANENDTIME("planEndTime","预计结束日期"),STATUS("status","状态");


    private String key;
    private String desc;

    BusinessField(String key,String desc) {
        this.key = key;
        this.desc = desc;
    }

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
}
