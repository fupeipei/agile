package com.yusys.agile.zentao.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ZtProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZtProjectExample() {
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

        public Criteria andIscatIsNull() {
            addCriterion("isCat is null");
            return (Criteria) this;
        }

        public Criteria andIscatIsNotNull() {
            addCriterion("isCat is not null");
            return (Criteria) this;
        }

        public Criteria andIscatEqualTo(String value) {
            addCriterion("isCat =", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatNotEqualTo(String value) {
            addCriterion("isCat <>", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatGreaterThan(String value) {
            addCriterion("isCat >", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatGreaterThanOrEqualTo(String value) {
            addCriterion("isCat >=", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatLessThan(String value) {
            addCriterion("isCat <", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatLessThanOrEqualTo(String value) {
            addCriterion("isCat <=", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatLike(String value) {
            addCriterion("isCat like", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatNotLike(String value) {
            addCriterion("isCat not like", value, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatIn(List<String> values) {
            addCriterion("isCat in", values, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatNotIn(List<String> values) {
            addCriterion("isCat not in", values, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatBetween(String value1, String value2) {
            addCriterion("isCat between", value1, value2, "iscat");
            return (Criteria) this;
        }

        public Criteria andIscatNotBetween(String value1, String value2) {
            addCriterion("isCat not between", value1, value2, "iscat");
            return (Criteria) this;
        }

        public Criteria andCatidIsNull() {
            addCriterion("catID is null");
            return (Criteria) this;
        }

        public Criteria andCatidIsNotNull() {
            addCriterion("catID is not null");
            return (Criteria) this;
        }

        public Criteria andCatidEqualTo(Integer value) {
            addCriterion("catID =", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidNotEqualTo(Integer value) {
            addCriterion("catID <>", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidGreaterThan(Integer value) {
            addCriterion("catID >", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidGreaterThanOrEqualTo(Integer value) {
            addCriterion("catID >=", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidLessThan(Integer value) {
            addCriterion("catID <", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidLessThanOrEqualTo(Integer value) {
            addCriterion("catID <=", value, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidIn(List<Integer> values) {
            addCriterion("catID in", values, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidNotIn(List<Integer> values) {
            addCriterion("catID not in", values, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidBetween(Integer value1, Integer value2) {
            addCriterion("catID between", value1, value2, "catid");
            return (Criteria) this;
        }

        public Criteria andCatidNotBetween(Integer value1, Integer value2) {
            addCriterion("catID not between", value1, value2, "catid");
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

        public Criteria andParentIsNull() {
            addCriterion("parent is null");
            return (Criteria) this;
        }

        public Criteria andParentIsNotNull() {
            addCriterion("parent is not null");
            return (Criteria) this;
        }

        public Criteria andParentEqualTo(Integer value) {
            addCriterion("parent =", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotEqualTo(Integer value) {
            addCriterion("parent <>", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThan(Integer value) {
            addCriterion("parent >", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent >=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThan(Integer value) {
            addCriterion("parent <", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualTo(Integer value) {
            addCriterion("parent <=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentIn(List<Integer> values) {
            addCriterion("parent in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotIn(List<Integer> values) {
            addCriterion("parent not in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentBetween(Integer value1, Integer value2) {
            addCriterion("parent between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotBetween(Integer value1, Integer value2) {
            addCriterion("parent not between", value1, value2, "parent");
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

        public Criteria andBeginIsNull() {
            addCriterion("begin is null");
            return (Criteria) this;
        }

        public Criteria andBeginIsNotNull() {
            addCriterion("begin is not null");
            return (Criteria) this;
        }

        public Criteria andBeginEqualTo(Date value) {
            addCriterionForJDBCDate("begin =", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotEqualTo(Date value) {
            addCriterionForJDBCDate("begin <>", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginGreaterThan(Date value) {
            addCriterionForJDBCDate("begin >", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin >=", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginLessThan(Date value) {
            addCriterionForJDBCDate("begin <", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin <=", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginIn(List<Date> values) {
            addCriterionForJDBCDate("begin in", values, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotIn(List<Date> values) {
            addCriterionForJDBCDate("begin not in", values, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin between", value1, value2, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin not between", value1, value2, "begin");
            return (Criteria) this;
        }

        public Criteria andEndIsNull() {
            addCriterion("end is null");
            return (Criteria) this;
        }

        public Criteria andEndIsNotNull() {
            addCriterion("end is not null");
            return (Criteria) this;
        }

        public Criteria andEndEqualTo(Date value) {
            addCriterionForJDBCDate("end =", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotEqualTo(Date value) {
            addCriterionForJDBCDate("end <>", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThan(Date value) {
            addCriterionForJDBCDate("end >", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end >=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThan(Date value) {
            addCriterionForJDBCDate("end <", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end <=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndIn(List<Date> values) {
            addCriterionForJDBCDate("end in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotIn(List<Date> values) {
            addCriterionForJDBCDate("end not in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end not between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andDaysIsNull() {
            addCriterion("days is null");
            return (Criteria) this;
        }

        public Criteria andDaysIsNotNull() {
            addCriterion("days is not null");
            return (Criteria) this;
        }

        public Criteria andDaysEqualTo(Short value) {
            addCriterion("days =", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotEqualTo(Short value) {
            addCriterion("days <>", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThan(Short value) {
            addCriterion("days >", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThanOrEqualTo(Short value) {
            addCriterion("days >=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThan(Short value) {
            addCriterion("days <", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThanOrEqualTo(Short value) {
            addCriterion("days <=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysIn(List<Short> values) {
            addCriterion("days in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotIn(List<Short> values) {
            addCriterion("days not in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysBetween(Short value1, Short value2) {
            addCriterion("days between", value1, value2, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotBetween(Short value1, Short value2) {
            addCriterion("days not between", value1, value2, "days");
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

        public Criteria andStatgeIsNull() {
            addCriterion("statge is null");
            return (Criteria) this;
        }

        public Criteria andStatgeIsNotNull() {
            addCriterion("statge is not null");
            return (Criteria) this;
        }

        public Criteria andStatgeEqualTo(String value) {
            addCriterion("statge =", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeNotEqualTo(String value) {
            addCriterion("statge <>", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeGreaterThan(String value) {
            addCriterion("statge >", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeGreaterThanOrEqualTo(String value) {
            addCriterion("statge >=", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeLessThan(String value) {
            addCriterion("statge <", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeLessThanOrEqualTo(String value) {
            addCriterion("statge <=", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeLike(String value) {
            addCriterion("statge like", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeNotLike(String value) {
            addCriterion("statge not like", value, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeIn(List<String> values) {
            addCriterion("statge in", values, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeNotIn(List<String> values) {
            addCriterion("statge not in", values, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeBetween(String value1, String value2) {
            addCriterion("statge between", value1, value2, "statge");
            return (Criteria) this;
        }

        public Criteria andStatgeNotBetween(String value1, String value2) {
            addCriterion("statge not between", value1, value2, "statge");
            return (Criteria) this;
        }

        public Criteria andPriIsNull() {
            addCriterion("pri is null");
            return (Criteria) this;
        }

        public Criteria andPriIsNotNull() {
            addCriterion("pri is not null");
            return (Criteria) this;
        }

        public Criteria andPriEqualTo(String value) {
            addCriterion("pri =", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotEqualTo(String value) {
            addCriterion("pri <>", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriGreaterThan(String value) {
            addCriterion("pri >", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriGreaterThanOrEqualTo(String value) {
            addCriterion("pri >=", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriLessThan(String value) {
            addCriterion("pri <", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriLessThanOrEqualTo(String value) {
            addCriterion("pri <=", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriLike(String value) {
            addCriterion("pri like", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotLike(String value) {
            addCriterion("pri not like", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriIn(List<String> values) {
            addCriterion("pri in", values, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotIn(List<String> values) {
            addCriterion("pri not in", values, "pri");
            return (Criteria) this;
        }

        public Criteria andPriBetween(String value1, String value2) {
            addCriterion("pri between", value1, value2, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotBetween(String value1, String value2) {
            addCriterion("pri not between", value1, value2, "pri");
            return (Criteria) this;
        }

        public Criteria andOpenedbyIsNull() {
            addCriterion("openedBy is null");
            return (Criteria) this;
        }

        public Criteria andOpenedbyIsNotNull() {
            addCriterion("openedBy is not null");
            return (Criteria) this;
        }

        public Criteria andOpenedbyEqualTo(String value) {
            addCriterion("openedBy =", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyNotEqualTo(String value) {
            addCriterion("openedBy <>", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyGreaterThan(String value) {
            addCriterion("openedBy >", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyGreaterThanOrEqualTo(String value) {
            addCriterion("openedBy >=", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyLessThan(String value) {
            addCriterion("openedBy <", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyLessThanOrEqualTo(String value) {
            addCriterion("openedBy <=", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyLike(String value) {
            addCriterion("openedBy like", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyNotLike(String value) {
            addCriterion("openedBy not like", value, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyIn(List<String> values) {
            addCriterion("openedBy in", values, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyNotIn(List<String> values) {
            addCriterion("openedBy not in", values, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyBetween(String value1, String value2) {
            addCriterion("openedBy between", value1, value2, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpenedbyNotBetween(String value1, String value2) {
            addCriterion("openedBy not between", value1, value2, "openedby");
            return (Criteria) this;
        }

        public Criteria andOpeneddateIsNull() {
            addCriterion("openedDate is null");
            return (Criteria) this;
        }

        public Criteria andOpeneddateIsNotNull() {
            addCriterion("openedDate is not null");
            return (Criteria) this;
        }

        public Criteria andOpeneddateEqualTo(Date value) {
            addCriterion("openedDate =", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateNotEqualTo(Date value) {
            addCriterion("openedDate <>", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateGreaterThan(Date value) {
            addCriterion("openedDate >", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateGreaterThanOrEqualTo(Date value) {
            addCriterion("openedDate >=", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateLessThan(Date value) {
            addCriterion("openedDate <", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateLessThanOrEqualTo(Date value) {
            addCriterion("openedDate <=", value, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateIn(List<Date> values) {
            addCriterion("openedDate in", values, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateNotIn(List<Date> values) {
            addCriterion("openedDate not in", values, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateBetween(Date value1, Date value2) {
            addCriterion("openedDate between", value1, value2, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpeneddateNotBetween(Date value1, Date value2) {
            addCriterion("openedDate not between", value1, value2, "openeddate");
            return (Criteria) this;
        }

        public Criteria andOpenedversionIsNull() {
            addCriterion("openedVersion is null");
            return (Criteria) this;
        }

        public Criteria andOpenedversionIsNotNull() {
            addCriterion("openedVersion is not null");
            return (Criteria) this;
        }

        public Criteria andOpenedversionEqualTo(String value) {
            addCriterion("openedVersion =", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionNotEqualTo(String value) {
            addCriterion("openedVersion <>", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionGreaterThan(String value) {
            addCriterion("openedVersion >", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionGreaterThanOrEqualTo(String value) {
            addCriterion("openedVersion >=", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionLessThan(String value) {
            addCriterion("openedVersion <", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionLessThanOrEqualTo(String value) {
            addCriterion("openedVersion <=", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionLike(String value) {
            addCriterion("openedVersion like", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionNotLike(String value) {
            addCriterion("openedVersion not like", value, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionIn(List<String> values) {
            addCriterion("openedVersion in", values, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionNotIn(List<String> values) {
            addCriterion("openedVersion not in", values, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionBetween(String value1, String value2) {
            addCriterion("openedVersion between", value1, value2, "openedversion");
            return (Criteria) this;
        }

        public Criteria andOpenedversionNotBetween(String value1, String value2) {
            addCriterion("openedVersion not between", value1, value2, "openedversion");
            return (Criteria) this;
        }

        public Criteria andClosedbyIsNull() {
            addCriterion("closedBy is null");
            return (Criteria) this;
        }

        public Criteria andClosedbyIsNotNull() {
            addCriterion("closedBy is not null");
            return (Criteria) this;
        }

        public Criteria andClosedbyEqualTo(String value) {
            addCriterion("closedBy =", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyNotEqualTo(String value) {
            addCriterion("closedBy <>", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyGreaterThan(String value) {
            addCriterion("closedBy >", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyGreaterThanOrEqualTo(String value) {
            addCriterion("closedBy >=", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyLessThan(String value) {
            addCriterion("closedBy <", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyLessThanOrEqualTo(String value) {
            addCriterion("closedBy <=", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyLike(String value) {
            addCriterion("closedBy like", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyNotLike(String value) {
            addCriterion("closedBy not like", value, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyIn(List<String> values) {
            addCriterion("closedBy in", values, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyNotIn(List<String> values) {
            addCriterion("closedBy not in", values, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyBetween(String value1, String value2) {
            addCriterion("closedBy between", value1, value2, "closedby");
            return (Criteria) this;
        }

        public Criteria andClosedbyNotBetween(String value1, String value2) {
            addCriterion("closedBy not between", value1, value2, "closedby");
            return (Criteria) this;
        }

        public Criteria andCloseddateIsNull() {
            addCriterion("closedDate is null");
            return (Criteria) this;
        }

        public Criteria andCloseddateIsNotNull() {
            addCriterion("closedDate is not null");
            return (Criteria) this;
        }

        public Criteria andCloseddateEqualTo(Date value) {
            addCriterion("closedDate =", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateNotEqualTo(Date value) {
            addCriterion("closedDate <>", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateGreaterThan(Date value) {
            addCriterion("closedDate >", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateGreaterThanOrEqualTo(Date value) {
            addCriterion("closedDate >=", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateLessThan(Date value) {
            addCriterion("closedDate <", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateLessThanOrEqualTo(Date value) {
            addCriterion("closedDate <=", value, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateIn(List<Date> values) {
            addCriterion("closedDate in", values, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateNotIn(List<Date> values) {
            addCriterion("closedDate not in", values, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateBetween(Date value1, Date value2) {
            addCriterion("closedDate between", value1, value2, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCloseddateNotBetween(Date value1, Date value2) {
            addCriterion("closedDate not between", value1, value2, "closeddate");
            return (Criteria) this;
        }

        public Criteria andCanceledbyIsNull() {
            addCriterion("canceledBy is null");
            return (Criteria) this;
        }

        public Criteria andCanceledbyIsNotNull() {
            addCriterion("canceledBy is not null");
            return (Criteria) this;
        }

        public Criteria andCanceledbyEqualTo(String value) {
            addCriterion("canceledBy =", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyNotEqualTo(String value) {
            addCriterion("canceledBy <>", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyGreaterThan(String value) {
            addCriterion("canceledBy >", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyGreaterThanOrEqualTo(String value) {
            addCriterion("canceledBy >=", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyLessThan(String value) {
            addCriterion("canceledBy <", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyLessThanOrEqualTo(String value) {
            addCriterion("canceledBy <=", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyLike(String value) {
            addCriterion("canceledBy like", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyNotLike(String value) {
            addCriterion("canceledBy not like", value, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyIn(List<String> values) {
            addCriterion("canceledBy in", values, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyNotIn(List<String> values) {
            addCriterion("canceledBy not in", values, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyBetween(String value1, String value2) {
            addCriterion("canceledBy between", value1, value2, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceledbyNotBetween(String value1, String value2) {
            addCriterion("canceledBy not between", value1, value2, "canceledby");
            return (Criteria) this;
        }

        public Criteria andCanceleddateIsNull() {
            addCriterion("canceledDate is null");
            return (Criteria) this;
        }

        public Criteria andCanceleddateIsNotNull() {
            addCriterion("canceledDate is not null");
            return (Criteria) this;
        }

        public Criteria andCanceleddateEqualTo(Date value) {
            addCriterion("canceledDate =", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateNotEqualTo(Date value) {
            addCriterion("canceledDate <>", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateGreaterThan(Date value) {
            addCriterion("canceledDate >", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateGreaterThanOrEqualTo(Date value) {
            addCriterion("canceledDate >=", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateLessThan(Date value) {
            addCriterion("canceledDate <", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateLessThanOrEqualTo(Date value) {
            addCriterion("canceledDate <=", value, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateIn(List<Date> values) {
            addCriterion("canceledDate in", values, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateNotIn(List<Date> values) {
            addCriterion("canceledDate not in", values, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateBetween(Date value1, Date value2) {
            addCriterion("canceledDate between", value1, value2, "canceleddate");
            return (Criteria) this;
        }

        public Criteria andCanceleddateNotBetween(Date value1, Date value2) {
            addCriterion("canceledDate not between", value1, value2, "canceleddate");
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

        public Criteria andPmIsNull() {
            addCriterion("PM is null");
            return (Criteria) this;
        }

        public Criteria andPmIsNotNull() {
            addCriterion("PM is not null");
            return (Criteria) this;
        }

        public Criteria andPmEqualTo(String value) {
            addCriterion("PM =", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmNotEqualTo(String value) {
            addCriterion("PM <>", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmGreaterThan(String value) {
            addCriterion("PM >", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmGreaterThanOrEqualTo(String value) {
            addCriterion("PM >=", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmLessThan(String value) {
            addCriterion("PM <", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmLessThanOrEqualTo(String value) {
            addCriterion("PM <=", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmLike(String value) {
            addCriterion("PM like", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmNotLike(String value) {
            addCriterion("PM not like", value, "pm");
            return (Criteria) this;
        }

        public Criteria andPmIn(List<String> values) {
            addCriterion("PM in", values, "pm");
            return (Criteria) this;
        }

        public Criteria andPmNotIn(List<String> values) {
            addCriterion("PM not in", values, "pm");
            return (Criteria) this;
        }

        public Criteria andPmBetween(String value1, String value2) {
            addCriterion("PM between", value1, value2, "pm");
            return (Criteria) this;
        }

        public Criteria andPmNotBetween(String value1, String value2) {
            addCriterion("PM not between", value1, value2, "pm");
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

        public Criteria andTeamIsNull() {
            addCriterion("team is null");
            return (Criteria) this;
        }

        public Criteria andTeamIsNotNull() {
            addCriterion("team is not null");
            return (Criteria) this;
        }

        public Criteria andTeamEqualTo(String value) {
            addCriterion("team =", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotEqualTo(String value) {
            addCriterion("team <>", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamGreaterThan(String value) {
            addCriterion("team >", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamGreaterThanOrEqualTo(String value) {
            addCriterion("team >=", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLessThan(String value) {
            addCriterion("team <", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLessThanOrEqualTo(String value) {
            addCriterion("team <=", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLike(String value) {
            addCriterion("team like", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotLike(String value) {
            addCriterion("team not like", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamIn(List<String> values) {
            addCriterion("team in", values, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotIn(List<String> values) {
            addCriterion("team not in", values, "team");
            return (Criteria) this;
        }

        public Criteria andTeamBetween(String value1, String value2) {
            addCriterion("team between", value1, value2, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotBetween(String value1, String value2) {
            addCriterion("team not between", value1, value2, "team");
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