package com.yusys.agile.zentao.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZtProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZtProductExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andLineIsNull() {
            addCriterion("line is null");
            return (Criteria) this;
        }

        public Criteria andLineIsNotNull() {
            addCriterion("line is not null");
            return (Criteria) this;
        }

        public Criteria andLineEqualTo(Integer value) {
            addCriterion("line =", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotEqualTo(Integer value) {
            addCriterion("line <>", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThan(Integer value) {
            addCriterion("line >", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThanOrEqualTo(Integer value) {
            addCriterion("line >=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThan(Integer value) {
            addCriterion("line <", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThanOrEqualTo(Integer value) {
            addCriterion("line <=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineIn(List<Integer> values) {
            addCriterion("line in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotIn(List<Integer> values) {
            addCriterion("line not in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineBetween(Integer value1, Integer value2) {
            addCriterion("line between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotBetween(Integer value1, Integer value2) {
            addCriterion("line not between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPoIsNull() {
            addCriterion("PO is null");
            return (Criteria) this;
        }

        public Criteria andPoIsNotNull() {
            addCriterion("PO is not null");
            return (Criteria) this;
        }

        public Criteria andPoEqualTo(String value) {
            addCriterion("PO =", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoNotEqualTo(String value) {
            addCriterion("PO <>", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoGreaterThan(String value) {
            addCriterion("PO >", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoGreaterThanOrEqualTo(String value) {
            addCriterion("PO >=", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoLessThan(String value) {
            addCriterion("PO <", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoLessThanOrEqualTo(String value) {
            addCriterion("PO <=", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoLike(String value) {
            addCriterion("PO like", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoNotLike(String value) {
            addCriterion("PO not like", value, "po");
            return (Criteria) this;
        }

        public Criteria andPoIn(List<String> values) {
            addCriterion("PO in", values, "po");
            return (Criteria) this;
        }

        public Criteria andPoNotIn(List<String> values) {
            addCriterion("PO not in", values, "po");
            return (Criteria) this;
        }

        public Criteria andPoBetween(String value1, String value2) {
            addCriterion("PO between", value1, value2, "po");
            return (Criteria) this;
        }

        public Criteria andPoNotBetween(String value1, String value2) {
            addCriterion("PO not between", value1, value2, "po");
            return (Criteria) this;
        }

        public Criteria andQdIsNull() {
            addCriterion("QD is null");
            return (Criteria) this;
        }

        public Criteria andQdIsNotNull() {
            addCriterion("QD is not null");
            return (Criteria) this;
        }

        public Criteria andQdEqualTo(String value) {
            addCriterion("QD =", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdNotEqualTo(String value) {
            addCriterion("QD <>", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdGreaterThan(String value) {
            addCriterion("QD >", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdGreaterThanOrEqualTo(String value) {
            addCriterion("QD >=", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdLessThan(String value) {
            addCriterion("QD <", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdLessThanOrEqualTo(String value) {
            addCriterion("QD <=", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdLike(String value) {
            addCriterion("QD like", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdNotLike(String value) {
            addCriterion("QD not like", value, "qd");
            return (Criteria) this;
        }

        public Criteria andQdIn(List<String> values) {
            addCriterion("QD in", values, "qd");
            return (Criteria) this;
        }

        public Criteria andQdNotIn(List<String> values) {
            addCriterion("QD not in", values, "qd");
            return (Criteria) this;
        }

        public Criteria andQdBetween(String value1, String value2) {
            addCriterion("QD between", value1, value2, "qd");
            return (Criteria) this;
        }

        public Criteria andQdNotBetween(String value1, String value2) {
            addCriterion("QD not between", value1, value2, "qd");
            return (Criteria) this;
        }

        public Criteria andRdIsNull() {
            addCriterion("RD is null");
            return (Criteria) this;
        }

        public Criteria andRdIsNotNull() {
            addCriterion("RD is not null");
            return (Criteria) this;
        }

        public Criteria andRdEqualTo(String value) {
            addCriterion("RD =", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdNotEqualTo(String value) {
            addCriterion("RD <>", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdGreaterThan(String value) {
            addCriterion("RD >", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdGreaterThanOrEqualTo(String value) {
            addCriterion("RD >=", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdLessThan(String value) {
            addCriterion("RD <", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdLessThanOrEqualTo(String value) {
            addCriterion("RD <=", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdLike(String value) {
            addCriterion("RD like", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdNotLike(String value) {
            addCriterion("RD not like", value, "rd");
            return (Criteria) this;
        }

        public Criteria andRdIn(List<String> values) {
            addCriterion("RD in", values, "rd");
            return (Criteria) this;
        }

        public Criteria andRdNotIn(List<String> values) {
            addCriterion("RD not in", values, "rd");
            return (Criteria) this;
        }

        public Criteria andRdBetween(String value1, String value2) {
            addCriterion("RD between", value1, value2, "rd");
            return (Criteria) this;
        }

        public Criteria andRdNotBetween(String value1, String value2) {
            addCriterion("RD not between", value1, value2, "rd");
            return (Criteria) this;
        }

        public Criteria andAclIsNull() {
            addCriterion("acl is null");
            return (Criteria) this;
        }

        public Criteria andAclIsNotNull() {
            addCriterion("acl is not null");
            return (Criteria) this;
        }

        public Criteria andAclEqualTo(String value) {
            addCriterion("acl =", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclNotEqualTo(String value) {
            addCriterion("acl <>", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclGreaterThan(String value) {
            addCriterion("acl >", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclGreaterThanOrEqualTo(String value) {
            addCriterion("acl >=", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclLessThan(String value) {
            addCriterion("acl <", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclLessThanOrEqualTo(String value) {
            addCriterion("acl <=", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclLike(String value) {
            addCriterion("acl like", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclNotLike(String value) {
            addCriterion("acl not like", value, "acl");
            return (Criteria) this;
        }

        public Criteria andAclIn(List<String> values) {
            addCriterion("acl in", values, "acl");
            return (Criteria) this;
        }

        public Criteria andAclNotIn(List<String> values) {
            addCriterion("acl not in", values, "acl");
            return (Criteria) this;
        }

        public Criteria andAclBetween(String value1, String value2) {
            addCriterion("acl between", value1, value2, "acl");
            return (Criteria) this;
        }

        public Criteria andAclNotBetween(String value1, String value2) {
            addCriterion("acl not between", value1, value2, "acl");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIsNull() {
            addCriterion("createdBy is null");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIsNotNull() {
            addCriterion("createdBy is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedbyEqualTo(String value) {
            addCriterion("createdBy =", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotEqualTo(String value) {
            addCriterion("createdBy <>", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyGreaterThan(String value) {
            addCriterion("createdBy >", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyGreaterThanOrEqualTo(String value) {
            addCriterion("createdBy >=", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLessThan(String value) {
            addCriterion("createdBy <", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLessThanOrEqualTo(String value) {
            addCriterion("createdBy <=", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLike(String value) {
            addCriterion("createdBy like", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotLike(String value) {
            addCriterion("createdBy not like", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIn(List<String> values) {
            addCriterion("createdBy in", values, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotIn(List<String> values) {
            addCriterion("createdBy not in", values, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyBetween(String value1, String value2) {
            addCriterion("createdBy between", value1, value2, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotBetween(String value1, String value2) {
            addCriterion("createdBy not between", value1, value2, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreateddateIsNull() {
            addCriterion("createdDate is null");
            return (Criteria) this;
        }

        public Criteria andCreateddateIsNotNull() {
            addCriterion("createdDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreateddateEqualTo(Date value) {
            addCriterion("createdDate =", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotEqualTo(Date value) {
            addCriterion("createdDate <>", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateGreaterThan(Date value) {
            addCriterion("createdDate >", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdDate >=", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateLessThan(Date value) {
            addCriterion("createdDate <", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateLessThanOrEqualTo(Date value) {
            addCriterion("createdDate <=", value, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateIn(List<Date> values) {
            addCriterion("createdDate in", values, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotIn(List<Date> values) {
            addCriterion("createdDate not in", values, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateBetween(Date value1, Date value2) {
            addCriterion("createdDate between", value1, value2, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreateddateNotBetween(Date value1, Date value2) {
            addCriterion("createdDate not between", value1, value2, "createddate");
            return (Criteria) this;
        }

        public Criteria andCreatedversionIsNull() {
            addCriterion("createdVersion is null");
            return (Criteria) this;
        }

        public Criteria andCreatedversionIsNotNull() {
            addCriterion("createdVersion is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedversionEqualTo(String value) {
            addCriterion("createdVersion =", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionNotEqualTo(String value) {
            addCriterion("createdVersion <>", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionGreaterThan(String value) {
            addCriterion("createdVersion >", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionGreaterThanOrEqualTo(String value) {
            addCriterion("createdVersion >=", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionLessThan(String value) {
            addCriterion("createdVersion <", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionLessThanOrEqualTo(String value) {
            addCriterion("createdVersion <=", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionLike(String value) {
            addCriterion("createdVersion like", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionNotLike(String value) {
            addCriterion("createdVersion not like", value, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionIn(List<String> values) {
            addCriterion("createdVersion in", values, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionNotIn(List<String> values) {
            addCriterion("createdVersion not in", values, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionBetween(String value1, String value2) {
            addCriterion("createdVersion between", value1, value2, "createdversion");
            return (Criteria) this;
        }

        public Criteria andCreatedversionNotBetween(String value1, String value2) {
            addCriterion("createdVersion not between", value1, value2, "createdversion");
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

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(String value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(String value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(String value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(String value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(String value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(String value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLike(String value) {
            addCriterion("deleted like", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotLike(String value) {
            addCriterion("deleted not like", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<String> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<String> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(String value1, String value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(String value1, String value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
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