package com.yusys.agile.user.dto;


/**

 * @Date: 2021/2/6
 */
public class ProjectUserDTO {
    private Long projectId;
    private Long userId;
    private String userName;
    private Integer hourNum;
    private Integer taskNum;
    private Integer submitOnceNum;
    private Integer submitLineNum;
    private Integer reduceLineNum;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getHourNum() {
        return hourNum;
    }

    public void setHourNum(Integer hourNum) {
        this.hourNum = hourNum;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Integer getSubmitOnceNum() {
        return submitOnceNum;
    }

    public void setSubmitOnceNum(Integer submitOnceNum) {
        this.submitOnceNum = submitOnceNum;
    }

    public Integer getSubmitLineNum() {
        return submitLineNum;
    }

    public void setSubmitLineNum(Integer submitLineNum) {
        this.submitLineNum = submitLineNum;
    }

    public Integer getReduceLineNum() {
        return reduceLineNum;
    }

    public void setReduceLineNum(Integer reduceLineNum) {
        this.reduceLineNum = reduceLineNum;
    }
}
