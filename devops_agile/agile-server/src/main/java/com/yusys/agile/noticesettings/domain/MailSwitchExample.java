package com.yusys.agile.noticesettings.domain;

import java.util.ArrayList;
import java.util.List;

public class MailSwitchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MailSwitchExample() {
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

        public Criteria andMailIdIsNull() {
            addCriterion("mail_id is null");
            return (Criteria) this;
        }

        public Criteria andMailIdIsNotNull() {
            addCriterion("mail_id is not null");
            return (Criteria) this;
        }

        public Criteria andMailIdEqualTo(Long value) {
            addCriterion("mail_id =", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotEqualTo(Long value) {
            addCriterion("mail_id <>", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdGreaterThan(Long value) {
            addCriterion("mail_id >", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("mail_id >=", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdLessThan(Long value) {
            addCriterion("mail_id <", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdLessThanOrEqualTo(Long value) {
            addCriterion("mail_id <=", value, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdIn(List<Long> values) {
            addCriterion("mail_id in", values, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotIn(List<Long> values) {
            addCriterion("mail_id not in", values, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdBetween(Long value1, Long value2) {
            addCriterion("mail_id between", value1, value2, "mailId");
            return (Criteria) this;
        }

        public Criteria andMailIdNotBetween(Long value1, Long value2) {
            addCriterion("mail_id not between", value1, value2, "mailId");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andMailTypeIsNull() {
            addCriterion("mail_type is null");
            return (Criteria) this;
        }

        public Criteria andMailTypeIsNotNull() {
            addCriterion("mail_type is not null");
            return (Criteria) this;
        }

        public Criteria andMailTypeEqualTo(Byte value) {
            addCriterion("mail_type =", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeNotEqualTo(Byte value) {
            addCriterion("mail_type <>", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeGreaterThan(Byte value) {
            addCriterion("mail_type >", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("mail_type >=", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeLessThan(Byte value) {
            addCriterion("mail_type <", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeLessThanOrEqualTo(Byte value) {
            addCriterion("mail_type <=", value, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeIn(List<Byte> values) {
            addCriterion("mail_type in", values, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeNotIn(List<Byte> values) {
            addCriterion("mail_type not in", values, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeBetween(Byte value1, Byte value2) {
            addCriterion("mail_type between", value1, value2, "mailType");
            return (Criteria) this;
        }

        public Criteria andMailTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("mail_type not between", value1, value2, "mailType");
            return (Criteria) this;
        }

        public Criteria andMainSwitchIsNull() {
            addCriterion("main_switch is null");
            return (Criteria) this;
        }

        public Criteria andMainSwitchIsNotNull() {
            addCriterion("main_switch is not null");
            return (Criteria) this;
        }

        public Criteria andMainSwitchEqualTo(Byte value) {
            addCriterion("main_switch =", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNotEqualTo(Byte value) {
            addCriterion("main_switch <>", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchGreaterThan(Byte value) {
            addCriterion("main_switch >", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchGreaterThanOrEqualTo(Byte value) {
            addCriterion("main_switch >=", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchLessThan(Byte value) {
            addCriterion("main_switch <", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchLessThanOrEqualTo(Byte value) {
            addCriterion("main_switch <=", value, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchIn(List<Byte> values) {
            addCriterion("main_switch in", values, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNotIn(List<Byte> values) {
            addCriterion("main_switch not in", values, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchBetween(Byte value1, Byte value2) {
            addCriterion("main_switch between", value1, value2, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNotBetween(Byte value1, Byte value2) {
            addCriterion("main_switch not between", value1, value2, "mainSwitch");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameIsNull() {
            addCriterion("main_switch_name is null");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameIsNotNull() {
            addCriterion("main_switch_name is not null");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameEqualTo(String value) {
            addCriterion("main_switch_name =", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameNotEqualTo(String value) {
            addCriterion("main_switch_name <>", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameGreaterThan(String value) {
            addCriterion("main_switch_name >", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameGreaterThanOrEqualTo(String value) {
            addCriterion("main_switch_name >=", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameLessThan(String value) {
            addCriterion("main_switch_name <", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameLessThanOrEqualTo(String value) {
            addCriterion("main_switch_name <=", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameLike(String value) {
            addCriterion("main_switch_name like", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameNotLike(String value) {
            addCriterion("main_switch_name not like", value, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameIn(List<String> values) {
            addCriterion("main_switch_name in", values, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameNotIn(List<String> values) {
            addCriterion("main_switch_name not in", values, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameBetween(String value1, String value2) {
            addCriterion("main_switch_name between", value1, value2, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andMainSwitchNameNotBetween(String value1, String value2) {
            addCriterion("main_switch_name not between", value1, value2, "mainSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchIsNull() {
            addCriterion("sub_switch is null");
            return (Criteria) this;
        }

        public Criteria andSubSwitchIsNotNull() {
            addCriterion("sub_switch is not null");
            return (Criteria) this;
        }

        public Criteria andSubSwitchEqualTo(Byte value) {
            addCriterion("sub_switch =", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNotEqualTo(Byte value) {
            addCriterion("sub_switch <>", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchGreaterThan(Byte value) {
            addCriterion("sub_switch >", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchGreaterThanOrEqualTo(Byte value) {
            addCriterion("sub_switch >=", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchLessThan(Byte value) {
            addCriterion("sub_switch <", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchLessThanOrEqualTo(Byte value) {
            addCriterion("sub_switch <=", value, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchIn(List<Byte> values) {
            addCriterion("sub_switch in", values, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNotIn(List<Byte> values) {
            addCriterion("sub_switch not in", values, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchBetween(Byte value1, Byte value2) {
            addCriterion("sub_switch between", value1, value2, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNotBetween(Byte value1, Byte value2) {
            addCriterion("sub_switch not between", value1, value2, "subSwitch");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameIsNull() {
            addCriterion("sub_switch_name is null");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameIsNotNull() {
            addCriterion("sub_switch_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameEqualTo(String value) {
            addCriterion("sub_switch_name =", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameNotEqualTo(String value) {
            addCriterion("sub_switch_name <>", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameGreaterThan(String value) {
            addCriterion("sub_switch_name >", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameGreaterThanOrEqualTo(String value) {
            addCriterion("sub_switch_name >=", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameLessThan(String value) {
            addCriterion("sub_switch_name <", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameLessThanOrEqualTo(String value) {
            addCriterion("sub_switch_name <=", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameLike(String value) {
            addCriterion("sub_switch_name like", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameNotLike(String value) {
            addCriterion("sub_switch_name not like", value, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameIn(List<String> values) {
            addCriterion("sub_switch_name in", values, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameNotIn(List<String> values) {
            addCriterion("sub_switch_name not in", values, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameBetween(String value1, String value2) {
            addCriterion("sub_switch_name between", value1, value2, "subSwitchName");
            return (Criteria) this;
        }

        public Criteria andSubSwitchNameNotBetween(String value1, String value2) {
            addCriterion("sub_switch_name not between", value1, value2, "subSwitchName");
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