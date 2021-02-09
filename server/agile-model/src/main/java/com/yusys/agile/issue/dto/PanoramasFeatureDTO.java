package com.yusys.agile.issue.dto;

import java.util.List;

/**
 * @description
 * @date 2020/10/23
 */
public class PanoramasFeatureDTO {
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getFeatureStatus() {
        return featureStatus;
    }

    public void setFeatureStatus(String featureStatus) {
        this.featureStatus = featureStatus;
    }

    public String getSysTester() {
        return sysTester;
    }

    public void setSysTester(String sysTester) {
        this.sysTester = sysTester;
    }

    public String getSysTestStatus() {
        return sysTestStatus;
    }

    public void setSysTestStatus(String sysTestStatus) {
        this.sysTestStatus = sysTestStatus;
    }

    private Long issueId;
    private String systemName;
    private String handler;
    private String featureStatus;
    private String sysTester;
    private String sysTestStatus;

    public List<PanoramasStoryDTO> getStories() {
        return stories;
    }

    public void setStories(List<PanoramasStoryDTO> stories) {
        this.stories = stories;
    }

    private List<PanoramasStoryDTO> stories;
}
