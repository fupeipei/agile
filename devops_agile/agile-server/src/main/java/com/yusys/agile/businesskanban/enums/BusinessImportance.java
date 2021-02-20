package com.yusys.agile.businesskanban.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * @Date: 2021/2/1
 * @Description:  重要程度
 */
public enum BusinessImportance {
    GENERAL((byte)0, "一般"), IMPOTENCE((byte)1, "重要"), URGENT((byte)2, "紧急");

    private Byte key;
    private String desc;

    BusinessImportance(Byte key, String desc) {
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

    public static Map<Byte, String> getBusinessImportanceMap(){
        Map<Byte,String> businessImportanceMap = new HashMap<>();
        for(BusinessImportance type : BusinessImportance.values()){
            businessImportanceMap.put(
                    type.getKey(),
                    type.getDesc());
        }
        return businessImportanceMap;
    }
}
