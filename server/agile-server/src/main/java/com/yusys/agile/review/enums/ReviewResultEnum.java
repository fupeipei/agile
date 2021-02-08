package com.yusys.agile.review.enums;

import com.yusys.agile.issue.enums.IssueTypeEnum;

/**
 * 评审记录中的结果枚举
 *
 *     
 * @create 2020-09-08 14:24
 */
public enum ReviewResultEnum {
    WAIT("0", "待定"),
    PASS("1", "通过"),
    FAIL("2", "不通过");

    public String CODE;
    public String NAME;


    private ReviewResultEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (IssueTypeEnum stateType : IssueTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    public static String getId(String name) {
        for (ReviewResultEnum stateType : ReviewResultEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
    }

    // 普通方法
    public static ReviewResultEnum getByCode(String code) {
        for (ReviewResultEnum stateType : ReviewResultEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
