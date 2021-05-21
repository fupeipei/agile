package com.yusys.agile.issuev3.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SIssueExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SIssueExample() {
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

        public Criteria andIssueIdIsNull() {
            addCriterion("issue_id is null");
            return (Criteria) this;
        }

        public Criteria andIssueIdIsNotNull() {
            addCriterion("issue_id is not null");
            return (Criteria) this;
        }

        public Criteria andIssueIdEqualTo(Long value) {
            addCriterion("issue_id =", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdNotEqualTo(Long value) {
            addCriterion("issue_id <>", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdGreaterThan(Long value) {
            addCriterion("issue_id >", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdGreaterThanOrEqualTo(Long value) {
            addCriterion("issue_id >=", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdLessThan(Long value) {
            addCriterion("issue_id <", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdLessThanOrEqualTo(Long value) {
            addCriterion("issue_id <=", value, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdIn(List<Long> values) {
            addCriterion("issue_id in", values, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdNotIn(List<Long> values) {
            addCriterion("issue_id not in", values, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdBetween(Long value1, Long value2) {
            addCriterion("issue_id between", value1, value2, "issueId");
            return (Criteria) this;
        }

        public Criteria andIssueIdNotBetween(Long value1, Long value2) {
            addCriterion("issue_id not between", value1, value2, "issueId");
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

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andIssueTypeIsNull() {
            addCriterion("issue_type is null");
            return (Criteria) this;
        }

        public Criteria andIssueTypeIsNotNull() {
            addCriterion("issue_type is not null");
            return (Criteria) this;
        }

        public Criteria andIssueTypeEqualTo(Byte value) {
            addCriterion("issue_type =", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeNotEqualTo(Byte value) {
            addCriterion("issue_type <>", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeGreaterThan(Byte value) {
            addCriterion("issue_type >", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("issue_type >=", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeLessThan(Byte value) {
            addCriterion("issue_type <", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeLessThanOrEqualTo(Byte value) {
            addCriterion("issue_type <=", value, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeIn(List<Byte> values) {
            addCriterion("issue_type in", values, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeNotIn(List<Byte> values) {
            addCriterion("issue_type not in", values, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeBetween(Byte value1, Byte value2) {
            addCriterion("issue_type between", value1, value2, "issueType");
            return (Criteria) this;
        }

        public Criteria andIssueTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("issue_type not between", value1, value2, "issueType");
            return (Criteria) this;
        }

        public Criteria andSprintIdIsNull() {
            addCriterion("sprint_id is null");
            return (Criteria) this;
        }

        public Criteria andSprintIdIsNotNull() {
            addCriterion("sprint_id is not null");
            return (Criteria) this;
        }

        public Criteria andSprintIdEqualTo(Long value) {
            addCriterion("sprint_id =", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdNotEqualTo(Long value) {
            addCriterion("sprint_id <>", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdGreaterThan(Long value) {
            addCriterion("sprint_id >", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sprint_id >=", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdLessThan(Long value) {
            addCriterion("sprint_id <", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdLessThanOrEqualTo(Long value) {
            addCriterion("sprint_id <=", value, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdIn(List<Long> values) {
            addCriterion("sprint_id in", values, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdNotIn(List<Long> values) {
            addCriterion("sprint_id not in", values, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdBetween(Long value1, Long value2) {
            addCriterion("sprint_id between", value1, value2, "sprintId");
            return (Criteria) this;
        }

        public Criteria andSprintIdNotBetween(Long value1, Long value2) {
            addCriterion("sprint_id not between", value1, value2, "sprintId");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNull() {
            addCriterion("module_id is null");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNotNull() {
            addCriterion("module_id is not null");
            return (Criteria) this;
        }

        public Criteria andModuleIdEqualTo(Long value) {
            addCriterion("module_id =", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotEqualTo(Long value) {
            addCriterion("module_id <>", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThan(Long value) {
            addCriterion("module_id >", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("module_id >=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThan(Long value) {
            addCriterion("module_id <", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThanOrEqualTo(Long value) {
            addCriterion("module_id <=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdIn(List<Long> values) {
            addCriterion("module_id in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotIn(List<Long> values) {
            addCriterion("module_id not in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdBetween(Long value1, Long value2) {
            addCriterion("module_id between", value1, value2, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotBetween(Long value1, Long value2) {
            addCriterion("module_id not between", value1, value2, "moduleId");
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

        public Criteria andHandlerIsNull() {
            addCriterion("handler is null");
            return (Criteria) this;
        }

        public Criteria andHandlerIsNotNull() {
            addCriterion("handler is not null");
            return (Criteria) this;
        }

        public Criteria andHandlerEqualTo(Long value) {
            addCriterion("handler =", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerNotEqualTo(Long value) {
            addCriterion("handler <>", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerGreaterThan(Long value) {
            addCriterion("handler >", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerGreaterThanOrEqualTo(Long value) {
            addCriterion("handler >=", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerLessThan(Long value) {
            addCriterion("handler <", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerLessThanOrEqualTo(Long value) {
            addCriterion("handler <=", value, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerIn(List<Long> values) {
            addCriterion("handler in", values, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerNotIn(List<Long> values) {
            addCriterion("handler not in", values, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerBetween(Long value1, Long value2) {
            addCriterion("handler between", value1, value2, "handler");
            return (Criteria) this;
        }

        public Criteria andHandlerNotBetween(Long value1, Long value2) {
            addCriterion("handler not between", value1, value2, "handler");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNull() {
            addCriterion("begin_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNotNull() {
            addCriterion("begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginDateEqualTo(Date value) {
            addCriterion("begin_date =", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotEqualTo(Date value) {
            addCriterion("begin_date <>", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThan(Date value) {
            addCriterion("begin_date >", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterion("begin_date >=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThan(Date value) {
            addCriterion("begin_date <", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThanOrEqualTo(Date value) {
            addCriterion("begin_date <=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIn(List<Date> values) {
            addCriterion("begin_date in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotIn(List<Date> values) {
            addCriterion("begin_date not in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateBetween(Date value1, Date value2) {
            addCriterion("begin_date between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotBetween(Date value1, Date value2) {
            addCriterion("begin_date not between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadIsNull() {
            addCriterion("plan_workload is null");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadIsNotNull() {
            addCriterion("plan_workload is not null");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadEqualTo(Integer value) {
            addCriterion("plan_workload =", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadNotEqualTo(Integer value) {
            addCriterion("plan_workload <>", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadGreaterThan(Integer value) {
            addCriterion("plan_workload >", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_workload >=", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadLessThan(Integer value) {
            addCriterion("plan_workload <", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadLessThanOrEqualTo(Integer value) {
            addCriterion("plan_workload <=", value, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadIn(List<Integer> values) {
            addCriterion("plan_workload in", values, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadNotIn(List<Integer> values) {
            addCriterion("plan_workload not in", values, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadBetween(Integer value1, Integer value2) {
            addCriterion("plan_workload between", value1, value2, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanWorkloadNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_workload not between", value1, value2, "planWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadIsNull() {
            addCriterion("really_workload is null");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadIsNotNull() {
            addCriterion("really_workload is not null");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadEqualTo(Integer value) {
            addCriterion("really_workload =", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadNotEqualTo(Integer value) {
            addCriterion("really_workload <>", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadGreaterThan(Integer value) {
            addCriterion("really_workload >", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadGreaterThanOrEqualTo(Integer value) {
            addCriterion("really_workload >=", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadLessThan(Integer value) {
            addCriterion("really_workload <", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadLessThanOrEqualTo(Integer value) {
            addCriterion("really_workload <=", value, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadIn(List<Integer> values) {
            addCriterion("really_workload in", values, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadNotIn(List<Integer> values) {
            addCriterion("really_workload not in", values, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadBetween(Integer value1, Integer value2) {
            addCriterion("really_workload between", value1, value2, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andReallyWorkloadNotBetween(Integer value1, Integer value2) {
            addCriterion("really_workload not between", value1, value2, "reallyWorkload");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Byte value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Byte value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Byte value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Byte value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Byte value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Byte value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Byte> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Byte> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Byte value1, Byte value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Byte value1, Byte value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andImportanceIsNull() {
            addCriterion("importance is null");
            return (Criteria) this;
        }

        public Criteria andImportanceIsNotNull() {
            addCriterion("importance is not null");
            return (Criteria) this;
        }

        public Criteria andImportanceEqualTo(Byte value) {
            addCriterion("importance =", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotEqualTo(Byte value) {
            addCriterion("importance <>", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceGreaterThan(Byte value) {
            addCriterion("importance >", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceGreaterThanOrEqualTo(Byte value) {
            addCriterion("importance >=", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceLessThan(Byte value) {
            addCriterion("importance <", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceLessThanOrEqualTo(Byte value) {
            addCriterion("importance <=", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceIn(List<Byte> values) {
            addCriterion("importance in", values, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotIn(List<Byte> values) {
            addCriterion("importance not in", values, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceBetween(Byte value1, Byte value2) {
            addCriterion("importance between", value1, value2, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotBetween(Byte value1, Byte value2) {
            addCriterion("importance not between", value1, value2, "importance");
            return (Criteria) this;
        }

        public Criteria andStageIdIsNull() {
            addCriterion("stage_id is null");
            return (Criteria) this;
        }

        public Criteria andStageIdIsNotNull() {
            addCriterion("stage_id is not null");
            return (Criteria) this;
        }

        public Criteria andStageIdEqualTo(Long value) {
            addCriterion("stage_id =", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdNotEqualTo(Long value) {
            addCriterion("stage_id <>", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdGreaterThan(Long value) {
            addCriterion("stage_id >", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdGreaterThanOrEqualTo(Long value) {
            addCriterion("stage_id >=", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdLessThan(Long value) {
            addCriterion("stage_id <", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdLessThanOrEqualTo(Long value) {
            addCriterion("stage_id <=", value, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdIn(List<Long> values) {
            addCriterion("stage_id in", values, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdNotIn(List<Long> values) {
            addCriterion("stage_id not in", values, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdBetween(Long value1, Long value2) {
            addCriterion("stage_id between", value1, value2, "stageId");
            return (Criteria) this;
        }

        public Criteria andStageIdNotBetween(Long value1, Long value2) {
            addCriterion("stage_id not between", value1, value2, "stageId");
            return (Criteria) this;
        }

        public Criteria andLaneIdIsNull() {
            addCriterion("lane_id is null");
            return (Criteria) this;
        }

        public Criteria andLaneIdIsNotNull() {
            addCriterion("lane_id is not null");
            return (Criteria) this;
        }

        public Criteria andLaneIdEqualTo(Long value) {
            addCriterion("lane_id =", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdNotEqualTo(Long value) {
            addCriterion("lane_id <>", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdGreaterThan(Long value) {
            addCriterion("lane_id >", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdGreaterThanOrEqualTo(Long value) {
            addCriterion("lane_id >=", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdLessThan(Long value) {
            addCriterion("lane_id <", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdLessThanOrEqualTo(Long value) {
            addCriterion("lane_id <=", value, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdIn(List<Long> values) {
            addCriterion("lane_id in", values, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdNotIn(List<Long> values) {
            addCriterion("lane_id not in", values, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdBetween(Long value1, Long value2) {
            addCriterion("lane_id between", value1, value2, "laneId");
            return (Criteria) this;
        }

        public Criteria andLaneIdNotBetween(Long value1, Long value2) {
            addCriterion("lane_id not between", value1, value2, "laneId");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andIsCollectIsNull() {
            addCriterion("is_collect is null");
            return (Criteria) this;
        }

        public Criteria andIsCollectIsNotNull() {
            addCriterion("is_collect is not null");
            return (Criteria) this;
        }

        public Criteria andIsCollectEqualTo(Byte value) {
            addCriterion("is_collect =", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectNotEqualTo(Byte value) {
            addCriterion("is_collect <>", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectGreaterThan(Byte value) {
            addCriterion("is_collect >", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_collect >=", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectLessThan(Byte value) {
            addCriterion("is_collect <", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectLessThanOrEqualTo(Byte value) {
            addCriterion("is_collect <=", value, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectIn(List<Byte> values) {
            addCriterion("is_collect in", values, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectNotIn(List<Byte> values) {
            addCriterion("is_collect not in", values, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectBetween(Byte value1, Byte value2) {
            addCriterion("is_collect between", value1, value2, "isCollect");
            return (Criteria) this;
        }

        public Criteria andIsCollectNotBetween(Byte value1, Byte value2) {
            addCriterion("is_collect not between", value1, value2, "isCollect");
            return (Criteria) this;
        }

        public Criteria andCompletionIsNull() {
            addCriterion("completion is null");
            return (Criteria) this;
        }

        public Criteria andCompletionIsNotNull() {
            addCriterion("completion is not null");
            return (Criteria) this;
        }

        public Criteria andCompletionEqualTo(String value) {
            addCriterion("completion =", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionNotEqualTo(String value) {
            addCriterion("completion <>", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionGreaterThan(String value) {
            addCriterion("completion >", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionGreaterThanOrEqualTo(String value) {
            addCriterion("completion >=", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionLessThan(String value) {
            addCriterion("completion <", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionLessThanOrEqualTo(String value) {
            addCriterion("completion <=", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionLike(String value) {
            addCriterion("completion like", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionNotLike(String value) {
            addCriterion("completion not like", value, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionIn(List<String> values) {
            addCriterion("completion in", values, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionNotIn(List<String> values) {
            addCriterion("completion not in", values, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionBetween(String value1, String value2) {
            addCriterion("completion between", value1, value2, "completion");
            return (Criteria) this;
        }

        public Criteria andCompletionNotBetween(String value1, String value2) {
            addCriterion("completion not between", value1, value2, "completion");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNull() {
            addCriterion("task_type is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNotNull() {
            addCriterion("task_type is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeEqualTo(Integer value) {
            addCriterion("task_type =", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotEqualTo(Integer value) {
            addCriterion("task_type <>", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThan(Integer value) {
            addCriterion("task_type >", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_type >=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThan(Integer value) {
            addCriterion("task_type <", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThanOrEqualTo(Integer value) {
            addCriterion("task_type <=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIn(List<Integer> values) {
            addCriterion("task_type in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotIn(List<Integer> values) {
            addCriterion("task_type not in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeBetween(Integer value1, Integer value2) {
            addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("task_type not between", value1, value2, "taskType");
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

        public Criteria andFaultTypeIsNull() {
            addCriterion("fault_type is null");
            return (Criteria) this;
        }

        public Criteria andFaultTypeIsNotNull() {
            addCriterion("fault_type is not null");
            return (Criteria) this;
        }

        public Criteria andFaultTypeEqualTo(Long value) {
            addCriterion("fault_type =", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeNotEqualTo(Long value) {
            addCriterion("fault_type <>", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeGreaterThan(Long value) {
            addCriterion("fault_type >", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("fault_type >=", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeLessThan(Long value) {
            addCriterion("fault_type <", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeLessThanOrEqualTo(Long value) {
            addCriterion("fault_type <=", value, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeIn(List<Long> values) {
            addCriterion("fault_type in", values, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeNotIn(List<Long> values) {
            addCriterion("fault_type not in", values, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeBetween(Long value1, Long value2) {
            addCriterion("fault_type between", value1, value2, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultTypeNotBetween(Long value1, Long value2) {
            addCriterion("fault_type not between", value1, value2, "faultType");
            return (Criteria) this;
        }

        public Criteria andFaultLevelIsNull() {
            addCriterion("fault_level is null");
            return (Criteria) this;
        }

        public Criteria andFaultLevelIsNotNull() {
            addCriterion("fault_level is not null");
            return (Criteria) this;
        }

        public Criteria andFaultLevelEqualTo(Long value) {
            addCriterion("fault_level =", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelNotEqualTo(Long value) {
            addCriterion("fault_level <>", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelGreaterThan(Long value) {
            addCriterion("fault_level >", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelGreaterThanOrEqualTo(Long value) {
            addCriterion("fault_level >=", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelLessThan(Long value) {
            addCriterion("fault_level <", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelLessThanOrEqualTo(Long value) {
            addCriterion("fault_level <=", value, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelIn(List<Long> values) {
            addCriterion("fault_level in", values, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelNotIn(List<Long> values) {
            addCriterion("fault_level not in", values, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelBetween(Long value1, Long value2) {
            addCriterion("fault_level between", value1, value2, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFaultLevelNotBetween(Long value1, Long value2) {
            addCriterion("fault_level not between", value1, value2, "faultLevel");
            return (Criteria) this;
        }

        public Criteria andFixedTimeIsNull() {
            addCriterion("fixed_time is null");
            return (Criteria) this;
        }

        public Criteria andFixedTimeIsNotNull() {
            addCriterion("fixed_time is not null");
            return (Criteria) this;
        }

        public Criteria andFixedTimeEqualTo(Date value) {
            addCriterion("fixed_time =", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeNotEqualTo(Date value) {
            addCriterion("fixed_time <>", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeGreaterThan(Date value) {
            addCriterion("fixed_time >", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("fixed_time >=", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeLessThan(Date value) {
            addCriterion("fixed_time <", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeLessThanOrEqualTo(Date value) {
            addCriterion("fixed_time <=", value, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeIn(List<Date> values) {
            addCriterion("fixed_time in", values, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeNotIn(List<Date> values) {
            addCriterion("fixed_time not in", values, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeBetween(Date value1, Date value2) {
            addCriterion("fixed_time between", value1, value2, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andFixedTimeNotBetween(Date value1, Date value2) {
            addCriterion("fixed_time not between", value1, value2, "fixedTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIsNull() {
            addCriterion("close_time is null");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIsNotNull() {
            addCriterion("close_time is not null");
            return (Criteria) this;
        }

        public Criteria andCloseTimeEqualTo(Date value) {
            addCriterion("close_time =", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotEqualTo(Date value) {
            addCriterion("close_time <>", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThan(Date value) {
            addCriterion("close_time >", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("close_time >=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThan(Date value) {
            addCriterion("close_time <", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThanOrEqualTo(Date value) {
            addCriterion("close_time <=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIn(List<Date> values) {
            addCriterion("close_time in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotIn(List<Date> values) {
            addCriterion("close_time not in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeBetween(Date value1, Date value2) {
            addCriterion("close_time between", value1, value2, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotBetween(Date value1, Date value2) {
            addCriterion("close_time not between", value1, value2, "closeTime");
            return (Criteria) this;
        }

        public Criteria andFixedUidIsNull() {
            addCriterion("fixed_uid is null");
            return (Criteria) this;
        }

        public Criteria andFixedUidIsNotNull() {
            addCriterion("fixed_uid is not null");
            return (Criteria) this;
        }

        public Criteria andFixedUidEqualTo(Long value) {
            addCriterion("fixed_uid =", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidNotEqualTo(Long value) {
            addCriterion("fixed_uid <>", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidGreaterThan(Long value) {
            addCriterion("fixed_uid >", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidGreaterThanOrEqualTo(Long value) {
            addCriterion("fixed_uid >=", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidLessThan(Long value) {
            addCriterion("fixed_uid <", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidLessThanOrEqualTo(Long value) {
            addCriterion("fixed_uid <=", value, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidIn(List<Long> values) {
            addCriterion("fixed_uid in", values, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidNotIn(List<Long> values) {
            addCriterion("fixed_uid not in", values, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidBetween(Long value1, Long value2) {
            addCriterion("fixed_uid between", value1, value2, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andFixedUidNotBetween(Long value1, Long value2) {
            addCriterion("fixed_uid not between", value1, value2, "fixedUid");
            return (Criteria) this;
        }

        public Criteria andTestUidIsNull() {
            addCriterion("test_uid is null");
            return (Criteria) this;
        }

        public Criteria andTestUidIsNotNull() {
            addCriterion("test_uid is not null");
            return (Criteria) this;
        }

        public Criteria andTestUidEqualTo(Long value) {
            addCriterion("test_uid =", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidNotEqualTo(Long value) {
            addCriterion("test_uid <>", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidGreaterThan(Long value) {
            addCriterion("test_uid >", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidGreaterThanOrEqualTo(Long value) {
            addCriterion("test_uid >=", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidLessThan(Long value) {
            addCriterion("test_uid <", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidLessThanOrEqualTo(Long value) {
            addCriterion("test_uid <=", value, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidIn(List<Long> values) {
            addCriterion("test_uid in", values, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidNotIn(List<Long> values) {
            addCriterion("test_uid not in", values, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidBetween(Long value1, Long value2) {
            addCriterion("test_uid between", value1, value2, "testUid");
            return (Criteria) this;
        }

        public Criteria andTestUidNotBetween(Long value1, Long value2) {
            addCriterion("test_uid not between", value1, value2, "testUid");
            return (Criteria) this;
        }

        public Criteria andVersionIdIsNull() {
            addCriterion("version_id is null");
            return (Criteria) this;
        }

        public Criteria andVersionIdIsNotNull() {
            addCriterion("version_id is not null");
            return (Criteria) this;
        }

        public Criteria andVersionIdEqualTo(Long value) {
            addCriterion("version_id =", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotEqualTo(Long value) {
            addCriterion("version_id <>", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThan(Long value) {
            addCriterion("version_id >", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("version_id >=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThan(Long value) {
            addCriterion("version_id <", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThanOrEqualTo(Long value) {
            addCriterion("version_id <=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdIn(List<Long> values) {
            addCriterion("version_id in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotIn(List<Long> values) {
            addCriterion("version_id not in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdBetween(Long value1, Long value2) {
            addCriterion("version_id between", value1, value2, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotBetween(Long value1, Long value2) {
            addCriterion("version_id not between", value1, value2, "versionId");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNull() {
            addCriterion("case_id is null");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNotNull() {
            addCriterion("case_id is not null");
            return (Criteria) this;
        }

        public Criteria andCaseIdEqualTo(Long value) {
            addCriterion("case_id =", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotEqualTo(Long value) {
            addCriterion("case_id <>", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThan(Long value) {
            addCriterion("case_id >", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("case_id >=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThan(Long value) {
            addCriterion("case_id <", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThanOrEqualTo(Long value) {
            addCriterion("case_id <=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdIn(List<Long> values) {
            addCriterion("case_id in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotIn(List<Long> values) {
            addCriterion("case_id not in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdBetween(Long value1, Long value2) {
            addCriterion("case_id between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotBetween(Long value1, Long value2) {
            addCriterion("case_id not between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andDeadlineIsNull() {
            addCriterion("deadline is null");
            return (Criteria) this;
        }

        public Criteria andDeadlineIsNotNull() {
            addCriterion("deadline is not null");
            return (Criteria) this;
        }

        public Criteria andDeadlineEqualTo(Date value) {
            addCriterion("deadline =", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineNotEqualTo(Date value) {
            addCriterion("deadline <>", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineGreaterThan(Date value) {
            addCriterion("deadline >", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("deadline >=", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineLessThan(Date value) {
            addCriterion("deadline <", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("deadline <=", value, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineIn(List<Date> values) {
            addCriterion("deadline in", values, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineNotIn(List<Date> values) {
            addCriterion("deadline not in", values, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineBetween(Date value1, Date value2) {
            addCriterion("deadline between", value1, value2, "deadline");
            return (Criteria) this;
        }

        public Criteria andDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("deadline not between", value1, value2, "deadline");
            return (Criteria) this;
        }

        public Criteria andFileIsNull() {
            addCriterion("file is null");
            return (Criteria) this;
        }

        public Criteria andFileIsNotNull() {
            addCriterion("file is not null");
            return (Criteria) this;
        }

        public Criteria andFileEqualTo(String value) {
            addCriterion("file =", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotEqualTo(String value) {
            addCriterion("file <>", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThan(String value) {
            addCriterion("file >", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThanOrEqualTo(String value) {
            addCriterion("file >=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThan(String value) {
            addCriterion("file <", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThanOrEqualTo(String value) {
            addCriterion("file <=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLike(String value) {
            addCriterion("file like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotLike(String value) {
            addCriterion("file not like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileIn(List<String> values) {
            addCriterion("file in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotIn(List<String> values) {
            addCriterion("file not in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileBetween(String value1, String value2) {
            addCriterion("file between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotBetween(String value1, String value2) {
            addCriterion("file not between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andUrgencyIsNull() {
            addCriterion("urgency is null");
            return (Criteria) this;
        }

        public Criteria andUrgencyIsNotNull() {
            addCriterion("urgency is not null");
            return (Criteria) this;
        }

        public Criteria andUrgencyEqualTo(String value) {
            addCriterion("urgency =", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotEqualTo(String value) {
            addCriterion("urgency <>", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyGreaterThan(String value) {
            addCriterion("urgency >", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyGreaterThanOrEqualTo(String value) {
            addCriterion("urgency >=", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLessThan(String value) {
            addCriterion("urgency <", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLessThanOrEqualTo(String value) {
            addCriterion("urgency <=", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLike(String value) {
            addCriterion("urgency like", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotLike(String value) {
            addCriterion("urgency not like", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyIn(List<String> values) {
            addCriterion("urgency in", values, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotIn(List<String> values) {
            addCriterion("urgency not in", values, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyBetween(String value1, String value2) {
            addCriterion("urgency between", value1, value2, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotBetween(String value1, String value2) {
            addCriterion("urgency not between", value1, value2, "urgency");
            return (Criteria) this;
        }

        public Criteria andCauseIsNull() {
            addCriterion("cause is null");
            return (Criteria) this;
        }

        public Criteria andCauseIsNotNull() {
            addCriterion("cause is not null");
            return (Criteria) this;
        }

        public Criteria andCauseEqualTo(String value) {
            addCriterion("cause =", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseNotEqualTo(String value) {
            addCriterion("cause <>", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseGreaterThan(String value) {
            addCriterion("cause >", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseGreaterThanOrEqualTo(String value) {
            addCriterion("cause >=", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseLessThan(String value) {
            addCriterion("cause <", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseLessThanOrEqualTo(String value) {
            addCriterion("cause <=", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseLike(String value) {
            addCriterion("cause like", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseNotLike(String value) {
            addCriterion("cause not like", value, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseIn(List<String> values) {
            addCriterion("cause in", values, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseNotIn(List<String> values) {
            addCriterion("cause not in", values, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseBetween(String value1, String value2) {
            addCriterion("cause between", value1, value2, "cause");
            return (Criteria) this;
        }

        public Criteria andCauseNotBetween(String value1, String value2) {
            addCriterion("cause not between", value1, value2, "cause");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdIsNull() {
            addCriterion("manual_case_id is null");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdIsNotNull() {
            addCriterion("manual_case_id is not null");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdEqualTo(Long value) {
            addCriterion("manual_case_id =", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdNotEqualTo(Long value) {
            addCriterion("manual_case_id <>", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdGreaterThan(Long value) {
            addCriterion("manual_case_id >", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("manual_case_id >=", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdLessThan(Long value) {
            addCriterion("manual_case_id <", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdLessThanOrEqualTo(Long value) {
            addCriterion("manual_case_id <=", value, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdIn(List<Long> values) {
            addCriterion("manual_case_id in", values, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdNotIn(List<Long> values) {
            addCriterion("manual_case_id not in", values, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdBetween(Long value1, Long value2) {
            addCriterion("manual_case_id between", value1, value2, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andManualCaseIdNotBetween(Long value1, Long value2) {
            addCriterion("manual_case_id not between", value1, value2, "manualCaseId");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadIsNull() {
            addCriterion("remain_workload is null");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadIsNotNull() {
            addCriterion("remain_workload is not null");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadEqualTo(Integer value) {
            addCriterion("remain_workload =", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadNotEqualTo(Integer value) {
            addCriterion("remain_workload <>", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadGreaterThan(Integer value) {
            addCriterion("remain_workload >", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadGreaterThanOrEqualTo(Integer value) {
            addCriterion("remain_workload >=", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadLessThan(Integer value) {
            addCriterion("remain_workload <", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadLessThanOrEqualTo(Integer value) {
            addCriterion("remain_workload <=", value, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadIn(List<Integer> values) {
            addCriterion("remain_workload in", values, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadNotIn(List<Integer> values) {
            addCriterion("remain_workload not in", values, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadBetween(Integer value1, Integer value2) {
            addCriterion("remain_workload between", value1, value2, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andRemainWorkloadNotBetween(Integer value1, Integer value2) {
            addCriterion("remain_workload not between", value1, value2, "remainWorkload");
            return (Criteria) this;
        }

        public Criteria andBugIdIsNull() {
            addCriterion("bug_id is null");
            return (Criteria) this;
        }

        public Criteria andBugIdIsNotNull() {
            addCriterion("bug_id is not null");
            return (Criteria) this;
        }

        public Criteria andBugIdEqualTo(Long value) {
            addCriterion("bug_id =", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdNotEqualTo(Long value) {
            addCriterion("bug_id <>", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdGreaterThan(Long value) {
            addCriterion("bug_id >", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bug_id >=", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdLessThan(Long value) {
            addCriterion("bug_id <", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdLessThanOrEqualTo(Long value) {
            addCriterion("bug_id <=", value, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdIn(List<Long> values) {
            addCriterion("bug_id in", values, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdNotIn(List<Long> values) {
            addCriterion("bug_id not in", values, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdBetween(Long value1, Long value2) {
            addCriterion("bug_id between", value1, value2, "bugId");
            return (Criteria) this;
        }

        public Criteria andBugIdNotBetween(Long value1, Long value2) {
            addCriterion("bug_id not between", value1, value2, "bugId");
            return (Criteria) this;
        }

        public Criteria andOrderIsNull() {
            addCriterion("order is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("order is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(Integer value) {
            addCriterion("order =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(Integer value) {
            addCriterion("order <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(Integer value) {
            addCriterion("order >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("order >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(Integer value) {
            addCriterion("order <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(Integer value) {
            addCriterion("order <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<Integer> values) {
            addCriterion("order in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<Integer> values) {
            addCriterion("order not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(Integer value1, Integer value2) {
            addCriterion("order between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("order not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andBlockStateIsNull() {
            addCriterion("block_state is null");
            return (Criteria) this;
        }

        public Criteria andBlockStateIsNotNull() {
            addCriterion("block_state is not null");
            return (Criteria) this;
        }

        public Criteria andBlockStateEqualTo(Byte value) {
            addCriterion("block_state =", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateNotEqualTo(Byte value) {
            addCriterion("block_state <>", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateGreaterThan(Byte value) {
            addCriterion("block_state >", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("block_state >=", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateLessThan(Byte value) {
            addCriterion("block_state <", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateLessThanOrEqualTo(Byte value) {
            addCriterion("block_state <=", value, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateIn(List<Byte> values) {
            addCriterion("block_state in", values, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateNotIn(List<Byte> values) {
            addCriterion("block_state not in", values, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateBetween(Byte value1, Byte value2) {
            addCriterion("block_state between", value1, value2, "blockState");
            return (Criteria) this;
        }

        public Criteria andBlockStateNotBetween(Byte value1, Byte value2) {
            addCriterion("block_state not between", value1, value2, "blockState");
            return (Criteria) this;
        }

        public Criteria andReopenTimesIsNull() {
            addCriterion("reopen_times is null");
            return (Criteria) this;
        }

        public Criteria andReopenTimesIsNotNull() {
            addCriterion("reopen_times is not null");
            return (Criteria) this;
        }

        public Criteria andReopenTimesEqualTo(Integer value) {
            addCriterion("reopen_times =", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesNotEqualTo(Integer value) {
            addCriterion("reopen_times <>", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesGreaterThan(Integer value) {
            addCriterion("reopen_times >", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("reopen_times >=", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesLessThan(Integer value) {
            addCriterion("reopen_times <", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesLessThanOrEqualTo(Integer value) {
            addCriterion("reopen_times <=", value, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesIn(List<Integer> values) {
            addCriterion("reopen_times in", values, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesNotIn(List<Integer> values) {
            addCriterion("reopen_times not in", values, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesBetween(Integer value1, Integer value2) {
            addCriterion("reopen_times between", value1, value2, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andReopenTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("reopen_times not between", value1, value2, "reopenTimes");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassIsNull() {
            addCriterion("assess_is_pass is null");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassIsNotNull() {
            addCriterion("assess_is_pass is not null");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassEqualTo(Byte value) {
            addCriterion("assess_is_pass =", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassNotEqualTo(Byte value) {
            addCriterion("assess_is_pass <>", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassGreaterThan(Byte value) {
            addCriterion("assess_is_pass >", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassGreaterThanOrEqualTo(Byte value) {
            addCriterion("assess_is_pass >=", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassLessThan(Byte value) {
            addCriterion("assess_is_pass <", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassLessThanOrEqualTo(Byte value) {
            addCriterion("assess_is_pass <=", value, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassIn(List<Byte> values) {
            addCriterion("assess_is_pass in", values, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassNotIn(List<Byte> values) {
            addCriterion("assess_is_pass not in", values, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassBetween(Byte value1, Byte value2) {
            addCriterion("assess_is_pass between", value1, value2, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessIsPassNotBetween(Byte value1, Byte value2) {
            addCriterion("assess_is_pass not between", value1, value2, "assessIsPass");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksIsNull() {
            addCriterion("assess_remarks is null");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksIsNotNull() {
            addCriterion("assess_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksEqualTo(String value) {
            addCriterion("assess_remarks =", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksNotEqualTo(String value) {
            addCriterion("assess_remarks <>", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksGreaterThan(String value) {
            addCriterion("assess_remarks >", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("assess_remarks >=", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksLessThan(String value) {
            addCriterion("assess_remarks <", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksLessThanOrEqualTo(String value) {
            addCriterion("assess_remarks <=", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksLike(String value) {
            addCriterion("assess_remarks like", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksNotLike(String value) {
            addCriterion("assess_remarks not like", value, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksIn(List<String> values) {
            addCriterion("assess_remarks in", values, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksNotIn(List<String> values) {
            addCriterion("assess_remarks not in", values, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksBetween(String value1, String value2) {
            addCriterion("assess_remarks between", value1, value2, "assessRemarks");
            return (Criteria) this;
        }

        public Criteria andAssessRemarksNotBetween(String value1, String value2) {
            addCriterion("assess_remarks not between", value1, value2, "assessRemarks");
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

        public Criteria andIsArchiveIsNull() {
            addCriterion("is_archive is null");
            return (Criteria) this;
        }

        public Criteria andIsArchiveIsNotNull() {
            addCriterion("is_archive is not null");
            return (Criteria) this;
        }

        public Criteria andIsArchiveEqualTo(Byte value) {
            addCriterion("is_archive =", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotEqualTo(Byte value) {
            addCriterion("is_archive <>", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveGreaterThan(Byte value) {
            addCriterion("is_archive >", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_archive >=", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveLessThan(Byte value) {
            addCriterion("is_archive <", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveLessThanOrEqualTo(Byte value) {
            addCriterion("is_archive <=", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveIn(List<Byte> values) {
            addCriterion("is_archive in", values, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotIn(List<Byte> values) {
            addCriterion("is_archive not in", values, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveBetween(Byte value1, Byte value2) {
            addCriterion("is_archive between", value1, value2, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotBetween(Byte value1, Byte value2) {
            addCriterion("is_archive not between", value1, value2, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsCancelIsNull() {
            addCriterion("is_cancel is null");
            return (Criteria) this;
        }

        public Criteria andIsCancelIsNotNull() {
            addCriterion("is_cancel is not null");
            return (Criteria) this;
        }

        public Criteria andIsCancelEqualTo(Byte value) {
            addCriterion("is_cancel =", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelNotEqualTo(Byte value) {
            addCriterion("is_cancel <>", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelGreaterThan(Byte value) {
            addCriterion("is_cancel >", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_cancel >=", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelLessThan(Byte value) {
            addCriterion("is_cancel <", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelLessThanOrEqualTo(Byte value) {
            addCriterion("is_cancel <=", value, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelIn(List<Byte> values) {
            addCriterion("is_cancel in", values, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelNotIn(List<Byte> values) {
            addCriterion("is_cancel not in", values, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelBetween(Byte value1, Byte value2) {
            addCriterion("is_cancel between", value1, value2, "isCancel");
            return (Criteria) this;
        }

        public Criteria andIsCancelNotBetween(Byte value1, Byte value2) {
            addCriterion("is_cancel not between", value1, value2, "isCancel");
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