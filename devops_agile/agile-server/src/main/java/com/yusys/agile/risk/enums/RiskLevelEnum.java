package com.yusys.agile.risk.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName RiskLevelEnum
 * @Description 风险等级枚举类
 *
 * @Date 2020/8/11 14:42
 * @Version 1.0
 */
public enum RiskLevelEnum {
    /**
     * 风险等级：高中低
     */
    HIGH(new Byte("1"),"高"),
    MEDIUM(new Byte("2"),"中"),
    LOW(new Byte("3"),"低");

    private Byte CODE;
    private String NAME;

    private RiskLevelEnum(Byte code,String name){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (RiskLevelEnum levelEnum : RiskLevelEnum.values()) {
            if (levelEnum.CODE.equals(code)) {
                return levelEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static RiskLevelEnum getByCode(Byte code) {
        for (RiskLevelEnum levelEnum : RiskLevelEnum.values()) {
            if (levelEnum.CODE.equals(code)) {
                return levelEnum;
            }
        }
        return null;
    }
    // 普通方法
    public static String getDesc(Byte code) {
        for (RiskLevelEnum levelEnum : RiskLevelEnum.values()) {
            if (levelEnum.CODE.equals(code)) {
                return levelEnum.NAME;
            }
        }
        return "";
    }
    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (RiskLevelEnum levelEnum : RiskLevelEnum.values()) {
            jsonObject.put(levelEnum.CODE.toString(),levelEnum.NAME);
        }
        return jsonObject;
    }
}
