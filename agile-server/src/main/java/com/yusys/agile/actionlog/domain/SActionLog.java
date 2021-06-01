package com.yusys.agile.actionlog.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
* 操作日志记录
*/
@AllArgsConstructor
@Builder
public class SActionLog {
    /**
    * 主键ID
    */
    private Long actionLogId;

    /**
    * 动作类型
    */
    private String actionCode;

    /**
    * 事物ID
    */
    private Long objId;

    /**
    * 事物类型
    */
    private Long objType;

    /**
    * 动作描述或备注
    */
    private String remark;

    /**
    * 操作结果  成功或失败
    */
    private String result;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 操作员id 对应工号
    */
    private String operatorId;

    /**
    * 租户code
    */
    private String tenantCode;

    public Long getActionLogId() {
        return actionLogId;
    }

    public void setActionLogId(Long actionLogId) {
        this.actionLogId = actionLogId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Long getObjType() {
        return objType;
    }

    public void setObjType(Long objType) {
        this.objType = objType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
    * toString
    * @return String String
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", actionLogId=").append(actionLogId);
        sb.append(", actionCode=").append(actionCode);
        sb.append(", objId=").append(objId);
        sb.append(", objType=").append(objType);
        sb.append(", remark=").append(remark);
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", tenantCode=").append(tenantCode);
        sb.append("]");
        return sb.toString();
    }
}