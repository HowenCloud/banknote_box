package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class SystemSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public SystemSettingExample() {
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

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setCount(int count) {
        this.count=count;
    }

    public int getCount() {
        return count;
    }

    public Criteria getCriteria() {
        if (oredCriteria.size() != 0) {return oredCriteria.get(0);}
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
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

        public Criteria andLibraryIdIsNull() {
            addCriterion("library_id is null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIsNotNull() {
            addCriterion("library_id is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdEqualTo(Integer value) {
            addCriterion("library_id =", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotEqualTo(Integer value) {
            addCriterion("library_id <>", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThan(Integer value) {
            addCriterion("library_id >", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("library_id >=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThan(Integer value) {
            addCriterion("library_id <", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThanOrEqualTo(Integer value) {
            addCriterion("library_id <=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIn(List<Integer> values) {
            addCriterion("library_id in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotIn(List<Integer> values) {
            addCriterion("library_id not in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdBetween(Integer value1, Integer value2) {
            addCriterion("library_id between", value1, value2, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("library_id not between", value1, value2, "libraryId");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpIsNull() {
            addCriterion("assignments_reader_writer_ip is null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpIsNotNull() {
            addCriterion("assignments_reader_writer_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpEqualTo(String value) {
            addCriterion("assignments_reader_writer_ip =", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpNotEqualTo(String value) {
            addCriterion("assignments_reader_writer_ip <>", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpGreaterThan(String value) {
            addCriterion("assignments_reader_writer_ip >", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpGreaterThanOrEqualTo(String value) {
            addCriterion("assignments_reader_writer_ip >=", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpLessThan(String value) {
            addCriterion("assignments_reader_writer_ip <", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpLessThanOrEqualTo(String value) {
            addCriterion("assignments_reader_writer_ip <=", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpLike(String value) {
            addCriterion("assignments_reader_writer_ip like", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpNotLike(String value) {
            addCriterion("assignments_reader_writer_ip not like", value, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpIn(List<String> values) {
            addCriterion("assignments_reader_writer_ip in", values, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpNotIn(List<String> values) {
            addCriterion("assignments_reader_writer_ip not in", values, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpBetween(String value1, String value2) {
            addCriterion("assignments_reader_writer_ip between", value1, value2, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsReaderWriterIpNotBetween(String value1, String value2) {
            addCriterion("assignments_reader_writer_ip not between", value1, value2, "assignmentsReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpIsNull() {
            addCriterion("handover_reader_writer_ip is null");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpIsNotNull() {
            addCriterion("handover_reader_writer_ip is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpEqualTo(String value) {
            addCriterion("handover_reader_writer_ip =", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpNotEqualTo(String value) {
            addCriterion("handover_reader_writer_ip <>", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpGreaterThan(String value) {
            addCriterion("handover_reader_writer_ip >", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpGreaterThanOrEqualTo(String value) {
            addCriterion("handover_reader_writer_ip >=", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpLessThan(String value) {
            addCriterion("handover_reader_writer_ip <", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpLessThanOrEqualTo(String value) {
            addCriterion("handover_reader_writer_ip <=", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpLike(String value) {
            addCriterion("handover_reader_writer_ip like", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpNotLike(String value) {
            addCriterion("handover_reader_writer_ip not like", value, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpIn(List<String> values) {
            addCriterion("handover_reader_writer_ip in", values, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpNotIn(List<String> values) {
            addCriterion("handover_reader_writer_ip not in", values, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpBetween(String value1, String value2) {
            addCriterion("handover_reader_writer_ip between", value1, value2, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andHandoverReaderWriterIpNotBetween(String value1, String value2) {
            addCriterion("handover_reader_writer_ip not between", value1, value2, "handoverReaderWriterIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpIsNull() {
            addCriterion("assignments_amera_ip is null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpIsNotNull() {
            addCriterion("assignments_amera_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpEqualTo(String value) {
            addCriterion("assignments_amera_ip =", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpNotEqualTo(String value) {
            addCriterion("assignments_amera_ip <>", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpGreaterThan(String value) {
            addCriterion("assignments_amera_ip >", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpGreaterThanOrEqualTo(String value) {
            addCriterion("assignments_amera_ip >=", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpLessThan(String value) {
            addCriterion("assignments_amera_ip <", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpLessThanOrEqualTo(String value) {
            addCriterion("assignments_amera_ip <=", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpLike(String value) {
            addCriterion("assignments_amera_ip like", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpNotLike(String value) {
            addCriterion("assignments_amera_ip not like", value, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpIn(List<String> values) {
            addCriterion("assignments_amera_ip in", values, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpNotIn(List<String> values) {
            addCriterion("assignments_amera_ip not in", values, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpBetween(String value1, String value2) {
            addCriterion("assignments_amera_ip between", value1, value2, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andAssignmentsAmeraIpNotBetween(String value1, String value2) {
            addCriterion("assignments_amera_ip not between", value1, value2, "assignmentsAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpIsNull() {
            addCriterion("handover_amera_ip is null");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpIsNotNull() {
            addCriterion("handover_amera_ip is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpEqualTo(String value) {
            addCriterion("handover_amera_ip =", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpNotEqualTo(String value) {
            addCriterion("handover_amera_ip <>", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpGreaterThan(String value) {
            addCriterion("handover_amera_ip >", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpGreaterThanOrEqualTo(String value) {
            addCriterion("handover_amera_ip >=", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpLessThan(String value) {
            addCriterion("handover_amera_ip <", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpLessThanOrEqualTo(String value) {
            addCriterion("handover_amera_ip <=", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpLike(String value) {
            addCriterion("handover_amera_ip like", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpNotLike(String value) {
            addCriterion("handover_amera_ip not like", value, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpIn(List<String> values) {
            addCriterion("handover_amera_ip in", values, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpNotIn(List<String> values) {
            addCriterion("handover_amera_ip not in", values, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpBetween(String value1, String value2) {
            addCriterion("handover_amera_ip between", value1, value2, "handoverAmeraIp");
            return (Criteria) this;
        }

        public Criteria andHandoverAmeraIpNotBetween(String value1, String value2) {
            addCriterion("handover_amera_ip not between", value1, value2, "handoverAmeraIp");
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