package com.yusys.agile.changemanagement.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description 业务需求详情信息实体类
 * @date 2020/12/07
 */
public class EpicDetailInfoDTO extends EpicInfoDTO implements Serializable {

    private static final long serialVersionUID = 6408175163093495856L;

    /**
     * 需求排期
     */
    private String reqScheduling;

    /**
     * 需求提出人
     */
    private String makeMan;

    /**
     * 提出二级部门
     */
    private String makeSecondaryDep;

    /**
     * 提出二级部门中文名
     */
    private String makeSecondaryDepName;

    /**
     * 提出三级部门
     */
    private String makeTertiaryDep;

    /**
     * 提出三级部门中文名
     */
    private String makeTertiaryDepName;

    /**
     * 业务部门期望上线时间
     */
    private String planOnlineDate;

    /**
     * 是否是集团需求
     */
    private String ifGroup;

    /**
     * 厂商计划部署日期
     */
    private String vendorPlanDeployDate;

    /**
     * 需求相关文档
     */
    private List<EpicDocumentDTO> relatedDocuments;

    public String getReqScheduling() {
        return reqScheduling;
    }

    public void setReqScheduling(String reqScheduling) {
        this.reqScheduling = reqScheduling;
    }

    public String getMakeMan() {
        return makeMan;
    }

    public void setMakeMan(String makeMan) {
        this.makeMan = makeMan;
    }

    public String getMakeSecondaryDep() {
        return makeSecondaryDep;
    }

    public void setMakeSecondaryDep(String makeSecondaryDep) {
        this.makeSecondaryDep = makeSecondaryDep;
    }

    public String getMakeSecondaryDepName() {
        return makeSecondaryDepName;
    }

    public void setMakeSecondaryDepName(String makeSecondaryDepName) {
        this.makeSecondaryDepName = makeSecondaryDepName;
    }

    public String getMakeTertiaryDep() {
        return makeTertiaryDep;
    }

    public void setMakeTertiaryDep(String makeTertiaryDep) {
        this.makeTertiaryDep = makeTertiaryDep;
    }

    public String getMakeTertiaryDepName() {
        return makeTertiaryDepName;
    }

    public void setMakeTertiaryDepName(String makeTertiaryDepName) {
        this.makeTertiaryDepName = makeTertiaryDepName;
    }

    public String getPlanOnlineDate() {
        return planOnlineDate;
    }

    public void setPlanOnlineDate(String planOnlineDate) {
        this.planOnlineDate = planOnlineDate;
    }

    public String getIfGroup() {
        return ifGroup;
    }

    public void setIfGroup(String ifGroup) {
        this.ifGroup = ifGroup;
    }

    public String getVendorPlanDeployDate() {
        return vendorPlanDeployDate;
    }

    public void setVendorPlanDeployDate(String vendorPlanDeployDate) {
        this.vendorPlanDeployDate = vendorPlanDeployDate;
    }

    public List<EpicDocumentDTO> getRelatedDocuments() {
        return relatedDocuments;
    }

    public void setRelatedDocuments(List<EpicDocumentDTO> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }

    @Override
    public String toString() {
        return "EpicDetailInfoDTO{" +
                "reqScheduling='" + reqScheduling + '\'' +
                ", makeMan='" + makeMan + '\'' +
                ", makeSecondaryDep='" + makeSecondaryDep + '\'' +
                ", makeSecondaryDepName='" + makeSecondaryDepName + '\'' +
                ", makeTertiaryDep='" + makeTertiaryDep + '\'' +
                ", makeTertiaryDepName='" + makeTertiaryDepName + '\'' +
                ", planOnlineDate='" + planOnlineDate + '\'' +
                ", ifGroup='" + ifGroup + '\'' +
                ", vendorPlanDeployDate='" + vendorPlanDeployDate + '\'' +
                ", relatedDocuments=" + relatedDocuments +
                '}';
    }
}
