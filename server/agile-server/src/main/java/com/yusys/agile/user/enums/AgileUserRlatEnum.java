package com.yusys.agile.user.enums;

/**
 * 功能描述:敏捷业务和用户关系美剧
 *
 * @param
 * @date 2020/8/14
 * @return
 */
public enum AgileUserRlatEnum {
    MILESTONE("里程碑", 1),
    REVIEW("评审", 2);


    public Integer CODE;
    public String NAME;


    private AgileUserRlatEnum(String name, Integer code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (AgileUserRlatEnum stateType : AgileUserRlatEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static AgileUserRlatEnum getByCode(Integer code) {
        for (AgileUserRlatEnum stateType : AgileUserRlatEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
}
