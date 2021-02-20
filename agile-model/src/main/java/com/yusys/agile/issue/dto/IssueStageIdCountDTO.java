package com.yusys.agile.issue.dto;

/**

 * @Date: 2021/2/19
 */
public class IssueStageIdCountDTO {
    private Long projectId;

    private Long systemId;

    private String systemName;

    private Long versionId;

    private String versionName;

    //就绪阶段
    private Integer readyStageNum;
    //分析阶段
    private Integer analysisStageNum;
    //设计阶段
    private Integer designStageNum;
    //开发阶段
    private Integer developStageNum;
    //测试阶段
    private Integer testStageNum;
    //上线阶段
    private Integer onlineStageNum;
    //完成阶段
    private Integer finishStageNum;
    //联调测试
    private Integer debugTestNum;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getReadyStageNum() {
        return readyStageNum;
    }

    public void setReadyStageNum(Integer readyStageNum) {
        this.readyStageNum = readyStageNum;
    }

    public Integer getAnalysisStageNum() {
        return analysisStageNum;
    }

    public void setAnalysisStageNum(Integer analysisStageNum) {
        this.analysisStageNum = analysisStageNum;
    }

    public Integer getDesignStageNum() {
        return designStageNum;
    }

    public void setDesignStageNum(Integer designStageNum) {
        this.designStageNum = designStageNum;
    }

    public Integer getDevelopStageNum() {
        return developStageNum;
    }

    public void setDevelopStageNum(Integer developStageNum) {
        this.developStageNum = developStageNum;
    }

    public Integer getTestStageNum() {
        return testStageNum;
    }

    public void setTestStageNum(Integer testStageNum) {
        this.testStageNum = testStageNum;
    }

    public Integer getOnlineStageNum() {
        return onlineStageNum;
    }

    public void setOnlineStageNum(Integer onlineStageNum) {
        this.onlineStageNum = onlineStageNum;
    }

    public Integer getFinishStageNum() {
        return finishStageNum;
    }

    public void setFinishStageNum(Integer finishStageNum) {
        this.finishStageNum = finishStageNum;
    }

    public Integer getDebugTestNum() {
        return debugTestNum;
    }

    public void setDebugTestNum(Integer debugTestNum) {
        this.debugTestNum = debugTestNum;
    }
}
