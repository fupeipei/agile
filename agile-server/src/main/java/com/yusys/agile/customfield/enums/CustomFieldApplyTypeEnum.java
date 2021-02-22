package com.yusys.agile.customfield.enums;

/**
 * 自定义字段应用枚举
 *
 * @create 2021/2/1
 */
public enum CustomFieldApplyTypeEnum {
    PRODUCT("6", "产品"),
    PROJECT("7", "项目"),

    EPIC("1", "业务需求"),
    FEATURE("2", "研发需求"),
    STORY("3", "故事"),
    TASK("4", "任务"),
    FAULT("5", "缺陷");

    private String CODE;
    private String MSG;

    private CustomFieldApplyTypeEnum(String CODE, String MSG) {
        this.CODE = CODE;
        this.MSG = MSG;
    }

    public static String getMsg(String code) {
        CustomFieldApplyTypeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            CustomFieldApplyTypeEnum fieldEnum = var1[var3];
            if (fieldEnum.CODE.equals(code)) {
                return fieldEnum.MSG;
            }
        }

        return "";
    }

    public String getCODE() {
        return this.CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getMSG() {
        return this.MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
