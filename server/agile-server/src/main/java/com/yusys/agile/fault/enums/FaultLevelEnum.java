package com.yusys.agile.fault.enums;

/**
 * 缺陷等级枚举
 *
 * @create 2021-2-2 10:51
 */
public enum FaultLevelEnum {
    INFO(1000L, "info", "提示"),
    NORMAL(1001L, "normal", "一般"),
    SERIOUS(1003L, "serious", "严重"),
    BLOCK(1004L, "block", "阻断");

    private Long ID;
    private String CODE;
    private String MSG;

    FaultLevelEnum(Long ID, String CODE, String MSG) {
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
        for (FaultLevelEnum stateType : FaultLevelEnum.values()) {
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
        for (FaultLevelEnum stateType : FaultLevelEnum.values()) {
            if (stateType.CODE.equals(code)) {
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
     * @date 2021/2/3
     */
    public static Long getIdByCode(String code) {
        for (FaultLevelEnum stateType : FaultLevelEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.ID;
            }
        }
        return null;
    }
}
