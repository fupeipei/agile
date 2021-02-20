package com.yusys.agile.fault.enums;

/**
 * 缺陷类型枚举
 *
 * @create 2021-2-2 11:00
 */
public enum FaultTypeEnum {
    CODE_ERROR(1000L, "codeerror", "代码错误"),
    INTERFACE(1001L, "interface", "界面优化"),
    CONFIG(1002L, "config", "配置相关"),
    INSTALL(1003L, "install", "安装部署"),
    SECURITY(1004L, "security", "安全相关"),
    PERFORMANCE(1005L, "performance", "性能问题"),
    STANDARD(1006L, "standard", "标准规范"),
    AUTOMATION(1007L, "automation", "测试脚本"),
    DESION_DEFECT(1008L, "designdefect", "设计缺陷"),
    OTHERS(1009L, "others", "其他"),
    ONLINE(1010L, "online", "线上bug");

    private Long ID;
    private String CODE;
    private String MSG;

    FaultTypeEnum(Long ID, String CODE, String MSG) {
        this.ID = ID;
        this.CODE = CODE;
        this.MSG = MSG;
    }

    public Long getID() {
        return ID;
    }

    public String getCODE() {
        return CODE;
    }

    public String getMSG() {
        return MSG;
    }

    /**
     * 功能描述: 根据id返回对应的名称
     *
     * @param id
     * @return java.lang.String
     * @date 2021/2/2
     */
    public static String getMsgById(Long id) {
        for (FaultTypeEnum stateType : FaultTypeEnum.values()) {
            if (stateType.ID.equals(id)) {
                return stateType.MSG;
            }
        }
        return "";
    }

    /**
     * 功能描述: 根据id返回对应的名称
     *
     * @param code
     * @return java.lang.String
     * @date 2021/2/2
     */
    public static String getMsgByCode(String code) {
        for (FaultTypeEnum stateType : FaultTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.MSG;
            }
        }
        return "";
    }


    /**
     * 功能描述: 根据code返回对应的id
     *
     * @param code
     * @return java.lang.String
     * @date 2021/2/2
     */
    public static Long getIdByCode(String code) {
        for (FaultTypeEnum stateType : FaultTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.ID;
            }
        }
        return null;
    }


}
