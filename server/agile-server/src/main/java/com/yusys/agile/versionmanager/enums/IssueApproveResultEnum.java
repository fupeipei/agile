package com.yusys.agile.versionmanager.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName VersionStatusEnum
 * @Description 审批结果
 * @Date 2020/8/19 09:54
 * @Version 1.0
 */
public enum IssueApproveResultEnum {
    NOT_APPROVE("0", "不通过"),
    APPROVING("1", "通过");
    public String CODE;
    public String NAME;

    private IssueApproveResultEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IssueApproveResultEnum statusEnum : IssueApproveResultEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueApproveResultEnum getByCode(String code) {
        for (IssueApproveResultEnum statusEnum : IssueApproveResultEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    // 普通方法
    public static String getDesc(String code) {
        for (IssueApproveResultEnum statusEnum : IssueApproveResultEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (IssueApproveResultEnum statusEnum : IssueApproveResultEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(), statusEnum.NAME);
        }
        return jsonObject;
    }
}
