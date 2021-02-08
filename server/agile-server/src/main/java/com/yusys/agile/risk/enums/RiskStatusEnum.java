package com.yusys.agile.risk.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName RiskStatusEnum
 * @Description 风险状态
 *
 * @Date 2020/8/11 14:56
 * @Version 1.0
 */
public enum RiskStatusEnum {
    /**
     * 风险状态
     */
    DOING(new Byte("0"),"待完成"),
    DONE(new Byte("1"),"已完成");

    public Byte CODE;
    public String NAME;

    private RiskStatusEnum(Byte code,String name){
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (RiskStatusEnum statusEnum : RiskStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static RiskStatusEnum getByCode(Byte code) {
        for (RiskStatusEnum statusEnum : RiskStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
    // 普通方法
    public static String getDesc(Byte code) {
        for (RiskStatusEnum statusEnum : RiskStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }
    // 普通方法
    public static JSONObject getAllRiskStatus() {
        JSONObject jsonObject = new JSONObject();
        for (RiskStatusEnum statusEnum : RiskStatusEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(),statusEnum.NAME);
        }
        return jsonObject;
    }
}
