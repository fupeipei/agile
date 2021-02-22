package com.yusys.agile.versionmanager.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName VersionStatusEnum
 * @Description 部署类型枚举类型
 * @Date 2020/8/19 09:54
 * @Version 1.0
 */
public enum DeployTypeEnum {
    /**
     * 部署类型：0：常规部署 1：热部署
     */
    GENERALDEPLOYMENT(new Byte("0"), "常规部署"),
    HOTDEPLOYMENT(new Byte("1"), "热部署"),
    TMPEPLOYMENT(new Byte("2"), "临时部署"),
    OTHER(new Byte("3"), "其他");

    private Byte CODE;
    private String NAME;

    private DeployTypeEnum(Byte code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Byte code) {
        for (DeployTypeEnum statusEnum : DeployTypeEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static DeployTypeEnum getByCode(Byte code) {
        for (DeployTypeEnum statusEnum : DeployTypeEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    // 普通方法
    public static String getDesc(Byte code) {
        for (DeployTypeEnum statusEnum : DeployTypeEnum.values()) {
            if (statusEnum.CODE.equals(code)) {
                return statusEnum.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static JSONObject getAllRiskLevel() {
        JSONObject jsonObject = new JSONObject();
        for (DeployTypeEnum statusEnum : DeployTypeEnum.values()) {
            jsonObject.put(statusEnum.CODE.toString(), statusEnum.NAME);
        }
        return jsonObject;
    }

    // 普通方法
    public Byte getCode() {
        return CODE;
    }
}
