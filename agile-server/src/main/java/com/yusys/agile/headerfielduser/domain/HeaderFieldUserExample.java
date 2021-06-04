package com.yusys.agile.headerfielduser.domain;

import java.util.ArrayList;
import java.util.List;

public class HeaderFieldUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<HeaderFieldUserExample.Criteria> oredCriteria;

    public HeaderFieldUserExample() {
        oredCriteria = new ArrayList<HeaderFieldUserExample.Criteria>();
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

    public List<HeaderFieldUserExample.Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(HeaderFieldUserExample.Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public HeaderFieldUserExample.Criteria or() {
        HeaderFieldUserExample.Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public HeaderFieldUserExample.Criteria createCriteria() {
        HeaderFieldUserExample.Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected HeaderFieldUserExample.Criteria createCriteriaInternal() {
        HeaderFieldUserExample.Criteria criteria = new HeaderFieldUserExample.Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<HeaderFieldUserExample.Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<HeaderFieldUserExample.Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<HeaderFieldUserExample.Criterion> getAllCriteria() {
            return criteria;
        }

        public List<HeaderFieldUserExample.Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new HeaderFieldUserExample.Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new HeaderFieldUserExample.Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new HeaderFieldUserExample.Criterion(condition, value1, value2));
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdIsNull() {
            addCriterion("header_user_id is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdIsNotNull() {
            addCriterion("header_user_id is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdEqualTo(Long value) {
            addCriterion("header_user_id =", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdNotEqualTo(Long value) {
            addCriterion("header_user_id <>", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdGreaterThan(Long value) {
            addCriterion("header_user_id >", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("header_user_id >=", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdLessThan(Long value) {
            addCriterion("header_user_id <", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdLessThanOrEqualTo(Long value) {
            addCriterion("header_user_id <=", value, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdIn(List<Long> values) {
            addCriterion("header_user_id in", values, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdNotIn(List<Long> values) {
            addCriterion("header_user_id not in", values, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdBetween(Long value1, Long value2) {
            addCriterion("header_user_id between", value1, value2, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andHeaderUserIdNotBetween(Long value1, Long value2) {
            addCriterion("header_user_id not between", value1, value2, "headerUserId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdIsNull() {
            addCriterion("field_id is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdIsNotNull() {
            addCriterion("field_id is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdEqualTo(Long value) {
            addCriterion("field_id =", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdNotEqualTo(Long value) {
            addCriterion("field_id <>", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdGreaterThan(Long value) {
            addCriterion("field_id >", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdGreaterThanOrEqualTo(Long value) {
            addCriterion("field_id >=", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdLessThan(Long value) {
            addCriterion("field_id <", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdLessThanOrEqualTo(Long value) {
            addCriterion("field_id <=", value, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdIn(List<Long> values) {
            addCriterion("field_id in", values, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdNotIn(List<Long> values) {
            addCriterion("field_id not in", values, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdBetween(Long value1, Long value2) {
            addCriterion("field_id between", value1, value2, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldIdNotBetween(Long value1, Long value2) {
            addCriterion("field_id not between", value1, value2, "fieldId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdEqualTo(Long value) {
            addCriterion("project_id =", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdNotEqualTo(Long value) {
            addCriterion("project_id <>", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdGreaterThan(Long value) {
            addCriterion("project_id >", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdGreaterThanOrEqualTo(Long value) {
            addCriterion("project_id >=", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdLessThan(Long value) {
            addCriterion("project_id <", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdLessThanOrEqualTo(Long value) {
            addCriterion("project_id <=", value, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdIn(List<Long> values) {
            addCriterion("project_id in", values, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdNotIn(List<Long> values) {
            addCriterion("project_id not in", values, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdBetween(Long value1, Long value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andProjectIdNotBetween(Long value1, Long value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoEqualTo(Integer value) {
            addCriterion("order_no =", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoNotEqualTo(Integer value) {
            addCriterion("order_no <>", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoGreaterThan(Integer value) {
            addCriterion("order_no >", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_no >=", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoLessThan(Integer value) {
            addCriterion("order_no <", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoLessThanOrEqualTo(Integer value) {
            addCriterion("order_no <=", value, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoIn(List<Integer> values) {
            addCriterion("order_no in", values, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoNotIn(List<Integer> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoBetween(Integer value1, Integer value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andOrderNoNotBetween(Integer value1, Integer value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeIsNull() {
            addCriterion("field_type is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeIsNotNull() {
            addCriterion("field_type is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeEqualTo(Byte value) {
            addCriterion("field_type =", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeNotEqualTo(Byte value) {
            addCriterion("field_type <>", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeGreaterThan(Byte value) {
            addCriterion("field_type >", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("field_type >=", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeLessThan(Byte value) {
            addCriterion("field_type <", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeLessThanOrEqualTo(Byte value) {
            addCriterion("field_type <=", value, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeIn(List<Byte> values) {
            addCriterion("field_type in", values, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeNotIn(List<Byte> values) {
            addCriterion("field_type not in", values, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeBetween(Byte value1, Byte value2) {
            addCriterion("field_type between", value1, value2, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andFieldTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("field_type not between", value1, value2, "fieldType");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyIsNull() {
            addCriterion("apply is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyIsNotNull() {
            addCriterion("apply is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyEqualTo(Byte value) {
            addCriterion("apply =", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyNotEqualTo(Byte value) {
            addCriterion("apply <>", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyGreaterThan(Byte value) {
            addCriterion("apply >", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyGreaterThanOrEqualTo(Byte value) {
            addCriterion("apply >=", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyLessThan(Byte value) {
            addCriterion("apply <", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyLessThanOrEqualTo(Byte value) {
            addCriterion("apply <=", value, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyIn(List<Byte> values) {
            addCriterion("apply in", values, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyNotIn(List<Byte> values) {
            addCriterion("apply not in", values, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyBetween(Byte value1, Byte value2) {
            addCriterion("apply between", value1, value2, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andApplyNotBetween(Byte value1, Byte value2) {
            addCriterion("apply not between", value1, value2, "apply");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryEqualTo(Byte value) {
            addCriterion("category =", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryNotEqualTo(Byte value) {
            addCriterion("category <>", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryGreaterThan(Byte value) {
            addCriterion("category >", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryGreaterThanOrEqualTo(Byte value) {
            addCriterion("category >=", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryLessThan(Byte value) {
            addCriterion("category <", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryLessThanOrEqualTo(Byte value) {
            addCriterion("category <=", value, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryIn(List<Byte> values) {
            addCriterion("category in", values, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryNotIn(List<Byte> values) {
            addCriterion("category not in", values, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryBetween(Byte value1, Byte value2) {
            addCriterion("category between", value1, value2, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andCategoryNotBetween(Byte value1, Byte value2) {
            addCriterion("category not between", value1, value2, "category");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterIsNull() {
            addCriterion("is_filter is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterIsNotNull() {
            addCriterion("is_filter is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterEqualTo(Byte value) {
            addCriterion("is_filter =", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterNotEqualTo(Byte value) {
            addCriterion("is_filter <>", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterGreaterThan(Byte value) {
            addCriterion("is_filter >", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_filter >=", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterLessThan(Byte value) {
            addCriterion("is_filter <", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterLessThanOrEqualTo(Byte value) {
            addCriterion("is_filter <=", value, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterIn(List<Byte> values) {
            addCriterion("is_filter in", values, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterNotIn(List<Byte> values) {
            addCriterion("is_filter not in", values, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterBetween(Byte value1, Byte value2) {
            addCriterion("is_filter between", value1, value2, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andIsFilterNotBetween(Byte value1, Byte value2) {
            addCriterion("is_filter not between", value1, value2, "isFilter");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateIsNull() {
            addCriterion("state is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeIsNull() {
            addCriterion("tenant_code is null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeIsNotNull() {
            addCriterion("tenant_code is not null");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeEqualTo(String value) {
            addCriterion("tenant_code =", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeNotEqualTo(String value) {
            addCriterion("tenant_code <>", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeGreaterThan(String value) {
            addCriterion("tenant_code >", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tenant_code >=", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeLessThan(String value) {
            addCriterion("tenant_code <", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeLessThanOrEqualTo(String value) {
            addCriterion("tenant_code <=", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeLike(String value) {
            addCriterion("tenant_code like", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeNotLike(String value) {
            addCriterion("tenant_code not like", value, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeIn(List<String> values) {
            addCriterion("tenant_code in", values, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeNotIn(List<String> values) {
            addCriterion("tenant_code not in", values, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeBetween(String value1, String value2) {
            addCriterion("tenant_code between", value1, value2, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }

        public HeaderFieldUserExample.Criteria andTenantCodeNotBetween(String value1, String value2) {
            addCriterion("tenant_code not between", value1, value2, "tenantCode");
            return (HeaderFieldUserExample.Criteria) this;
        }
    }

    public static class Criteria extends HeaderFieldUserExample.GeneratedCriteria {

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