package com.galaxis.wcs.yanfeng.database.wms.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItStockExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ItStockExample() {
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

        public Criteria andStockTypeIsNull() {
            addCriterion("stock_type is null");
            return (Criteria) this;
        }

        public Criteria andStockTypeIsNotNull() {
            addCriterion("stock_type is not null");
            return (Criteria) this;
        }

        public Criteria andStockTypeEqualTo(String value) {
            addCriterion("stock_type =", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotEqualTo(String value) {
            addCriterion("stock_type <>", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThan(String value) {
            addCriterion("stock_type >", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeGreaterThanOrEqualTo(String value) {
            addCriterion("stock_type >=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThan(String value) {
            addCriterion("stock_type <", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLessThanOrEqualTo(String value) {
            addCriterion("stock_type <=", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeLike(String value) {
            addCriterion("stock_type like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotLike(String value) {
            addCriterion("stock_type not like", value, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeIn(List<String> values) {
            addCriterion("stock_type in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotIn(List<String> values) {
            addCriterion("stock_type not in", values, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeBetween(String value1, String value2) {
            addCriterion("stock_type between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andStockTypeNotBetween(String value1, String value2) {
            addCriterion("stock_type not between", value1, value2, "stockType");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseIsNull() {
            addCriterion("erp_warehouse is null");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseIsNotNull() {
            addCriterion("erp_warehouse is not null");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseEqualTo(String value) {
            addCriterion("erp_warehouse =", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseNotEqualTo(String value) {
            addCriterion("erp_warehouse <>", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseGreaterThan(String value) {
            addCriterion("erp_warehouse >", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("erp_warehouse >=", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseLessThan(String value) {
            addCriterion("erp_warehouse <", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseLessThanOrEqualTo(String value) {
            addCriterion("erp_warehouse <=", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseLike(String value) {
            addCriterion("erp_warehouse like", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseNotLike(String value) {
            addCriterion("erp_warehouse not like", value, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseIn(List<String> values) {
            addCriterion("erp_warehouse in", values, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseNotIn(List<String> values) {
            addCriterion("erp_warehouse not in", values, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseBetween(String value1, String value2) {
            addCriterion("erp_warehouse between", value1, value2, "erpWarehouse");
            return (Criteria) this;
        }

        public Criteria andErpWarehouseNotBetween(String value1, String value2) {
            addCriterion("erp_warehouse not between", value1, value2, "erpWarehouse");
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