package com.yusys.agile.issue.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CleanIssueDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CleanIssueDataExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
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

        public Criteria andBizNameIsNull() {
            addCriterion("biz_name is null");
            return (Criteria) this;
        }

        public Criteria andBizNameIsNotNull() {
            addCriterion("biz_name is not null");
            return (Criteria) this;
        }

        public Criteria andBizNameEqualTo(String value) {
            addCriterion("biz_name =", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotEqualTo(String value) {
            addCriterion("biz_name <>", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameGreaterThan(String value) {
            addCriterion("biz_name >", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameGreaterThanOrEqualTo(String value) {
            addCriterion("biz_name >=", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLessThan(String value) {
            addCriterion("biz_name <", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLessThanOrEqualTo(String value) {
            addCriterion("biz_name <=", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameLike(String value) {
            addCriterion("biz_name like", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotLike(String value) {
            addCriterion("biz_name not like", value, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameIn(List<String> values) {
            addCriterion("biz_name in", values, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotIn(List<String> values) {
            addCriterion("biz_name not in", values, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameBetween(String value1, String value2) {
            addCriterion("biz_name between", value1, value2, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNameNotBetween(String value1, String value2) {
            addCriterion("biz_name not between", value1, value2, "bizName");
            return (Criteria) this;
        }

        public Criteria andBizNumIsNull() {
            addCriterion("biz_num is null");
            return (Criteria) this;
        }

        public Criteria andBizNumIsNotNull() {
            addCriterion("biz_num is not null");
            return (Criteria) this;
        }

        public Criteria andBizNumEqualTo(String value) {
            addCriterion("biz_num =", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumNotEqualTo(String value) {
            addCriterion("biz_num <>", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumGreaterThan(String value) {
            addCriterion("biz_num >", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumGreaterThanOrEqualTo(String value) {
            addCriterion("biz_num >=", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumLessThan(String value) {
            addCriterion("biz_num <", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumLessThanOrEqualTo(String value) {
            addCriterion("biz_num <=", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumLike(String value) {
            addCriterion("biz_num like", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumNotLike(String value) {
            addCriterion("biz_num not like", value, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumIn(List<String> values) {
            addCriterion("biz_num in", values, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumNotIn(List<String> values) {
            addCriterion("biz_num not in", values, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumBetween(String value1, String value2) {
            addCriterion("biz_num between", value1, value2, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizNumNotBetween(String value1, String value2) {
            addCriterion("biz_num not between", value1, value2, "bizNum");
            return (Criteria) this;
        }

        public Criteria andBizSourceIsNull() {
            addCriterion("biz_source is null");
            return (Criteria) this;
        }

        public Criteria andBizSourceIsNotNull() {
            addCriterion("biz_source is not null");
            return (Criteria) this;
        }

        public Criteria andBizSourceEqualTo(Long value) {
            addCriterion("biz_source =", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceNotEqualTo(Long value) {
            addCriterion("biz_source <>", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceGreaterThan(Long value) {
            addCriterion("biz_source >", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceGreaterThanOrEqualTo(Long value) {
            addCriterion("biz_source >=", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceLessThan(Long value) {
            addCriterion("biz_source <", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceLessThanOrEqualTo(Long value) {
            addCriterion("biz_source <=", value, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceIn(List<Long> values) {
            addCriterion("biz_source in", values, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceNotIn(List<Long> values) {
            addCriterion("biz_source not in", values, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceBetween(Long value1, Long value2) {
            addCriterion("biz_source between", value1, value2, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizSourceNotBetween(Long value1, Long value2) {
            addCriterion("biz_source not between", value1, value2, "bizSource");
            return (Criteria) this;
        }

        public Criteria andBizResIsNull() {
            addCriterion("biz_res is null");
            return (Criteria) this;
        }

        public Criteria andBizResIsNotNull() {
            addCriterion("biz_res is not null");
            return (Criteria) this;
        }

        public Criteria andBizResEqualTo(String value) {
            addCriterion("biz_res =", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResNotEqualTo(String value) {
            addCriterion("biz_res <>", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResGreaterThan(String value) {
            addCriterion("biz_res >", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResGreaterThanOrEqualTo(String value) {
            addCriterion("biz_res >=", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResLessThan(String value) {
            addCriterion("biz_res <", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResLessThanOrEqualTo(String value) {
            addCriterion("biz_res <=", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResLike(String value) {
            addCriterion("biz_res like", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResNotLike(String value) {
            addCriterion("biz_res not like", value, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResIn(List<String> values) {
            addCriterion("biz_res in", values, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResNotIn(List<String> values) {
            addCriterion("biz_res not in", values, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResBetween(String value1, String value2) {
            addCriterion("biz_res between", value1, value2, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizResNotBetween(String value1, String value2) {
            addCriterion("biz_res not between", value1, value2, "bizRes");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingIsNull() {
            addCriterion("biz_scheduling is null");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingIsNotNull() {
            addCriterion("biz_scheduling is not null");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingEqualTo(String value) {
            addCriterion("biz_scheduling =", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingNotEqualTo(String value) {
            addCriterion("biz_scheduling <>", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingGreaterThan(String value) {
            addCriterion("biz_scheduling >", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingGreaterThanOrEqualTo(String value) {
            addCriterion("biz_scheduling >=", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingLessThan(String value) {
            addCriterion("biz_scheduling <", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingLessThanOrEqualTo(String value) {
            addCriterion("biz_scheduling <=", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingLike(String value) {
            addCriterion("biz_scheduling like", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingNotLike(String value) {
            addCriterion("biz_scheduling not like", value, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingIn(List<String> values) {
            addCriterion("biz_scheduling in", values, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingNotIn(List<String> values) {
            addCriterion("biz_scheduling not in", values, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingBetween(String value1, String value2) {
            addCriterion("biz_scheduling between", value1, value2, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andBizSchedulingNotBetween(String value1, String value2) {
            addCriterion("biz_scheduling not between", value1, value2, "bizScheduling");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumIsNull() {
            addCriterion("parta_req_num is null");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumIsNotNull() {
            addCriterion("parta_req_num is not null");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumEqualTo(String value) {
            addCriterion("parta_req_num =", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumNotEqualTo(String value) {
            addCriterion("parta_req_num <>", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumGreaterThan(String value) {
            addCriterion("parta_req_num >", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumGreaterThanOrEqualTo(String value) {
            addCriterion("parta_req_num >=", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumLessThan(String value) {
            addCriterion("parta_req_num <", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumLessThanOrEqualTo(String value) {
            addCriterion("parta_req_num <=", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumLike(String value) {
            addCriterion("parta_req_num like", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumNotLike(String value) {
            addCriterion("parta_req_num not like", value, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumIn(List<String> values) {
            addCriterion("parta_req_num in", values, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumNotIn(List<String> values) {
            addCriterion("parta_req_num not in", values, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumBetween(String value1, String value2) {
            addCriterion("parta_req_num between", value1, value2, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andPartaReqNumNotBetween(String value1, String value2) {
            addCriterion("parta_req_num not between", value1, value2, "partaReqNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumIsNull() {
            addCriterion("scheduling_num is null");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumIsNotNull() {
            addCriterion("scheduling_num is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumEqualTo(String value) {
            addCriterion("scheduling_num =", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumNotEqualTo(String value) {
            addCriterion("scheduling_num <>", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumGreaterThan(String value) {
            addCriterion("scheduling_num >", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumGreaterThanOrEqualTo(String value) {
            addCriterion("scheduling_num >=", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumLessThan(String value) {
            addCriterion("scheduling_num <", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumLessThanOrEqualTo(String value) {
            addCriterion("scheduling_num <=", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumLike(String value) {
            addCriterion("scheduling_num like", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumNotLike(String value) {
            addCriterion("scheduling_num not like", value, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumIn(List<String> values) {
            addCriterion("scheduling_num in", values, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumNotIn(List<String> values) {
            addCriterion("scheduling_num not in", values, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumBetween(String value1, String value2) {
            addCriterion("scheduling_num between", value1, value2, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andSchedulingNumNotBetween(String value1, String value2) {
            addCriterion("scheduling_num not between", value1, value2, "schedulingNum");
            return (Criteria) this;
        }

        public Criteria andMakeManIsNull() {
            addCriterion("make_man is null");
            return (Criteria) this;
        }

        public Criteria andMakeManIsNotNull() {
            addCriterion("make_man is not null");
            return (Criteria) this;
        }

        public Criteria andMakeManEqualTo(String value) {
            addCriterion("make_man =", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManNotEqualTo(String value) {
            addCriterion("make_man <>", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManGreaterThan(String value) {
            addCriterion("make_man >", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManGreaterThanOrEqualTo(String value) {
            addCriterion("make_man >=", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManLessThan(String value) {
            addCriterion("make_man <", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManLessThanOrEqualTo(String value) {
            addCriterion("make_man <=", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManLike(String value) {
            addCriterion("make_man like", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManNotLike(String value) {
            addCriterion("make_man not like", value, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManIn(List<String> values) {
            addCriterion("make_man in", values, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManNotIn(List<String> values) {
            addCriterion("make_man not in", values, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManBetween(String value1, String value2) {
            addCriterion("make_man between", value1, value2, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeManNotBetween(String value1, String value2) {
            addCriterion("make_man not between", value1, value2, "makeMan");
            return (Criteria) this;
        }

        public Criteria andMakeDepartIsNull() {
            addCriterion("make_depart is null");
            return (Criteria) this;
        }

        public Criteria andMakeDepartIsNotNull() {
            addCriterion("make_depart is not null");
            return (Criteria) this;
        }

        public Criteria andMakeDepartEqualTo(Long value) {
            addCriterion("make_depart =", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartNotEqualTo(Long value) {
            addCriterion("make_depart <>", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartGreaterThan(Long value) {
            addCriterion("make_depart >", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartGreaterThanOrEqualTo(Long value) {
            addCriterion("make_depart >=", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartLessThan(Long value) {
            addCriterion("make_depart <", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartLessThanOrEqualTo(Long value) {
            addCriterion("make_depart <=", value, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartIn(List<Long> values) {
            addCriterion("make_depart in", values, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartNotIn(List<Long> values) {
            addCriterion("make_depart not in", values, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartBetween(Long value1, Long value2) {
            addCriterion("make_depart between", value1, value2, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andMakeDepartNotBetween(Long value1, Long value2) {
            addCriterion("make_depart not between", value1, value2, "makeDepart");
            return (Criteria) this;
        }

        public Criteria andReqGroupIsNull() {
            addCriterion("req_group is null");
            return (Criteria) this;
        }

        public Criteria andReqGroupIsNotNull() {
            addCriterion("req_group is not null");
            return (Criteria) this;
        }

        public Criteria andReqGroupEqualTo(String value) {
            addCriterion("req_group =", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupNotEqualTo(String value) {
            addCriterion("req_group <>", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupGreaterThan(String value) {
            addCriterion("req_group >", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupGreaterThanOrEqualTo(String value) {
            addCriterion("req_group >=", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupLessThan(String value) {
            addCriterion("req_group <", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupLessThanOrEqualTo(String value) {
            addCriterion("req_group <=", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupLike(String value) {
            addCriterion("req_group like", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupNotLike(String value) {
            addCriterion("req_group not like", value, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupIn(List<String> values) {
            addCriterion("req_group in", values, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupNotIn(List<String> values) {
            addCriterion("req_group not in", values, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupBetween(String value1, String value2) {
            addCriterion("req_group between", value1, value2, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andReqGroupNotBetween(String value1, String value2) {
            addCriterion("req_group not between", value1, value2, "reqGroup");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusIsNull() {
            addCriterion("biz_plan_status is null");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusIsNotNull() {
            addCriterion("biz_plan_status is not null");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusEqualTo(Long value) {
            addCriterion("biz_plan_status =", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusNotEqualTo(Long value) {
            addCriterion("biz_plan_status <>", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusGreaterThan(Long value) {
            addCriterion("biz_plan_status >", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusGreaterThanOrEqualTo(Long value) {
            addCriterion("biz_plan_status >=", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusLessThan(Long value) {
            addCriterion("biz_plan_status <", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusLessThanOrEqualTo(Long value) {
            addCriterion("biz_plan_status <=", value, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusIn(List<Long> values) {
            addCriterion("biz_plan_status in", values, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusNotIn(List<Long> values) {
            addCriterion("biz_plan_status not in", values, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusBetween(Long value1, Long value2) {
            addCriterion("biz_plan_status between", value1, value2, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizPlanStatusNotBetween(Long value1, Long value2) {
            addCriterion("biz_plan_status not between", value1, value2, "bizPlanStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusIsNull() {
            addCriterion("biz_status is null");
            return (Criteria) this;
        }

        public Criteria andBizStatusIsNotNull() {
            addCriterion("biz_status is not null");
            return (Criteria) this;
        }

        public Criteria andBizStatusEqualTo(Long value) {
            addCriterion("biz_status =", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusNotEqualTo(Long value) {
            addCriterion("biz_status <>", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusGreaterThan(Long value) {
            addCriterion("biz_status >", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusGreaterThanOrEqualTo(Long value) {
            addCriterion("biz_status >=", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusLessThan(Long value) {
            addCriterion("biz_status <", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusLessThanOrEqualTo(Long value) {
            addCriterion("biz_status <=", value, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusIn(List<Long> values) {
            addCriterion("biz_status in", values, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusNotIn(List<Long> values) {
            addCriterion("biz_status not in", values, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusBetween(Long value1, Long value2) {
            addCriterion("biz_status between", value1, value2, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andBizStatusNotBetween(Long value1, Long value2) {
            addCriterion("biz_status not between", value1, value2, "bizStatus");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeIsNull() {
            addCriterion("ask_line_time is null");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeIsNotNull() {
            addCriterion("ask_line_time is not null");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeEqualTo(String value) {
            addCriterion("ask_line_time =", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeNotEqualTo(String value) {
            addCriterion("ask_line_time <>", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeGreaterThan(String value) {
            addCriterion("ask_line_time >", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ask_line_time >=", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeLessThan(String value) {
            addCriterion("ask_line_time <", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeLessThanOrEqualTo(String value) {
            addCriterion("ask_line_time <=", value, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeIn(List<String> values) {
            addCriterion("ask_line_time in", values, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeNotIn(List<String> values) {
            addCriterion("ask_line_time not in", values, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeBetween(String value1, String value2) {
            addCriterion("ask_line_time between", value1, value2, "askLineTime");
            return (Criteria) this;
        }

        public Criteria andAskLineTimeNotBetween(String value1, String value2) {
            addCriterion("ask_line_time not between", value1, value2, "askLineTime");
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

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andCloseTimeEqualTo(String value) {
            addCriterion("close_time =", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotEqualTo(String value) {
            addCriterion("close_time <>", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThan(String value) {
            addCriterion("close_time >", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeGreaterThanOrEqualTo(String value) {
            addCriterion("close_time >=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThan(String value) {
            addCriterion("close_time <", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLessThanOrEqualTo(String value) {
            addCriterion("close_time <=", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeLike(String value) {
            addCriterion("close_time like", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotLike(String value) {
            addCriterion("close_time not like", value, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeIn(List<String> values) {
            addCriterion("close_time in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotIn(List<String> values) {
            addCriterion("close_time not in", values, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeBetween(String value1, String value2) {
            addCriterion("close_time between", value1, value2, "closeTime");
            return (Criteria) this;
        }

        public Criteria andCloseTimeNotBetween(String value1, String value2) {
            addCriterion("close_time not between", value1, value2, "closeTime");
            return (Criteria) this;
        }

        public Criteria andSystemNameIsNull() {
            addCriterion("system_name is null");
            return (Criteria) this;
        }

        public Criteria andSystemNameIsNotNull() {
            addCriterion("system_name is not null");
            return (Criteria) this;
        }

        public Criteria andSystemNameEqualTo(String value) {
            addCriterion("system_name =", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotEqualTo(String value) {
            addCriterion("system_name <>", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameGreaterThan(String value) {
            addCriterion("system_name >", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameGreaterThanOrEqualTo(String value) {
            addCriterion("system_name >=", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLessThan(String value) {
            addCriterion("system_name <", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLessThanOrEqualTo(String value) {
            addCriterion("system_name <=", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLike(String value) {
            addCriterion("system_name like", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotLike(String value) {
            addCriterion("system_name not like", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameIn(List<String> values) {
            addCriterion("system_name in", values, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotIn(List<String> values) {
            addCriterion("system_name not in", values, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameBetween(String value1, String value2) {
            addCriterion("system_name between", value1, value2, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotBetween(String value1, String value2) {
            addCriterion("system_name not between", value1, value2, "systemName");
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