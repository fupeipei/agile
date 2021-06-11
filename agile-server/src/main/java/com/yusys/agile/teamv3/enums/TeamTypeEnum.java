package com.yusys.agile.teamv3.enums;

/**
 * 团队类型枚举
 * @Author zhaofeng
 * @Date 2021/6/11 10:42
 */
public enum TeamTypeEnum {

    agile_team("M","敏捷团队"),
    lean_team("N","精益团队");

    private String code;
    private String name;

    TeamTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }
}
