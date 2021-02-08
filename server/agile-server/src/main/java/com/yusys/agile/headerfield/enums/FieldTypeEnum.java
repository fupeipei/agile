package com.yusys.agile.headerfield.enums;


public enum FieldTypeEnum {

    NUMBER(Byte.parseByte("0"), "number"),
    TEXT(Byte.parseByte("1"), "text"),
    TIMEDATE(Byte.parseByte("2"), "time_date"),
    SELECT(Byte.parseByte("3"), "select"),
    TREE(Byte.parseByte("4"), "tree"),
    DATE(Byte.parseByte("5"), "date"),
    TEXTAREA(Byte.parseByte("6"), "textarea");



    private Byte value;

    private String name;

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    FieldTypeEnum(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 普通方法
    public static String getName(Byte value) {
        for (FieldTypeEnum fieldType : FieldTypeEnum.values()) {
            if (fieldType.value.equals(value)) {
                return fieldType.name;
            }
        }
        return "";
    }
}
