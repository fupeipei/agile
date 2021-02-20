package com.yusys.agile.versionmanager.enums;

public enum  CMPVersionStateEnum {
    VERSION_STATE_UNCONFIRMED("unconfirmed","5"),//未确认
    VERSION_STATE_REVIEW("review","6"),//审批中
    VERSION_STATE_BASELINE("baseline","7"),//基线状态
    VERSION_STATE_REJECT("reject","8"),//审批未通过
    VERSION_STATE_CONFIRMED("confirmed","9"),//已确认
    VERSION_STATE_CHANGED("changed", "10"),//有变更
    VERSION_STATE_CHANGED_REVIEW("reviewChange","11"),//变更审批中
    VERSION_STATE_TORELEASED("toReleased","12"),//待发布
    VERSION_STATE_RELEASED("released","13");//已发布

    private String code;
    private String state;

    CMPVersionStateEnum(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public static String getState(String code){
        String state = null;
        for (CMPVersionStateEnum cmpVersionStateEnum : CMPVersionStateEnum.values()) {
            if (cmpVersionStateEnum.code.equals(code)) {
                state = cmpVersionStateEnum.state;
            }
        }
        return state;
    }

}
