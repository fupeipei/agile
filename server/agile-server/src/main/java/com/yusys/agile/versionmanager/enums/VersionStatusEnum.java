package com.yusys.agile.versionmanager.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName VersionStatusEnum
 * @Description 版本状态枚举类型
 * @Date 2020/8/19 09:54
 * @Version 1.0
 */
public enum VersionStatusEnum {
    /**
     * 版本状态：0:未确定，1:审批中，2:已确定，3:有变更,4
     */
    UNDETERMINED("0", "未确认"),
    UNDERAPPROVAL("1", "审批中"),
    CONFIRMED("2", "已确认"),
    MODIFY("3", "有变更"),
    VERSION_STATE_UNCONFIRMED("4", "未确认"),
    VERSION_STATE_BASELINE("5", "基线状态"),
    VERSION_STATE_REJECT("6", "审批未通过"),
    VERSION_STATE_CHANGED_REVIEW("7", "变更审批中"),
    VERSION_STATE_TORELEASED("8", "待发布"),
    VERSION_STATE_RELEASED("9", "已发布");

    private String CODE;
    private String NAME;

    private VersionStatusEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (VersionStatusEnum statusEnum : VersionStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static VersionStatusEnum getByCode(String code) {
        for (VersionStatusEnum statusEnum : VersionStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    // 普通方法
    public static String getDesc(String code) {
        for (VersionStatusEnum statusEnum : VersionStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (VersionStatusEnum statusEnum : VersionStatusEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(), statusEnum.NAME);
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
