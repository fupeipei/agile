package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtProjectproductKey implements Serializable {
    private Integer project;

    private Integer product;

    private static final long serialVersionUID = 1L;

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }
}