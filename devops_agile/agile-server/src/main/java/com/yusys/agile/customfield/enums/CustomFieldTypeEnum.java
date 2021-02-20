package com.yusys.agile.customfield.enums;

/**
 * 自定义字类型枚举
 *
 * @create 2021/2/1
 */
public enum CustomFieldTypeEnum {
    NUMBER(0, "数字"),
    Single_line_text(1, "单行文本"),
    EPIC(6, "多行文本"),
    FEATURE(5, "日期"),
    STORY(3, "下拉框"),
    TASK(7, "平台成员"),
    FAULT(8, "项目成员");

    private Integer CODE;
    private String MSG;

    private CustomFieldTypeEnum(Integer CODE, String MSG) {
        this.CODE = CODE;
        this.MSG = MSG;
    }

    public static String getMsg(Integer code) {
        CustomFieldTypeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            CustomFieldTypeEnum fieldEnum = var1[var3];
            if (fieldEnum.CODE.equals(code)) {
                return fieldEnum.MSG;
            }
        }

        return "";
    }

    public Integer getCODE() {
        return this.CODE;
    }

    public void setCODE(Integer CODE) {
        this.CODE = CODE;
    }

    public String getMSG() {
        return this.MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
