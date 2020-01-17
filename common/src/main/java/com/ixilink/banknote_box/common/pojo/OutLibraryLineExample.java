package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class OutLibraryLineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public OutLibraryLineExample() {
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

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Integer value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Integer value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Integer value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Integer value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Integer> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Integer> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNull() {
            addCriterion("line_name is null");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNotNull() {
            addCriterion("line_name is not null");
            return (Criteria) this;
        }

        public Criteria andLineNameEqualTo(String value) {
            addCriterion("line_name =", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotEqualTo(String value) {
            addCriterion("line_name <>", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThan(String value) {
            addCriterion("line_name >", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThanOrEqualTo(String value) {
            addCriterion("line_name >=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThan(String value) {
            addCriterion("line_name <", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThanOrEqualTo(String value) {
            addCriterion("line_name <=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLike(String value) {
            addCriterion("line_name like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotLike(String value) {
            addCriterion("line_name not like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameIn(List<String> values) {
            addCriterion("line_name in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotIn(List<String> values) {
            addCriterion("line_name not in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameBetween(String value1, String value2) {
            addCriterion("line_name between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotBetween(String value1, String value2) {
            addCriterion("line_name not between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIsNull() {
            addCriterion("total_box is null");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIsNotNull() {
            addCriterion("total_box is not null");
            return (Criteria) this;
        }

        public Criteria andTotalBoxEqualTo(Integer value) {
            addCriterion("total_box =", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotEqualTo(Integer value) {
            addCriterion("total_box <>", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxGreaterThan(Integer value) {
            addCriterion("total_box >", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_box >=", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxLessThan(Integer value) {
            addCriterion("total_box <", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxLessThanOrEqualTo(Integer value) {
            addCriterion("total_box <=", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIn(List<Integer> values) {
            addCriterion("total_box in", values, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotIn(List<Integer> values) {
            addCriterion("total_box not in", values, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxBetween(Integer value1, Integer value2) {
            addCriterion("total_box between", value1, value2, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotBetween(Integer value1, Integer value2) {
            addCriterion("total_box not between", value1, value2, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIsNull() {
            addCriterion("total_money is null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIsNotNull() {
            addCriterion("total_money is not null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyEqualTo(Integer value) {
            addCriterion("total_money =", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotEqualTo(Integer value) {
            addCriterion("total_money <>", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThan(Integer value) {
            addCriterion("total_money >", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_money >=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThan(Integer value) {
            addCriterion("total_money <", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("total_money <=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIn(List<Integer> values) {
            addCriterion("total_money in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotIn(List<Integer> values) {
            addCriterion("total_money not in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyBetween(Integer value1, Integer value2) {
            addCriterion("total_money between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("total_money not between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andBatchIsNull() {
            addCriterion("batch is null");
            return (Criteria) this;
        }

        public Criteria andBatchIsNotNull() {
            addCriterion("batch is not null");
            return (Criteria) this;
        }

        public Criteria andBatchEqualTo(Integer value) {
            addCriterion("batch =", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotEqualTo(Integer value) {
            addCriterion("batch <>", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThan(Integer value) {
            addCriterion("batch >", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch >=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThan(Integer value) {
            addCriterion("batch <", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThanOrEqualTo(Integer value) {
            addCriterion("batch <=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchIn(List<Integer> values) {
            addCriterion("batch in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotIn(List<Integer> values) {
            addCriterion("batch not in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchBetween(Integer value1, Integer value2) {
            addCriterion("batch between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotBetween(Integer value1, Integer value2) {
            addCriterion("batch not between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIsNull() {
            addCriterion("boxing_state is null");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIsNotNull() {
            addCriterion("boxing_state is not null");
            return (Criteria) this;
        }

        public Criteria andBoxingStateEqualTo(Integer value) {
            addCriterion("boxing_state =", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotEqualTo(Integer value) {
            addCriterion("boxing_state <>", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateGreaterThan(Integer value) {
            addCriterion("boxing_state >", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("boxing_state >=", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateLessThan(Integer value) {
            addCriterion("boxing_state <", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateLessThanOrEqualTo(Integer value) {
            addCriterion("boxing_state <=", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIn(List<Integer> values) {
            addCriterion("boxing_state in", values, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotIn(List<Integer> values) {
            addCriterion("boxing_state not in", values, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateBetween(Integer value1, Integer value2) {
            addCriterion("boxing_state between", value1, value2, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotBetween(Integer value1, Integer value2) {
            addCriterion("boxing_state not between", value1, value2, "boxingState");
            return (Criteria) this;
        }

        public Criteria andCheckStateIsNull() {
            addCriterion("check_state is null");
            return (Criteria) this;
        }

        public Criteria andCheckStateIsNotNull() {
            addCriterion("check_state is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStateEqualTo(Integer value) {
            addCriterion("check_state =", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotEqualTo(Integer value) {
            addCriterion("check_state <>", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateGreaterThan(Integer value) {
            addCriterion("check_state >", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_state >=", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateLessThan(Integer value) {
            addCriterion("check_state <", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateLessThanOrEqualTo(Integer value) {
            addCriterion("check_state <=", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateIn(List<Integer> values) {
            addCriterion("check_state in", values, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotIn(List<Integer> values) {
            addCriterion("check_state not in", values, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateBetween(Integer value1, Integer value2) {
            addCriterion("check_state between", value1, value2, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotBetween(Integer value1, Integer value2) {
            addCriterion("check_state not between", value1, value2, "checkState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateIsNull() {
            addCriterion("handover_check_state is null");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateIsNotNull() {
            addCriterion("handover_check_state is not null");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateEqualTo(Integer value) {
            addCriterion("handover_check_state =", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateNotEqualTo(Integer value) {
            addCriterion("handover_check_state <>", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateGreaterThan(Integer value) {
            addCriterion("handover_check_state >", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("handover_check_state >=", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateLessThan(Integer value) {
            addCriterion("handover_check_state <", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateLessThanOrEqualTo(Integer value) {
            addCriterion("handover_check_state <=", value, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateIn(List<Integer> values) {
            addCriterion("handover_check_state in", values, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateNotIn(List<Integer> values) {
            addCriterion("handover_check_state not in", values, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateBetween(Integer value1, Integer value2) {
            addCriterion("handover_check_state between", value1, value2, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andHandoverCheckStateNotBetween(Integer value1, Integer value2) {
            addCriterion("handover_check_state not between", value1, value2, "handoverCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateIsNull() {
            addCriterion("add_check_state is null");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateIsNotNull() {
            addCriterion("add_check_state is not null");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateEqualTo(Integer value) {
            addCriterion("add_check_state =", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateNotEqualTo(Integer value) {
            addCriterion("add_check_state <>", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateGreaterThan(Integer value) {
            addCriterion("add_check_state >", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_check_state >=", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateLessThan(Integer value) {
            addCriterion("add_check_state <", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateLessThanOrEqualTo(Integer value) {
            addCriterion("add_check_state <=", value, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateIn(List<Integer> values) {
            addCriterion("add_check_state in", values, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateNotIn(List<Integer> values) {
            addCriterion("add_check_state not in", values, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateBetween(Integer value1, Integer value2) {
            addCriterion("add_check_state between", value1, value2, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andAddCheckStateNotBetween(Integer value1, Integer value2) {
            addCriterion("add_check_state not between", value1, value2, "addCheckState");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleIsNull() {
            addCriterion("check_schedule is null");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleIsNotNull() {
            addCriterion("check_schedule is not null");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleEqualTo(Integer value) {
            addCriterion("check_schedule =", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleNotEqualTo(Integer value) {
            addCriterion("check_schedule <>", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleGreaterThan(Integer value) {
            addCriterion("check_schedule >", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_schedule >=", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleLessThan(Integer value) {
            addCriterion("check_schedule <", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleLessThanOrEqualTo(Integer value) {
            addCriterion("check_schedule <=", value, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleIn(List<Integer> values) {
            addCriterion("check_schedule in", values, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleNotIn(List<Integer> values) {
            addCriterion("check_schedule not in", values, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleBetween(Integer value1, Integer value2) {
            addCriterion("check_schedule between", value1, value2, "checkSchedule");
            return (Criteria) this;
        }

        public Criteria andCheckScheduleNotBetween(Integer value1, Integer value2) {
            addCriterion("check_schedule not between", value1, value2, "checkSchedule");
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