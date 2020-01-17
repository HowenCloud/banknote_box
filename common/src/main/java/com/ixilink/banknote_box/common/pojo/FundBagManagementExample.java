package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class FundBagManagementExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public FundBagManagementExample() {
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

        public Criteria andInstallationTimeIsNull() {
            addCriterion("installation_time is null");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeIsNotNull() {
            addCriterion("installation_time is not null");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeEqualTo(Long value) {
            addCriterion("installation_time =", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeNotEqualTo(Long value) {
            addCriterion("installation_time <>", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeGreaterThan(Long value) {
            addCriterion("installation_time >", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("installation_time >=", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeLessThan(Long value) {
            addCriterion("installation_time <", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeLessThanOrEqualTo(Long value) {
            addCriterion("installation_time <=", value, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeIn(List<Long> values) {
            addCriterion("installation_time in", values, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeNotIn(List<Long> values) {
            addCriterion("installation_time not in", values, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeBetween(Long value1, Long value2) {
            addCriterion("installation_time between", value1, value2, "installationTime");
            return (Criteria) this;
        }

        public Criteria andInstallationTimeNotBetween(Long value1, Long value2) {
            addCriterion("installation_time not between", value1, value2, "installationTime");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountIsNull() {
            addCriterion("bagging_amount is null");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountIsNotNull() {
            addCriterion("bagging_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountEqualTo(Integer value) {
            addCriterion("bagging_amount =", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountNotEqualTo(Integer value) {
            addCriterion("bagging_amount <>", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountGreaterThan(Integer value) {
            addCriterion("bagging_amount >", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bagging_amount >=", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountLessThan(Integer value) {
            addCriterion("bagging_amount <", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountLessThanOrEqualTo(Integer value) {
            addCriterion("bagging_amount <=", value, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountIn(List<Integer> values) {
            addCriterion("bagging_amount in", values, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountNotIn(List<Integer> values) {
            addCriterion("bagging_amount not in", values, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountBetween(Integer value1, Integer value2) {
            addCriterion("bagging_amount between", value1, value2, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("bagging_amount not between", value1, value2, "baggingAmount");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIsNull() {
            addCriterion("bagging_personnel is null");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIsNotNull() {
            addCriterion("bagging_personnel is not null");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelEqualTo(String value) {
            addCriterion("bagging_personnel =", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelNotEqualTo(String value) {
            addCriterion("bagging_personnel <>", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelGreaterThan(String value) {
            addCriterion("bagging_personnel >", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelGreaterThanOrEqualTo(String value) {
            addCriterion("bagging_personnel >=", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelLessThan(String value) {
            addCriterion("bagging_personnel <", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelLessThanOrEqualTo(String value) {
            addCriterion("bagging_personnel <=", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelLike(String value) {
            addCriterion("bagging_personnel like", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelNotLike(String value) {
            addCriterion("bagging_personnel not like", value, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIn(List<String> values) {
            addCriterion("bagging_personnel in", values, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelNotIn(List<String> values) {
            addCriterion("bagging_personnel not in", values, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelBetween(String value1, String value2) {
            addCriterion("bagging_personnel between", value1, value2, "baggingPersonnel");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelNotBetween(String value1, String value2) {
            addCriterion("bagging_personnel not between", value1, value2, "baggingPersonnel");
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

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andFundBagNameIsNull() {
            addCriterion("fund_bag_name is null");
            return (Criteria) this;
        }

        public Criteria andFundBagNameIsNotNull() {
            addCriterion("fund_bag_name is not null");
            return (Criteria) this;
        }

        public Criteria andFundBagNameEqualTo(String value) {
            addCriterion("fund_bag_name =", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameNotEqualTo(String value) {
            addCriterion("fund_bag_name <>", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameGreaterThan(String value) {
            addCriterion("fund_bag_name >", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameGreaterThanOrEqualTo(String value) {
            addCriterion("fund_bag_name >=", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameLessThan(String value) {
            addCriterion("fund_bag_name <", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameLessThanOrEqualTo(String value) {
            addCriterion("fund_bag_name <=", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameLike(String value) {
            addCriterion("fund_bag_name like", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameNotLike(String value) {
            addCriterion("fund_bag_name not like", value, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameIn(List<String> values) {
            addCriterion("fund_bag_name in", values, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameNotIn(List<String> values) {
            addCriterion("fund_bag_name not in", values, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameBetween(String value1, String value2) {
            addCriterion("fund_bag_name between", value1, value2, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andFundBagNameNotBetween(String value1, String value2) {
            addCriterion("fund_bag_name not between", value1, value2, "fundBagName");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllIsNull() {
            addCriterion("bagging_amount_all is null");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllIsNotNull() {
            addCriterion("bagging_amount_all is not null");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllEqualTo(Integer value) {
            addCriterion("bagging_amount_all =", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllNotEqualTo(Integer value) {
            addCriterion("bagging_amount_all <>", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllGreaterThan(Integer value) {
            addCriterion("bagging_amount_all >", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllGreaterThanOrEqualTo(Integer value) {
            addCriterion("bagging_amount_all >=", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllLessThan(Integer value) {
            addCriterion("bagging_amount_all <", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllLessThanOrEqualTo(Integer value) {
            addCriterion("bagging_amount_all <=", value, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllIn(List<Integer> values) {
            addCriterion("bagging_amount_all in", values, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllNotIn(List<Integer> values) {
            addCriterion("bagging_amount_all not in", values, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllBetween(Integer value1, Integer value2) {
            addCriterion("bagging_amount_all between", value1, value2, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingAmountAllNotBetween(Integer value1, Integer value2) {
            addCriterion("bagging_amount_all not between", value1, value2, "baggingAmountAll");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberIsNull() {
            addCriterion("bagging_lock_bar_number is null");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberIsNotNull() {
            addCriterion("bagging_lock_bar_number is not null");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberEqualTo(String value) {
            addCriterion("bagging_lock_bar_number =", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberNotEqualTo(String value) {
            addCriterion("bagging_lock_bar_number <>", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberGreaterThan(String value) {
            addCriterion("bagging_lock_bar_number >", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberGreaterThanOrEqualTo(String value) {
            addCriterion("bagging_lock_bar_number >=", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberLessThan(String value) {
            addCriterion("bagging_lock_bar_number <", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberLessThanOrEqualTo(String value) {
            addCriterion("bagging_lock_bar_number <=", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberLike(String value) {
            addCriterion("bagging_lock_bar_number like", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberNotLike(String value) {
            addCriterion("bagging_lock_bar_number not like", value, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberIn(List<String> values) {
            addCriterion("bagging_lock_bar_number in", values, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberNotIn(List<String> values) {
            addCriterion("bagging_lock_bar_number not in", values, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberBetween(String value1, String value2) {
            addCriterion("bagging_lock_bar_number between", value1, value2, "baggingLockBarNumber");
            return (Criteria) this;
        }

        public Criteria andBaggingLockBarNumberNotBetween(String value1, String value2) {
            addCriterion("bagging_lock_bar_number not between", value1, value2, "baggingLockBarNumber");
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

        public Criteria andBaggingPersonnelIdIsNull() {
            addCriterion("bagging_personnel_id is null");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdIsNotNull() {
            addCriterion("bagging_personnel_id is not null");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdEqualTo(String value) {
            addCriterion("bagging_personnel_id =", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdNotEqualTo(String value) {
            addCriterion("bagging_personnel_id <>", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdGreaterThan(String value) {
            addCriterion("bagging_personnel_id >", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdGreaterThanOrEqualTo(String value) {
            addCriterion("bagging_personnel_id >=", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdLessThan(String value) {
            addCriterion("bagging_personnel_id <", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdLessThanOrEqualTo(String value) {
            addCriterion("bagging_personnel_id <=", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdLike(String value) {
            addCriterion("bagging_personnel_id like", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdNotLike(String value) {
            addCriterion("bagging_personnel_id not like", value, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdIn(List<String> values) {
            addCriterion("bagging_personnel_id in", values, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdNotIn(List<String> values) {
            addCriterion("bagging_personnel_id not in", values, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdBetween(String value1, String value2) {
            addCriterion("bagging_personnel_id between", value1, value2, "baggingPersonnelId");
            return (Criteria) this;
        }

        public Criteria andBaggingPersonnelIdNotBetween(String value1, String value2) {
            addCriterion("bagging_personnel_id not between", value1, value2, "baggingPersonnelId");
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