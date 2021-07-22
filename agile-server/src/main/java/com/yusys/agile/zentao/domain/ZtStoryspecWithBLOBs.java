package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtStoryspecWithBLOBs extends ZtStoryspec implements Serializable {
    private String spec;

    private String verify;

    private static final long serialVersionUID = 1L;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify == null ? null : verify.trim();
    }
}