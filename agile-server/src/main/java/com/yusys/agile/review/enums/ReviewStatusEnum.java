package com.yusys.agile.review.enums;

import com.yusys.agile.issue.enums.IssueTypeEnum;

/**
 * 评审状态枚举
 *
 * @create 2020-09-08 14:24
 */
public enum ReviewStatusEnum {
    CANCEL("0", "已取消"),
    REVIEWING("3", "评审中"),
    PASS("1", "通过"),
    FAIL("2", "不通过");

    public String CODE;
    private String NAME;


    ReviewStatusEnum(String code, String name) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (ReviewStatusEnum stateType : ReviewStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    public static String getId(String name) {
        for (ReviewStatusEnum stateType : ReviewStatusEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
    }

    // 普通方法
    public static ReviewStatusEnum getByCode(String code) {
        for (ReviewStatusEnum stateType : ReviewStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
