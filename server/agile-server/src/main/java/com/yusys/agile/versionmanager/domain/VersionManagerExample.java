package com.yusys.agile.versionmanager.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VersionManagerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VersionManagerExample() {
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

        public Criteria andVersionNameIsNull() {
            addCriterion("version_name is null");
            return (Criteria) this;
        }

        public Criteria andVersionNameIsNotNull() {
            addCriterion("version_name is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNameEqualTo(String value) {
            addCriterion("version_name =", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotEqualTo(String value) {
            addCriterion("version_name <>", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameGreaterThan(String value) {
            addCriterion("version_name >", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameGreaterThanOrEqualTo(String value) {
            addCriterion("version_name >=", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLessThan(String value) {
            addCriterion("version_name <", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLessThanOrEqualTo(String value) {
            addCriterion("version_name <=", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLike(String value) {
            addCriterion("version_name like", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotLike(String value) {
            addCriterion("version_name not like", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameIn(List<String> values) {
            addCriterion("version_name in", values, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotIn(List<String> values) {
            addCriterion("version_name not in", values, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameBetween(String value1, String value2) {
            addCriterion("version_name between", value1, value2, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotBetween(String value1, String value2) {
            addCriterion("version_name not between", value1, value2, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionCodeIsNull() {
            addCriterion("version_code is null");
            return (Criteria) this;
        }

        public Criteria andVersionCodeIsNotNull() {
            addCriterion("version_code is not null");
            return (Criteria) this;
        }

        public Criteria andVersionCodeEqualTo(String value) {
            addCriterion("version_code =", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeNotEqualTo(String value) {
            addCriterion("version_code <>", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeGreaterThan(String value) {
            addCriterion("version_code >", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("version_code >=", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeLessThan(String value) {
            addCriterion("version_code <", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeLessThanOrEqualTo(String value) {
            addCriterion("version_code <=", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeLike(String value) {
            addCriterion("version_code like", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeNotLike(String value) {
            addCriterion("version_code not like", value, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeIn(List<String> values) {
            addCriterion("version_code in", values, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeNotIn(List<String> values) {
            addCriterion("version_code not in", values, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeBetween(String value1, String value2) {
            addCriterion("version_code between", value1, value2, "versionCode");
            return (Criteria) this;
        }

        public Criteria andVersionCodeNotBetween(String value1, String value2) {
            addCriterion("version_code not between", value1, value2, "versionCode");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateIsNull() {
            addCriterion("plan_release_date is null");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateIsNotNull() {
            addCriterion("plan_release_date is not null");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("plan_release_date =", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("plan_release_date <>", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("plan_release_date >", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_release_date >=", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateLessThan(Date value) {
            addCriterionForJDBCDate("plan_release_date <", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_release_date <=", value, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("plan_release_date in", values, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("plan_release_date not in", values, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_release_date between", value1, value2, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanReleaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_release_date not between", value1, value2, "planReleaseDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateIsNull() {
            addCriterion("plan_deploy_date is null");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateIsNotNull() {
            addCriterion("plan_deploy_date is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateEqualTo(Date value) {
            addCriterionForJDBCDate("plan_deploy_date =", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("plan_deploy_date <>", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateGreaterThan(Date value) {
            addCriterionForJDBCDate("plan_deploy_date >", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_deploy_date >=", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateLessThan(Date value) {
            addCriterionForJDBCDate("plan_deploy_date <", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_deploy_date <=", value, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateIn(List<Date> values) {
            addCriterionForJDBCDate("plan_deploy_date in", values, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("plan_deploy_date not in", values, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_deploy_date between", value1, value2, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andPlanDeployDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_deploy_date not between", value1, value2, "planDeployDate");
            return (Criteria) this;
        }

        public Criteria andVersionStateIsNull() {
            addCriterion("version_state is null");
            return (Criteria) this;
        }

        public Criteria andVersionStateIsNotNull() {
            addCriterion("version_state is not null");
            return (Criteria) this;
        }

        public Criteria andVersionStateEqualTo(String value) {
            addCriterion("version_state =", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateNotEqualTo(String value) {
            addCriterion("version_state <>", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateGreaterThan(String value) {
            addCriterion("version_state >", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateGreaterThanOrEqualTo(String value) {
            addCriterion("version_state >=", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateLessThan(String value) {
            addCriterion("version_state <", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateLessThanOrEqualTo(String value) {
            addCriterion("version_state <=", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateLike(String value) {
            addCriterion("version_state like", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateNotLike(String value) {
            addCriterion("version_state not like", value, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateIn(List<String> values) {
            addCriterion("version_state in", values, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateNotIn(List<String> values) {
            addCriterion("version_state not in", values, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateBetween(String value1, String value2) {
            addCriterion("version_state between", value1, value2, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionStateNotBetween(String value1, String value2) {
            addCriterion("version_state not between", value1, value2, "versionState");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeIsNull() {
            addCriterion("version_describe is null");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeIsNotNull() {
            addCriterion("version_describe is not null");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeEqualTo(String value) {
            addCriterion("version_describe =", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeNotEqualTo(String value) {
            addCriterion("version_describe <>", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeGreaterThan(String value) {
            addCriterion("version_describe >", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("version_describe >=", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeLessThan(String value) {
            addCriterion("version_describe <", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeLessThanOrEqualTo(String value) {
            addCriterion("version_describe <=", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeLike(String value) {
            addCriterion("version_describe like", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeNotLike(String value) {
            addCriterion("version_describe not like", value, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeIn(List<String> values) {
            addCriterion("version_describe in", values, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeNotIn(List<String> values) {
            addCriterion("version_describe not in", values, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeBetween(String value1, String value2) {
            addCriterion("version_describe between", value1, value2, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andVersionDescribeNotBetween(String value1, String value2) {
            addCriterion("version_describe not between", value1, value2, "versionDescribe");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateIsNull() {
            addCriterion("change_release_date is null");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateIsNotNull() {
            addCriterion("change_release_date is not null");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("change_release_date =", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("change_release_date <>", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("change_release_date >", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("change_release_date >=", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateLessThan(Date value) {
            addCriterionForJDBCDate("change_release_date <", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("change_release_date <=", value, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("change_release_date in", values, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("change_release_date not in", values, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("change_release_date between", value1, value2, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReleaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("change_release_date not between", value1, value2, "changeReleaseDate");
            return (Criteria) this;
        }

        public Criteria andChangeReasonIsNull() {
            addCriterion("change_reason is null");
            return (Criteria) this;
        }

        public Criteria andChangeReasonIsNotNull() {
            addCriterion("change_reason is not null");
            return (Criteria) this;
        }

        public Criteria andChangeReasonEqualTo(String value) {
            addCriterion("change_reason =", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonNotEqualTo(String value) {
            addCriterion("change_reason <>", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonGreaterThan(String value) {
            addCriterion("change_reason >", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonGreaterThanOrEqualTo(String value) {
            addCriterion("change_reason >=", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonLessThan(String value) {
            addCriterion("change_reason <", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonLessThanOrEqualTo(String value) {
            addCriterion("change_reason <=", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonLike(String value) {
            addCriterion("change_reason like", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonNotLike(String value) {
            addCriterion("change_reason not like", value, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonIn(List<String> values) {
            addCriterion("change_reason in", values, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonNotIn(List<String> values) {
            addCriterion("change_reason not in", values, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonBetween(String value1, String value2) {
            addCriterion("change_reason between", value1, value2, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeReasonNotBetween(String value1, String value2) {
            addCriterion("change_reason not between", value1, value2, "changeReason");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionIsNull() {
            addCriterion("change_description is null");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionIsNotNull() {
            addCriterion("change_description is not null");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionEqualTo(String value) {
            addCriterion("change_description =", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionNotEqualTo(String value) {
            addCriterion("change_description <>", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionGreaterThan(String value) {
            addCriterion("change_description >", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("change_description >=", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionLessThan(String value) {
            addCriterion("change_description <", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionLessThanOrEqualTo(String value) {
            addCriterion("change_description <=", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionLike(String value) {
            addCriterion("change_description like", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionNotLike(String value) {
            addCriterion("change_description not like", value, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionIn(List<String> values) {
            addCriterion("change_description in", values, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionNotIn(List<String> values) {
            addCriterion("change_description not in", values, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionBetween(String value1, String value2) {
            addCriterion("change_description between", value1, value2, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andChangeDescriptionNotBetween(String value1, String value2) {
            addCriterion("change_description not between", value1, value2, "changeDescription");
            return (Criteria) this;
        }

        public Criteria andSendToRmpIsNull() {
            addCriterion("send_to_rmp is null");
            return (Criteria) this;
        }

        public Criteria andSendToRmpIsNotNull() {
            addCriterion("send_to_rmp is not null");
            return (Criteria) this;
        }

        public Criteria andSendToRmpEqualTo(Integer value) {
            addCriterion("send_to_rmp =", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpNotEqualTo(Integer value) {
            addCriterion("send_to_rmp <>", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpGreaterThan(Integer value) {
            addCriterion("send_to_rmp >", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_to_rmp >=", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpLessThan(Integer value) {
            addCriterion("send_to_rmp <", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpLessThanOrEqualTo(Integer value) {
            addCriterion("send_to_rmp <=", value, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpIn(List<Integer> values) {
            addCriterion("send_to_rmp in", values, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpNotIn(List<Integer> values) {
            addCriterion("send_to_rmp not in", values, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpBetween(Integer value1, Integer value2) {
            addCriterion("send_to_rmp between", value1, value2, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToRmpNotBetween(Integer value1, Integer value2) {
            addCriterion("send_to_rmp not between", value1, value2, "sendToRmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpIsNull() {
            addCriterion("send_to_cmp is null");
            return (Criteria) this;
        }

        public Criteria andSendToCmpIsNotNull() {
            addCriterion("send_to_cmp is not null");
            return (Criteria) this;
        }

        public Criteria andSendToCmpEqualTo(Integer value) {
            addCriterion("send_to_cmp =", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpNotEqualTo(Integer value) {
            addCriterion("send_to_cmp <>", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpGreaterThan(Integer value) {
            addCriterion("send_to_cmp >", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_to_cmp >=", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpLessThan(Integer value) {
            addCriterion("send_to_cmp <", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpLessThanOrEqualTo(Integer value) {
            addCriterion("send_to_cmp <=", value, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpIn(List<Integer> values) {
            addCriterion("send_to_cmp in", values, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpNotIn(List<Integer> values) {
            addCriterion("send_to_cmp not in", values, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpBetween(Integer value1, Integer value2) {
            addCriterion("send_to_cmp between", value1, value2, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToCmpNotBetween(Integer value1, Integer value2) {
            addCriterion("send_to_cmp not between", value1, value2, "sendToCmp");
            return (Criteria) this;
        }

        public Criteria andSendToItcIsNull() {
            addCriterion("send_to_itc is null");
            return (Criteria) this;
        }

        public Criteria andSendToItcIsNotNull() {
            addCriterion("send_to_itc is not null");
            return (Criteria) this;
        }

        public Criteria andSendToItcEqualTo(Integer value) {
            addCriterion("send_to_itc =", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcNotEqualTo(Integer value) {
            addCriterion("send_to_itc <>", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcGreaterThan(Integer value) {
            addCriterion("send_to_itc >", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_to_itc >=", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcLessThan(Integer value) {
            addCriterion("send_to_itc <", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcLessThanOrEqualTo(Integer value) {
            addCriterion("send_to_itc <=", value, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcIn(List<Integer> values) {
            addCriterion("send_to_itc in", values, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcNotIn(List<Integer> values) {
            addCriterion("send_to_itc not in", values, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcBetween(Integer value1, Integer value2) {
            addCriterion("send_to_itc between", value1, value2, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andSendToItcNotBetween(Integer value1, Integer value2) {
            addCriterion("send_to_itc not between", value1, value2, "sendToItc");
            return (Criteria) this;
        }

        public Criteria andReviewCountIsNull() {
            addCriterion("review_count is null");
            return (Criteria) this;
        }

        public Criteria andReviewCountIsNotNull() {
            addCriterion("review_count is not null");
            return (Criteria) this;
        }

        public Criteria andReviewCountEqualTo(Integer value) {
            addCriterion("review_count =", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountNotEqualTo(Integer value) {
            addCriterion("review_count <>", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountGreaterThan(Integer value) {
            addCriterion("review_count >", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("review_count >=", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountLessThan(Integer value) {
            addCriterion("review_count <", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountLessThanOrEqualTo(Integer value) {
            addCriterion("review_count <=", value, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountIn(List<Integer> values) {
            addCriterion("review_count in", values, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountNotIn(List<Integer> values) {
            addCriterion("review_count not in", values, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountBetween(Integer value1, Integer value2) {
            addCriterion("review_count between", value1, value2, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewCountNotBetween(Integer value1, Integer value2) {
            addCriterion("review_count not between", value1, value2, "reviewCount");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeIsNull() {
            addCriterion("review_start_time is null");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeIsNotNull() {
            addCriterion("review_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeEqualTo(Date value) {
            addCriterion("review_start_time =", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeNotEqualTo(Date value) {
            addCriterion("review_start_time <>", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeGreaterThan(Date value) {
            addCriterion("review_start_time >", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("review_start_time >=", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeLessThan(Date value) {
            addCriterion("review_start_time <", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("review_start_time <=", value, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeIn(List<Date> values) {
            addCriterion("review_start_time in", values, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeNotIn(List<Date> values) {
            addCriterion("review_start_time not in", values, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeBetween(Date value1, Date value2) {
            addCriterion("review_start_time between", value1, value2, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("review_start_time not between", value1, value2, "reviewStartTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeIsNull() {
            addCriterion("review_end_time is null");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeIsNotNull() {
            addCriterion("review_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeEqualTo(Date value) {
            addCriterion("review_end_time =", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeNotEqualTo(Date value) {
            addCriterion("review_end_time <>", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeGreaterThan(Date value) {
            addCriterion("review_end_time >", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("review_end_time >=", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeLessThan(Date value) {
            addCriterion("review_end_time <", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("review_end_time <=", value, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeIn(List<Date> values) {
            addCriterion("review_end_time in", values, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeNotIn(List<Date> values) {
            addCriterion("review_end_time not in", values, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeBetween(Date value1, Date value2) {
            addCriterion("review_end_time between", value1, value2, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andReviewEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("review_end_time not between", value1, value2, "reviewEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberIsNull() {
            addCriterion("plan_delivery_number is null");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberIsNotNull() {
            addCriterion("plan_delivery_number is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberEqualTo(Integer value) {
            addCriterion("plan_delivery_number =", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberNotEqualTo(Integer value) {
            addCriterion("plan_delivery_number <>", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberGreaterThan(Integer value) {
            addCriterion("plan_delivery_number >", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_delivery_number >=", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberLessThan(Integer value) {
            addCriterion("plan_delivery_number <", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberLessThanOrEqualTo(Integer value) {
            addCriterion("plan_delivery_number <=", value, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberIn(List<Integer> values) {
            addCriterion("plan_delivery_number in", values, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberNotIn(List<Integer> values) {
            addCriterion("plan_delivery_number not in", values, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberBetween(Integer value1, Integer value2) {
            addCriterion("plan_delivery_number between", value1, value2, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andPlanDeliveryNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_delivery_number not between", value1, value2, "planDeliveryNumber");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagIsNull() {
            addCriterion("baseline_flag is null");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagIsNotNull() {
            addCriterion("baseline_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagEqualTo(Byte value) {
            addCriterion("baseline_flag =", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagNotEqualTo(Byte value) {
            addCriterion("baseline_flag <>", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagGreaterThan(Byte value) {
            addCriterion("baseline_flag >", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("baseline_flag >=", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagLessThan(Byte value) {
            addCriterion("baseline_flag <", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagLessThanOrEqualTo(Byte value) {
            addCriterion("baseline_flag <=", value, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagIn(List<Byte> values) {
            addCriterion("baseline_flag in", values, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagNotIn(List<Byte> values) {
            addCriterion("baseline_flag not in", values, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagBetween(Byte value1, Byte value2) {
            addCriterion("baseline_flag between", value1, value2, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andBaselineFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("baseline_flag not between", value1, value2, "baselineFlag");
            return (Criteria) this;
        }

        public Criteria andOperationUidIsNull() {
            addCriterion("operation_uid is null");
            return (Criteria) this;
        }

        public Criteria andOperationUidIsNotNull() {
            addCriterion("operation_uid is not null");
            return (Criteria) this;
        }

        public Criteria andOperationUidEqualTo(Integer value) {
            addCriterion("operation_uid =", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidNotEqualTo(Integer value) {
            addCriterion("operation_uid <>", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidGreaterThan(Integer value) {
            addCriterion("operation_uid >", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("operation_uid >=", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidLessThan(Integer value) {
            addCriterion("operation_uid <", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidLessThanOrEqualTo(Integer value) {
            addCriterion("operation_uid <=", value, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidIn(List<Integer> values) {
            addCriterion("operation_uid in", values, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidNotIn(List<Integer> values) {
            addCriterion("operation_uid not in", values, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidBetween(Integer value1, Integer value2) {
            addCriterion("operation_uid between", value1, value2, "operationUid");
            return (Criteria) this;
        }

        public Criteria andOperationUidNotBetween(Integer value1, Integer value2) {
            addCriterion("operation_uid not between", value1, value2, "operationUid");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeIsNull() {
            addCriterion("baseline_time is null");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeIsNotNull() {
            addCriterion("baseline_time is not null");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeEqualTo(Date value) {
            addCriterion("baseline_time =", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeNotEqualTo(Date value) {
            addCriterion("baseline_time <>", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeGreaterThan(Date value) {
            addCriterion("baseline_time >", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("baseline_time >=", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeLessThan(Date value) {
            addCriterion("baseline_time <", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeLessThanOrEqualTo(Date value) {
            addCriterion("baseline_time <=", value, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeIn(List<Date> values) {
            addCriterion("baseline_time in", values, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeNotIn(List<Date> values) {
            addCriterion("baseline_time not in", values, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeBetween(Date value1, Date value2) {
            addCriterion("baseline_time between", value1, value2, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andBaselineTimeNotBetween(Date value1, Date value2) {
            addCriterion("baseline_time not between", value1, value2, "baselineTime");
            return (Criteria) this;
        }

        public Criteria andDeployTypeIsNull() {
            addCriterion("deploy_type is null");
            return (Criteria) this;
        }

        public Criteria andDeployTypeIsNotNull() {
            addCriterion("deploy_type is not null");
            return (Criteria) this;
        }

        public Criteria andDeployTypeEqualTo(Byte value) {
            addCriterion("deploy_type =", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeNotEqualTo(Byte value) {
            addCriterion("deploy_type <>", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeGreaterThan(Byte value) {
            addCriterion("deploy_type >", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("deploy_type >=", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeLessThan(Byte value) {
            addCriterion("deploy_type <", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeLessThanOrEqualTo(Byte value) {
            addCriterion("deploy_type <=", value, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeIn(List<Byte> values) {
            addCriterion("deploy_type in", values, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeNotIn(List<Byte> values) {
            addCriterion("deploy_type not in", values, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeBetween(Byte value1, Byte value2) {
            addCriterion("deploy_type between", value1, value2, "deployType");
            return (Criteria) this;
        }

        public Criteria andDeployTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("deploy_type not between", value1, value2, "deployType");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIsNull() {
            addCriterion("batch_code is null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIsNotNull() {
            addCriterion("batch_code is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeEqualTo(String value) {
            addCriterion("batch_code =", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotEqualTo(String value) {
            addCriterion("batch_code <>", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThan(String value) {
            addCriterion("batch_code >", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThanOrEqualTo(String value) {
            addCriterion("batch_code >=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThan(String value) {
            addCriterion("batch_code <", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThanOrEqualTo(String value) {
            addCriterion("batch_code <=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLike(String value) {
            addCriterion("batch_code like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotLike(String value) {
            addCriterion("batch_code not like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIn(List<String> values) {
            addCriterion("batch_code in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotIn(List<String> values) {
            addCriterion("batch_code not in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeBetween(String value1, String value2) {
            addCriterion("batch_code between", value1, value2, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotBetween(String value1, String value2) {
            addCriterion("batch_code not between", value1, value2, "batchCode");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodIsNull() {
            addCriterion("deploy_period is null");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodIsNotNull() {
            addCriterion("deploy_period is not null");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodEqualTo(String value) {
            addCriterion("deploy_period =", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodNotEqualTo(String value) {
            addCriterion("deploy_period <>", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodGreaterThan(String value) {
            addCriterion("deploy_period >", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("deploy_period >=", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodLessThan(String value) {
            addCriterion("deploy_period <", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodLessThanOrEqualTo(String value) {
            addCriterion("deploy_period <=", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodLike(String value) {
            addCriterion("deploy_period like", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodNotLike(String value) {
            addCriterion("deploy_period not like", value, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodIn(List<String> values) {
            addCriterion("deploy_period in", values, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodNotIn(List<String> values) {
            addCriterion("deploy_period not in", values, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodBetween(String value1, String value2) {
            addCriterion("deploy_period between", value1, value2, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andDeployPeriodNotBetween(String value1, String value2) {
            addCriterion("deploy_period not between", value1, value2, "deployPeriod");
            return (Criteria) this;
        }

        public Criteria andMainWatchIsNull() {
            addCriterion("main_watch is null");
            return (Criteria) this;
        }

        public Criteria andMainWatchIsNotNull() {
            addCriterion("main_watch is not null");
            return (Criteria) this;
        }

        public Criteria andMainWatchEqualTo(String value) {
            addCriterion("main_watch =", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchNotEqualTo(String value) {
            addCriterion("main_watch <>", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchGreaterThan(String value) {
            addCriterion("main_watch >", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchGreaterThanOrEqualTo(String value) {
            addCriterion("main_watch >=", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchLessThan(String value) {
            addCriterion("main_watch <", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchLessThanOrEqualTo(String value) {
            addCriterion("main_watch <=", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchLike(String value) {
            addCriterion("main_watch like", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchNotLike(String value) {
            addCriterion("main_watch not like", value, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchIn(List<String> values) {
            addCriterion("main_watch in", values, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchNotIn(List<String> values) {
            addCriterion("main_watch not in", values, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchBetween(String value1, String value2) {
            addCriterion("main_watch between", value1, value2, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andMainWatchNotBetween(String value1, String value2) {
            addCriterion("main_watch not between", value1, value2, "mainWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchIsNull() {
            addCriterion("secondary_watch is null");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchIsNotNull() {
            addCriterion("secondary_watch is not null");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchEqualTo(String value) {
            addCriterion("secondary_watch =", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchNotEqualTo(String value) {
            addCriterion("secondary_watch <>", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchGreaterThan(String value) {
            addCriterion("secondary_watch >", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchGreaterThanOrEqualTo(String value) {
            addCriterion("secondary_watch >=", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchLessThan(String value) {
            addCriterion("secondary_watch <", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchLessThanOrEqualTo(String value) {
            addCriterion("secondary_watch <=", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchLike(String value) {
            addCriterion("secondary_watch like", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchNotLike(String value) {
            addCriterion("secondary_watch not like", value, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchIn(List<String> values) {
            addCriterion("secondary_watch in", values, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchNotIn(List<String> values) {
            addCriterion("secondary_watch not in", values, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchBetween(String value1, String value2) {
            addCriterion("secondary_watch between", value1, value2, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andSecondaryWatchNotBetween(String value1, String value2) {
            addCriterion("secondary_watch not between", value1, value2, "secondaryWatch");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeIsNull() {
            addCriterion("main_guarantee is null");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeIsNotNull() {
            addCriterion("main_guarantee is not null");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeEqualTo(String value) {
            addCriterion("main_guarantee =", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeNotEqualTo(String value) {
            addCriterion("main_guarantee <>", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeGreaterThan(String value) {
            addCriterion("main_guarantee >", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeGreaterThanOrEqualTo(String value) {
            addCriterion("main_guarantee >=", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeLessThan(String value) {
            addCriterion("main_guarantee <", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeLessThanOrEqualTo(String value) {
            addCriterion("main_guarantee <=", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeLike(String value) {
            addCriterion("main_guarantee like", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeNotLike(String value) {
            addCriterion("main_guarantee not like", value, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeIn(List<String> values) {
            addCriterion("main_guarantee in", values, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeNotIn(List<String> values) {
            addCriterion("main_guarantee not in", values, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeBetween(String value1, String value2) {
            addCriterion("main_guarantee between", value1, value2, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andMainGuaranteeNotBetween(String value1, String value2) {
            addCriterion("main_guarantee not between", value1, value2, "mainGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeIsNull() {
            addCriterion("secondary_guarantee is null");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeIsNotNull() {
            addCriterion("secondary_guarantee is not null");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeEqualTo(String value) {
            addCriterion("secondary_guarantee =", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeNotEqualTo(String value) {
            addCriterion("secondary_guarantee <>", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeGreaterThan(String value) {
            addCriterion("secondary_guarantee >", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeGreaterThanOrEqualTo(String value) {
            addCriterion("secondary_guarantee >=", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeLessThan(String value) {
            addCriterion("secondary_guarantee <", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeLessThanOrEqualTo(String value) {
            addCriterion("secondary_guarantee <=", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeLike(String value) {
            addCriterion("secondary_guarantee like", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeNotLike(String value) {
            addCriterion("secondary_guarantee not like", value, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeIn(List<String> values) {
            addCriterion("secondary_guarantee in", values, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeNotIn(List<String> values) {
            addCriterion("secondary_guarantee not in", values, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeBetween(String value1, String value2) {
            addCriterion("secondary_guarantee between", value1, value2, "secondaryGuarantee");
            return (Criteria) this;
        }

        public Criteria andSecondaryGuaranteeNotBetween(String value1, String value2) {
            addCriterion("secondary_guarantee not between", value1, value2, "secondaryGuarantee");
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

        public Criteria andFitstEditionDateIsNull() {
            addCriterion("fitst_edition_date is null");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateIsNotNull() {
            addCriterion("fitst_edition_date is not null");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateEqualTo(Date value) {
            addCriterionForJDBCDate("fitst_edition_date =", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("fitst_edition_date <>", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateGreaterThan(Date value) {
            addCriterionForJDBCDate("fitst_edition_date >", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fitst_edition_date >=", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateLessThan(Date value) {
            addCriterionForJDBCDate("fitst_edition_date <", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fitst_edition_date <=", value, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateIn(List<Date> values) {
            addCriterionForJDBCDate("fitst_edition_date in", values, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("fitst_edition_date not in", values, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fitst_edition_date between", value1, value2, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andFitstEditionDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fitst_edition_date not between", value1, value2, "fitstEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateIsNull() {
            addCriterion("sealed_edition_date is null");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateIsNotNull() {
            addCriterion("sealed_edition_date is not null");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateEqualTo(Date value) {
            addCriterionForJDBCDate("sealed_edition_date =", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("sealed_edition_date <>", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateGreaterThan(Date value) {
            addCriterionForJDBCDate("sealed_edition_date >", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sealed_edition_date >=", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateLessThan(Date value) {
            addCriterionForJDBCDate("sealed_edition_date <", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sealed_edition_date <=", value, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateIn(List<Date> values) {
            addCriterionForJDBCDate("sealed_edition_date in", values, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("sealed_edition_date not in", values, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sealed_edition_date between", value1, value2, "sealedEditionDate");
            return (Criteria) this;
        }

        public Criteria andSealedEditionDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sealed_edition_date not between", value1, value2, "sealedEditionDate");
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