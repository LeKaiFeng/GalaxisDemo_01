package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.util.ArrayList;
import java.util.List;

public class GaLocationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public GaLocationExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
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

        public Criteria andLocationIsNull() {
            addCriterion("`location` is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("`location` is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(Integer value) {
            addCriterion("`location` =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(Integer value) {
            addCriterion("`location` <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(Integer value) {
            addCriterion("`location` >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(Integer value) {
            addCriterion("`location` >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(Integer value) {
            addCriterion("`location` <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(Integer value) {
            addCriterion("`location` <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<Integer> values) {
            addCriterion("`location` in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<Integer> values) {
            addCriterion("`location` not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(Integer value1, Integer value2) {
            addCriterion("`location` between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(Integer value1, Integer value2) {
            addCriterion("`location` not between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("`level` is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("`level` is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("`level` =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("`level` <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("`level` >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("`level` >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("`level` <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("`level` <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("`level` in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("`level` not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("`level` between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("`level` not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andPosIsNull() {
            addCriterion("pos is null");
            return (Criteria) this;
        }

        public Criteria andPosIsNotNull() {
            addCriterion("pos is not null");
            return (Criteria) this;
        }

        public Criteria andPosEqualTo(Integer value) {
            addCriterion("pos =", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotEqualTo(Integer value) {
            addCriterion("pos <>", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosGreaterThan(Integer value) {
            addCriterion("pos >", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("pos >=", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosLessThan(Integer value) {
            addCriterion("pos <", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosLessThanOrEqualTo(Integer value) {
            addCriterion("pos <=", value, "pos");
            return (Criteria) this;
        }

        public Criteria andPosIn(List<Integer> values) {
            addCriterion("pos in", values, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotIn(List<Integer> values) {
            addCriterion("pos not in", values, "pos");
            return (Criteria) this;
        }

        public Criteria andPosBetween(Integer value1, Integer value2) {
            addCriterion("pos between", value1, value2, "pos");
            return (Criteria) this;
        }

        public Criteria andPosNotBetween(Integer value1, Integer value2) {
            addCriterion("pos not between", value1, value2, "pos");
            return (Criteria) this;
        }

        public Criteria andAisleIsNull() {
            addCriterion("aisle is null");
            return (Criteria) this;
        }

        public Criteria andAisleIsNotNull() {
            addCriterion("aisle is not null");
            return (Criteria) this;
        }

        public Criteria andAisleEqualTo(Integer value) {
            addCriterion("aisle =", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotEqualTo(Integer value) {
            addCriterion("aisle <>", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleGreaterThan(Integer value) {
            addCriterion("aisle >", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleGreaterThanOrEqualTo(Integer value) {
            addCriterion("aisle >=", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleLessThan(Integer value) {
            addCriterion("aisle <", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleLessThanOrEqualTo(Integer value) {
            addCriterion("aisle <=", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleIn(List<Integer> values) {
            addCriterion("aisle in", values, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotIn(List<Integer> values) {
            addCriterion("aisle not in", values, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleBetween(Integer value1, Integer value2) {
            addCriterion("aisle between", value1, value2, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotBetween(Integer value1, Integer value2) {
            addCriterion("aisle not between", value1, value2, "aisle");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`state` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`state` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("`state` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("`state` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("`state` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("`state` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("`state` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("`state` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("`state` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("`state` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("`state` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("`state` not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andBoxNumberIsNull() {
            addCriterion("box_number is null");
            return (Criteria) this;
        }

        public Criteria andBoxNumberIsNotNull() {
            addCriterion("box_number is not null");
            return (Criteria) this;
        }

        public Criteria andBoxNumberEqualTo(String value) {
            addCriterion("box_number =", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberNotEqualTo(String value) {
            addCriterion("box_number <>", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberGreaterThan(String value) {
            addCriterion("box_number >", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberGreaterThanOrEqualTo(String value) {
            addCriterion("box_number >=", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberLessThan(String value) {
            addCriterion("box_number <", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberLessThanOrEqualTo(String value) {
            addCriterion("box_number <=", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberLike(String value) {
            addCriterion("box_number like", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberNotLike(String value) {
            addCriterion("box_number not like", value, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberIn(List<String> values) {
            addCriterion("box_number in", values, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberNotIn(List<String> values) {
            addCriterion("box_number not in", values, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberBetween(String value1, String value2) {
            addCriterion("box_number between", value1, value2, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andBoxNumberNotBetween(String value1, String value2) {
            addCriterion("box_number not between", value1, value2, "boxNumber");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(Integer value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(Integer value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(Integer value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(Integer value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(Integer value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<Integer> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<Integer> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(Integer value1, Integer value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(Integer value1, Integer value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andLiftAreaIsNull() {
            addCriterion("lift_area is null");
            return (Criteria) this;
        }

        public Criteria andLiftAreaIsNotNull() {
            addCriterion("lift_area is not null");
            return (Criteria) this;
        }

        public Criteria andLiftAreaEqualTo(String value) {
            addCriterion("lift_area =", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaNotEqualTo(String value) {
            addCriterion("lift_area <>", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaGreaterThan(String value) {
            addCriterion("lift_area >", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaGreaterThanOrEqualTo(String value) {
            addCriterion("lift_area >=", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaLessThan(String value) {
            addCriterion("lift_area <", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaLessThanOrEqualTo(String value) {
            addCriterion("lift_area <=", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaLike(String value) {
            addCriterion("lift_area like", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaNotLike(String value) {
            addCriterion("lift_area not like", value, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaIn(List<String> values) {
            addCriterion("lift_area in", values, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaNotIn(List<String> values) {
            addCriterion("lift_area not in", values, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaBetween(String value1, String value2) {
            addCriterion("lift_area between", value1, value2, "liftArea");
            return (Criteria) this;
        }

        public Criteria andLiftAreaNotBetween(String value1, String value2) {
            addCriterion("lift_area not between", value1, value2, "liftArea");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andColorAreaIsNull() {
            addCriterion("color_area is null");
            return (Criteria) this;
        }

        public Criteria andColorAreaIsNotNull() {
            addCriterion("color_area is not null");
            return (Criteria) this;
        }

        public Criteria andColorAreaEqualTo(String value) {
            addCriterion("color_area =", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaNotEqualTo(String value) {
            addCriterion("color_area <>", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaGreaterThan(String value) {
            addCriterion("color_area >", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaGreaterThanOrEqualTo(String value) {
            addCriterion("color_area >=", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaLessThan(String value) {
            addCriterion("color_area <", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaLessThanOrEqualTo(String value) {
            addCriterion("color_area <=", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaLike(String value) {
            addCriterion("color_area like", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaNotLike(String value) {
            addCriterion("color_area not like", value, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaIn(List<String> values) {
            addCriterion("color_area in", values, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaNotIn(List<String> values) {
            addCriterion("color_area not in", values, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaBetween(String value1, String value2) {
            addCriterion("color_area between", value1, value2, "colorArea");
            return (Criteria) this;
        }

        public Criteria andColorAreaNotBetween(String value1, String value2) {
            addCriterion("color_area not between", value1, value2, "colorArea");
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
    }

    /**
     */
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