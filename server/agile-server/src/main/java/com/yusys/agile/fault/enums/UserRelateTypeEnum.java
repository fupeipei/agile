package com.yusys.agile.fault.enums;

import lombok.Getter;

/**
 * 用户关系枚举
 *
 * @create 2021-2-2 16:22
 */
public enum UserRelateTypeEnum {
    //    PROJECT(1, "项目成员"),
//    SYSTEM(2, "系统成员"),
//    APPLICATION(3, "应用成员");
    PROJECT(2, "项目级");

    @Getter
    private int value;
    @Getter
    private String name;

    UserRelateTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
