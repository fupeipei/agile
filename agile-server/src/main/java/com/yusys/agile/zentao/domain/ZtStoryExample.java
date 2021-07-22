package com.yusys.agile.zentao.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ZtStoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZtStoryExample() {
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

        public Criteria andProductIsNull() {
            addCriterion("product is null");
            return (Criteria) this;
        }

        public Criteria andProductIsNotNull() {
            addCriterion("product is not null");
            return (Criteria) this;
        }

        public Criteria andProductEqualTo(Integer value) {
            addCriterion("product =", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductNotEqualTo(Integer value) {
            addCriterion("product <>", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductGreaterThan(Integer value) {
            addCriterion("product >", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductGreaterThanOrEqualTo(Integer value) {
            addCriterion("product >=", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductLessThan(Integer value) {
            addCriterion("product <", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductLessThanOrEqualTo(Integer value) {
            addCriterion("product <=", value, "product");
            return (Criteria) this;
        }

        public Criteria andProductIn(List<Integer> values) {
            addCriterion("product in", values, "product");
            return (Criteria) this;
        }

        public Criteria andProductNotIn(List<Integer> values) {
            addCriterion("product not in", values, "product");
            return (Criteria) this;
        }

        public Criteria andProductBetween(Integer value1, Integer value2) {
            addCriterion("product between", value1, value2, "product");
            return (Criteria) this;
        }

        public Criteria andProductNotBetween(Integer value1, Integer value2) {
            addCriterion("product not between", value1, value2, "product");
            return (Criteria) this;
        }

        public Criteria andBranchIsNull() {
            addCriterion("branch is null");
            return (Criteria) this;
        }

        public Criteria andBranchIsNotNull() {
            addCriterion("branch is not null");
            return (Criteria) this;
        }

        public Criteria andBranchEqualTo(Integer value) {
            addCriterion("branch =", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotEqualTo(Integer value) {
            addCriterion("branch <>", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThan(Integer value) {
            addCriterion("branch >", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch >=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThan(Integer value) {
            addCriterion("branch <", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThanOrEqualTo(Integer value) {
            addCriterion("branch <=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchIn(List<Integer> values) {
            addCriterion("branch in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotIn(List<Integer> values) {
            addCriterion("branch not in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchBetween(Integer value1, Integer value2) {
            addCriterion("branch between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotBetween(Integer value1, Integer value2) {
            addCriterion("branch not between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andModuleIsNull() {
            addCriterion("module is null");
            return (Criteria) this;
        }

        public Criteria andModuleIsNotNull() {
            addCriterion("module is not null");
            return (Criteria) this;
        }

        public Criteria andModuleEqualTo(Integer value) {
            addCriterion("module =", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotEqualTo(Integer value) {
            addCriterion("module <>", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleGreaterThan(Integer value) {
            addCriterion("module >", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleGreaterThanOrEqualTo(Integer value) {
            addCriterion("module >=", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleLessThan(Integer value) {
            addCriterion("module <", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleLessThanOrEqualTo(Integer value) {
            addCriterion("module <=", value, "module");
            return (Criteria) this;
        }

        public Criteria andModuleIn(List<Integer> values) {
            addCriterion("module in", values, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotIn(List<Integer> values) {
            addCriterion("module not in", values, "module");
            return (Criteria) this;
        }

        public Criteria andModuleBetween(Integer value1, Integer value2) {
            addCriterion("module between", value1, value2, "module");
            return (Criteria) this;
        }

        public Criteria andModuleNotBetween(Integer value1, Integer value2) {
            addCriterion("module not between", value1, value2, "module");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourcenoteIsNull() {
            addCriterion("sourceNote is null");
            return (Criteria) this;
        }

        public Criteria andSourcenoteIsNotNull() {
            addCriterion("sourceNote is not null");
            return (Criteria) this;
        }

        public Criteria andSourcenoteEqualTo(String value) {
            addCriterion("sourceNote =", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteNotEqualTo(String value) {
            addCriterion("sourceNote <>", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteGreaterThan(String value) {
            addCriterion("sourceNote >", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteGreaterThanOrEqualTo(String value) {
            addCriterion("sourceNote >=", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteLessThan(String value) {
            addCriterion("sourceNote <", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteLessThanOrEqualTo(String value) {
            addCriterion("sourceNote <=", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteLike(String value) {
            addCriterion("sourceNote like", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteNotLike(String value) {
            addCriterion("sourceNote not like", value, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteIn(List<String> values) {
            addCriterion("sourceNote in", values, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteNotIn(List<String> values) {
            addCriterion("sourceNote not in", values, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteBetween(String value1, String value2) {
            addCriterion("sourceNote between", value1, value2, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andSourcenoteNotBetween(String value1, String value2) {
            addCriterion("sourceNote not between", value1, value2, "sourcenote");
            return (Criteria) this;
        }

        public Criteria andFrombugIsNull() {
            addCriterion("fromBug is null");
            return (Criteria) this;
        }

        public Criteria andFrombugIsNotNull() {
            addCriterion("fromBug is not null");
            return (Criteria) this;
        }

        public Criteria andFrombugEqualTo(Integer value) {
            addCriterion("fromBug =", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugNotEqualTo(Integer value) {
            addCriterion("fromBug <>", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugGreaterThan(Integer value) {
            addCriterion("fromBug >", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugGreaterThanOrEqualTo(Integer value) {
            addCriterion("fromBug >=", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugLessThan(Integer value) {
            addCriterion("fromBug <", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugLessThanOrEqualTo(Integer value) {
            addCriterion("fromBug <=", value, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugIn(List<Integer> values) {
            addCriterion("fromBug in", values, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugNotIn(List<Integer> values) {
            addCriterion("fromBug not in", values, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugBetween(Integer value1, Integer value2) {
            addCriterion("fromBug between", value1, value2, "frombug");
            return (Criteria) this;
        }

        public Criteria andFrombugNotBetween(Integer value1, Integer value2) {
            addCriterion("fromBug not between", value1, value2, "frombug");
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

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
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

        public Criteria andPriIsNull() {
            addCriterion("pri is null");
            return (Criteria) this;
        }

        public Criteria andPriIsNotNull() {
            addCriterion("pri is not null");
            return (Criteria) this;
        }

        public Criteria andPriEqualTo(Byte value) {
            addCriterion("pri =", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotEqualTo(Byte value) {
            addCriterion("pri <>", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriGreaterThan(Byte value) {
            addCriterion("pri >", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriGreaterThanOrEqualTo(Byte value) {
            addCriterion("pri >=", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriLessThan(Byte value) {
            addCriterion("pri <", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriLessThanOrEqualTo(Byte value) {
            addCriterion("pri <=", value, "pri");
            return (Criteria) this;
        }

        public Criteria andPriIn(List<Byte> values) {
            addCriterion("pri in", values, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotIn(List<Byte> values) {
            addCriterion("pri not in", values, "pri");
            return (Criteria) this;
        }

        public Criteria andPriBetween(Byte value1, Byte value2) {
            addCriterion("pri between", value1, value2, "pri");
            return (Criteria) this;
        }

        public Criteria andPriNotBetween(Byte value1, Byte value2) {
            addCriterion("pri not between", value1, value2, "pri");
            return (Criteria) this;
        }

        public Criteria andEstimateIsNull() {
            addCriterion("estimate is null");
            return (Criteria) this;
        }

        public Criteria andEstimateIsNotNull() {
            addCriterion("estimate is not null");
            return (Criteria) this;
        }

        public Criteria andEstimateEqualTo(Float value) {
            addCriterion("estimate =", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateNotEqualTo(Float value) {
            addCriterion("estimate <>", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateGreaterThan(Float value) {
            addCriterion("estimate >", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateGreaterThanOrEqualTo(Float value) {
            addCriterion("estimate >=", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateLessThan(Float value) {
            addCriterion("estimate <", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateLessThanOrEqualTo(Float value) {
            addCriterion("estimate <=", value, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateIn(List<Float> values) {
            addCriterion("estimate in", values, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateNotIn(List<Float> values) {
            addCriterion("estimate not in", values, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateBetween(Float value1, Float value2) {
            addCriterion("estimate between", value1, value2, "estimate");
            return (Criteria) this;
        }

        public Criteria andEstimateNotBetween(Float value1, Float value2) {
            addCriterion("estimate not between", value1, value2, "estimate");
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

        public Criteria andColorIsNull() {
            addCriterion("color is null");
            return (Criteria) this;
        }

        public Criteria andColorIsNotNull() {
            addCriterion("color is not null");
            return (Criteria) this;
        }

        public Criteria andColorEqualTo(String value) {
            addCriterion("color =", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotEqualTo(String value) {
            addCriterion("color <>", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThan(String value) {
            addCriterion("color >", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThanOrEqualTo(String value) {
            addCriterion("color >=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThan(String value) {
            addCriterion("color <", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThanOrEqualTo(String value) {
            addCriterion("color <=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLike(String value) {
            addCriterion("color like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotLike(String value) {
            addCriterion("color not like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorIn(List<String> values) {
            addCriterion("color in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotIn(List<String> values) {
            addCriterion("color not in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorBetween(String value1, String value2) {
            addCriterion("color between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotBetween(String value1, String value2) {
            addCriterion("color not between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(String value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(String value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(String value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(String value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(String value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(String value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLike(String value) {
            addCriterion("stage like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotLike(String value) {
            addCriterion("stage not like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<String> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<String> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(String value1, String value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(String value1, String value2) {
            addCriterion("stage not between", value1, value2, "stage");
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

        public Criteria andAssignedtoIsNull() {
            addCriterion("assignedTo is null");
            return (Criteria) this;
        }

        public Criteria andAssignedtoIsNotNull() {
            addCriterion("assignedTo is not null");
            return (Criteria) this;
        }

        public Criteria andAssignedtoEqualTo(String value) {
            addCriterion("assignedTo =", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoNotEqualTo(String value) {
            addCriterion("assignedTo <>", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoGreaterThan(String value) {
            addCriterion("assignedTo >", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoGreaterThanOrEqualTo(String value) {
            addCriterion("assignedTo >=", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoLessThan(String value) {
            addCriterion("assignedTo <", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoLessThanOrEqualTo(String value) {
            addCriterion("assignedTo <=", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoLike(String value) {
            addCriterion("assignedTo like", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoNotLike(String value) {
            addCriterion("assignedTo not like", value, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoIn(List<String> values) {
            addCriterion("assignedTo in", values, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoNotIn(List<String> values) {
            addCriterion("assignedTo not in", values, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoBetween(String value1, String value2) {
            addCriterion("assignedTo between", value1, value2, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssignedtoNotBetween(String value1, String value2) {
            addCriterion("assignedTo not between", value1, value2, "assignedto");
            return (Criteria) this;
        }

        public Criteria andAssigneddateIsNull() {
            addCriterion("assignedDate is null");
            return (Criteria) this;
        }

        public Criteria andAssigneddateIsNotNull() {
            addCriterion("assignedDate is not null");
            return (Criteria) this;
        }

        public Criteria andAssigneddateEqualTo(Date value) {
            addCriterion("assignedDate =", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateNotEqualTo(Date value) {
            addCriterion("assignedDate <>", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateGreaterThan(Date value) {
            addCriterion("assignedDate >", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateGreaterThanOrEqualTo(Date value) {
            addCriterion("assignedDate >=", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateLessThan(Date value) {
            addCriterion("assignedDate <", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateLessThanOrEqualTo(Date value) {
            addCriterion("assignedDate <=", value, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateIn(List<Date> values) {
            addCriterion("assignedDate in", values, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateNotIn(List<Date> values) {
            addCriterion("assignedDate not in", values, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateBetween(Date value1, Date value2) {
            addCriterion("assignedDate between", value1, value2, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andAssigneddateNotBetween(Date value1, Date value2) {
            addCriterion("assignedDate not between", value1, value2, "assigneddate");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyIsNull() {
            addCriterion("lastEditedBy is null");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyIsNotNull() {
            addCriterion("lastEditedBy is not null");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyEqualTo(String value) {
            addCriterion("lastEditedBy =", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyNotEqualTo(String value) {
            addCriterion("lastEditedBy <>", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyGreaterThan(String value) {
            addCriterion("lastEditedBy >", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyGreaterThanOrEqualTo(String value) {
            addCriterion("lastEditedBy >=", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyLessThan(String value) {
            addCriterion("lastEditedBy <", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyLessThanOrEqualTo(String value) {
            addCriterion("lastEditedBy <=", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyLike(String value) {
            addCriterion("lastEditedBy like", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyNotLike(String value) {
            addCriterion("lastEditedBy not like", value, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyIn(List<String> values) {
            addCriterion("lastEditedBy in", values, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyNotIn(List<String> values) {
            addCriterion("lastEditedBy not in", values, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyBetween(String value1, String value2) {
            addCriterion("lastEditedBy between", value1, value2, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLasteditedbyNotBetween(String value1, String value2) {
            addCriterion("lastEditedBy not between", value1, value2, "lasteditedby");
            return (Criteria) this;
        }

        public Criteria andLastediteddateIsNull() {
            addCriterion("lastEditedDate is null");
            return (Criteria) this;
        }

        public Criteria andLastediteddateIsNotNull() {
            addCriterion("lastEditedDate is not null");
            return (Criteria) this;
        }

        public Criteria andLastediteddateEqualTo(Date value) {
            addCriterion("lastEditedDate =", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateNotEqualTo(Date value) {
            addCriterion("lastEditedDate <>", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateGreaterThan(Date value) {
            addCriterion("lastEditedDate >", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateGreaterThanOrEqualTo(Date value) {
            addCriterion("lastEditedDate >=", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateLessThan(Date value) {
            addCriterion("lastEditedDate <", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateLessThanOrEqualTo(Date value) {
            addCriterion("lastEditedDate <=", value, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateIn(List<Date> values) {
            addCriterion("lastEditedDate in", values, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateNotIn(List<Date> values) {
            addCriterion("lastEditedDate not in", values, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateBetween(Date value1, Date value2) {
            addCriterion("lastEditedDate between", value1, value2, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andLastediteddateNotBetween(Date value1, Date value2) {
            addCriterion("lastEditedDate not between", value1, value2, "lastediteddate");
            return (Criteria) this;
        }

        public Criteria andReviewedbyIsNull() {
            addCriterion("reviewedBy is null");
            return (Criteria) this;
        }

        public Criteria andReviewedbyIsNotNull() {
            addCriterion("reviewedBy is not null");
            return (Criteria) this;
        }

        public Criteria andReviewedbyEqualTo(String value) {
            addCriterion("reviewedBy =", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyNotEqualTo(String value) {
            addCriterion("reviewedBy <>", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyGreaterThan(String value) {
            addCriterion("reviewedBy >", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyGreaterThanOrEqualTo(String value) {
            addCriterion("reviewedBy >=", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyLessThan(String value) {
            addCriterion("reviewedBy <", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyLessThanOrEqualTo(String value) {
            addCriterion("reviewedBy <=", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyLike(String value) {
            addCriterion("reviewedBy like", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyNotLike(String value) {
            addCriterion("reviewedBy not like", value, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyIn(List<String> values) {
            addCriterion("reviewedBy in", values, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyNotIn(List<String> values) {
            addCriterion("reviewedBy not in", values, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyBetween(String value1, String value2) {
            addCriterion("reviewedBy between", value1, value2, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andReviewedbyNotBetween(String value1, String value2) {
            addCriterion("reviewedBy not between", value1, value2, "reviewedby");
            return (Criteria) this;
        }

        public Criteria andRevieweddateIsNull() {
            addCriterion("reviewedDate is null");
            return (Criteria) this;
        }

        public Criteria andRevieweddateIsNotNull() {
            addCriterion("reviewedDate is not null");
            return (Criteria) this;
        }

        public Criteria andRevieweddateEqualTo(Date value) {
            addCriterionForJDBCDate("reviewedDate =", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("reviewedDate <>", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateGreaterThan(Date value) {
            addCriterionForJDBCDate("reviewedDate >", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reviewedDate >=", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateLessThan(Date value) {
            addCriterionForJDBCDate("reviewedDate <", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("reviewedDate <=", value, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateIn(List<Date> values) {
            addCriterionForJDBCDate("reviewedDate in", values, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("reviewedDate not in", values, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reviewedDate between", value1, value2, "revieweddate");
            return (Criteria) this;
        }

        public Criteria andRevieweddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("reviewedDate not between", value1, value2, "revieweddate");
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

        public Criteria andClosedreasonIsNull() {
            addCriterion("closedReason is null");
            return (Criteria) this;
        }

        public Criteria andClosedreasonIsNotNull() {
            addCriterion("closedReason is not null");
            return (Criteria) this;
        }

        public Criteria andClosedreasonEqualTo(String value) {
            addCriterion("closedReason =", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonNotEqualTo(String value) {
            addCriterion("closedReason <>", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonGreaterThan(String value) {
            addCriterion("closedReason >", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonGreaterThanOrEqualTo(String value) {
            addCriterion("closedReason >=", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonLessThan(String value) {
            addCriterion("closedReason <", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonLessThanOrEqualTo(String value) {
            addCriterion("closedReason <=", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonLike(String value) {
            addCriterion("closedReason like", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonNotLike(String value) {
            addCriterion("closedReason not like", value, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonIn(List<String> values) {
            addCriterion("closedReason in", values, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonNotIn(List<String> values) {
            addCriterion("closedReason not in", values, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonBetween(String value1, String value2) {
            addCriterion("closedReason between", value1, value2, "closedreason");
            return (Criteria) this;
        }

        public Criteria andClosedreasonNotBetween(String value1, String value2) {
            addCriterion("closedReason not between", value1, value2, "closedreason");
            return (Criteria) this;
        }

        public Criteria andTobugIsNull() {
            addCriterion("toBug is null");
            return (Criteria) this;
        }

        public Criteria andTobugIsNotNull() {
            addCriterion("toBug is not null");
            return (Criteria) this;
        }

        public Criteria andTobugEqualTo(Integer value) {
            addCriterion("toBug =", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugNotEqualTo(Integer value) {
            addCriterion("toBug <>", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugGreaterThan(Integer value) {
            addCriterion("toBug >", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugGreaterThanOrEqualTo(Integer value) {
            addCriterion("toBug >=", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugLessThan(Integer value) {
            addCriterion("toBug <", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugLessThanOrEqualTo(Integer value) {
            addCriterion("toBug <=", value, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugIn(List<Integer> values) {
            addCriterion("toBug in", values, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugNotIn(List<Integer> values) {
            addCriterion("toBug not in", values, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugBetween(Integer value1, Integer value2) {
            addCriterion("toBug between", value1, value2, "tobug");
            return (Criteria) this;
        }

        public Criteria andTobugNotBetween(Integer value1, Integer value2) {
            addCriterion("toBug not between", value1, value2, "tobug");
            return (Criteria) this;
        }

        public Criteria andChildstoriesIsNull() {
            addCriterion("childStories is null");
            return (Criteria) this;
        }

        public Criteria andChildstoriesIsNotNull() {
            addCriterion("childStories is not null");
            return (Criteria) this;
        }

        public Criteria andChildstoriesEqualTo(String value) {
            addCriterion("childStories =", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesNotEqualTo(String value) {
            addCriterion("childStories <>", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesGreaterThan(String value) {
            addCriterion("childStories >", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesGreaterThanOrEqualTo(String value) {
            addCriterion("childStories >=", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesLessThan(String value) {
            addCriterion("childStories <", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesLessThanOrEqualTo(String value) {
            addCriterion("childStories <=", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesLike(String value) {
            addCriterion("childStories like", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesNotLike(String value) {
            addCriterion("childStories not like", value, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesIn(List<String> values) {
            addCriterion("childStories in", values, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesNotIn(List<String> values) {
            addCriterion("childStories not in", values, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesBetween(String value1, String value2) {
            addCriterion("childStories between", value1, value2, "childstories");
            return (Criteria) this;
        }

        public Criteria andChildstoriesNotBetween(String value1, String value2) {
            addCriterion("childStories not between", value1, value2, "childstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesIsNull() {
            addCriterion("linkStories is null");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesIsNotNull() {
            addCriterion("linkStories is not null");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesEqualTo(String value) {
            addCriterion("linkStories =", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesNotEqualTo(String value) {
            addCriterion("linkStories <>", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesGreaterThan(String value) {
            addCriterion("linkStories >", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesGreaterThanOrEqualTo(String value) {
            addCriterion("linkStories >=", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesLessThan(String value) {
            addCriterion("linkStories <", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesLessThanOrEqualTo(String value) {
            addCriterion("linkStories <=", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesLike(String value) {
            addCriterion("linkStories like", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesNotLike(String value) {
            addCriterion("linkStories not like", value, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesIn(List<String> values) {
            addCriterion("linkStories in", values, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesNotIn(List<String> values) {
            addCriterion("linkStories not in", values, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesBetween(String value1, String value2) {
            addCriterion("linkStories between", value1, value2, "linkstories");
            return (Criteria) this;
        }

        public Criteria andLinkstoriesNotBetween(String value1, String value2) {
            addCriterion("linkStories not between", value1, value2, "linkstories");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryIsNull() {
            addCriterion("duplicateStory is null");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryIsNotNull() {
            addCriterion("duplicateStory is not null");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryEqualTo(Integer value) {
            addCriterion("duplicateStory =", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryNotEqualTo(Integer value) {
            addCriterion("duplicateStory <>", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryGreaterThan(Integer value) {
            addCriterion("duplicateStory >", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("duplicateStory >=", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryLessThan(Integer value) {
            addCriterion("duplicateStory <", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryLessThanOrEqualTo(Integer value) {
            addCriterion("duplicateStory <=", value, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryIn(List<Integer> values) {
            addCriterion("duplicateStory in", values, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryNotIn(List<Integer> values) {
            addCriterion("duplicateStory not in", values, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryBetween(Integer value1, Integer value2) {
            addCriterion("duplicateStory between", value1, value2, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andDuplicatestoryNotBetween(Integer value1, Integer value2) {
            addCriterion("duplicateStory not between", value1, value2, "duplicatestory");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Short value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Short value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Short value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Short value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Short value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Short value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Short> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Short> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Short value1, Short value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Short value1, Short value2) {
            addCriterion("version not between", value1, value2, "version");
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