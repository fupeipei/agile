package com.yusys.agile.versionmanager.dto;

public class BjReceiveVersionStateFromCMP {
    private Integer versionId;
    private Integer publishState;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }
}
