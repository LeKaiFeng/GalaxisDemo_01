package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public TaskExample() {
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Long value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Long value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Long value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Long value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Long value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Long value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Long> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Long> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Long value1, Long value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Long value1, Long value2) {
            addCriterion("seq not between", value1, value2, "seq");
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

        public Criteria andSLevelIsNull() {
            addCriterion("s_level is null");
            return (Criteria) this;
        }

        public Criteria andSLevelIsNotNull() {
            addCriterion("s_level is not null");
            return (Criteria) this;
        }

        public Criteria andSLevelEqualTo(Integer value) {
            addCriterion("s_level =", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelNotEqualTo(Integer value) {
            addCriterion("s_level <>", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelGreaterThan(Integer value) {
            addCriterion("s_level >", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_level >=", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelLessThan(Integer value) {
            addCriterion("s_level <", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelLessThanOrEqualTo(Integer value) {
            addCriterion("s_level <=", value, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelIn(List<Integer> values) {
            addCriterion("s_level in", values, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelNotIn(List<Integer> values) {
            addCriterion("s_level not in", values, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelBetween(Integer value1, Integer value2) {
            addCriterion("s_level between", value1, value2, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("s_level not between", value1, value2, "sLevel");
            return (Criteria) this;
        }

        public Criteria andSLocationIsNull() {
            addCriterion("s_location is null");
            return (Criteria) this;
        }

        public Criteria andSLocationIsNotNull() {
            addCriterion("s_location is not null");
            return (Criteria) this;
        }

        public Criteria andSLocationEqualTo(Integer value) {
            addCriterion("s_location =", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationNotEqualTo(Integer value) {
            addCriterion("s_location <>", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationGreaterThan(Integer value) {
            addCriterion("s_location >", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_location >=", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationLessThan(Integer value) {
            addCriterion("s_location <", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationLessThanOrEqualTo(Integer value) {
            addCriterion("s_location <=", value, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationIn(List<Integer> values) {
            addCriterion("s_location in", values, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationNotIn(List<Integer> values) {
            addCriterion("s_location not in", values, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationBetween(Integer value1, Integer value2) {
            addCriterion("s_location between", value1, value2, "sLocation");
            return (Criteria) this;
        }

        public Criteria andSLocationNotBetween(Integer value1, Integer value2) {
            addCriterion("s_location not between", value1, value2, "sLocation");
            return (Criteria) this;
        }

        public Criteria andELevelIsNull() {
            addCriterion("e_level is null");
            return (Criteria) this;
        }

        public Criteria andELevelIsNotNull() {
            addCriterion("e_level is not null");
            return (Criteria) this;
        }

        public Criteria andELevelEqualTo(Integer value) {
            addCriterion("e_level =", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelNotEqualTo(Integer value) {
            addCriterion("e_level <>", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelGreaterThan(Integer value) {
            addCriterion("e_level >", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_level >=", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelLessThan(Integer value) {
            addCriterion("e_level <", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelLessThanOrEqualTo(Integer value) {
            addCriterion("e_level <=", value, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelIn(List<Integer> values) {
            addCriterion("e_level in", values, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelNotIn(List<Integer> values) {
            addCriterion("e_level not in", values, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelBetween(Integer value1, Integer value2) {
            addCriterion("e_level between", value1, value2, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELevelNotBetween(Integer value1, Integer value2) {
            addCriterion("e_level not between", value1, value2, "eLevel");
            return (Criteria) this;
        }

        public Criteria andELocationIsNull() {
            addCriterion("e_location is null");
            return (Criteria) this;
        }

        public Criteria andELocationIsNotNull() {
            addCriterion("e_location is not null");
            return (Criteria) this;
        }

        public Criteria andELocationEqualTo(Integer value) {
            addCriterion("e_location =", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationNotEqualTo(Integer value) {
            addCriterion("e_location <>", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationGreaterThan(Integer value) {
            addCriterion("e_location >", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_location >=", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationLessThan(Integer value) {
            addCriterion("e_location <", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationLessThanOrEqualTo(Integer value) {
            addCriterion("e_location <=", value, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationIn(List<Integer> values) {
            addCriterion("e_location in", values, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationNotIn(List<Integer> values) {
            addCriterion("e_location not in", values, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationBetween(Integer value1, Integer value2) {
            addCriterion("e_location between", value1, value2, "eLocation");
            return (Criteria) this;
        }

        public Criteria andELocationNotBetween(Integer value1, Integer value2) {
            addCriterion("e_location not between", value1, value2, "eLocation");
            return (Criteria) this;
        }

        public Criteria andRLevelIsNull() {
            addCriterion("r_level is null");
            return (Criteria) this;
        }

        public Criteria andRLevelIsNotNull() {
            addCriterion("r_level is not null");
            return (Criteria) this;
        }

        public Criteria andRLevelEqualTo(Integer value) {
            addCriterion("r_level =", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelNotEqualTo(Integer value) {
            addCriterion("r_level <>", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelGreaterThan(Integer value) {
            addCriterion("r_level >", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("r_level >=", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelLessThan(Integer value) {
            addCriterion("r_level <", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelLessThanOrEqualTo(Integer value) {
            addCriterion("r_level <=", value, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelIn(List<Integer> values) {
            addCriterion("r_level in", values, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelNotIn(List<Integer> values) {
            addCriterion("r_level not in", values, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelBetween(Integer value1, Integer value2) {
            addCriterion("r_level between", value1, value2, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("r_level not between", value1, value2, "rLevel");
            return (Criteria) this;
        }

        public Criteria andRLocationIsNull() {
            addCriterion("r_location is null");
            return (Criteria) this;
        }

        public Criteria andRLocationIsNotNull() {
            addCriterion("r_location is not null");
            return (Criteria) this;
        }

        public Criteria andRLocationEqualTo(Integer value) {
            addCriterion("r_location =", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationNotEqualTo(Integer value) {
            addCriterion("r_location <>", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationGreaterThan(Integer value) {
            addCriterion("r_location >", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationGreaterThanOrEqualTo(Integer value) {
            addCriterion("r_location >=", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationLessThan(Integer value) {
            addCriterion("r_location <", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationLessThanOrEqualTo(Integer value) {
            addCriterion("r_location <=", value, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationIn(List<Integer> values) {
            addCriterion("r_location in", values, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationNotIn(List<Integer> values) {
            addCriterion("r_location not in", values, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationBetween(Integer value1, Integer value2) {
            addCriterion("r_location between", value1, value2, "rLocation");
            return (Criteria) this;
        }

        public Criteria andRLocationNotBetween(Integer value1, Integer value2) {
            addCriterion("r_location not between", value1, value2, "rLocation");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(LocalDateTime value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(LocalDateTime value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(LocalDateTime value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(LocalDateTime value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<LocalDateTime> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<LocalDateTime> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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