package com.yusys.agile.versionmanager.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName VersionStatusEnum
 * @Description 审批状态
 *
 * @Date 2020/8/19 09:54
 * @Version 1.0
 */
public enum IssueApproveStatusEnum {
    NOT_APPROVE("0","未审批"),
    APPROVING("1","审批中"),
    APPROVED("2","已审批");

    public String CODE;
    public String NAME;

    private IssueApproveStatusEnum(String code, String name){
        this.CODE=code;
        this.NAME=name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IssueApproveStatusEnum statusEnum : IssueApproveStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static IssueApproveStatusEnum getByCode(String code) {
        for (IssueApproveStatusEnum statusEnum : IssueApproveStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
    // 普通方法
    public static String getDesc(String code) {
        for (IssueApproveStatusEnum statusEnum : IssueApproveStatusEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }
    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (IssueApproveStatusEnum statusEnum : IssueApproveStatusEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(),statusEnum.NAME);
        }
        return jsonObject;
    }
}
