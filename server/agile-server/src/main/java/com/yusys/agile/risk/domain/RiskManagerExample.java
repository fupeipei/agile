package com.yusys.agile.risk.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiskManagerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RiskManagerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRiskIdIsNull() {
            addCriterion("risk_id is null");
            return (Criteria) this;
        }

        public Criteria andRiskIdIsNotNull() {
            addCriterion("risk_id is not null");
            return (Criteria) this;
        }

        public Criteria andRiskIdEqualTo(Long value) {
            addCriterion("risk_id =", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdNotEqualTo(Long value) {
            addCriterion("risk_id <>", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdGreaterThan(Long value) {
            addCriterion("risk_id >", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("risk_id >=", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdLessThan(Long value) {
            addCriterion("risk_id <", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdLessThanOrEqualTo(Long value) {
            addCriterion("risk_id <=", value, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdIn(List<Long> values) {
            addCriterion("risk_id in", values, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdNotIn(List<Long> values) {
            addCriterion("risk_id not in", values, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdBetween(Long value1, Long value2) {
            addCriterion("risk_id between", value1, value2, "riskId");
            return (Criteria) this;
        }

        public Criteria andRiskIdNotBetween(Long value1, Long value2) {
            addCriterion("risk_id not between", value1, value2, "riskId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Long value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Long value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Long value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Long value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Long value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Long value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Long> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Long> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Long value1, Long value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Long value1, Long value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andSystemIdIsNull() {
            addCriterion("system_id is null");
            return (Criteria) this;
        }

        public Criteria andSystemIdIsNotNull() {
            addCriterion("system_id is not null");
            return (Criteria) this;
        }

        public Criteria andSystemIdEqualTo(Long value) {
            addCriterion("system_id =", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotEqualTo(Long value) {
            addCriterion("system_id <>", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdGreaterThan(Long value) {
            addCriterion("system_id >", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdGreaterThanOrEqualTo(Long value) {
            addCriterion("system_id >=", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdLessThan(Long value) {
            addCriterion("system_id <", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdLessThanOrEqualTo(Long value) {
            addCriterion("system_id <=", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdIn(List<Long> values) {
            addCriterion("system_id in", values, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotIn(List<Long> values) {
            addCriterion("system_id not in", values, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdBetween(Long value1, Long value2) {
            addCriterion("system_id between", value1, value2, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotBetween(Long value1, Long value2) {
            addCriterion("system_id not between", value1, value2, "systemId");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIsNull() {
            addCriterion("tenant_code is null");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIsNotNull() {
            addCriterion("tenant_code is not null");
            return (Criteria) this;
        }

        public Criteria andTenantCodeEqualTo(String value) {
            addCriterion("tenant_code =", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotEqualTo(String value) {
            addCriterion("tenant_code <>", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeGreaterThan(String value) {
            addCriterion("tenant_code >", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tenant_code >=", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLessThan(String value) {
            addCriterion("tenant_code <", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLessThanOrEqualTo(String value) {
            addCriterion("tenant_code <=", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLike(String value) {
            addCriterion("tenant_code like", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotLike(String value) {
            addCriterion("tenant_code not like", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIn(List<String> values) {
            addCriterion("tenant_code in", values, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotIn(List<String> values) {
            addCriterion("tenant_code not in", values, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeBetween(String value1, String value2) {
            addCriterion("tenant_code between", value1, value2, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotBetween(String value1, String value2) {
            addCriterion("tenant_code not between", value1, value2, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNull() {
            addCriterion("risk_level is null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNotNull() {
            addCriterion("risk_level is not null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelEqualTo(Byte value) {
            addCriterion("risk_level =", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotEqualTo(Byte value) {
            addCriterion("risk_level <>", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThan(Byte value) {
            addCriterion("risk_level >", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("risk_level >=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThan(Byte value) {
            addCriterion("risk_level <", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThanOrEqualTo(Byte value) {
            addCriterion("risk_level <=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIn(List<Byte> values) {
            addCriterion("risk_level in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotIn(List<Byte> values) {
            addCriterion("risk_level not in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelBetween(Byte value1, Byte value2) {
            addCriterion("risk_level between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("risk_level not between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskStatusIsNull() {
            addCriterion("risk_status is null");
            return (Criteria) this;
        }

        public Criteria andRiskStatusIsNotNull() {
            addCriterion("risk_status is not null");
            return (Criteria) this;
        }

        public Criteria andRiskStatusEqualTo(Byte value) {
            addCriterion("risk_status =", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusNotEqualTo(Byte value) {
            addCriterion("risk_status <>", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusGreaterThan(Byte value) {
            addCriterion("risk_status >", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("risk_status >=", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusLessThan(Byte value) {
            addCriterion("risk_status <", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusLessThanOrEqualTo(Byte value) {
            addCriterion("risk_status <=", value, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusIn(List<Byte> values) {
            addCriterion("risk_status in", values, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusNotIn(List<Byte> values) {
            addCriterion("risk_status not in", values, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusBetween(Byte value1, Byte value2) {
            addCriterion("risk_status between", value1, value2, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("risk_status not between", value1, value2, "riskStatus");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeIsNull() {
            addCriterion("risk_start_time is null");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeIsNotNull() {
            addCriterion("risk_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeEqualTo(Date value) {
            addCriterion("risk_start_time =", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeNotEqualTo(Date value) {
            addCriterion("risk_start_time <>", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeGreaterThan(Date value) {
            addCriterion("risk_start_time >", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("risk_start_time >=", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeLessThan(Date value) {
            addCriterion("risk_start_time <", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("risk_start_time <=", value, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeIn(List<Date> values) {
            addCriterion("risk_start_time in", values, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeNotIn(List<Date> values) {
            addCriterion("risk_start_time not in", values, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeBetween(Date value1, Date value2) {
            addCriterion("risk_start_time between", value1, value2, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("risk_start_time not between", value1, value2, "riskStartTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeIsNull() {
            addCriterion("risk_end_time is null");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeIsNotNull() {
            addCriterion("risk_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeEqualTo(Date value) {
            addCriterion("risk_end_time =", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeNotEqualTo(Date value) {
            addCriterion("risk_end_time <>", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeGreaterThan(Date value) {
            addCriterion("risk_end_time >", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("risk_end_time >=", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeLessThan(Date value) {
            addCriterion("risk_end_time <", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("risk_end_time <=", value, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeIn(List<Date> values) {
            addCriterion("risk_end_time in", values, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeNotIn(List<Date> values) {
            addCriterion("risk_end_time not in", values, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeBetween(Date value1, Date value2) {
            addCriterion("risk_end_time between", value1, value2, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRiskEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("risk_end_time not between", value1, value2, "riskEndTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateUidIsNull() {
            addCriterion("create_uid is null");
            return (Criteria) this;
        }

        public Criteria andCreateUidIsNotNull() {
            addCriterion("create_uid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUidEqualTo(Long value) {
            addCriterion("create_uid =", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotEqualTo(Long value) {
            addCriterion("create_uid <>", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidGreaterThan(Long value) {
            addCriterion("create_uid >", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidGreaterThanOrEqualTo(Long value) {
            addCriterion("create_uid >=", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidLessThan(Long value) {
            addCriterion("create_uid <", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidLessThanOrEqualTo(Long value) {
            addCriterion("create_uid <=", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidIn(List<Long> values) {
            addCriterion("create_uid in", values, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotIn(List<Long> values) {
            addCriterion("create_uid not in", values, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidBetween(Long value1, Long value2) {
            addCriterion("create_uid between", value1, value2, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotBetween(Long value1, Long value2) {
            addCriterion("create_uid not between", value1, value2, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("create_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("create_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("create_name =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("create_name <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("create_name >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_name >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("create_name <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("create_name <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("create_name like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("create_name not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("create_name in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("create_name not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("create_name between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("create_name not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUidIsNull() {
            addCriterion("update_uid is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUidIsNotNull() {
            addCriterion("update_uid is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUidEqualTo(Long value) {
            addCriterion("update_uid =", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidNotEqualTo(Long value) {
            addCriterion("update_uid <>", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidGreaterThan(Long value) {
            addCriterion("update_uid >", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidGreaterThanOrEqualTo(Long value) {
            addCriterion("update_uid >=", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidLessThan(Long value) {
            addCriterion("update_uid <", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidLessThanOrEqualTo(Long value) {
            addCriterion("update_uid <=", value, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidIn(List<Long> values) {
            addCriterion("update_uid in", values, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidNotIn(List<Long> values) {
            addCriterion("update_uid not in", values, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidBetween(Long value1, Long value2) {
            addCriterion("update_uid between", value1, value2, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateUidNotBetween(Long value1, Long value2) {
            addCriterion("update_uid not between", value1, value2, "updateUid");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}