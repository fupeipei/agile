package com.yusys.agile.milestone.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MilestoneExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MilestoneExample() {
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

        public Criteria andMilestoneIdIsNull() {
            addCriterion("milestone_id is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdIsNotNull() {
            addCriterion("milestone_id is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdEqualTo(Long value) {
            addCriterion("milestone_id =", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotEqualTo(Long value) {
            addCriterion("milestone_id <>", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdGreaterThan(Long value) {
            addCriterion("milestone_id >", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdGreaterThanOrEqualTo(Long value) {
            addCriterion("milestone_id >=", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdLessThan(Long value) {
            addCriterion("milestone_id <", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdLessThanOrEqualTo(Long value) {
            addCriterion("milestone_id <=", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdIn(List<Long> values) {
            addCriterion("milestone_id in", values, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotIn(List<Long> values) {
            addCriterion("milestone_id not in", values, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdBetween(Long value1, Long value2) {
            addCriterion("milestone_id between", value1, value2, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotBetween(Long value1, Long value2) {
            addCriterion("milestone_id not between", value1, value2, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIsNull() {
            addCriterion("milestone_name is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIsNotNull() {
            addCriterion("milestone_name is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameEqualTo(String value) {
            addCriterion("milestone_name =", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotEqualTo(String value) {
            addCriterion("milestone_name <>", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameGreaterThan(String value) {
            addCriterion("milestone_name >", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameGreaterThanOrEqualTo(String value) {
            addCriterion("milestone_name >=", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLessThan(String value) {
            addCriterion("milestone_name <", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLessThanOrEqualTo(String value) {
            addCriterion("milestone_name <=", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLike(String value) {
            addCriterion("milestone_name like", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotLike(String value) {
            addCriterion("milestone_name not like", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIn(List<String> values) {
            addCriterion("milestone_name in", values, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotIn(List<String> values) {
            addCriterion("milestone_name not in", values, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameBetween(String value1, String value2) {
            addCriterion("milestone_name between", value1, value2, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotBetween(String value1, String value2) {
            addCriterion("milestone_name not between", value1, value2, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusIsNull() {
            addCriterion("milestone_status is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusIsNotNull() {
            addCriterion("milestone_status is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusEqualTo(Integer value) {
            addCriterion("milestone_status =", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusNotEqualTo(Integer value) {
            addCriterion("milestone_status <>", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusGreaterThan(Integer value) {
            addCriterion("milestone_status >", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("milestone_status >=", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusLessThan(Integer value) {
            addCriterion("milestone_status <", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusLessThanOrEqualTo(Integer value) {
            addCriterion("milestone_status <=", value, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusIn(List<Integer> values) {
            addCriterion("milestone_status in", values, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusNotIn(List<Integer> values) {
            addCriterion("milestone_status not in", values, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusBetween(Integer value1, Integer value2) {
            addCriterion("milestone_status between", value1, value2, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andMilestoneStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("milestone_status not between", value1, value2, "milestoneStatus");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
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

        public Criteria andPlanFinishTimeIsNull() {
            addCriterion("plan_finish_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeIsNotNull() {
            addCriterion("plan_finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeEqualTo(Date value) {
            addCriterion("plan_finish_time =", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeNotEqualTo(Date value) {
            addCriterion("plan_finish_time <>", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeGreaterThan(Date value) {
            addCriterion("plan_finish_time >", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_finish_time >=", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeLessThan(Date value) {
            addCriterion("plan_finish_time <", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_finish_time <=", value, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeIn(List<Date> values) {
            addCriterion("plan_finish_time in", values, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeNotIn(List<Date> values) {
            addCriterion("plan_finish_time not in", values, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeBetween(Date value1, Date value2) {
            addCriterion("plan_finish_time between", value1, value2, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andPlanFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_finish_time not between", value1, value2, "planFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeIsNull() {
            addCriterion("real_finish_time is null");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeIsNotNull() {
            addCriterion("real_finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeEqualTo(Date value) {
            addCriterion("real_finish_time =", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeNotEqualTo(Date value) {
            addCriterion("real_finish_time <>", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeGreaterThan(Date value) {
            addCriterion("real_finish_time >", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("real_finish_time >=", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeLessThan(Date value) {
            addCriterion("real_finish_time <", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("real_finish_time <=", value, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeIn(List<Date> values) {
            addCriterion("real_finish_time in", values, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeNotIn(List<Date> values) {
            addCriterion("real_finish_time not in", values, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeBetween(Date value1, Date value2) {
            addCriterion("real_finish_time between", value1, value2, "realFinishTime");
            return (Criteria) this;
        }

        public Criteria andRealFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("real_finish_time not between", value1, value2, "realFinishTime");
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