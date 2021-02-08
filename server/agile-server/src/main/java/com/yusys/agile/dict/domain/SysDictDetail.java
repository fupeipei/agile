package com.yusys.agile.dict.domain;

import java.io.Serializable;

public class SysDictDetail implements Serializable {
    private Long id;

    private String dictCode;

    private String detailCode;

    private String detailName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode == null ? null : detailCode.trim();
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName == null ? null : detailName.trim();
    }
}