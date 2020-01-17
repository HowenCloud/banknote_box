package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class OutLibraryTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public OutLibraryTaskExample() {
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
            addCriterion("out_library_task.id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("out_library_task.id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("out_library_task.id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("out_library_task.id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("out_library_task.id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("out_library_task.id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("out_library_task.id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("out_library_task.id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTotalLineIsNull() {
            addCriterion("out_library_task.total_line is null");
            return (Criteria) this;
        }

        public Criteria andTotalLineIsNotNull() {
            addCriterion("out_library_task.total_line is not null");
            return (Criteria) this;
        }

        public Criteria andTotalLineEqualTo(Integer value) {
            addCriterion("out_library_task.total_line =", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineNotEqualTo(Integer value) {
            addCriterion("out_library_task.total_line <>", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineGreaterThan(Integer value) {
            addCriterion("out_library_task.total_line >", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_line >=", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineLessThan(Integer value) {
            addCriterion("out_library_task.total_line <", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_line <=", value, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineIn(List<Integer> values) {
            addCriterion("out_library_task.total_line in", values, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineNotIn(List<Integer> values) {
            addCriterion("out_library_task.total_line not in", values, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_line between", value1, value2, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalLineNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_line not between", value1, value2, "totalLine");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIsNull() {
            addCriterion("out_library_task.total_box is null");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIsNotNull() {
            addCriterion("out_library_task.total_box is not null");
            return (Criteria) this;
        }

        public Criteria andTotalBoxEqualTo(Integer value) {
            addCriterion("out_library_task.total_box =", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotEqualTo(Integer value) {
            addCriterion("out_library_task.total_box <>", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxGreaterThan(Integer value) {
            addCriterion("out_library_task.total_box >", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_box >=", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxLessThan(Integer value) {
            addCriterion("out_library_task.total_box <", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_box <=", value, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxIn(List<Integer> values) {
            addCriterion("out_library_task.total_box in", values, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotIn(List<Integer> values) {
            addCriterion("out_library_task.total_box not in", values, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_box between", value1, value2, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalBoxNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_box not between", value1, value2, "totalBox");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIsNull() {
            addCriterion("out_library_task.total_money is null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIsNotNull() {
            addCriterion("out_library_task.total_money is not null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyEqualTo(Integer value) {
            addCriterion("out_library_task.total_money =", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotEqualTo(Integer value) {
            addCriterion("out_library_task.total_money <>", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThan(Integer value) {
            addCriterion("out_library_task.total_money >", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_money >=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThan(Integer value) {
            addCriterion("out_library_task.total_money <", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.total_money <=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIn(List<Integer> values) {
            addCriterion("out_library_task.total_money in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotIn(List<Integer> values) {
            addCriterion("out_library_task.total_money not in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_money between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.total_money not between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("out_library_task.publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("out_library_task.publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(Integer value) {
            addCriterion("out_library_task.publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(Integer value) {
            addCriterion("out_library_task.publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(Integer value) {
            addCriterion("out_library_task.publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(Integer value) {
            addCriterion("out_library_task.publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<Integer> values) {
            addCriterion("out_library_task.publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<Integer> values) {
            addCriterion("out_library_task.publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("out_library_task.create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("out_library_task.create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("out_library_task.create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("out_library_task.create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("out_library_task.create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("out_library_task.create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("out_library_task.create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("out_library_task.create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("out_library_task.create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("out_library_task.create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("out_library_task.create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
            addCriterion("out_library_task.create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNull() {
            addCriterion("out_library_task.apply_user is null");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNotNull() {
            addCriterion("out_library_task.apply_user is not null");
            return (Criteria) this;
        }

        public Criteria andApplyUserEqualTo(Integer value) {
            addCriterion("out_library_task.apply_user =", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotEqualTo(Integer value) {
            addCriterion("out_library_task.apply_user <>", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThan(Integer value) {
            addCriterion("out_library_task.apply_user >", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.apply_user >=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThan(Integer value) {
            addCriterion("out_library_task.apply_user <", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.apply_user <=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserIn(List<Integer> values) {
            addCriterion("out_library_task.apply_user in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotIn(List<Integer> values) {
            addCriterion("out_library_task.apply_user not in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.apply_user between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.apply_user not between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyStateIsNull() {
            addCriterion("out_library_task.apply_state is null");
            return (Criteria) this;
        }

        public Criteria andApplyStateIsNotNull() {
            addCriterion("out_library_task.apply_state is not null");
            return (Criteria) this;
        }

        public Criteria andApplyStateEqualTo(Integer value) {
            addCriterion("out_library_task.apply_state =", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateNotEqualTo(Integer value) {
            addCriterion("out_library_task.apply_state <>", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateGreaterThan(Integer value) {
            addCriterion("out_library_task.apply_state >", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.apply_state >=", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateLessThan(Integer value) {
            addCriterion("out_library_task.apply_state <", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.apply_state <=", value, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateIn(List<Integer> values) {
            addCriterion("out_library_task.apply_state in", values, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateNotIn(List<Integer> values) {
            addCriterion("out_library_task.apply_state not in", values, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.apply_state between", value1, value2, "applyState");
            return (Criteria) this;
        }

        public Criteria andApplyStateNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.apply_state not between", value1, value2, "applyState");
            return (Criteria) this;
        }

        public Criteria andCheckStateIsNull() {
            addCriterion("out_library_task.check_state is null");
            return (Criteria) this;
        }

        public Criteria andCheckStateIsNotNull() {
            addCriterion("out_library_task.check_state is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStateEqualTo(Integer value) {
            addCriterion("out_library_task.check_state =", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotEqualTo(Integer value) {
            addCriterion("out_library_task.check_state <>", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateGreaterThan(Integer value) {
            addCriterion("out_library_task.check_state >", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.check_state >=", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateLessThan(Integer value) {
            addCriterion("out_library_task.check_state <", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.check_state <=", value, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateIn(List<Integer> values) {
            addCriterion("out_library_task.check_state in", values, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotIn(List<Integer> values) {
            addCriterion("out_library_task.check_state not in", values, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.check_state between", value1, value2, "checkState");
            return (Criteria) this;
        }

        public Criteria andCheckStateNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.check_state not between", value1, value2, "checkState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIsNull() {
            addCriterion("out_library_task.boxing_state is null");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIsNotNull() {
            addCriterion("out_library_task.boxing_state is not null");
            return (Criteria) this;
        }

        public Criteria andBoxingStateEqualTo(Integer value) {
            addCriterion("out_library_task.boxing_state =", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotEqualTo(Integer value) {
            addCriterion("out_library_task.boxing_state <>", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateGreaterThan(Integer value) {
            addCriterion("out_library_task.boxing_state >", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.boxing_state >=", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateLessThan(Integer value) {
            addCriterion("out_library_task.boxing_state <", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.boxing_state <=", value, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateIn(List<Integer> values) {
            addCriterion("out_library_task.boxing_state in", values, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotIn(List<Integer> values) {
            addCriterion("out_library_task.boxing_state not in", values, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.boxing_state between", value1, value2, "boxingState");
            return (Criteria) this;
        }

        public Criteria andBoxingStateNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.boxing_state not between", value1, value2, "boxingState");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNull() {
            addCriterion("out_library_task.task_state is null");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNotNull() {
            addCriterion("out_library_task.task_state is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStateEqualTo(Integer value) {
            addCriterion("out_library_task.task_state =", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotEqualTo(Integer value) {
            addCriterion("out_library_task.task_state <>", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThan(Integer value) {
            addCriterion("out_library_task.task_state >", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.task_state >=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThan(Integer value) {
            addCriterion("out_library_task.task_state <", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.task_state <=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateIn(List<Integer> values) {
            addCriterion("out_library_task.task_state in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotIn(List<Integer> values) {
            addCriterion("out_library_task.task_state not in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.task_state between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.task_state not between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIsNull() {
            addCriterion("out_library_task.task_schedule is null");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIsNotNull() {
            addCriterion("out_library_task.task_schedule is not null");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleEqualTo(Integer value) {
            addCriterion("out_library_task.task_schedule =", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotEqualTo(Integer value) {
            addCriterion("out_library_task.task_schedule <>", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleGreaterThan(Integer value) {
            addCriterion("out_library_task.task_schedule >", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.task_schedule >=", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleLessThan(Integer value) {
            addCriterion("out_library_task.task_schedule <", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.task_schedule <=", value, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleIn(List<Integer> values) {
            addCriterion("out_library_task.task_schedule in", values, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotIn(List<Integer> values) {
            addCriterion("out_library_task.task_schedule not in", values, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.task_schedule between", value1, value2, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andTaskScheduleNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.task_schedule not between", value1, value2, "taskSchedule");
            return (Criteria) this;
        }

        public Criteria andBatchIsNull() {
            addCriterion("out_library_task.batch is null");
            return (Criteria) this;
        }

        public Criteria andBatchIsNotNull() {
            addCriterion("out_library_task.batch is not null");
            return (Criteria) this;
        }

        public Criteria andBatchEqualTo(Integer value) {
            addCriterion("out_library_task.batch =", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotEqualTo(Integer value) {
            addCriterion("out_library_task.batch <>", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThan(Integer value) {
            addCriterion("out_library_task.batch >", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.batch >=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThan(Integer value) {
            addCriterion("out_library_task.batch <", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.batch <=", value, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchIn(List<Integer> values) {
            addCriterion("out_library_task.batch in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotIn(List<Integer> values) {
            addCriterion("out_library_task.batch not in", values, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.batch between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andBatchNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.batch not between", value1, value2, "batch");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIsNull() {
            addCriterion("out_library_task.library_id is null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIsNotNull() {
            addCriterion("out_library_task.library_id is not null");
            return (Criteria) this;
        }

        public Criteria andLibraryIdEqualTo(Integer value) {
            addCriterion("out_library_task.library_id =", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotEqualTo(Integer value) {
            addCriterion("out_library_task.library_id <>", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThan(Integer value) {
            addCriterion("out_library_task.library_id >", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.library_id >=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThan(Integer value) {
            addCriterion("out_library_task.library_id <", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_library_task.library_id <=", value, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdIn(List<Integer> values) {
            addCriterion("out_library_task.library_id in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotIn(List<Integer> values) {
            addCriterion("out_library_task.library_id not in", values, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.library_id between", value1, value2, "libraryId");
            return (Criteria) this;
        }

        public Criteria andLibraryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_library_task.library_id not between", value1, value2, "libraryId");
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