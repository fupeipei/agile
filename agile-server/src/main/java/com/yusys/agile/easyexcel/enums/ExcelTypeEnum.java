package com.yusys.agile.easyexcel.enums;

/**
 *  @Description: excel枚举
 *  @author: zhao_yd
 *  @Date: 2021/5/28 4:42 下午
 *
 */
public enum ExcelTypeEnum {

    EPITC("Epic",(byte)1),
    FEATURE("Feature",(byte)2),
    STORY("Story",(byte)3),
    TASK("Task",(byte)4),
    SRPINT("Sprint",(byte)5);

    private String fieldName;
    private Byte fieldValue;

    ExcelTypeEnum(String fieldName, Byte fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static String getFieldName(Byte fieldValue) {
        String fieldName = "";
        for (ExcelTypeEnum reqSubject : ExcelTypeEnum.values()) {
            if (reqSubject.fieldValue.equals(fieldValue)) {
                fieldName = reqSubject.fieldName;
            }
        }
        return fieldName;
    }
}
