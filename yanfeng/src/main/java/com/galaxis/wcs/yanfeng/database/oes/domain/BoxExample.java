package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoxExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public BoxExample() {
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

        public Criteria andCartonNoIsNull() {
            addCriterion("carton_no is null");
            return (Criteria) this;
        }

        public Criteria andCartonNoIsNotNull() {
            addCriterion("carton_no is not null");
            return (Criteria) this;
        }

        public Criteria andCartonNoEqualTo(String value) {
            addCriterion("carton_no =", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoNotEqualTo(String value) {
            addCriterion("carton_no <>", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoGreaterThan(String value) {
            addCriterion("carton_no >", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoGreaterThanOrEqualTo(String value) {
            addCriterion("carton_no >=", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoLessThan(String value) {
            addCriterion("carton_no <", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoLessThanOrEqualTo(String value) {
            addCriterion("carton_no <=", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoLike(String value) {
            addCriterion("carton_no like", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoNotLike(String value) {
            addCriterion("carton_no not like", value, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoIn(List<String> values) {
            addCriterion("carton_no in", values, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoNotIn(List<String> values) {
            addCriterion("carton_no not in", values, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoBetween(String value1, String value2) {
            addCriterion("carton_no between", value1, value2, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andCartonNoNotBetween(String value1, String value2) {
            addCriterion("carton_no not between", value1, value2, "cartonNo");
            return (Criteria) this;
        }

        public Criteria andPartNoIsNull() {
            addCriterion("part_no is null");
            return (Criteria) this;
        }

        public Criteria andPartNoIsNotNull() {
            addCriterion("part_no is not null");
            return (Criteria) this;
        }

        public Criteria andPartNoEqualTo(String value) {
            addCriterion("part_no =", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoNotEqualTo(String value) {
            addCriterion("part_no <>", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoGreaterThan(String value) {
            addCriterion("part_no >", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoGreaterThanOrEqualTo(String value) {
            addCriterion("part_no >=", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoLessThan(String value) {
            addCriterion("part_no <", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoLessThanOrEqualTo(String value) {
            addCriterion("part_no <=", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoLike(String value) {
            addCriterion("part_no like", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoNotLike(String value) {
            addCriterion("part_no not like", value, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoIn(List<String> values) {
            addCriterion("part_no in", values, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoNotIn(List<String> values) {
            addCriterion("part_no not in", values, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoBetween(String value1, String value2) {
            addCriterion("part_no between", value1, value2, "partNo");
            return (Criteria) this;
        }

        public Criteria andPartNoNotBetween(String value1, String value2) {
            addCriterion("part_no not between", value1, value2, "partNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
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

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(BigDecimal value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(BigDecimal value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(BigDecimal value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(BigDecimal value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<BigDecimal> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<BigDecimal> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("qty not between", value1, value2, "qty");
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

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andLengthIsNull() {
            addCriterion("`length` is null");
            return (Criteria) this;
        }

        public Criteria andLengthIsNotNull() {
            addCriterion("`length` is not null");
            return (Criteria) this;
        }

        public Criteria andLengthEqualTo(BigDecimal value) {
            addCriterion("`length` =", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotEqualTo(BigDecimal value) {
            addCriterion("`length` <>", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThan(BigDecimal value) {
            addCriterion("`length` >", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("`length` >=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThan(BigDecimal value) {
            addCriterion("`length` <", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("`length` <=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthIn(List<BigDecimal> values) {
            addCriterion("`length` in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotIn(List<BigDecimal> values) {
            addCriterion("`length` not in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`length` between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`length` not between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andBoxPositionIsNull() {
            addCriterion("box_position is null");
            return (Criteria) this;
        }

        public Criteria andBoxPositionIsNotNull() {
            addCriterion("box_position is not null");
            return (Criteria) this;
        }

        public Criteria andBoxPositionEqualTo(Integer value) {
            addCriterion("box_position =", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionNotEqualTo(Integer value) {
            addCriterion("box_position <>", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionGreaterThan(Integer value) {
            addCriterion("box_position >", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionGreaterThanOrEqualTo(Integer value) {
            addCriterion("box_position >=", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionLessThan(Integer value) {
            addCriterion("box_position <", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionLessThanOrEqualTo(Integer value) {
            addCriterion("box_position <=", value, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionIn(List<Integer> values) {
            addCriterion("box_position in", values, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionNotIn(List<Integer> values) {
            addCriterion("box_position not in", values, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionBetween(Integer value1, Integer value2) {
            addCriterion("box_position between", value1, value2, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andBoxPositionNotBetween(Integer value1, Integer value2) {
            addCriterion("box_position not between", value1, value2, "boxPosition");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andNcIsNull() {
            addCriterion("nc is null");
            return (Criteria) this;
        }

        public Criteria andNcIsNotNull() {
            addCriterion("nc is not null");
            return (Criteria) this;
        }

        public Criteria andNcEqualTo(Integer value) {
            addCriterion("nc =", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcNotEqualTo(Integer value) {
            addCriterion("nc <>", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcGreaterThan(Integer value) {
            addCriterion("nc >", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcGreaterThanOrEqualTo(Integer value) {
            addCriterion("nc >=", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcLessThan(Integer value) {
            addCriterion("nc <", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcLessThanOrEqualTo(Integer value) {
            addCriterion("nc <=", value, "nc");
            return (Criteria) this;
        }

        public Criteria andNcIn(List<Integer> values) {
            addCriterion("nc in", values, "nc");
            return (Criteria) this;
        }

        public Criteria andNcNotIn(List<Integer> values) {
            addCriterion("nc not in", values, "nc");
            return (Criteria) this;
        }

        public Criteria andNcBetween(Integer value1, Integer value2) {
            addCriterion("nc between", value1, value2, "nc");
            return (Criteria) this;
        }

        public Criteria andNcNotBetween(Integer value1, Integer value2) {
            addCriterion("nc not between", value1, value2, "nc");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andPlcSeqIsNull() {
            addCriterion("plc_seq is null");
            return (Criteria) this;
        }

        public Criteria andPlcSeqIsNotNull() {
            addCriterion("plc_seq is not null");
            return (Criteria) this;
        }

        public Criteria andPlcSeqEqualTo(Integer value) {
            addCriterion("plc_seq =", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqNotEqualTo(Integer value) {
            addCriterion("plc_seq <>", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqGreaterThan(Integer value) {
            addCriterion("plc_seq >", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("plc_seq >=", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqLessThan(Integer value) {
            addCriterion("plc_seq <", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqLessThanOrEqualTo(Integer value) {
            addCriterion("plc_seq <=", value, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqIn(List<Integer> values) {
            addCriterion("plc_seq in", values, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqNotIn(List<Integer> values) {
            addCriterion("plc_seq not in", values, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqBetween(Integer value1, Integer value2) {
            addCriterion("plc_seq between", value1, value2, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("plc_seq not between", value1, value2, "plcSeq");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdIsNull() {
            addCriterion("plc_task_id is null");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdIsNotNull() {
            addCriterion("plc_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdEqualTo(Integer value) {
            addCriterion("plc_task_id =", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdNotEqualTo(Integer value) {
            addCriterion("plc_task_id <>", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdGreaterThan(Integer value) {
            addCriterion("plc_task_id >", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plc_task_id >=", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdLessThan(Integer value) {
            addCriterion("plc_task_id <", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("plc_task_id <=", value, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdIn(List<Integer> values) {
            addCriterion("plc_task_id in", values, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdNotIn(List<Integer> values) {
            addCriterion("plc_task_id not in", values, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("plc_task_id between", value1, value2, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andPlcTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plc_task_id not between", value1, value2, "plcTaskId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andProdLotIsNull() {
            addCriterion("prod_lot is null");
            return (Criteria) this;
        }

        public Criteria andProdLotIsNotNull() {
            addCriterion("prod_lot is not null");
            return (Criteria) this;
        }

        public Criteria andProdLotEqualTo(String value) {
            addCriterion("prod_lot =", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotNotEqualTo(String value) {
            addCriterion("prod_lot <>", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotGreaterThan(String value) {
            addCriterion("prod_lot >", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotGreaterThanOrEqualTo(String value) {
            addCriterion("prod_lot >=", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotLessThan(String value) {
            addCriterion("prod_lot <", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotLessThanOrEqualTo(String value) {
            addCriterion("prod_lot <=", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotLike(String value) {
            addCriterion("prod_lot like", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotNotLike(String value) {
            addCriterion("prod_lot not like", value, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotIn(List<String> values) {
            addCriterion("prod_lot in", values, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotNotIn(List<String> values) {
            addCriterion("prod_lot not in", values, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotBetween(String value1, String value2) {
            addCriterion("prod_lot between", value1, value2, "prodLot");
            return (Criteria) this;
        }

        public Criteria andProdLotNotBetween(String value1, String value2) {
            addCriterion("prod_lot not between", value1, value2, "prodLot");
            return (Criteria) this;
        }

        public Criteria andRecLotIsNull() {
            addCriterion("rec_lot is null");
            return (Criteria) this;
        }

        public Criteria andRecLotIsNotNull() {
            addCriterion("rec_lot is not null");
            return (Criteria) this;
        }

        public Criteria andRecLotEqualTo(String value) {
            addCriterion("rec_lot =", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotNotEqualTo(String value) {
            addCriterion("rec_lot <>", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotGreaterThan(String value) {
            addCriterion("rec_lot >", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotGreaterThanOrEqualTo(String value) {
            addCriterion("rec_lot >=", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotLessThan(String value) {
            addCriterion("rec_lot <", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotLessThanOrEqualTo(String value) {
            addCriterion("rec_lot <=", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotLike(String value) {
            addCriterion("rec_lot like", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotNotLike(String value) {
            addCriterion("rec_lot not like", value, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotIn(List<String> values) {
            addCriterion("rec_lot in", values, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotNotIn(List<String> values) {
            addCriterion("rec_lot not in", values, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotBetween(String value1, String value2) {
            addCriterion("rec_lot between", value1, value2, "recLot");
            return (Criteria) this;
        }

        public Criteria andRecLotNotBetween(String value1, String value2) {
            addCriterion("rec_lot not between", value1, value2, "recLot");
            return (Criteria) this;
        }

        public Criteria andVendorCodeIsNull() {
            addCriterion("vendor_code is null");
            return (Criteria) this;
        }

        public Criteria andVendorCodeIsNotNull() {
            addCriterion("vendor_code is not null");
            return (Criteria) this;
        }

        public Criteria andVendorCodeEqualTo(String value) {
            addCriterion("vendor_code =", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeNotEqualTo(String value) {
            addCriterion("vendor_code <>", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeGreaterThan(String value) {
            addCriterion("vendor_code >", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("vendor_code >=", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeLessThan(String value) {
            addCriterion("vendor_code <", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeLessThanOrEqualTo(String value) {
            addCriterion("vendor_code <=", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeLike(String value) {
            addCriterion("vendor_code like", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeNotLike(String value) {
            addCriterion("vendor_code not like", value, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeIn(List<String> values) {
            addCriterion("vendor_code in", values, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeNotIn(List<String> values) {
            addCriterion("vendor_code not in", values, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeBetween(String value1, String value2) {
            addCriterion("vendor_code between", value1, value2, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andVendorCodeNotBetween(String value1, String value2) {
            addCriterion("vendor_code not between", value1, value2, "vendorCode");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeIsNull() {
            addCriterion("order_rec_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeIsNotNull() {
            addCriterion("order_rec_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeEqualTo(LocalDateTime value) {
            addCriterion("order_rec_time =", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeNotEqualTo(LocalDateTime value) {
            addCriterion("order_rec_time <>", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeGreaterThan(LocalDateTime value) {
            addCriterion("order_rec_time >", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("order_rec_time >=", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeLessThan(LocalDateTime value) {
            addCriterion("order_rec_time <", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("order_rec_time <=", value, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeIn(List<LocalDateTime> values) {
            addCriterion("order_rec_time in", values, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeNotIn(List<LocalDateTime> values) {
            addCriterion("order_rec_time not in", values, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("order_rec_time between", value1, value2, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andOrderRecTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("order_rec_time not between", value1, value2, "orderRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeIsNull() {
            addCriterion("real_rec_time is null");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeIsNotNull() {
            addCriterion("real_rec_time is not null");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeEqualTo(LocalDateTime value) {
            addCriterion("real_rec_time =", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeNotEqualTo(LocalDateTime value) {
            addCriterion("real_rec_time <>", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeGreaterThan(LocalDateTime value) {
            addCriterion("real_rec_time >", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("real_rec_time >=", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeLessThan(LocalDateTime value) {
            addCriterion("real_rec_time <", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("real_rec_time <=", value, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeIn(List<LocalDateTime> values) {
            addCriterion("real_rec_time in", values, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeNotIn(List<LocalDateTime> values) {
            addCriterion("real_rec_time not in", values, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("real_rec_time between", value1, value2, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andRealRecTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("real_rec_time not between", value1, value2, "realRecTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateIsNull() {
            addCriterion("advance_date is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateIsNotNull() {
            addCriterion("advance_date is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateEqualTo(String value) {
            addCriterion("advance_date =", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateNotEqualTo(String value) {
            addCriterion("advance_date <>", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateGreaterThan(String value) {
            addCriterion("advance_date >", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateGreaterThanOrEqualTo(String value) {
            addCriterion("advance_date >=", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateLessThan(String value) {
            addCriterion("advance_date <", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateLessThanOrEqualTo(String value) {
            addCriterion("advance_date <=", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateLike(String value) {
            addCriterion("advance_date like", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateNotLike(String value) {
            addCriterion("advance_date not like", value, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateIn(List<String> values) {
            addCriterion("advance_date in", values, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateNotIn(List<String> values) {
            addCriterion("advance_date not in", values, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateBetween(String value1, String value2) {
            addCriterion("advance_date between", value1, value2, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceDateNotBetween(String value1, String value2) {
            addCriterion("advance_date not between", value1, value2, "advanceDate");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeIsNull() {
            addCriterion("advance_time is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeIsNotNull() {
            addCriterion("advance_time is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeEqualTo(String value) {
            addCriterion("advance_time =", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeNotEqualTo(String value) {
            addCriterion("advance_time <>", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeGreaterThan(String value) {
            addCriterion("advance_time >", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeGreaterThanOrEqualTo(String value) {
            addCriterion("advance_time >=", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeLessThan(String value) {
            addCriterion("advance_time <", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeLessThanOrEqualTo(String value) {
            addCriterion("advance_time <=", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeLike(String value) {
            addCriterion("advance_time like", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeNotLike(String value) {
            addCriterion("advance_time not like", value, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeIn(List<String> values) {
            addCriterion("advance_time in", values, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeNotIn(List<String> values) {
            addCriterion("advance_time not in", values, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeBetween(String value1, String value2) {
            addCriterion("advance_time between", value1, value2, "advanceTime");
            return (Criteria) this;
        }

        public Criteria andAdvanceTimeNotBetween(String value1, String value2) {
            addCriterion("advance_time not between", value1, value2, "advanceTime");
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