package com.yusys.agile.burndown.dto;

/**
 * @Description 成员工作量/任务数
 * @Date 2020-07-01
 */
public class HistogramTaskDTO {

    /**
     * 处理人ID
     */
    private Long handler;

    /**
     * 处理人姓名
     */
    private String handlerName;

    /**
     * 团队成员剩余工时之和
     */
    private Integer sumRemainWorkLoad;
    /**
     * 团队成员实际工时之和
     */
    private Integer sumReallyWorkLoad;

    /**
     * 团队成员任务数
     */
    private Integer taskNum;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Long getHandler() {
        return handler;
    }

    public void setHandler(Long handler) {
        this.handler = handler;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Integer getSumRemainWorkLoad() {
        return sumRemainWorkLoad;
    }

    public void setSumRemainWorkLoad(Integer sumRemainWorkLoad) {
        this.sumRemainWorkLoad = sumRemainWorkLoad;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }


    public Integer getSumReallyWorkLoad() {
        return sumReallyWorkLoad;
    }

    public void setSumReallyWorkLoad(Integer sumReallyWorkLoad) {
        this.sumReallyWorkLoad = sumReallyWorkLoad;
    }

    @Override
    public String toString() {
        return "HistogramTaskDTO{" +
                "handler=" + handler +
                ", handlerName='" + handlerName + '\'' +
                ", sumReallyWorkLoad=" + sumReallyWorkLoad +
                ", taskNum=" + taskNum +
                '}';
    }
}
