package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class OutLibraryLineAtmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public OutLibraryLineAtmExample() {
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

        public Criteria andLineIdIsNull() {
            addCriterion("line_id is null");
            return (Criteria) this;
        }

        public Criteria andLineIdIsNotNull() {
            addCriterion("line_id is not null");
            return (Criteria) this;
        }

        public Criteria andLineIdEqualTo(Integer value) {
            addCriterion("line_id =", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotEqualTo(Integer value) {
            addCriterion("line_id <>", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdGreaterThan(Integer value) {
            addCriterion("line_id >", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_id >=", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdLessThan(Integer value) {
            addCriterion("line_id <", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdLessThanOrEqualTo(Integer value) {
            addCriterion("line_id <=", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdIn(List<Integer> values) {
            addCriterion("line_id in", values, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotIn(List<Integer> values) {
            addCriterion("line_id not in", values, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdBetween(Integer value1, Integer value2) {
            addCriterion("line_id between", value1, value2, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("line_id not between", value1, value2, "lineId");
            return (Criteria) this;
        }

        public Criteria andAtmIdIsNull() {
            addCriterion("atm_id is null");
            return (Criteria) this;
        }

        public Criteria andAtmIdIsNotNull() {
            addCriterion("atm_id is not null");
            return (Criteria) this;
        }

        public Criteria andAtmIdEqualTo(Integer value) {
            addCriterion("atm_id =", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdNotEqualTo(Integer value) {
            addCriterion("atm_id <>", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdGreaterThan(Integer value) {
            addCriterion("atm_id >", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("atm_id >=", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdLessThan(Integer value) {
            addCriterion("atm_id <", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdLessThanOrEqualTo(Integer value) {
            addCriterion("atm_id <=", value, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdIn(List<Integer> values) {
            addCriterion("atm_id in", values, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdNotIn(List<Integer> values) {
            addCriterion("atm_id not in", values, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdBetween(Integer value1, Integer value2) {
            addCriterion("atm_id between", value1, value2, "atmId");
            return (Criteria) this;
        }

        public Criteria andAtmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("atm_id not between", value1, value2, "atmId");
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

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(Integer value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(Integer value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(Integer value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(Integer value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(Integer value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<Integer> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<Integer> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(Integer value1, Integer value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andScheduleIsNull() {
            addCriterion("schedule is null");
            return (Criteria) this;
        }

        public Criteria andScheduleIsNotNull() {
            addCriterion("schedule is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleEqualTo(Integer value) {
            addCriterion("schedule =", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleNotEqualTo(Integer value) {
            addCriterion("schedule <>", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleGreaterThan(Integer value) {
            addCriterion("schedule >", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleGreaterThanOrEqualTo(Integer value) {
            addCriterion("schedule >=", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleLessThan(Integer value) {
            addCriterion("schedule <", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleLessThanOrEqualTo(Integer value) {
            addCriterion("schedule <=", value, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleIn(List<Integer> values) {
            addCriterion("schedule in", values, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleNotIn(List<Integer> values) {
            addCriterion("schedule not in", values, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleBetween(Integer value1, Integer value2) {
            addCriterion("schedule between", value1, value2, "schedule");
            return (Criteria) this;
        }

        public Criteria andScheduleNotBetween(Integer value1, Integer value2) {
            addCriterion("schedule not between", value1, value2, "schedule");
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

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
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

        public Criteria andSupplementBoxIsNull() {
            addCriterion("supplement_box is null");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxIsNotNull() {
            addCriterion("supplement_box is not null");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxEqualTo(String value) {
            addCriterion("supplement_box =", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxNotEqualTo(String value) {
            addCriterion("supplement_box <>", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxGreaterThan(String value) {
            addCriterion("supplement_box >", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxGreaterThanOrEqualTo(String value) {
            addCriterion("supplement_box >=", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxLessThan(String value) {
            addCriterion("supplement_box <", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxLessThanOrEqualTo(String value) {
            addCriterion("supplement_box <=", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxLike(String value) {
            addCriterion("supplement_box like", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxNotLike(String value) {
            addCriterion("supplement_box not like", value, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxIn(List<String> values) {
            addCriterion("supplement_box in", values, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxNotIn(List<String> values) {
            addCriterion("supplement_box not in", values, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxBetween(String value1, String value2) {
            addCriterion("supplement_box between", value1, value2, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andSupplementBoxNotBetween(String value1, String value2) {
            addCriterion("supplement_box not between", value1, value2, "supplementBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxIsNull() {
            addCriterion("deposit_box is null");
            return (Criteria) this;
        }

        public Criteria andDepositBoxIsNotNull() {
            addCriterion("deposit_box is not null");
            return (Criteria) this;
        }

        public Criteria andDepositBoxEqualTo(String value) {
            addCriterion("deposit_box =", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxNotEqualTo(String value) {
            addCriterion("deposit_box <>", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxGreaterThan(String value) {
            addCriterion("deposit_box >", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxGreaterThanOrEqualTo(String value) {
            addCriterion("deposit_box >=", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxLessThan(String value) {
            addCriterion("deposit_box <", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxLessThanOrEqualTo(String value) {
            addCriterion("deposit_box <=", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxLike(String value) {
            addCriterion("deposit_box like", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxNotLike(String value) {
            addCriterion("deposit_box not like", value, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxIn(List<String> values) {
            addCriterion("deposit_box in", values, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxNotIn(List<String> values) {
            addCriterion("deposit_box not in", values, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxBetween(String value1, String value2) {
            addCriterion("deposit_box between", value1, value2, "depositBox");
            return (Criteria) this;
        }

        public Criteria andDepositBoxNotBetween(String value1, String value2) {
            addCriterion("deposit_box not between", value1, value2, "depositBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxIsNull() {
            addCriterion("take_box is null");
            return (Criteria) this;
        }

        public Criteria andTakeBoxIsNotNull() {
            addCriterion("take_box is not null");
            return (Criteria) this;
        }

        public Criteria andTakeBoxEqualTo(String value) {
            addCriterion("take_box =", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxNotEqualTo(String value) {
            addCriterion("take_box <>", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxGreaterThan(String value) {
            addCriterion("take_box >", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxGreaterThanOrEqualTo(String value) {
            addCriterion("take_box >=", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxLessThan(String value) {
            addCriterion("take_box <", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxLessThanOrEqualTo(String value) {
            addCriterion("take_box <=", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxLike(String value) {
            addCriterion("take_box like", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxNotLike(String value) {
            addCriterion("take_box not like", value, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxIn(List<String> values) {
            addCriterion("take_box in", values, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxNotIn(List<String> values) {
            addCriterion("take_box not in", values, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxBetween(String value1, String value2) {
            addCriterion("take_box between", value1, value2, "takeBox");
            return (Criteria) this;
        }

        public Criteria andTakeBoxNotBetween(String value1, String value2) {
            addCriterion("take_box not between", value1, value2, "takeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxIsNull() {
            addCriterion("supplement_take_box is null");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxIsNotNull() {
            addCriterion("supplement_take_box is not null");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxEqualTo(String value) {
            addCriterion("supplement_take_box =", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxNotEqualTo(String value) {
            addCriterion("supplement_take_box <>", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxGreaterThan(String value) {
            addCriterion("supplement_take_box >", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxGreaterThanOrEqualTo(String value) {
            addCriterion("supplement_take_box >=", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxLessThan(String value) {
            addCriterion("supplement_take_box <", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxLessThanOrEqualTo(String value) {
            addCriterion("supplement_take_box <=", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxLike(String value) {
            addCriterion("supplement_take_box like", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxNotLike(String value) {
            addCriterion("supplement_take_box not like", value, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxIn(List<String> values) {
            addCriterion("supplement_take_box in", values, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxNotIn(List<String> values) {
            addCriterion("supplement_take_box not in", values, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxBetween(String value1, String value2) {
            addCriterion("supplement_take_box between", value1, value2, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andSupplementTakeBoxNotBetween(String value1, String value2) {
            addCriterion("supplement_take_box not between", value1, value2, "supplementTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxIsNull() {
            addCriterion("original_take_box is null");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxIsNotNull() {
            addCriterion("original_take_box is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxEqualTo(String value) {
            addCriterion("original_take_box =", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxNotEqualTo(String value) {
            addCriterion("original_take_box <>", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxGreaterThan(String value) {
            addCriterion("original_take_box >", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxGreaterThanOrEqualTo(String value) {
            addCriterion("original_take_box >=", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxLessThan(String value) {
            addCriterion("original_take_box <", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxLessThanOrEqualTo(String value) {
            addCriterion("original_take_box <=", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxLike(String value) {
            addCriterion("original_take_box like", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxNotLike(String value) {
            addCriterion("original_take_box not like", value, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxIn(List<String> values) {
            addCriterion("original_take_box in", values, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxNotIn(List<String> values) {
            addCriterion("original_take_box not in", values, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxBetween(String value1, String value2) {
            addCriterion("original_take_box between", value1, value2, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andOriginalTakeBoxNotBetween(String value1, String value2) {
            addCriterion("original_take_box not between", value1, value2, "originalTakeBox");
            return (Criteria) this;
        }

        public Criteria andInStateIsNull() {
            addCriterion("in_state is null");
            return (Criteria) this;
        }

        public Criteria andInStateIsNotNull() {
            addCriterion("in_state is not null");
            return (Criteria) this;
        }

        public Criteria andInStateEqualTo(Integer value) {
            addCriterion("in_state =", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateNotEqualTo(Integer value) {
            addCriterion("in_state <>", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateGreaterThan(Integer value) {
            addCriterion("in_state >", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_state >=", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateLessThan(Integer value) {
            addCriterion("in_state <", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateLessThanOrEqualTo(Integer value) {
            addCriterion("in_state <=", value, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateIn(List<Integer> values) {
            addCriterion("in_state in", values, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateNotIn(List<Integer> values) {
            addCriterion("in_state not in", values, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateBetween(Integer value1, Integer value2) {
            addCriterion("in_state between", value1, value2, "inState");
            return (Criteria) this;
        }

        public Criteria andInStateNotBetween(Integer value1, Integer value2) {
            addCriterion("in_state not between", value1, value2, "inState");
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