package com.yusys.agile.sprint.dto;

import com.yusys.agile.project.dto.ProjectFaultDTO;
import com.yusys.agile.project.dto.ProjectStoryDTO;
import com.yusys.agile.project.dto.ProjectTaskDTO;
import com.yusys.agile.team.dto.TeamDTO;

import java.util.Date;
import java.util.List;

/**
 * 迭代DTO
 *

 * @create 2020-04-10 17:24
 */
public class SprintDTO {
    private Long sprintId;

    private Long projectId;

    private String sprintName;

    private String sprintDesc;

    private Byte status;

    private String state;

    private Date startTime;

    private Date endTime;

    private Date finishTime;

    private Long teamId;

    private String teamName;

    private Integer workHours;

    private String versionNumber;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String hold;

    private String improve;

    private String terminate;

    private String sprintDays;

    private List<Date> sprintDayList;

    private List<UserSprintHourDTO> members;

    private List<TeamDTO> teamDTOList;

    private Double sprintRate;

    private List<Long> issueIds;

    private Byte issueType;

    private Integer planDays;

    private ProjectStoryDTO story;

    private ProjectTaskDTO task;

    private ProjectFaultDTO fault;

    private Integer planWorkload;

    private Integer finishWorkload;

    private Integer userNum;

    private Integer pageNum;

    private Integer pageSize;

    public List<Long> getListStorys() {
        return listStorys;
    }

    public void setListStorys(List<Long> listStorys) {
        this.listStorys = listStorys;
    }

    /**
     * 未完成故事列表
     */
    private List<Long> listStorys;
    /**
     * 判断sprintType 是否为需求规划（demandPlan）是返回迭代下关联的故事ID（storys）
     */
    private String sprintType;
    /**
     *迭代下关联的故事IDS
     */
    private List<Long> storyIds;

    private Double progress;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName == null ? null : sprintName.trim();
    }

    public String getSprintDesc() {
        return sprintDesc;
    }

    public void setSprintDesc(String sprintDesc) {
        this.sprintDesc = sprintDesc == null ? null : sprintDesc.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold == null ? null : hold.trim();
    }

    public String getImprove() {
        return improve;
    }

    public void setImprove(String improve) {
        this.improve = improve == null ? null : improve.trim();
    }

    public String getTerminate() {
        return terminate;
    }

    public void setTerminate(String terminate) {
        this.terminate = terminate == null ? null : terminate.trim();
    }

    public String getSprintDays() {
        return sprintDays;
    }

    public void setSprintDays(String sprintDays) {
        this.sprintDays = sprintDays == null ? null : sprintDays.trim();
    }

    public List<Date> getSprintDayList() {
        return sprintDayList;
    }

    public void setSprintDayList(List<Date> sprintDayList) {
        this.sprintDayList = sprintDayList;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<UserSprintHourDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserSprintHourDTO> members) {
        this.members = members;
    }

    public List<TeamDTO> getTeamDTOList() {
        return teamDTOList;
    }

    public void setTeamDTOList(List<TeamDTO> teamDTOList) {
        this.teamDTOList = teamDTOList;
    }

    public Double getSprintRate() {
        return sprintRate;
    }

    public void setSprintRate(Double sprintRate) {
        this.sprintRate = sprintRate;
    }

    public List<Long> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(List<Long> issueIds) {
        this.issueIds = issueIds;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Integer getPlanDays() {
        return planDays;
    }

    public void setPlanDays(Integer planDays) {
        this.planDays = planDays;
    }

    public ProjectStoryDTO getStory() {
        return story;
    }

    public void setStory(ProjectStoryDTO story) {
        this.story = story;
    }

    public ProjectTaskDTO getTask() {
        return task;
    }

    public void setTask(ProjectTaskDTO task) {
        this.task = task;
    }

    public ProjectFaultDTO getFault() {
        return fault;
    }

    public void setFault(ProjectFaultDTO fault) {
        this.fault = fault;
    }

    public Integer getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(Integer planWorkload) {
        this.planWorkload = planWorkload;
    }

    public Integer getFinishWorkload() {
        return finishWorkload;
    }

    public void setFinishWorkload(Integer finishWorkload) {
        this.finishWorkload = finishWorkload;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSprintType() {
        return sprintType;
    }

    public void setSprintType(String sprintType) {
        this.sprintType = sprintType;
    }

    public List<Long> getStoryIds() {
        return storyIds;
    }

    public void setStoryIds(List<Long> storyIds) {
        this.storyIds = storyIds;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "SprintDTO{" +
                "sprintId=" + sprintId +
                ", projectId=" + projectId +
                ", sprintName='" + sprintName + '\'' +
                ", sprintDesc='" + sprintDesc + '\'' +
                ", status=" + status +
                ", state='" + state + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", finishTime=" + finishTime +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", workHours=" + workHours +
                ", versionNumber='" + versionNumber + '\'' +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", hold='" + hold + '\'' +
                ", improve='" + improve + '\'' +
                ", terminate='" + terminate + '\'' +
                ", sprintDays='" + sprintDays + '\'' +
                ", sprintDayList=" + sprintDayList +
                ", members=" + members +
                ", teamDTOList=" + teamDTOList +
                ", sprintRate=" + sprintRate +
                ", issueIds=" + issueIds +
                ", issueType=" + issueType +
                ", planDays=" + planDays +
                ", story=" + story +
                ", task=" + task +
                ", userNum=" + userNum +
                '}';
    }

}
