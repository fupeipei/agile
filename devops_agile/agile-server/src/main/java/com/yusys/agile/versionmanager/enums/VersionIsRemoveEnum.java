package com.yusys.agile.versionmanager.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName VersionStatusEnum
 * @Description 版本、需求是否移除

 * @Date 2020/8/19 09:54
 * @Version 1.0
 */
public enum VersionIsRemoveEnum {
    /**
     * 版本状态：0:已移除，1:未移除
     */
    FALSE("0","已移除"),
    TRUE("1","未移除");

    private String CODE;
    private String NAME;

    private VersionIsRemoveEnum(String code, String name){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (VersionIsRemoveEnum statusEnum : VersionIsRemoveEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static VersionIsRemoveEnum getByCode(String code) {
        for (VersionIsRemoveEnum statusEnum : VersionIsRemoveEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
    // 普通方法
    public static String getDesc(String code) {
        for (VersionIsRemoveEnum statusEnum : VersionIsRemoveEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }
    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (VersionIsRemoveEnum statusEnum : VersionIsRemoveEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(),statusEnum.NAME);
        }
        return jsonObject;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
