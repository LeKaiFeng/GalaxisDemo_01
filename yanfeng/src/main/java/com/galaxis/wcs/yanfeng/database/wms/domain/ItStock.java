package com.galaxis.wcs.yanfeng.database.wms.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * VM_IT_STOCK
 * @author 
 */
public class ItStock implements Serializable {
    private String orderNo;

    private String partNo;

    private String cartonNo;

    private BigDecimal qty;

    private String unit;

    private String stockType;

    private String erpWarehouse;

    private String prodLot;

    private String recLot;

    private static final long serialVersionUID = 1L;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getErpWarehouse() {
        return erpWarehouse;
    }

    public void setErpWarehouse(String erpWarehouse) {
        this.erpWarehouse = erpWarehouse;
    }

    public String getProdLot() {
        return prodLot;
    }

    public void setProdLot(String prodLot) {
        this.prodLot = prodLot;
    }

    public String getRecLot() {
        return recLot;
    }

    public void setRecLot(String recLot) {
        this.recLot = recLot;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ItStock other = (ItStock) that;
        return (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getCartonNo() == null ? other.getCartonNo() == null : this.getCartonNo().equals(other.getCartonNo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getErpWarehouse() == null ? other.getErpWarehouse() == null : this.getErpWarehouse().equals(other.getErpWarehouse()))
            && (this.getProdLot() == null ? other.getProdLot() == null : this.getProdLot().equals(other.getProdLot()))
            && (this.getRecLot() == null ? other.getRecLot() == null : this.getRecLot().equals(other.getRecLot()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getCartonNo() == null) ? 0 : getCartonNo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getErpWarehouse() == null) ? 0 : getErpWarehouse().hashCode());
        result = prime * result + ((getProdLot() == null) ? 0 : getProdLot().hashCode());
        result = prime * result + ((getRecLot() == null) ? 0 : getRecLot().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderNo=").append(orderNo);
        sb.append(", partNo=").append(partNo);
        sb.append(", cartonNo=").append(cartonNo);
        sb.append(", qty=").append(qty);
        sb.append(", unit=").append(unit);
        sb.append(", stockType=").append(stockType);
        sb.append(", erpWarehouse=").append(erpWarehouse);
        sb.append(", prodLot=").append(prodLot);
        sb.append(", recLot=").append(recLot);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}