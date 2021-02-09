package com.yusys.agile.issue.domain;

import java.util.Date;

/**
 * @Date 2021/2/4
 * @Description 项目中的工作项状态个数统计
 */
public class IssueProjectStatus {
    private Long projectId;
    private Date calculateDate;
    private Byte issueType;
    private Integer inSprint;
    private Integer finished;
    private Integer notStarted;
    private Date createTime;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Integer getInSprint() {
        return inSprint;
    }

    public void setInSprint(Integer inSprint) {
        this.inSprint = inSprint;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getNotStarted() {
        return notStarted;
    }

    public void setNotStarted(Integer notStarted) {
        this.notStarted = notStarted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
