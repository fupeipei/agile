package com.yusys.agile.customfield.enums;

/**
 * 自定义字类型枚举
 *
 * @create 2021/2/1
 */
public enum CustomFieldTypeEnum {
    /**
     * 数字
     */
    number(0, "数字"),
    /**
     * 单行文本
     */
    single_line_text(1, "单行文本"),
    /**
     * 多行文本
     */
    multi_line_text(6, "多行文本"),
    /**
     * 日期
     */
    date_input(5, "日期"),
    /**
     * 下拉框
     */
    select_input(3, "下拉框"),

//    2021-6-8 这两个没用
    TASK(7, "平台成员"),
    FAULT(8, "项目成员");

    private Integer CODE;
    private String MSG;

    CustomFieldTypeEnum(Integer CODE, String MSG) {
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

}
