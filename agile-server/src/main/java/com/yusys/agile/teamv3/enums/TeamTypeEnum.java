package com.yusys.agile.teamv3.enums;

/**
 * 团队类型枚举
 * @Author zhaofeng
 * @Date 2021/6/11 10:42
 */
public enum TeamTypeEnum {

    agile_team("M","敏捷"),
    lean_team("N","精益");

    private String code;
    private String name;

    TeamTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code){
        TeamTypeEnum[] values = TeamTypeEnum.values();
        for (TeamTypeEnum value : values) {
            if(value.code.equals(code)){
                return value.name;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
