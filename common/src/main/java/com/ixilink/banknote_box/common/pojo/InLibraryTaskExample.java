package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class InLibraryTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public InLibraryTaskExample() {
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

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(Integer value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(Integer value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(Integer value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(Integer value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(Integer value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(Integer value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<Integer> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<Integer> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(Integer value1, Integer value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(Integer value1, Integer value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
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

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNull() {
            addCriterion("task_state is null");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNotNull() {
            addCriterion("task_state is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStateEqualTo(Integer value) {
            addCriterion("task_state =", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotEqualTo(Integer value) {
            addCriterion("task_state <>", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThan(Integer value) {
            addCriterion("task_state >", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_state >=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThan(Integer value) {
            addCriterion("task_state <", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThanOrEqualTo(Integer value) {
            addCriterion("task_state <=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateIn(List<Integer> values) {
            addCriterion("task_state in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotIn(List<Integer> values) {
            addCriterion("task_state not in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateBetween(Integer value1, Integer value2) {
            addCriterion("task_state between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotBetween(Integer value1, Integer value2) {
            addCriterion("task_state not between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIsNull() {
            addCriterion("task_schedule is null");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIsNotNull() {
            addCriterion("task_schedule is not null");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleEqualTo(Integer value) {
            addCriterion("task_schedule =", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotEqualTo(Integer value) {
            addCriterion("task_schedule <>", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleGreaterThan(Integer value) {
            addCriterion("task_schedule >", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_schedule >=", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleLessThan(Integer value) {
            addCriterion("task_schedule <", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleLessThanOrEqualTo(Integer value) {
            addCriterion("task_schedule <=", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIn(List<Integer> values) {
            addCriterion("task_schedule in", values, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotIn(List<Integer> values) {
            addCriterion("task_schedule not in", values, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleBetween(Integer value1, Integer value2) {
            addCriterion("task_schedule between", value1, value2, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotBetween(Integer value1, Integer value2) {
            addCriterion("task_schedule not between", value1, value2, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andDownTimeIsNull() {
            addCriterion("down_time is null");
            return (Criteria) this;
        }

        public Criteria andDownTimeIsNotNull() {
            addCriterion("down_time is not null");
            return (Criteria) this;
        }

        public Criteria andDownTimeEqualTo(Long value) {
            addCriterion("down_time =", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeNotEqualTo(Long value) {
            addCriterion("down_time <>", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeGreaterThan(Long value) {
            addCriterion("down_time >", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("down_time >=", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeLessThan(Long value) {
            addCriterion("down_time <", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeLessThanOrEqualTo(Long value) {
            addCriterion("down_time <=", value, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeIn(List<Long> values) {
            addCriterion("down_time in", values, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeNotIn(List<Long> values) {
            addCriterion("down_time not in", values, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeBetween(Long value1, Long value2) {
            addCriterion("down_time between", value1, value2, "downTime");
            return (Criteria) this;
        }

        public Criteria andDownTimeNotBetween(Long value1, Long value2) {
            addCriterion("down_time not between", value1, value2, "downTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIsNull() {
            addCriterion("handover_time is null");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIsNotNull() {
            addCriterion("handover_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeEqualTo(Long value) {
            addCriterion("handover_time =", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotEqualTo(Long value) {
            addCriterion("handover_time <>", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeGreaterThan(Long value) {
            addCriterion("handover_time >", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("handover_time >=", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeLessThan(Long value) {
            addCriterion("handover_time <", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeLessThanOrEqualTo(Long value) {
            addCriterion("handover_time <=", value, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeIn(List<Long> values) {
            addCriterion("handover_time in", values, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotIn(List<Long> values) {
            addCriterion("handover_time not in", values, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeBetween(Long value1, Long value2) {
            addCriterion("handover_time between", value1, value2, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andHandoverTimeNotBetween(Long value1, Long value2) {
            addCriterion("handover_time not between", value1, value2, "handoverTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIsNull() {
            addCriterion("receive_time is null");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIsNotNull() {
            addCriterion("receive_time is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeEqualTo(Long value) {
            addCriterion("receive_time =", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotEqualTo(Long value) {
            addCriterion("receive_time <>", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeGreaterThan(Long value) {
            addCriterion("receive_time >", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("receive_time >=", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeLessThan(Long value) {
            addCriterion("receive_time <", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeLessThanOrEqualTo(Long value) {
            addCriterion("receive_time <=", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIn(List<Long> values) {
            addCriterion("receive_time in", values, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotIn(List<Long> values) {
            addCriterion("receive_time not in", values, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeBetween(Long value1, Long value2) {
            addCriterion("receive_time between", value1, value2, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotBetween(Long value1, Long value2) {
            addCriterion("receive_time not between", value1, value2, "receiveTime");
            return (Criteria) this;
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