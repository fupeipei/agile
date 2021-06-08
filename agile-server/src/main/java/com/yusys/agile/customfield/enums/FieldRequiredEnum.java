package com.yusys.agile.customfield.enums;

/**
 * 自定义字段是否必填
 * @Author zhaofeng
 * @Date 2021/6/3 15:27
 */
public enum FieldRequiredEnum {
    /**
     * 非必填
     */
    no_required("E", "非必填字段"),
    /**
     * 必填
     */
    required("U", "必填字段");

    private String code;
    private String name;

    private FieldRequiredEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
