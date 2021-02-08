package com.yusys.agile.headerfield.enums;

public enum IsCustomEnum {

    FALSE(Byte.parseByte("0"), "普通"),
    TRUE(Byte.parseByte("1"), "自定义");


    private Byte value;

    private String name;

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    IsCustomEnum(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
