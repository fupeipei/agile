package com.yusys.agile.sprintv3.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SSprintAttachmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SSprintAttachmentExample() {
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

        public Criteria andAttachmentIdIsNull() {
            addCriterion("attachment_id is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdIsNotNull() {
            addCriterion("attachment_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdEqualTo(Long value) {
            addCriterion("attachment_id =", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotEqualTo(Long value) {
            addCriterion("attachment_id <>", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdGreaterThan(Long value) {
            addCriterion("attachment_id >", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("attachment_id >=", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdLessThan(Long value) {
            addCriterion("attachment_id <", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdLessThanOrEqualTo(Long value) {
            addCriterion("attachment_id <=", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdIn(List<Long> values) {
            addCriterion("attachment_id in", values, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotIn(List<Long> values) {
            addCriterion("attachment_id not in", values, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdBetween(Long value1, Long value2) {
            addCriterion("attachment_id between", value1, value2, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotBetween(Long value1, Long value2) {
            addCriterion("attachment_id not between", value1, value2, "attachmentId");
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

        public Criteria andFileUriIsNull() {
            addCriterion("file_uri is null");
            return (Criteria) this;
        }

        public Criteria andFileUriIsNotNull() {
            addCriterion("file_uri is not null");
            return (Criteria) this;
        }

        public Criteria andFileUriEqualTo(String value) {
            addCriterion("file_uri =", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriNotEqualTo(String value) {
            addCriterion("file_uri <>", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriGreaterThan(String value) {
            addCriterion("file_uri >", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriGreaterThanOrEqualTo(String value) {
            addCriterion("file_uri >=", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriLessThan(String value) {
            addCriterion("file_uri <", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriLessThanOrEqualTo(String value) {
            addCriterion("file_uri <=", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriLike(String value) {
            addCriterion("file_uri like", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriNotLike(String value) {
            addCriterion("file_uri not like", value, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriIn(List<String> values) {
            addCriterion("file_uri in", values, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriNotIn(List<String> values) {
            addCriterion("file_uri not in", values, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriBetween(String value1, String value2) {
            addCriterion("file_uri between", value1, value2, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileUriNotBetween(String value1, String value2) {
            addCriterion("file_uri not between", value1, value2, "fileUri");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNull() {
            addCriterion("file_size is null");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNotNull() {
            addCriterion("file_size is not null");
            return (Criteria) this;
        }

        public Criteria andFileSizeEqualTo(Integer value) {
            addCriterion("file_size =", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotEqualTo(Integer value) {
            addCriterion("file_size <>", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThan(Integer value) {
            addCriterion("file_size >", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_size >=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThan(Integer value) {
            addCriterion("file_size <", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThanOrEqualTo(Integer value) {
            addCriterion("file_size <=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeIn(List<Integer> values) {
            addCriterion("file_size in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotIn(List<Integer> values) {
            addCriterion("file_size not in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeBetween(Integer value1, Integer value2) {
            addCriterion("file_size between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("file_size not between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Date value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Date value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Date value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Date value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Date> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Date> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Date value1, Date value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadUidIsNull() {
            addCriterion("upload_uid is null");
            return (Criteria) this;
        }

        public Criteria andUploadUidIsNotNull() {
            addCriterion("upload_uid is not null");
            return (Criteria) this;
        }

        public Criteria andUploadUidEqualTo(Long value) {
            addCriterion("upload_uid =", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidNotEqualTo(Long value) {
            addCriterion("upload_uid <>", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidGreaterThan(Long value) {
            addCriterion("upload_uid >", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidGreaterThanOrEqualTo(Long value) {
            addCriterion("upload_uid >=", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidLessThan(Long value) {
            addCriterion("upload_uid <", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidLessThanOrEqualTo(Long value) {
            addCriterion("upload_uid <=", value, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidIn(List<Long> values) {
            addCriterion("upload_uid in", values, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidNotIn(List<Long> values) {
            addCriterion("upload_uid not in", values, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidBetween(Long value1, Long value2) {
            addCriterion("upload_uid between", value1, value2, "uploadUid");
            return (Criteria) this;
        }

        public Criteria andUploadUidNotBetween(Long value1, Long value2) {
            addCriterion("upload_uid not between", value1, value2, "uploadUid");
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