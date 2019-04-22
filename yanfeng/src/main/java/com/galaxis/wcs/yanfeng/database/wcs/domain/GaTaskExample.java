package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GaTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public GaTaskExample() {
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

        public Criteria andOLevelIsNull() {
            addCriterion("o_level is null");
            return (Criteria) this;
        }

        public Criteria andOLevelIsNotNull() {
            addCriterion("o_level is not null");
            return (Criteria) this;
        }

        public Criteria andOLevelEqualTo(Integer value) {
            addCriterion("o_level =", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelNotEqualTo(Integer value) {
            addCriterion("o_level <>", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelGreaterThan(Integer value) {
            addCriterion("o_level >", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("o_level >=", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelLessThan(Integer value) {
            addCriterion("o_level <", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelLessThanOrEqualTo(Integer value) {
            addCriterion("o_level <=", value, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelIn(List<Integer> values) {
            addCriterion("o_level in", values, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelNotIn(List<Integer> values) {
            addCriterion("o_level not in", values, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelBetween(Integer value1, Integer value2) {
            addCriterion("o_level between", value1, value2, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("o_level not between", value1, value2, "oLevel");
            return (Criteria) this;
        }

        public Criteria andOPosIsNull() {
            addCriterion("o_pos is null");
            return (Criteria) this;
        }

        public Criteria andOPosIsNotNull() {
            addCriterion("o_pos is not null");
            return (Criteria) this;
        }

        public Criteria andOPosEqualTo(Integer value) {
            addCriterion("o_pos =", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosNotEqualTo(Integer value) {
            addCriterion("o_pos <>", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosGreaterThan(Integer value) {
            addCriterion("o_pos >", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("o_pos >=", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosLessThan(Integer value) {
            addCriterion("o_pos <", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosLessThanOrEqualTo(Integer value) {
            addCriterion("o_pos <=", value, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosIn(List<Integer> values) {
            addCriterion("o_pos in", values, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosNotIn(List<Integer> values) {
            addCriterion("o_pos not in", values, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosBetween(Integer value1, Integer value2) {
            addCriterion("o_pos between", value1, value2, "oPos");
            return (Criteria) this;
        }

        public Criteria andOPosNotBetween(Integer value1, Integer value2) {
            addCriterion("o_pos not between", value1, value2, "oPos");
            return (Criteria) this;
        }

        public Criteria andTLevelIsNull() {
            addCriterion("t_level is null");
            return (Criteria) this;
        }

        public Criteria andTLevelIsNotNull() {
            addCriterion("t_level is not null");
            return (Criteria) this;
        }

        public Criteria andTLevelEqualTo(Integer value) {
            addCriterion("t_level =", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelNotEqualTo(Integer value) {
            addCriterion("t_level <>", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelGreaterThan(Integer value) {
            addCriterion("t_level >", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("t_level >=", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelLessThan(Integer value) {
            addCriterion("t_level <", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelLessThanOrEqualTo(Integer value) {
            addCriterion("t_level <=", value, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelIn(List<Integer> values) {
            addCriterion("t_level in", values, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelNotIn(List<Integer> values) {
            addCriterion("t_level not in", values, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelBetween(Integer value1, Integer value2) {
            addCriterion("t_level between", value1, value2, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("t_level not between", value1, value2, "tLevel");
            return (Criteria) this;
        }

        public Criteria andTPosIsNull() {
            addCriterion("t_pos is null");
            return (Criteria) this;
        }

        public Criteria andTPosIsNotNull() {
            addCriterion("t_pos is not null");
            return (Criteria) this;
        }

        public Criteria andTPosEqualTo(Integer value) {
            addCriterion("t_pos =", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosNotEqualTo(Integer value) {
            addCriterion("t_pos <>", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosGreaterThan(Integer value) {
            addCriterion("t_pos >", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("t_pos >=", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosLessThan(Integer value) {
            addCriterion("t_pos <", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosLessThanOrEqualTo(Integer value) {
            addCriterion("t_pos <=", value, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosIn(List<Integer> values) {
            addCriterion("t_pos in", values, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosNotIn(List<Integer> values) {
            addCriterion("t_pos not in", values, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosBetween(Integer value1, Integer value2) {
            addCriterion("t_pos between", value1, value2, "tPos");
            return (Criteria) this;
        }

        public Criteria andTPosNotBetween(Integer value1, Integer value2) {
            addCriterion("t_pos not between", value1, value2, "tPos");
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

        public Criteria andSPosIsNull() {
            addCriterion("s_pos is null");
            return (Criteria) this;
        }

        public Criteria andSPosIsNotNull() {
            addCriterion("s_pos is not null");
            return (Criteria) this;
        }

        public Criteria andSPosEqualTo(Integer value) {
            addCriterion("s_pos =", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosNotEqualTo(Integer value) {
            addCriterion("s_pos <>", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosGreaterThan(Integer value) {
            addCriterion("s_pos >", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_pos >=", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosLessThan(Integer value) {
            addCriterion("s_pos <", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosLessThanOrEqualTo(Integer value) {
            addCriterion("s_pos <=", value, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosIn(List<Integer> values) {
            addCriterion("s_pos in", values, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosNotIn(List<Integer> values) {
            addCriterion("s_pos not in", values, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosBetween(Integer value1, Integer value2) {
            addCriterion("s_pos between", value1, value2, "sPos");
            return (Criteria) this;
        }

        public Criteria andSPosNotBetween(Integer value1, Integer value2) {
            addCriterion("s_pos not between", value1, value2, "sPos");
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

        public Criteria andEPosIsNull() {
            addCriterion("e_pos is null");
            return (Criteria) this;
        }

        public Criteria andEPosIsNotNull() {
            addCriterion("e_pos is not null");
            return (Criteria) this;
        }

        public Criteria andEPosEqualTo(Integer value) {
            addCriterion("e_pos =", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosNotEqualTo(Integer value) {
            addCriterion("e_pos <>", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosGreaterThan(Integer value) {
            addCriterion("e_pos >", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_pos >=", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosLessThan(Integer value) {
            addCriterion("e_pos <", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosLessThanOrEqualTo(Integer value) {
            addCriterion("e_pos <=", value, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosIn(List<Integer> values) {
            addCriterion("e_pos in", values, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosNotIn(List<Integer> values) {
            addCriterion("e_pos not in", values, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosBetween(Integer value1, Integer value2) {
            addCriterion("e_pos between", value1, value2, "ePos");
            return (Criteria) this;
        }

        public Criteria andEPosNotBetween(Integer value1, Integer value2) {
            addCriterion("e_pos not between", value1, value2, "ePos");
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

        public Criteria andRPosIsNull() {
            addCriterion("r_pos is null");
            return (Criteria) this;
        }

        public Criteria andRPosIsNotNull() {
            addCriterion("r_pos is not null");
            return (Criteria) this;
        }

        public Criteria andRPosEqualTo(Integer value) {
            addCriterion("r_pos =", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosNotEqualTo(Integer value) {
            addCriterion("r_pos <>", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosGreaterThan(Integer value) {
            addCriterion("r_pos >", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("r_pos >=", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosLessThan(Integer value) {
            addCriterion("r_pos <", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosLessThanOrEqualTo(Integer value) {
            addCriterion("r_pos <=", value, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosIn(List<Integer> values) {
            addCriterion("r_pos in", values, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosNotIn(List<Integer> values) {
            addCriterion("r_pos not in", values, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosBetween(Integer value1, Integer value2) {
            addCriterion("r_pos between", value1, value2, "rPos");
            return (Criteria) this;
        }

        public Criteria andRPosNotBetween(Integer value1, Integer value2) {
            addCriterion("r_pos not between", value1, value2, "rPos");
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

        public Criteria andAisleIsNull() {
            addCriterion("aisle is null");
            return (Criteria) this;
        }

        public Criteria andAisleIsNotNull() {
            addCriterion("aisle is not null");
            return (Criteria) this;
        }

        public Criteria andAisleEqualTo(String value) {
            addCriterion("aisle =", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotEqualTo(String value) {
            addCriterion("aisle <>", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleGreaterThan(String value) {
            addCriterion("aisle >", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleGreaterThanOrEqualTo(String value) {
            addCriterion("aisle >=", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleLessThan(String value) {
            addCriterion("aisle <", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleLessThanOrEqualTo(String value) {
            addCriterion("aisle <=", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleLike(String value) {
            addCriterion("aisle like", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotLike(String value) {
            addCriterion("aisle not like", value, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleIn(List<String> values) {
            addCriterion("aisle in", values, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotIn(List<String> values) {
            addCriterion("aisle not in", values, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleBetween(String value1, String value2) {
            addCriterion("aisle between", value1, value2, "aisle");
            return (Criteria) this;
        }

        public Criteria andAisleNotBetween(String value1, String value2) {
            addCriterion("aisle not between", value1, value2, "aisle");
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

        public Criteria andWmsidIsNull() {
            addCriterion("wmsid is null");
            return (Criteria) this;
        }

        public Criteria andWmsidIsNotNull() {
            addCriterion("wmsid is not null");
            return (Criteria) this;
        }

        public Criteria andWmsidEqualTo(String value) {
            addCriterion("wmsid =", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidNotEqualTo(String value) {
            addCriterion("wmsid <>", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidGreaterThan(String value) {
            addCriterion("wmsid >", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidGreaterThanOrEqualTo(String value) {
            addCriterion("wmsid >=", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidLessThan(String value) {
            addCriterion("wmsid <", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidLessThanOrEqualTo(String value) {
            addCriterion("wmsid <=", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidLike(String value) {
            addCriterion("wmsid like", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidNotLike(String value) {
            addCriterion("wmsid not like", value, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidIn(List<String> values) {
            addCriterion("wmsid in", values, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidNotIn(List<String> values) {
            addCriterion("wmsid not in", values, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidBetween(String value1, String value2) {
            addCriterion("wmsid between", value1, value2, "wmsid");
            return (Criteria) this;
        }

        public Criteria andWmsidNotBetween(String value1, String value2) {
            addCriterion("wmsid not between", value1, value2, "wmsid");
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

        public Criteria andAssignTimeIsNull() {
            addCriterion("assign_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNotNull() {
            addCriterion("assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeEqualTo(LocalDateTime value) {
            addCriterion("assign_time =", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotEqualTo(LocalDateTime value) {
            addCriterion("assign_time <>", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThan(LocalDateTime value) {
            addCriterion("assign_time >", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("assign_time >=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThan(LocalDateTime value) {
            addCriterion("assign_time <", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("assign_time <=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIn(List<LocalDateTime> values) {
            addCriterion("assign_time in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotIn(List<LocalDateTime> values) {
            addCriterion("assign_time not in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("assign_time between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("assign_time not between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(LocalDateTime value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(LocalDateTime value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(LocalDateTime value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(LocalDateTime value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<LocalDateTime> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<LocalDateTime> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(LocalDateTime value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(LocalDateTime value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(LocalDateTime value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(LocalDateTime value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<LocalDateTime> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<LocalDateTime> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdIsNull() {
            addCriterion("announce_id is null");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdIsNotNull() {
            addCriterion("announce_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdEqualTo(Integer value) {
            addCriterion("announce_id =", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdNotEqualTo(Integer value) {
            addCriterion("announce_id <>", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdGreaterThan(Integer value) {
            addCriterion("announce_id >", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("announce_id >=", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdLessThan(Integer value) {
            addCriterion("announce_id <", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdLessThanOrEqualTo(Integer value) {
            addCriterion("announce_id <=", value, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdIn(List<Integer> values) {
            addCriterion("announce_id in", values, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdNotIn(List<Integer> values) {
            addCriterion("announce_id not in", values, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdBetween(Integer value1, Integer value2) {
            addCriterion("announce_id between", value1, value2, "announceId");
            return (Criteria) this;
        }

        public Criteria andAnnounceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("announce_id not between", value1, value2, "announceId");
            return (Criteria) this;
        }

        public Criteria andTargetPosIsNull() {
            addCriterion("target_pos is null");
            return (Criteria) this;
        }

        public Criteria andTargetPosIsNotNull() {
            addCriterion("target_pos is not null");
            return (Criteria) this;
        }

        public Criteria andTargetPosEqualTo(String value) {
            addCriterion("target_pos =", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosNotEqualTo(String value) {
            addCriterion("target_pos <>", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosGreaterThan(String value) {
            addCriterion("target_pos >", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosGreaterThanOrEqualTo(String value) {
            addCriterion("target_pos >=", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosLessThan(String value) {
            addCriterion("target_pos <", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosLessThanOrEqualTo(String value) {
            addCriterion("target_pos <=", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosLike(String value) {
            addCriterion("target_pos like", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosNotLike(String value) {
            addCriterion("target_pos not like", value, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosIn(List<String> values) {
            addCriterion("target_pos in", values, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosNotIn(List<String> values) {
            addCriterion("target_pos not in", values, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosBetween(String value1, String value2) {
            addCriterion("target_pos between", value1, value2, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetPosNotBetween(String value1, String value2) {
            addCriterion("target_pos not between", value1, value2, "targetPos");
            return (Criteria) this;
        }

        public Criteria andTargetSideIsNull() {
            addCriterion("target_side is null");
            return (Criteria) this;
        }

        public Criteria andTargetSideIsNotNull() {
            addCriterion("target_side is not null");
            return (Criteria) this;
        }

        public Criteria andTargetSideEqualTo(String value) {
            addCriterion("target_side =", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideNotEqualTo(String value) {
            addCriterion("target_side <>", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideGreaterThan(String value) {
            addCriterion("target_side >", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideGreaterThanOrEqualTo(String value) {
            addCriterion("target_side >=", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideLessThan(String value) {
            addCriterion("target_side <", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideLessThanOrEqualTo(String value) {
            addCriterion("target_side <=", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideLike(String value) {
            addCriterion("target_side like", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideNotLike(String value) {
            addCriterion("target_side not like", value, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideIn(List<String> values) {
            addCriterion("target_side in", values, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideNotIn(List<String> values) {
            addCriterion("target_side not in", values, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideBetween(String value1, String value2) {
            addCriterion("target_side between", value1, value2, "targetSide");
            return (Criteria) this;
        }

        public Criteria andTargetSideNotBetween(String value1, String value2) {
            addCriterion("target_side not between", value1, value2, "targetSide");
            return (Criteria) this;
        }

        public Criteria andDeadLineIsNull() {
            addCriterion("dead_line is null");
            return (Criteria) this;
        }

        public Criteria andDeadLineIsNotNull() {
            addCriterion("dead_line is not null");
            return (Criteria) this;
        }

        public Criteria andDeadLineEqualTo(LocalDateTime value) {
            addCriterion("dead_line =", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineNotEqualTo(LocalDateTime value) {
            addCriterion("dead_line <>", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineGreaterThan(LocalDateTime value) {
            addCriterion("dead_line >", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("dead_line >=", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineLessThan(LocalDateTime value) {
            addCriterion("dead_line <", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("dead_line <=", value, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineIn(List<LocalDateTime> values) {
            addCriterion("dead_line in", values, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineNotIn(List<LocalDateTime> values) {
            addCriterion("dead_line not in", values, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("dead_line between", value1, value2, "deadLine");
            return (Criteria) this;
        }

        public Criteria andDeadLineNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("dead_line not between", value1, value2, "deadLine");
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

        public Criteria andContainerStatusIsNull() {
            addCriterion("Container_status is null");
            return (Criteria) this;
        }

        public Criteria andContainerStatusIsNotNull() {
            addCriterion("Container_status is not null");
            return (Criteria) this;
        }

        public Criteria andContainerStatusEqualTo(Integer value) {
            addCriterion("Container_status =", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusNotEqualTo(Integer value) {
            addCriterion("Container_status <>", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusGreaterThan(Integer value) {
            addCriterion("Container_status >", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("Container_status >=", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusLessThan(Integer value) {
            addCriterion("Container_status <", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusLessThanOrEqualTo(Integer value) {
            addCriterion("Container_status <=", value, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusIn(List<Integer> values) {
            addCriterion("Container_status in", values, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusNotIn(List<Integer> values) {
            addCriterion("Container_status not in", values, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusBetween(Integer value1, Integer value2) {
            addCriterion("Container_status between", value1, value2, "containerStatus");
            return (Criteria) this;
        }

        public Criteria andContainerStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("Container_status not between", value1, value2, "containerStatus");
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