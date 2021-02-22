package com.yusys.agile.businesskanban.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2021/2/1
 * @Description: 级别
 */
public enum BusinessLevel {
    GENERA((byte) 0, "低"), SECONDARY((byte) 1, "中"), HIGH((byte) 2, "高");

    private Byte key;
    private String desc;

    BusinessLevel(Byte key, String desc) {
        this.desc = desc;
        this.key = key;
    }

    public Byte getKey() {
        return key;
    }

    public void setKey(Byte key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<Byte, String> getBusinessLevelMap() {
        Map<Byte, String> businessLevelMap = new HashMap<>();
        for (BusinessLevel type : BusinessLevel.values()) {
            businessLevelMap.put(
                    type.getKey(),
                    type.getDesc());
        }
        return businessLevelMap;
    }
}
