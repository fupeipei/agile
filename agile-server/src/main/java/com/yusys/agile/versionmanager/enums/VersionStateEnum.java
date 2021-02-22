package com.yusys.agile.versionmanager.enums;

import java.util.HashMap;
import java.util.Map;

public enum VersionStateEnum {

    VERSION_STATE_UNCONFIRMED("unconfirmed", "未确认"),
    VERSION_STATE_REVIEW("review", "审批中"),
    VERSION_STATE_BASELINE("baseline", "基线状态"),
    VERSION_STATE_REJECT("reject", "审批未通过"),
    VERSION_STATE_CONFIRMED("confirmed", "已确认"),
    VERSION_STATE_CHANGED("changed", "有变更"),
    VERSION_STATE_CHANGED_REVIEW("reviewChange", "变更审批中"),
    VERSION_STATE_TORELEASED("toReleased", "待发布"),
    VERSION_STATE_RELEASED("released", "已发布");

    public String CODE;
    public String NAME;

    VersionStateEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    public static String getName(String code) {
        for (VersionStateEnum stateType : VersionStateEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    public static VersionStateEnum getByCode(String code) {
        for (VersionStateEnum stateType : VersionStateEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    public static Map<String, String> getVersionStateMap() {
        Map<String, String> versionStateMap = new HashMap<>();
        for (VersionStateEnum stateType : VersionStateEnum.values()) {
            if (!stateType.CODE.equals(VERSION_STATE_BASELINE.CODE)) {
                versionStateMap.put(stateType.CODE, stateType.NAME);
            }
        }
        return versionStateMap;
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
