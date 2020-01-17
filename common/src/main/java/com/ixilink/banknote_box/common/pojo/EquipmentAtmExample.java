package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAtmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int count = -1;

    public EquipmentAtmExample() {
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

        public Criteria andControllerIdIsNull() {
            addCriterion("controller_id is null");
            return (Criteria) this;
        }

        public Criteria andControllerIdIsNotNull() {
            addCriterion("controller_id is not null");
            return (Criteria) this;
        }

        public Criteria andControllerIdEqualTo(Integer value) {
            addCriterion("controller_id =", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdNotEqualTo(Integer value) {
            addCriterion("controller_id <>", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdGreaterThan(Integer value) {
            addCriterion("controller_id >", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("controller_id >=", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdLessThan(Integer value) {
            addCriterion("controller_id <", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdLessThanOrEqualTo(Integer value) {
            addCriterion("controller_id <=", value, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdIn(List<Integer> values) {
            addCriterion("controller_id in", values, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdNotIn(List<Integer> values) {
            addCriterion("controller_id not in", values, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdBetween(Integer value1, Integer value2) {
            addCriterion("controller_id between", value1, value2, "controllerId");
            return (Criteria) this;
        }

        public Criteria andControllerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("controller_id not between", value1, value2, "controllerId");
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

        public Criteria andNumberEqualTo(String value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLike(String value) {
            addCriterion("number like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotLike(String value) {
            addCriterion("number not like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<String> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<String> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(String value1, String value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(String value1, String value2) {
            addCriterion("number not between", value1, value2, "number");
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

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andPassagewayIsNull() {
            addCriterion("passageway is null");
            return (Criteria) this;
        }

        public Criteria andPassagewayIsNotNull() {
            addCriterion("passageway is not null");
            return (Criteria) this;
        }

        public Criteria andPassagewayEqualTo(Integer value) {
            addCriterion("passageway =", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotEqualTo(Integer value) {
            addCriterion("passageway <>", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayGreaterThan(Integer value) {
            addCriterion("passageway >", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayGreaterThanOrEqualTo(Integer value) {
            addCriterion("passageway >=", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayLessThan(Integer value) {
            addCriterion("passageway <", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayLessThanOrEqualTo(Integer value) {
            addCriterion("passageway <=", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayIn(List<Integer> values) {
            addCriterion("passageway in", values, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotIn(List<Integer> values) {
            addCriterion("passageway not in", values, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayBetween(Integer value1, Integer value2) {
            addCriterion("passageway between", value1, value2, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotBetween(Integer value1, Integer value2) {
            addCriterion("passageway not between", value1, value2, "passageway");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIsNull() {
            addCriterion("box_type is null");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIsNotNull() {
            addCriterion("box_type is not null");
            return (Criteria) this;
        }

        public Criteria andBoxTypeEqualTo(Integer value) {
            addCriterion("box_type =", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotEqualTo(Integer value) {
            addCriterion("box_type <>", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeGreaterThan(Integer value) {
            addCriterion("box_type >", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("box_type >=", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLessThan(Integer value) {
            addCriterion("box_type <", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLessThanOrEqualTo(Integer value) {
            addCriterion("box_type <=", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeLike(Integer value) {
            addCriterion("box_type like", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotLike(Integer value) {
            addCriterion("box_type not like", value, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeIn(List<Integer> values) {
            addCriterion("box_type in", values, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotIn(List<Integer> values) {
            addCriterion("box_type not in", values, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeBetween(Integer value1, Integer value2) {
            addCriterion("box_type between", value1, value2, "boxType");
            return (Criteria) this;
        }

        public Criteria andBoxTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("box_type not between", value1, value2, "boxType");
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

        public Criteria andBox1IsNull() {
            addCriterion("box1 is null");
            return (Criteria) this;
        }

        public Criteria andBox1IsNotNull() {
            addCriterion("box1 is not null");
            return (Criteria) this;
        }

        public Criteria andBox1EqualTo(Integer value) {
            addCriterion("box1 =", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1NotEqualTo(Integer value) {
            addCriterion("box1 <>", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1GreaterThan(Integer value) {
            addCriterion("box1 >", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1GreaterThanOrEqualTo(Integer value) {
            addCriterion("box1 >=", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1LessThan(Integer value) {
            addCriterion("box1 <", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1LessThanOrEqualTo(Integer value) {
            addCriterion("box1 <=", value, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1In(List<Integer> values) {
            addCriterion("box1 in", values, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1NotIn(List<Integer> values) {
            addCriterion("box1 not in", values, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1Between(Integer value1, Integer value2) {
            addCriterion("box1 between", value1, value2, "box1");
            return (Criteria) this;
        }

        public Criteria andBox1NotBetween(Integer value1, Integer value2) {
            addCriterion("box1 not between", value1, value2, "box1");
            return (Criteria) this;
        }

        public Criteria andBox2IsNull() {
            addCriterion("box2 is null");
            return (Criteria) this;
        }

        public Criteria andBox2IsNotNull() {
            addCriterion("box2 is not null");
            return (Criteria) this;
        }

        public Criteria andBox2EqualTo(Integer value) {
            addCriterion("box2 =", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2NotEqualTo(Integer value) {
            addCriterion("box2 <>", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2GreaterThan(Integer value) {
            addCriterion("box2 >", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2GreaterThanOrEqualTo(Integer value) {
            addCriterion("box2 >=", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2LessThan(Integer value) {
            addCriterion("box2 <", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2LessThanOrEqualTo(Integer value) {
            addCriterion("box2 <=", value, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2In(List<Integer> values) {
            addCriterion("box2 in", values, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2NotIn(List<Integer> values) {
            addCriterion("box2 not in", values, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2Between(Integer value1, Integer value2) {
            addCriterion("box2 between", value1, value2, "box2");
            return (Criteria) this;
        }

        public Criteria andBox2NotBetween(Integer value1, Integer value2) {
            addCriterion("box2 not between", value1, value2, "box2");
            return (Criteria) this;
        }

        public Criteria andBox3IsNull() {
            addCriterion("box3 is null");
            return (Criteria) this;
        }

        public Criteria andBox3IsNotNull() {
            addCriterion("box3 is not null");
            return (Criteria) this;
        }

        public Criteria andBox3EqualTo(Integer value) {
            addCriterion("box3 =", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3NotEqualTo(Integer value) {
            addCriterion("box3 <>", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3GreaterThan(Integer value) {
            addCriterion("box3 >", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3GreaterThanOrEqualTo(Integer value) {
            addCriterion("box3 >=", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3LessThan(Integer value) {
            addCriterion("box3 <", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3LessThanOrEqualTo(Integer value) {
            addCriterion("box3 <=", value, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3In(List<Integer> values) {
            addCriterion("box3 in", values, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3NotIn(List<Integer> values) {
            addCriterion("box3 not in", values, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3Between(Integer value1, Integer value2) {
            addCriterion("box3 between", value1, value2, "box3");
            return (Criteria) this;
        }

        public Criteria andBox3NotBetween(Integer value1, Integer value2) {
            addCriterion("box3 not between", value1, value2, "box3");
            return (Criteria) this;
        }

        public Criteria andBox4IsNull() {
            addCriterion("box4 is null");
            return (Criteria) this;
        }

        public Criteria andBox4IsNotNull() {
            addCriterion("box4 is not null");
            return (Criteria) this;
        }

        public Criteria andBox4EqualTo(Integer value) {
            addCriterion("box4 =", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4NotEqualTo(Integer value) {
            addCriterion("box4 <>", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4GreaterThan(Integer value) {
            addCriterion("box4 >", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4GreaterThanOrEqualTo(Integer value) {
            addCriterion("box4 >=", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4LessThan(Integer value) {
            addCriterion("box4 <", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4LessThanOrEqualTo(Integer value) {
            addCriterion("box4 <=", value, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4In(List<Integer> values) {
            addCriterion("box4 in", values, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4NotIn(List<Integer> values) {
            addCriterion("box4 not in", values, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4Between(Integer value1, Integer value2) {
            addCriterion("box4 between", value1, value2, "box4");
            return (Criteria) this;
        }

        public Criteria andBox4NotBetween(Integer value1, Integer value2) {
            addCriterion("box4 not between", value1, value2, "box4");
            return (Criteria) this;
        }

        public Criteria andBox5IsNull() {
            addCriterion("box5 is null");
            return (Criteria) this;
        }

        public Criteria andBox5IsNotNull() {
            addCriterion("box5 is not null");
            return (Criteria) this;
        }

        public Criteria andBox5EqualTo(Integer value) {
            addCriterion("box5 =", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5NotEqualTo(Integer value) {
            addCriterion("box5 <>", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5GreaterThan(Integer value) {
            addCriterion("box5 >", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5GreaterThanOrEqualTo(Integer value) {
            addCriterion("box5 >=", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5LessThan(Integer value) {
            addCriterion("box5 <", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5LessThanOrEqualTo(Integer value) {
            addCriterion("box5 <=", value, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5In(List<Integer> values) {
            addCriterion("box5 in", values, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5NotIn(List<Integer> values) {
            addCriterion("box5 not in", values, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5Between(Integer value1, Integer value2) {
            addCriterion("box5 between", value1, value2, "box5");
            return (Criteria) this;
        }

        public Criteria andBox5NotBetween(Integer value1, Integer value2) {
            addCriterion("box5 not between", value1, value2, "box5");
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