package com.yusys.agile.fault.dto;

/**
 * 缺陷修复DTO
 *
 *
 * @create 2020-04-26 15:22
 */
public class FaultFixDTO {

    /**
     * bugId
     */
    private Long bugId;
    /**
     * 处理人
     */
    private Long handleUserId;

    /**
     * 处理人
     */
    private Long handle_user_id;
    /**
     * 解决方案
     */
    private String resolution;
    /**
     * 解决时间
     */
    private String resolved_time;

    private String systemCode;

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public Long getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(Long handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolved_time() {
        return resolved_time;
    }

    public void setResolved_time(String resolved_time) {
        this.resolved_time = resolved_time;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Long getHandle_user_id() {
        return handle_user_id;
    }

    public void setHandle_user_id(Long handle_user_id) {
        this.handle_user_id = handle_user_id;
    }
}