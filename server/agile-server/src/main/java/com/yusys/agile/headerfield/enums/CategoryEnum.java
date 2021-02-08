package com.yusys.agile.headerfield.enums;

public enum CategoryEnum {

    EPIC(Byte.parseByte("1"), "epic"),
    FEATURE(Byte.parseByte("2"), "feature"),
    STORY(Byte.parseByte("3"), "story"),
    TASK(Byte.parseByte("4"), "task"),
    FAULT(Byte.parseByte("5"), "bug");


    private Byte value;

    private String name;

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    CategoryEnum(Byte value, String name) {
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
