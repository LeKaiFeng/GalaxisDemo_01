package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SkuInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public SkuInfoExample() {
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

        public Criteria andCheckTotalIsNull() {
            addCriterion("check_total is null");
            return (Criteria) this;
        }

        public Criteria andCheckTotalIsNotNull() {
            addCriterion("check_total is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTotalEqualTo(Integer value) {
            addCriterion("check_total =", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalNotEqualTo(Integer value) {
            addCriterion("check_total <>", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalGreaterThan(Integer value) {
            addCriterion("check_total >", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_total >=", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalLessThan(Integer value) {
            addCriterion("check_total <", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalLessThanOrEqualTo(Integer value) {
            addCriterion("check_total <=", value, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalIn(List<Integer> values) {
            addCriterion("check_total in", values, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalNotIn(List<Integer> values) {
            addCriterion("check_total not in", values, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalBetween(Integer value1, Integer value2) {
            addCriterion("check_total between", value1, value2, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("check_total not between", value1, value2, "checkTotal");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIsNull() {
            addCriterion("check_rule is null");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIsNotNull() {
            addCriterion("check_rule is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRuleEqualTo(Integer value) {
            addCriterion("check_rule =", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotEqualTo(Integer value) {
            addCriterion("check_rule <>", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleGreaterThan(Integer value) {
            addCriterion("check_rule >", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_rule >=", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleLessThan(Integer value) {
            addCriterion("check_rule <", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleLessThanOrEqualTo(Integer value) {
            addCriterion("check_rule <=", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIn(List<Integer> values) {
            addCriterion("check_rule in", values, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotIn(List<Integer> values) {
            addCriterion("check_rule not in", values, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleBetween(Integer value1, Integer value2) {
            addCriterion("check_rule between", value1, value2, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotBetween(Integer value1, Integer value2) {
            addCriterion("check_rule not between", value1, value2, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCountTotalIsNull() {
            addCriterion("count_total is null");
            return (Criteria) this;
        }

        public Criteria andCountTotalIsNotNull() {
            addCriterion("count_total is not null");
            return (Criteria) this;
        }

        public Criteria andCountTotalEqualTo(Integer value) {
            addCriterion("count_total =", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalNotEqualTo(Integer value) {
            addCriterion("count_total <>", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalGreaterThan(Integer value) {
            addCriterion("count_total >", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_total >=", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalLessThan(Integer value) {
            addCriterion("count_total <", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalLessThanOrEqualTo(Integer value) {
            addCriterion("count_total <=", value, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalIn(List<Integer> values) {
            addCriterion("count_total in", values, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalNotIn(List<Integer> values) {
            addCriterion("count_total not in", values, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalBetween(Integer value1, Integer value2) {
            addCriterion("count_total between", value1, value2, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("count_total not between", value1, value2, "countTotal");
            return (Criteria) this;
        }

        public Criteria andCountRuleIsNull() {
            addCriterion("count_rule is null");
            return (Criteria) this;
        }

        public Criteria andCountRuleIsNotNull() {
            addCriterion("count_rule is not null");
            return (Criteria) this;
        }

        public Criteria andCountRuleEqualTo(Integer value) {
            addCriterion("count_rule =", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleNotEqualTo(Integer value) {
            addCriterion("count_rule <>", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleGreaterThan(Integer value) {
            addCriterion("count_rule >", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_rule >=", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleLessThan(Integer value) {
            addCriterion("count_rule <", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleLessThanOrEqualTo(Integer value) {
            addCriterion("count_rule <=", value, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleIn(List<Integer> values) {
            addCriterion("count_rule in", values, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleNotIn(List<Integer> values) {
            addCriterion("count_rule not in", values, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleBetween(Integer value1, Integer value2) {
            addCriterion("count_rule between", value1, value2, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountRuleNotBetween(Integer value1, Integer value2) {
            addCriterion("count_rule not between", value1, value2, "countRule");
            return (Criteria) this;
        }

        public Criteria andCountTimesIsNull() {
            addCriterion("count_times is null");
            return (Criteria) this;
        }

        public Criteria andCountTimesIsNotNull() {
            addCriterion("count_times is not null");
            return (Criteria) this;
        }

        public Criteria andCountTimesEqualTo(BigDecimal value) {
            addCriterion("count_times =", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesNotEqualTo(BigDecimal value) {
            addCriterion("count_times <>", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesGreaterThan(BigDecimal value) {
            addCriterion("count_times >", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("count_times >=", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesLessThan(BigDecimal value) {
            addCriterion("count_times <", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("count_times <=", value, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesIn(List<BigDecimal> values) {
            addCriterion("count_times in", values, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesNotIn(List<BigDecimal> values) {
            addCriterion("count_times not in", values, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("count_times between", value1, value2, "countTimes");
            return (Criteria) this;
        }

        public Criteria andCountTimesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("count_times not between", value1, value2, "countTimes");
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