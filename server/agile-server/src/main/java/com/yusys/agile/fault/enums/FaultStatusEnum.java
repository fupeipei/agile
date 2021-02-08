package com.yusys.agile.fault.enums;

/**
 * 缺陷状态枚举
 *
 * @create 2021-2-2 10:11
 */
public enum FaultStatusEnum {
    NEW(1000L, "待修复"),
    PROCESSING(1001L, "修复中"),
    FIXED(1002L, "已修复"),
    CHECK(1003L, "验证中"),
    CLOSED(1004L, "已关闭");

    public Long CODE;
    public String MSG;


    // 普通方法
    public static String getMsg(Long code) {
        for (FaultStatusEnum faultStatusEnum : FaultStatusEnum.values()) {
            if (faultStatusEnum.CODE.equals(code)) {
                return faultStatusEnum.MSG;
            }
        }
        return "";
    }



    FaultStatusEnum(Long CODE, String MSG) {
        this.CODE = CODE;
        this.MSG = MSG;
    }

    public Long getCode() {
        return this.CODE;
    }
}
