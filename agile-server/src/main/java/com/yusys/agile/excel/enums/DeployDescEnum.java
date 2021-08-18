package com.yusys.agile.excel.enums;

/**
 * @Date 2021/2/1
 * @Description 部署说明枚举
 */
public enum DeployDescEnum {

    /**
     * 部署说明枚举
     */
    NODEPLOYACTION("791113", "无部署动作"),
    INFORMANAGEDEV("791115", "信管开发"),
    INFORMANAGECONF("791116", "信管配置"),
    BILLINGPROCEDUCE("791100", "计费程序"),
    BILLINGCONF("791101", "计费配置"),
    ACCOUNTPROCEDUCE("791102", "帐处程序"),
    ACCOUNTCONF("791103", "帐处配置"),
    ABMPROCEDUCE("791104", "ABM程序"),
    ABMCONF("791105", "ABM配置"),
    ACCOUNTMANAGEPROCEDUCE("791106", "帐管程序"),
    ACCOUNTMANAGECONF("791107", "帐管配置"),
    INTERFACEPROGRAM("791108", "接口程序"),
    INTERFACECONF("791109", "接口配置"),
    OPENPROGRAM("791110", "开通程序"),
    OPENPCONF("791111", "开通配置"),
    DATARECOVERY("791112", "数据修复"),
    CLOUDDETAILPROGRAM("386003", "云详单程序"),
    ACCOUNTDETAILPROGRAM("386004", "帐详单程序"),
    IMAGEPROGRAM("386005", "影像程序");
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    DeployDescEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code) {
        DeployDescEnum[] values = DeployDescEnum.values();
        for (DeployDescEnum deployDescEnum : values) {
            if (deployDescEnum.getCode().equals(code)) {
                return deployDescEnum.getDesc();
            }
        }
        return "";
    }
}
