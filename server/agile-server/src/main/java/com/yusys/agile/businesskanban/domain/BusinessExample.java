package com.yusys.agile.businesskanban.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BusinessExample() {
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

        public Criteria andBusinessIdIsNull() {
            addCriterion("business_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("business_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(Long value) {
            addCriterion("business_id =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(Long value) {
            addCriterion("business_id <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(Long value) {
            addCriterion("business_id >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(Long value) {
            addCriterion("business_id >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(Long value) {
            addCriterion("business_id <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(Long value) {
            addCriterion("business_id <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<Long> values) {
            addCriterion("business_id in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<Long> values) {
            addCriterion("business_id not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(Long value1, Long value2) {
            addCriterion("business_id between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(Long value1, Long value2) {
            addCriterion("business_id not between", value1, value2, "businessId");
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

        public Criteria andKanbanIdIsNull() {
            addCriterion("kanban_id is null");
            return (Criteria) this;
        }

        public Criteria andKanbanIdIsNotNull() {
            addCriterion("kanban_id is not null");
            return (Criteria) this;
        }

        public Criteria andKanbanIdEqualTo(Long value) {
            addCriterion("kanban_id =", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdNotEqualTo(Long value) {
            addCriterion("kanban_id <>", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdGreaterThan(Long value) {
            addCriterion("kanban_id >", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdGreaterThanOrEqualTo(Long value) {
            addCriterion("kanban_id >=", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdLessThan(Long value) {
            addCriterion("kanban_id <", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdLessThanOrEqualTo(Long value) {
            addCriterion("kanban_id <=", value, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdIn(List<Long> values) {
            addCriterion("kanban_id in", values, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdNotIn(List<Long> values) {
            addCriterion("kanban_id not in", values, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdBetween(Long value1, Long value2) {
            addCriterion("kanban_id between", value1, value2, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andKanbanIdNotBetween(Long value1, Long value2) {
            addCriterion("kanban_id not between", value1, value2, "kanbanId");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNull() {
            addCriterion("business_type is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNotNull() {
            addCriterion("business_type is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeEqualTo(Long value) {
            addCriterion("business_type =", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotEqualTo(Long value) {
            addCriterion("business_type <>", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThan(Long value) {
            addCriterion("business_type >", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("business_type >=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThan(Long value) {
            addCriterion("business_type <", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThanOrEqualTo(Long value) {
            addCriterion("business_type <=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIn(List<Long> values) {
            addCriterion("business_type in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotIn(List<Long> values) {
            addCriterion("business_type not in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeBetween(Long value1, Long value2) {
            addCriterion("business_type between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotBetween(Long value1, Long value2) {
            addCriterion("business_type not between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessStateIsNull() {
            addCriterion("business_state is null");
            return (Criteria) this;
        }

        public Criteria andBusinessStateIsNotNull() {
            addCriterion("business_state is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessStateEqualTo(Byte value) {
            addCriterion("business_state =", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateNotEqualTo(Byte value) {
            addCriterion("business_state <>", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateGreaterThan(Byte value) {
            addCriterion("business_state >", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("business_state >=", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateLessThan(Byte value) {
            addCriterion("business_state <", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateLessThanOrEqualTo(Byte value) {
            addCriterion("business_state <=", value, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateIn(List<Byte> values) {
            addCriterion("business_state in", values, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateNotIn(List<Byte> values) {
            addCriterion("business_state not in", values, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateBetween(Byte value1, Byte value2) {
            addCriterion("business_state between", value1, value2, "businessState");
            return (Criteria) this;
        }

        public Criteria andBusinessStateNotBetween(Byte value1, Byte value2) {
            addCriterion("business_state not between", value1, value2, "businessState");
            return (Criteria) this;
        }

        public Criteria andIsVisibleIsNull() {
            addCriterion("is_visible is null");
            return (Criteria) this;
        }

        public Criteria andIsVisibleIsNotNull() {
            addCriterion("is_visible is not null");
            return (Criteria) this;
        }

        public Criteria andIsVisibleEqualTo(Byte value) {
            addCriterion("is_visible =", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleNotEqualTo(Byte value) {
            addCriterion("is_visible <>", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleGreaterThan(Byte value) {
            addCriterion("is_visible >", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_visible >=", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleLessThan(Byte value) {
            addCriterion("is_visible <", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleLessThanOrEqualTo(Byte value) {
            addCriterion("is_visible <=", value, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleIn(List<Byte> values) {
            addCriterion("is_visible in", values, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleNotIn(List<Byte> values) {
            addCriterion("is_visible not in", values, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleBetween(Byte value1, Byte value2) {
            addCriterion("is_visible between", value1, value2, "isVisible");
            return (Criteria) this;
        }

        public Criteria andIsVisibleNotBetween(Byte value1, Byte value2) {
            addCriterion("is_visible not between", value1, value2, "isVisible");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerIsNull() {
            addCriterion("business_owner is null");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerIsNotNull() {
            addCriterion("business_owner is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerEqualTo(Long value) {
            addCriterion("business_owner =", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andTenantCodeEqualTo(String value) {
            addCriterion("tenant_code =", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNotEqualTo(Long value) {
            addCriterion("business_owner <>", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerGreaterThan(Long value) {
            addCriterion("business_owner >", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerGreaterThanOrEqualTo(Long value) {
            addCriterion("business_owner >=", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerLessThan(Long value) {
            addCriterion("business_owner <", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerLessThanOrEqualTo(Long value) {
            addCriterion("business_owner <=", value, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerIn(List<Long> values) {
            addCriterion("business_owner in", values, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNotIn(List<Long> values) {
            addCriterion("business_owner not in", values, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerBetween(Long value1, Long value2) {
            addCriterion("business_owner between", value1, value2, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNotBetween(Long value1, Long value2) {
            addCriterion("business_owner not between", value1, value2, "businessOwner");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameIsNull() {
            addCriterion("business_owner_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameIsNotNull() {
            addCriterion("business_owner_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameEqualTo(String value) {
            addCriterion("business_owner_name =", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameNotEqualTo(String value) {
            addCriterion("business_owner_name <>", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameGreaterThan(String value) {
            addCriterion("business_owner_name >", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_owner_name >=", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameLessThan(String value) {
            addCriterion("business_owner_name <", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameLessThanOrEqualTo(String value) {
            addCriterion("business_owner_name <=", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameLike(String value) {
            addCriterion("business_owner_name like", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameNotLike(String value) {
            addCriterion("business_owner_name not like", value, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameIn(List<String> values) {
            addCriterion("business_owner_name in", values, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameNotIn(List<String> values) {
            addCriterion("business_owner_name not in", values, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameBetween(String value1, String value2) {
            addCriterion("business_owner_name between", value1, value2, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessOwnerNameNotBetween(String value1, String value2) {
            addCriterion("business_owner_name not between", value1, value2, "businessOwnerName");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelIsNull() {
            addCriterion("business_level is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelIsNotNull() {
            addCriterion("business_level is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelEqualTo(Byte value) {
            addCriterion("business_level =", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelNotEqualTo(Byte value) {
            addCriterion("business_level <>", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelGreaterThan(Byte value) {
            addCriterion("business_level >", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("business_level >=", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelLessThan(Byte value) {
            addCriterion("business_level <", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelLessThanOrEqualTo(Byte value) {
            addCriterion("business_level <=", value, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelIn(List<Byte> values) {
            addCriterion("business_level in", values, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelNotIn(List<Byte> values) {
            addCriterion("business_level not in", values, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelBetween(Byte value1, Byte value2) {
            addCriterion("business_level between", value1, value2, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("business_level not between", value1, value2, "businessLevel");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceIsNull() {
            addCriterion("business_importance is null");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceIsNotNull() {
            addCriterion("business_importance is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceEqualTo(Byte value) {
            addCriterion("business_importance =", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceNotEqualTo(Byte value) {
            addCriterion("business_importance <>", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceGreaterThan(Byte value) {
            addCriterion("business_importance >", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceGreaterThanOrEqualTo(Byte value) {
            addCriterion("business_importance >=", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceLessThan(Byte value) {
            addCriterion("business_importance <", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceLessThanOrEqualTo(Byte value) {
            addCriterion("business_importance <=", value, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceIn(List<Byte> values) {
            addCriterion("business_importance in", values, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceNotIn(List<Byte> values) {
            addCriterion("business_importance not in", values, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceBetween(Byte value1, Byte value2) {
            addCriterion("business_importance between", value1, value2, "businessImportance");
            return (Criteria) this;
        }

        public Criteria andBusinessImportanceNotBetween(Byte value1, Byte value2) {
            addCriterion("business_importance not between", value1, value2, "businessImportance");
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

        public Criteria andActualWorkloadIsNull() {
            addCriterion("actual_workload is null");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadIsNotNull() {
            addCriterion("actual_workload is not null");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadEqualTo(Integer value) {
            addCriterion("actual_workload =", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadNotEqualTo(Integer value) {
            addCriterion("actual_workload <>", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadGreaterThan(Integer value) {
            addCriterion("actual_workload >", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_workload >=", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadLessThan(Integer value) {
            addCriterion("actual_workload <", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadLessThanOrEqualTo(Integer value) {
            addCriterion("actual_workload <=", value, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadIn(List<Integer> values) {
            addCriterion("actual_workload in", values, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadNotIn(List<Integer> values) {
            addCriterion("actual_workload not in", values, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadBetween(Integer value1, Integer value2) {
            addCriterion("actual_workload between", value1, value2, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andActualWorkloadNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_workload not between", value1, value2, "actualWorkload");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNull() {
            addCriterion("plan_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNotNull() {
            addCriterion("plan_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeEqualTo(Date value) {
            addCriterion("plan_start_time =", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotEqualTo(Date value) {
            addCriterion("plan_start_time <>", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThan(Date value) {
            addCriterion("plan_start_time >", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_start_time >=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThan(Date value) {
            addCriterion("plan_start_time <", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_start_time <=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIn(List<Date> values) {
            addCriterion("plan_start_time in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotIn(List<Date> values) {
            addCriterion("plan_start_time not in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeBetween(Date value1, Date value2) {
            addCriterion("plan_start_time between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_start_time not between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIsNull() {
            addCriterion("plan_end_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIsNotNull() {
            addCriterion("plan_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeEqualTo(Date value) {
            addCriterion("plan_end_time =", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotEqualTo(Date value) {
            addCriterion("plan_end_time <>", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeGreaterThan(Date value) {
            addCriterion("plan_end_time >", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_end_time >=", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeLessThan(Date value) {
            addCriterion("plan_end_time <", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_end_time <=", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIn(List<Date> values) {
            addCriterion("plan_end_time in", values, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotIn(List<Date> values) {
            addCriterion("plan_end_time not in", values, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeBetween(Date value1, Date value2) {
            addCriterion("plan_end_time between", value1, value2, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_end_time not between", value1, value2, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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