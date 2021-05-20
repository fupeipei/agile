package com.yusys.agile.teamv3.enums;

/**
 * @Author zhao
 * @Date 2021/4/22 18:14
 */
public enum QueryTypeEnum {
    /**
     * 我参与的
     */
    join(1),
    /**
     * 我创建的
     */
    created(2),
    /**
     * 与我相关的
     */
    have(3);

    private Integer type;

    QueryTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
