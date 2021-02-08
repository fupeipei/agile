package com.yusys.agile.issue.dto;

import java.util.List;

/**
 * @ClassName DemandPlanDTO
 * @Description 需求规划DTO
 *
 * @Date 2021/2/24 14:02
 * @Version 1.0
 */
public class DemandPlanDTO {

    private List<IssueDTO> epics;

    private List<IssueDTO> features;

    private List<IssueDTO> storys;

    public List<IssueDTO> getEpics() {
        return epics;
    }

    public void setEpics(List<IssueDTO> epics) {
        this.epics = epics;
    }

    public List<IssueDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<IssueDTO> features) {
        this.features = features;
    }

    public List<IssueDTO> getStorys() {
        return storys;
    }

    public void setStorys(List<IssueDTO> storys) {
        this.storys = storys;
    }
}
