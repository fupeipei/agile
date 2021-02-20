package com.yusys.agile.issue.domain;

import java.util.Date;

/**

 * @Date 2021/2/4
 * @Description 工作项状况 类型 1:epic 2:feature 3:story 4:task
 */
public class IssueStatus {
    private Long projectId;
    private Long sprintId;
    private Date sprintDate;
    private Byte issueType;
    private Integer inSprint;
    private Integer finished;
    private Integer notStarted;
    private Date createTime;

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Date getSprintDate() {
        return sprintDate;
    }

    public void setSprintDate(Date sprintDate) {
        this.sprintDate = sprintDate;
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
