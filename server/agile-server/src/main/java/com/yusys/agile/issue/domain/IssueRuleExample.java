package com.yusys.agile.issue.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssueRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IssueRuleExample() {
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

        public Criteria andRuleIdIsNull() {
            addCriterion("rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(Long value) {
            addCriterion("rule_id =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(Long value) {
            addCriterion("rule_id <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(Long value) {
            addCriterion("rule_id >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("rule_id >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(Long value) {
            addCriterion("rule_id <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(Long value) {
            addCriterion("rule_id <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<Long> values) {
            addCriterion("rule_id in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<Long> values) {
            addCriterion("rule_id not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(Long value1, Long value2) {
            addCriterion("rule_id between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(Long value1, Long value2) {
            addCriterion("rule_id not between", value1, value2, "ruleId");
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

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(Byte value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(Byte value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(Byte value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(Byte value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(Byte value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(Byte value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<Byte> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<Byte> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(Byte value1, Byte value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(Byte value1, Byte value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andFromStageIdIsNull() {
            addCriterion("from_stage_id is null");
            return (Criteria) this;
        }

        public Criteria andFromStageIdIsNotNull() {
            addCriterion("from_stage_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromStageIdEqualTo(Long value) {
            addCriterion("from_stage_id =", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdNotEqualTo(Long value) {
            addCriterion("from_stage_id <>", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdGreaterThan(Long value) {
            addCriterion("from_stage_id >", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_stage_id >=", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdLessThan(Long value) {
            addCriterion("from_stage_id <", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdLessThanOrEqualTo(Long value) {
            addCriterion("from_stage_id <=", value, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdIn(List<Long> values) {
            addCriterion("from_stage_id in", values, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdNotIn(List<Long> values) {
            addCriterion("from_stage_id not in", values, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdBetween(Long value1, Long value2) {
            addCriterion("from_stage_id between", value1, value2, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromStageIdNotBetween(Long value1, Long value2) {
            addCriterion("from_stage_id not between", value1, value2, "fromStageId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdIsNull() {
            addCriterion("from_lane_id is null");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdIsNotNull() {
            addCriterion("from_lane_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdEqualTo(Long value) {
            addCriterion("from_lane_id =", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdNotEqualTo(Long value) {
            addCriterion("from_lane_id <>", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdGreaterThan(Long value) {
            addCriterion("from_lane_id >", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_lane_id >=", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdLessThan(Long value) {
            addCriterion("from_lane_id <", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdLessThanOrEqualTo(Long value) {
            addCriterion("from_lane_id <=", value, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdIn(List<Long> values) {
            addCriterion("from_lane_id in", values, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdNotIn(List<Long> values) {
            addCriterion("from_lane_id not in", values, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdBetween(Long value1, Long value2) {
            addCriterion("from_lane_id between", value1, value2, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andFromLaneIdNotBetween(Long value1, Long value2) {
            addCriterion("from_lane_id not between", value1, value2, "fromLaneId");
            return (Criteria) this;
        }

        public Criteria andToStageIdIsNull() {
            addCriterion("to_stage_id is null");
            return (Criteria) this;
        }

        public Criteria andToStageIdIsNotNull() {
            addCriterion("to_stage_id is not null");
            return (Criteria) this;
        }

        public Criteria andToStageIdEqualTo(Long value) {
            addCriterion("to_stage_id =", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdNotEqualTo(Long value) {
            addCriterion("to_stage_id <>", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdGreaterThan(Long value) {
            addCriterion("to_stage_id >", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_stage_id >=", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdLessThan(Long value) {
            addCriterion("to_stage_id <", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdLessThanOrEqualTo(Long value) {
            addCriterion("to_stage_id <=", value, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdIn(List<Long> values) {
            addCriterion("to_stage_id in", values, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdNotIn(List<Long> values) {
            addCriterion("to_stage_id not in", values, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdBetween(Long value1, Long value2) {
            addCriterion("to_stage_id between", value1, value2, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToStageIdNotBetween(Long value1, Long value2) {
            addCriterion("to_stage_id not between", value1, value2, "toStageId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdIsNull() {
            addCriterion("to_lane_id is null");
            return (Criteria) this;
        }

        public Criteria andToLaneIdIsNotNull() {
            addCriterion("to_lane_id is not null");
            return (Criteria) this;
        }

        public Criteria andToLaneIdEqualTo(Long value) {
            addCriterion("to_lane_id =", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdNotEqualTo(Long value) {
            addCriterion("to_lane_id <>", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdGreaterThan(Long value) {
            addCriterion("to_lane_id >", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_lane_id >=", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdLessThan(Long value) {
            addCriterion("to_lane_id <", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdLessThanOrEqualTo(Long value) {
            addCriterion("to_lane_id <=", value, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdIn(List<Long> values) {
            addCriterion("to_lane_id in", values, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdNotIn(List<Long> values) {
            addCriterion("to_lane_id not in", values, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdBetween(Long value1, Long value2) {
            addCriterion("to_lane_id between", value1, value2, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andToLaneIdNotBetween(Long value1, Long value2) {
            addCriterion("to_lane_id not between", value1, value2, "toLaneId");
            return (Criteria) this;
        }

        public Criteria andIdCheckIsNull() {
            addCriterion("id_check is null");
            return (Criteria) this;
        }

        public Criteria andIdCheckIsNotNull() {
            addCriterion("id_check is not null");
            return (Criteria) this;
        }

        public Criteria andIdCheckEqualTo(Byte value) {
            addCriterion("id_check =", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckNotEqualTo(Byte value) {
            addCriterion("id_check <>", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckGreaterThan(Byte value) {
            addCriterion("id_check >", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckGreaterThanOrEqualTo(Byte value) {
            addCriterion("id_check >=", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckLessThan(Byte value) {
            addCriterion("id_check <", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckLessThanOrEqualTo(Byte value) {
            addCriterion("id_check <=", value, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckIn(List<Byte> values) {
            addCriterion("id_check in", values, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckNotIn(List<Byte> values) {
            addCriterion("id_check not in", values, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckBetween(Byte value1, Byte value2) {
            addCriterion("id_check between", value1, value2, "idCheck");
            return (Criteria) this;
        }

        public Criteria andIdCheckNotBetween(Byte value1, Byte value2) {
            addCriterion("id_check not between", value1, value2, "idCheck");
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