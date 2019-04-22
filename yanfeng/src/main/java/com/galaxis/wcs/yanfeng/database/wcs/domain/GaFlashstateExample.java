package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.util.ArrayList;
import java.util.List;

public class GaFlashstateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public GaFlashstateExample() {
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

        public Criteria andCarNumberIsNull() {
            addCriterion("car_number is null");
            return (Criteria) this;
        }

        public Criteria andCarNumberIsNotNull() {
            addCriterion("car_number is not null");
            return (Criteria) this;
        }

        public Criteria andCarNumberEqualTo(Integer value) {
            addCriterion("car_number =", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotEqualTo(Integer value) {
            addCriterion("car_number <>", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberGreaterThan(Integer value) {
            addCriterion("car_number >", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_number >=", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberLessThan(Integer value) {
            addCriterion("car_number <", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberLessThanOrEqualTo(Integer value) {
            addCriterion("car_number <=", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberIn(List<Integer> values) {
            addCriterion("car_number in", values, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotIn(List<Integer> values) {
            addCriterion("car_number not in", values, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberBetween(Integer value1, Integer value2) {
            addCriterion("car_number between", value1, value2, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("car_number not between", value1, value2, "carNumber");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCarLevelIsNull() {
            addCriterion("car_level is null");
            return (Criteria) this;
        }

        public Criteria andCarLevelIsNotNull() {
            addCriterion("car_level is not null");
            return (Criteria) this;
        }

        public Criteria andCarLevelEqualTo(Integer value) {
            addCriterion("car_level =", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotEqualTo(Integer value) {
            addCriterion("car_level <>", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThan(Integer value) {
            addCriterion("car_level >", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_level >=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThan(Integer value) {
            addCriterion("car_level <", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThanOrEqualTo(Integer value) {
            addCriterion("car_level <=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelIn(List<Integer> values) {
            addCriterion("car_level in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotIn(List<Integer> values) {
            addCriterion("car_level not in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelBetween(Integer value1, Integer value2) {
            addCriterion("car_level between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("car_level not between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarPosIsNull() {
            addCriterion("car_pos is null");
            return (Criteria) this;
        }

        public Criteria andCarPosIsNotNull() {
            addCriterion("car_pos is not null");
            return (Criteria) this;
        }

        public Criteria andCarPosEqualTo(Integer value) {
            addCriterion("car_pos =", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosNotEqualTo(Integer value) {
            addCriterion("car_pos <>", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosGreaterThan(Integer value) {
            addCriterion("car_pos >", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_pos >=", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosLessThan(Integer value) {
            addCriterion("car_pos <", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosLessThanOrEqualTo(Integer value) {
            addCriterion("car_pos <=", value, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosIn(List<Integer> values) {
            addCriterion("car_pos in", values, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosNotIn(List<Integer> values) {
            addCriterion("car_pos not in", values, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosBetween(Integer value1, Integer value2) {
            addCriterion("car_pos between", value1, value2, "carPos");
            return (Criteria) this;
        }

        public Criteria andCarPosNotBetween(Integer value1, Integer value2) {
            addCriterion("car_pos not between", value1, value2, "carPos");
            return (Criteria) this;
        }

        public Criteria andFlashStateIsNull() {
            addCriterion("flash_state is null");
            return (Criteria) this;
        }

        public Criteria andFlashStateIsNotNull() {
            addCriterion("flash_state is not null");
            return (Criteria) this;
        }

        public Criteria andFlashStateEqualTo(Integer value) {
            addCriterion("flash_state =", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateNotEqualTo(Integer value) {
            addCriterion("flash_state <>", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateGreaterThan(Integer value) {
            addCriterion("flash_state >", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("flash_state >=", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateLessThan(Integer value) {
            addCriterion("flash_state <", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateLessThanOrEqualTo(Integer value) {
            addCriterion("flash_state <=", value, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateIn(List<Integer> values) {
            addCriterion("flash_state in", values, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateNotIn(List<Integer> values) {
            addCriterion("flash_state not in", values, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateBetween(Integer value1, Integer value2) {
            addCriterion("flash_state between", value1, value2, "flashState");
            return (Criteria) this;
        }

        public Criteria andFlashStateNotBetween(Integer value1, Integer value2) {
            addCriterion("flash_state not between", value1, value2, "flashState");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(Integer value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(Integer value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(Integer value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(Integer value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(Integer value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<Integer> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<Integer> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(Integer value1, Integer value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(Integer value1, Integer value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andWmscommandIsNull() {
            addCriterion("wmsCommand is null");
            return (Criteria) this;
        }

        public Criteria andWmscommandIsNotNull() {
            addCriterion("wmsCommand is not null");
            return (Criteria) this;
        }

        public Criteria andWmscommandEqualTo(Integer value) {
            addCriterion("wmsCommand =", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandNotEqualTo(Integer value) {
            addCriterion("wmsCommand <>", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandGreaterThan(Integer value) {
            addCriterion("wmsCommand >", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandGreaterThanOrEqualTo(Integer value) {
            addCriterion("wmsCommand >=", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandLessThan(Integer value) {
            addCriterion("wmsCommand <", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandLessThanOrEqualTo(Integer value) {
            addCriterion("wmsCommand <=", value, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandIn(List<Integer> values) {
            addCriterion("wmsCommand in", values, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandNotIn(List<Integer> values) {
            addCriterion("wmsCommand not in", values, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandBetween(Integer value1, Integer value2) {
            addCriterion("wmsCommand between", value1, value2, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWmscommandNotBetween(Integer value1, Integer value2) {
            addCriterion("wmsCommand not between", value1, value2, "wmscommand");
            return (Criteria) this;
        }

        public Criteria andWorkmodeIsNull() {
            addCriterion("workmode is null");
            return (Criteria) this;
        }

        public Criteria andWorkmodeIsNotNull() {
            addCriterion("workmode is not null");
            return (Criteria) this;
        }

        public Criteria andWorkmodeEqualTo(Integer value) {
            addCriterion("workmode =", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeNotEqualTo(Integer value) {
            addCriterion("workmode <>", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeGreaterThan(Integer value) {
            addCriterion("workmode >", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("workmode >=", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeLessThan(Integer value) {
            addCriterion("workmode <", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeLessThanOrEqualTo(Integer value) {
            addCriterion("workmode <=", value, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeIn(List<Integer> values) {
            addCriterion("workmode in", values, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeNotIn(List<Integer> values) {
            addCriterion("workmode not in", values, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeBetween(Integer value1, Integer value2) {
            addCriterion("workmode between", value1, value2, "workmode");
            return (Criteria) this;
        }

        public Criteria andWorkmodeNotBetween(Integer value1, Integer value2) {
            addCriterion("workmode not between", value1, value2, "workmode");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNull() {
            addCriterion("is_active is null");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNotNull() {
            addCriterion("is_active is not null");
            return (Criteria) this;
        }

        public Criteria andIsActiveEqualTo(Integer value) {
            addCriterion("is_active =", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotEqualTo(Integer value) {
            addCriterion("is_active <>", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThan(Integer value) {
            addCriterion("is_active >", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_active >=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThan(Integer value) {
            addCriterion("is_active <", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThanOrEqualTo(Integer value) {
            addCriterion("is_active <=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveIn(List<Integer> values) {
            addCriterion("is_active in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotIn(List<Integer> values) {
            addCriterion("is_active not in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveBetween(Integer value1, Integer value2) {
            addCriterion("is_active between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotBetween(Integer value1, Integer value2) {
            addCriterion("is_active not between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoIsNull() {
            addCriterion("order_lift_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoIsNotNull() {
            addCriterion("order_lift_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoEqualTo(Integer value) {
            addCriterion("order_lift_no =", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoNotEqualTo(Integer value) {
            addCriterion("order_lift_no <>", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoGreaterThan(Integer value) {
            addCriterion("order_lift_no >", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_lift_no >=", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoLessThan(Integer value) {
            addCriterion("order_lift_no <", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoLessThanOrEqualTo(Integer value) {
            addCriterion("order_lift_no <=", value, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoIn(List<Integer> values) {
            addCriterion("order_lift_no in", values, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoNotIn(List<Integer> values) {
            addCriterion("order_lift_no not in", values, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoBetween(Integer value1, Integer value2) {
            addCriterion("order_lift_no between", value1, value2, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andOrderLiftNoNotBetween(Integer value1, Integer value2) {
            addCriterion("order_lift_no not between", value1, value2, "orderLiftNo");
            return (Criteria) this;
        }

        public Criteria andNLevelIsNull() {
            addCriterion("n_level is null");
            return (Criteria) this;
        }

        public Criteria andNLevelIsNotNull() {
            addCriterion("n_level is not null");
            return (Criteria) this;
        }

        public Criteria andNLevelEqualTo(Integer value) {
            addCriterion("n_level =", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelNotEqualTo(Integer value) {
            addCriterion("n_level <>", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelGreaterThan(Integer value) {
            addCriterion("n_level >", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("n_level >=", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelLessThan(Integer value) {
            addCriterion("n_level <", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelLessThanOrEqualTo(Integer value) {
            addCriterion("n_level <=", value, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelIn(List<Integer> values) {
            addCriterion("n_level in", values, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelNotIn(List<Integer> values) {
            addCriterion("n_level not in", values, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelBetween(Integer value1, Integer value2) {
            addCriterion("n_level between", value1, value2, "nLevel");
            return (Criteria) this;
        }

        public Criteria andNLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("n_level not between", value1, value2, "nLevel");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleIsNull() {
            addCriterion("inout_possible is null");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleIsNotNull() {
            addCriterion("inout_possible is not null");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleEqualTo(Integer value) {
            addCriterion("inout_possible =", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleNotEqualTo(Integer value) {
            addCriterion("inout_possible <>", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleGreaterThan(Integer value) {
            addCriterion("inout_possible >", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleGreaterThanOrEqualTo(Integer value) {
            addCriterion("inout_possible >=", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleLessThan(Integer value) {
            addCriterion("inout_possible <", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleLessThanOrEqualTo(Integer value) {
            addCriterion("inout_possible <=", value, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleIn(List<Integer> values) {
            addCriterion("inout_possible in", values, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleNotIn(List<Integer> values) {
            addCriterion("inout_possible not in", values, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleBetween(Integer value1, Integer value2) {
            addCriterion("inout_possible between", value1, value2, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andInoutPossibleNotBetween(Integer value1, Integer value2) {
            addCriterion("inout_possible not between", value1, value2, "inoutPossible");
            return (Criteria) this;
        }

        public Criteria andNPosIsNull() {
            addCriterion("n_pos is null");
            return (Criteria) this;
        }

        public Criteria andNPosIsNotNull() {
            addCriterion("n_pos is not null");
            return (Criteria) this;
        }

        public Criteria andNPosEqualTo(Integer value) {
            addCriterion("n_pos =", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosNotEqualTo(Integer value) {
            addCriterion("n_pos <>", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosGreaterThan(Integer value) {
            addCriterion("n_pos >", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("n_pos >=", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosLessThan(Integer value) {
            addCriterion("n_pos <", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosLessThanOrEqualTo(Integer value) {
            addCriterion("n_pos <=", value, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosIn(List<Integer> values) {
            addCriterion("n_pos in", values, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosNotIn(List<Integer> values) {
            addCriterion("n_pos not in", values, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosBetween(Integer value1, Integer value2) {
            addCriterion("n_pos between", value1, value2, "nPos");
            return (Criteria) this;
        }

        public Criteria andNPosNotBetween(Integer value1, Integer value2) {
            addCriterion("n_pos not between", value1, value2, "nPos");
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