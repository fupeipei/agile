package com.yusys.agile.versionmanager.enums;

public enum VersionChangeEnum {
    /**
     * 绑定
     */
    ADD_OPERATION_TYPE((byte)0, "add"),

    /**
     * 解绑
     */
    DELETE_OPERATION_TYPE((byte)2, "delete"),

    /**
     * 重新绑定
     */
    DELETE_ADD_OPERATION_TYPE((byte)3, "change");

    private Byte code;
    private String value;

    private VersionChangeEnum(Byte code, String value) {
        this.code = code;
        this.value = value;
    }

    public Byte getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static Byte getCode(String value) {
        Byte code = null;
        for (VersionChangeEnum versionChangeEnum : VersionChangeEnum.values()) {
            if (versionChangeEnum.value.equals(value)) {
                code = versionChangeEnum.code;
            }
        }
        return code;
    }
}
