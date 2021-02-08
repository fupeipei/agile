package com.yusys.agile.sprint.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSprintHourExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSprintHourExample() {
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

        public Criteria andHourIdIsNull() {
            addCriterion("hour_id is null");
            return (Criteria) this;
        }

        public Criteria andHourIdIsNotNull() {
            addCriterion("hour_id is not null");
            return (Criteria) this;
        }

        public Criteria andHourIdEqualTo(Long value) {
            addCriterion("hour_id =", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdNotEqualTo(Long value) {
            addCriterion("hour_id <>", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdGreaterThan(Long value) {
            addCriterion("hour_id >", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdGreaterThanOrEqualTo(Long value) {
            addCriterion("hour_id >=", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdLessThan(Long value) {
            addCriterion("hour_id <", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdLessThanOrEqualTo(Long value) {
            addCriterion("hour_id <=", value, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdIn(List<Long> values) {
            addCriterion("hour_id in", values, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdNotIn(List<Long> values) {
            addCriterion("hour_id not in", values, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdBetween(Long value1, Long value2) {
            addCriterion("hour_id between", value1, value2, "hourId");
            return (Criteria) this;
        }

        public Criteria andHourIdNotBetween(Long value1, Long value2) {
            addCriterion("hour_id not between", value1, value2, "hourId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andReallyHoursIsNull() {
            addCriterion("really_hours is null");
            return (Criteria) this;
        }

        public Criteria andReallyHoursIsNotNull() {
            addCriterion("really_hours is not null");
            return (Criteria) this;
        }

        public Criteria andReallyHoursEqualTo(Integer value) {
            addCriterion("really_hours =", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursNotEqualTo(Integer value) {
            addCriterion("really_hours <>", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursGreaterThan(Integer value) {
            addCriterion("really_hours >", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursGreaterThanOrEqualTo(Integer value) {
            addCriterion("really_hours >=", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursLessThan(Integer value) {
            addCriterion("really_hours <", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursLessThanOrEqualTo(Integer value) {
            addCriterion("really_hours <=", value, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursIn(List<Integer> values) {
            addCriterion("really_hours in", values, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursNotIn(List<Integer> values) {
            addCriterion("really_hours not in", values, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursBetween(Integer value1, Integer value2) {
            addCriterion("really_hours between", value1, value2, "reallyHours");
            return (Criteria) this;
        }

        public Criteria andReallyHoursNotBetween(Integer value1, Integer value2) {
            addCriterion("really_hours not between", value1, value2, "reallyHours");
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