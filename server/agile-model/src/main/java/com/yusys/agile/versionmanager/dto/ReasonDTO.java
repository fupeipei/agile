package com.yusys.agile.versionmanager.dto;

/**
 *
 * @description
 * @date 2021/3/28
 */
public class ReasonDTO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    private Integer id;
    private String reasonDesc;
}
