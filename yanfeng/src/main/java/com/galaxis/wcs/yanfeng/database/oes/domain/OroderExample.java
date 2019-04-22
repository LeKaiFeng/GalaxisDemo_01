package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OroderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public OroderExample() {
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

        public Criteria andCreateYmdIsNull() {
            addCriterion("create_ymd is null");
            return (Criteria) this;
        }

        public Criteria andCreateYmdIsNotNull() {
            addCriterion("create_ymd is not null");
            return (Criteria) this;
        }

        public Criteria andCreateYmdEqualTo(String value) {
            addCriterion("create_ymd =", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdNotEqualTo(String value) {
            addCriterion("create_ymd <>", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdGreaterThan(String value) {
            addCriterion("create_ymd >", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdGreaterThanOrEqualTo(String value) {
            addCriterion("create_ymd >=", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdLessThan(String value) {
            addCriterion("create_ymd <", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdLessThanOrEqualTo(String value) {
            addCriterion("create_ymd <=", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdLike(String value) {
            addCriterion("create_ymd like", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdNotLike(String value) {
            addCriterion("create_ymd not like", value, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdIn(List<String> values) {
            addCriterion("create_ymd in", values, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdNotIn(List<String> values) {
            addCriterion("create_ymd not in", values, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdBetween(String value1, String value2) {
            addCriterion("create_ymd between", value1, value2, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateYmdNotBetween(String value1, String value2) {
            addCriterion("create_ymd not between", value1, value2, "createYmd");
            return (Criteria) this;
        }

        public Criteria andCreateHmsIsNull() {
            addCriterion("create_hms is null");
            return (Criteria) this;
        }

        public Criteria andCreateHmsIsNotNull() {
            addCriterion("create_hms is not null");
            return (Criteria) this;
        }

        public Criteria andCreateHmsEqualTo(String value) {
            addCriterion("create_hms =", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsNotEqualTo(String value) {
            addCriterion("create_hms <>", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsGreaterThan(String value) {
            addCriterion("create_hms >", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsGreaterThanOrEqualTo(String value) {
            addCriterion("create_hms >=", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsLessThan(String value) {
            addCriterion("create_hms <", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsLessThanOrEqualTo(String value) {
            addCriterion("create_hms <=", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsLike(String value) {
            addCriterion("create_hms like", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsNotLike(String value) {
            addCriterion("create_hms not like", value, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsIn(List<String> values) {
            addCriterion("create_hms in", values, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsNotIn(List<String> values) {
            addCriterion("create_hms not in", values, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsBetween(String value1, String value2) {
            addCriterion("create_hms between", value1, value2, "createHms");
            return (Criteria) this;
        }

        public Criteria andCreateHmsNotBetween(String value1, String value2) {
            addCriterion("create_hms not between", value1, value2, "createHms");
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

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andActflgIsNull() {
            addCriterion("actflg is null");
            return (Criteria) this;
        }

        public Criteria andActflgIsNotNull() {
            addCriterion("actflg is not null");
            return (Criteria) this;
        }

        public Criteria andActflgEqualTo(String value) {
            addCriterion("actflg =", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgNotEqualTo(String value) {
            addCriterion("actflg <>", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgGreaterThan(String value) {
            addCriterion("actflg >", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgGreaterThanOrEqualTo(String value) {
            addCriterion("actflg >=", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgLessThan(String value) {
            addCriterion("actflg <", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgLessThanOrEqualTo(String value) {
            addCriterion("actflg <=", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgLike(String value) {
            addCriterion("actflg like", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgNotLike(String value) {
            addCriterion("actflg not like", value, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgIn(List<String> values) {
            addCriterion("actflg in", values, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgNotIn(List<String> values) {
            addCriterion("actflg not in", values, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgBetween(String value1, String value2) {
            addCriterion("actflg between", value1, value2, "actflg");
            return (Criteria) this;
        }

        public Criteria andActflgNotBetween(String value1, String value2) {
            addCriterion("actflg not between", value1, value2, "actflg");
            return (Criteria) this;
        }

        public Criteria andActymdIsNull() {
            addCriterion("actymd is null");
            return (Criteria) this;
        }

        public Criteria andActymdIsNotNull() {
            addCriterion("actymd is not null");
            return (Criteria) this;
        }

        public Criteria andActymdEqualTo(String value) {
            addCriterion("actymd =", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdNotEqualTo(String value) {
            addCriterion("actymd <>", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdGreaterThan(String value) {
            addCriterion("actymd >", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdGreaterThanOrEqualTo(String value) {
            addCriterion("actymd >=", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdLessThan(String value) {
            addCriterion("actymd <", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdLessThanOrEqualTo(String value) {
            addCriterion("actymd <=", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdLike(String value) {
            addCriterion("actymd like", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdNotLike(String value) {
            addCriterion("actymd not like", value, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdIn(List<String> values) {
            addCriterion("actymd in", values, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdNotIn(List<String> values) {
            addCriterion("actymd not in", values, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdBetween(String value1, String value2) {
            addCriterion("actymd between", value1, value2, "actymd");
            return (Criteria) this;
        }

        public Criteria andActymdNotBetween(String value1, String value2) {
            addCriterion("actymd not between", value1, value2, "actymd");
            return (Criteria) this;
        }

        public Criteria andActhmsIsNull() {
            addCriterion("acthms is null");
            return (Criteria) this;
        }

        public Criteria andActhmsIsNotNull() {
            addCriterion("acthms is not null");
            return (Criteria) this;
        }

        public Criteria andActhmsEqualTo(String value) {
            addCriterion("acthms =", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsNotEqualTo(String value) {
            addCriterion("acthms <>", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsGreaterThan(String value) {
            addCriterion("acthms >", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsGreaterThanOrEqualTo(String value) {
            addCriterion("acthms >=", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsLessThan(String value) {
            addCriterion("acthms <", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsLessThanOrEqualTo(String value) {
            addCriterion("acthms <=", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsLike(String value) {
            addCriterion("acthms like", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsNotLike(String value) {
            addCriterion("acthms not like", value, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsIn(List<String> values) {
            addCriterion("acthms in", values, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsNotIn(List<String> values) {
            addCriterion("acthms not in", values, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsBetween(String value1, String value2) {
            addCriterion("acthms between", value1, value2, "acthms");
            return (Criteria) this;
        }

        public Criteria andActhmsNotBetween(String value1, String value2) {
            addCriterion("acthms not between", value1, value2, "acthms");
            return (Criteria) this;
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(String value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(String value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(String value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(String value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(String value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(String value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLike(String value) {
            addCriterion("sid like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotLike(String value) {
            addCriterion("sid not like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<String> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<String> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(String value1, String value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(String value1, String value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andGuidIsNull() {
            addCriterion("guid is null");
            return (Criteria) this;
        }

        public Criteria andGuidIsNotNull() {
            addCriterion("guid is not null");
            return (Criteria) this;
        }

        public Criteria andGuidEqualTo(String value) {
            addCriterion("guid =", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotEqualTo(String value) {
            addCriterion("guid <>", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThan(String value) {
            addCriterion("guid >", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThanOrEqualTo(String value) {
            addCriterion("guid >=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThan(String value) {
            addCriterion("guid <", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThanOrEqualTo(String value) {
            addCriterion("guid <=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLike(String value) {
            addCriterion("guid like", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotLike(String value) {
            addCriterion("guid not like", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidIn(List<String> values) {
            addCriterion("guid in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotIn(List<String> values) {
            addCriterion("guid not in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidBetween(String value1, String value2) {
            addCriterion("guid between", value1, value2, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotBetween(String value1, String value2) {
            addCriterion("guid not between", value1, value2, "guid");
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