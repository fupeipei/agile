package com.yusys.agile.businesskanban.enums;

import java.util.HashMap;
import java.util.Map;

public enum BusinessType {

    //事务类型 1000：学习、1001：会议、1002：培训、1003：评审、1004：其他
    STUDY(1000L, "学习类"),
    MEETING(1001L, "会议类"),
    REVIEW(1002L, "评审类"),
    TRAIN(1003L, "培训类"),
    REQUIREMENT_ANALYSIS(1004L,"需求分析"),
    DOCUMENT(1005L,"文档类"),
    TECHNICAL_RESEARCH(1006L,"技术调研"),
    OTHER(1007L, "其他");

    public static Map<Long,String> getBusinessTypeMap(){
        Map<Long,String> businessTypeMap = new HashMap<>();
        for(BusinessType type : BusinessType.values()){
            businessTypeMap.put(
                    type.getNodeCode(),
                    type.getNodeMsg());
        }
        return businessTypeMap;
    }

    private Long nodeCode;
    private String nodeMsg;

    BusinessType(Long nodeCode, String nodeMsg) {
        this.nodeCode = nodeCode;
        this.nodeMsg = nodeMsg;
    }

    public Long getNodeCode() {
        return nodeCode;
    }

    public String getNodeMsg() {
        return nodeMsg;
    }
    // 普通方法
    public static String getNodeMsg(Long nodeCode) {
        for (BusinessType businessType : BusinessType.values()) {
            if (businessType.nodeCode.equals(nodeCode)) {
                return businessType.nodeMsg;
            }
        }
        return "";
    }
}
