package com.yusys.agile.businesskanban.enums;

import java.util.HashMap;
import java.util.Map;

public enum BusinessState {
    //事务状态 0:待领取、 1：待处理、2：处理中，4：完成
    UNCLAIMED((byte) 0, "未领取"),
    PENDING((byte) 1, "已领取"),
    PROCESSING((byte) 2, "进行中"),
    COMPLETE((byte) 3, "已完成");

    public static Map<Byte, String> getBusinessStateMap() {
        Map<Byte, String> businessStateMap = new HashMap<>();
        for (BusinessState type : BusinessState.values()) {
            businessStateMap.put(
                    type.getNodeCode(),
                    type.getNodeMsg());
        }
        return businessStateMap;
    }

    private Byte nodeCode;
    private String nodeMsg;

    BusinessState(Byte nodeCode, String nodeMsg) {
        this.nodeCode = nodeCode;
        this.nodeMsg = nodeMsg;
    }

    public Byte getNodeCode() {
        return nodeCode;
    }

    public String getNodeMsg() {
        return nodeMsg;
    }

    // 普通方法
    public static String getNodeMsg(Byte nodeCode) {
        for (BusinessState businessState : BusinessState.values()) {
            if (businessState.nodeCode.equals(nodeCode)) {
                return businessState.nodeMsg;
            }
        }
        return "";
    }
}
