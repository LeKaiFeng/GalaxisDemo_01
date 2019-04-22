package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * t_box
 * @author 
 */
public class Box implements Serializable {
    private Integer id;

    private String cartonNo;

    private String partNo;

    private String orderNo;

    private Integer level;

    private Integer location;

    private String area;

    private BigDecimal qty;

    private String unit;

    private BigDecimal weight;

    private BigDecimal length;

    private Integer boxPosition;

    private Integer status;

    private Integer nc;

    private String remark;

    private Integer plcSeq;

    private Integer plcTaskId;

    private String createUser;

    private String prodLot;

    private String recLot;

    private String vendorCode;

    private LocalDateTime orderRecTime;

    private LocalDateTime realRecTime;

    private String advanceDate;

    private String advanceTime;

    private static final long serialVersionUID = 1L;

    public Box() {
    }

    public Box(Integer id, String cartonNo, String partNo, String orderNo, Integer level, Integer location, String area, BigDecimal qty, String unit, BigDecimal weight, BigDecimal length, Integer boxPosition, Integer status, Integer nc, String remark, Integer plcSeq, Integer plcTaskId, String createUser, String prodLot, String recLot, String vendorCode, LocalDateTime orderRecTime, LocalDateTime realRecTime, String advanceDate, String advanceTime) {
        this.id = id;
        this.cartonNo = cartonNo;
        this.partNo = partNo;
        this.orderNo = orderNo;
        this.level = level;
        this.location = location;
        this.area = area;
        this.qty = qty;
        this.unit = unit;
        this.weight = weight;
        this.length = length;
        this.boxPosition = boxPosition;
        this.status = status;
        this.nc = nc;
        this.remark = remark;
        this.plcSeq = plcSeq;
        this.plcTaskId = plcTaskId;
        this.createUser = createUser;
        this.prodLot = prodLot;
        this.recLot = recLot;
        this.vendorCode = vendorCode;
        this.orderRecTime = orderRecTime;
        this.realRecTime = realRecTime;
        this.advanceDate = advanceDate;
        this.advanceTime = advanceTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Integer getBoxPosition() {
        return boxPosition;
    }

    public void setBoxPosition(Integer boxPosition) {
        this.boxPosition = boxPosition;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNc() {
        return nc;
    }

    public void setNc(Integer nc) {
        this.nc = nc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPlcSeq() {
        return plcSeq;
    }

    public void setPlcSeq(Integer plcSeq) {
        this.plcSeq = plcSeq;
    }

    public Integer getPlcTaskId() {
        return plcTaskId;
    }

    public void setPlcTaskId(Integer plcTaskId) {
        this.plcTaskId = plcTaskId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public LocalDateTime getOrderRecTime() {
        return orderRecTime;
    }

    public void setOrderRecTime(LocalDateTime orderRecTime) {
        this.orderRecTime = orderRecTime;
    }

    public LocalDateTime getRealRecTime() {
        return realRecTime;
    }

    public void setRealRecTime(LocalDateTime realRecTime) {
        this.realRecTime = realRecTime;
    }

    public String getAdvanceDate() {
        return advanceDate;
    }

    public void setAdvanceDate(String advanceDate) {
        this.advanceDate = advanceDate;
    }

    public String getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(String advanceTime) {
        this.advanceTime = advanceTime;
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
        Box other = (Box) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCartonNo() == null ? other.getCartonNo() == null : this.getCartonNo().equals(other.getCartonNo()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
            && (this.getBoxPosition() == null ? other.getBoxPosition() == null : this.getBoxPosition().equals(other.getBoxPosition()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getNc() == null ? other.getNc() == null : this.getNc().equals(other.getNc()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getPlcSeq() == null ? other.getPlcSeq() == null : this.getPlcSeq().equals(other.getPlcSeq()))
            && (this.getPlcTaskId() == null ? other.getPlcTaskId() == null : this.getPlcTaskId().equals(other.getPlcTaskId()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getProdLot() == null ? other.getProdLot() == null : this.getProdLot().equals(other.getProdLot()))
            && (this.getRecLot() == null ? other.getRecLot() == null : this.getRecLot().equals(other.getRecLot()))
            && (this.getVendorCode() == null ? other.getVendorCode() == null : this.getVendorCode().equals(other.getVendorCode()))
            && (this.getOrderRecTime() == null ? other.getOrderRecTime() == null : this.getOrderRecTime().equals(other.getOrderRecTime()))
            && (this.getRealRecTime() == null ? other.getRealRecTime() == null : this.getRealRecTime().equals(other.getRealRecTime()))
            && (this.getAdvanceDate() == null ? other.getAdvanceDate() == null : this.getAdvanceDate().equals(other.getAdvanceDate()))
            && (this.getAdvanceTime() == null ? other.getAdvanceTime() == null : this.getAdvanceTime().equals(other.getAdvanceTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCartonNo() == null) ? 0 : getCartonNo().hashCode());
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getBoxPosition() == null) ? 0 : getBoxPosition().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNc() == null) ? 0 : getNc().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getPlcSeq() == null) ? 0 : getPlcSeq().hashCode());
        result = prime * result + ((getPlcTaskId() == null) ? 0 : getPlcTaskId().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getProdLot() == null) ? 0 : getProdLot().hashCode());
        result = prime * result + ((getRecLot() == null) ? 0 : getRecLot().hashCode());
        result = prime * result + ((getVendorCode() == null) ? 0 : getVendorCode().hashCode());
        result = prime * result + ((getOrderRecTime() == null) ? 0 : getOrderRecTime().hashCode());
        result = prime * result + ((getRealRecTime() == null) ? 0 : getRealRecTime().hashCode());
        result = prime * result + ((getAdvanceDate() == null) ? 0 : getAdvanceDate().hashCode());
        result = prime * result + ((getAdvanceTime() == null) ? 0 : getAdvanceTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cartonNo=").append(cartonNo);
        sb.append(", partNo=").append(partNo);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", level=").append(level);
        sb.append(", location=").append(location);
        sb.append(", area=").append(area);
        sb.append(", qty=").append(qty);
        sb.append(", unit=").append(unit);
        sb.append(", weight=").append(weight);
        sb.append(", length=").append(length);
        sb.append(", boxPosition=").append(boxPosition);
        sb.append(", status=").append(status);
        sb.append(", nc=").append(nc);
        sb.append(", remark=").append(remark);
        sb.append(", plcSeq=").append(plcSeq);
        sb.append(", plcTaskId=").append(plcTaskId);
        sb.append(", createUser=").append(createUser);
        sb.append(", prodLot=").append(prodLot);
        sb.append(", recLot=").append(recLot);
        sb.append(", vendorCode=").append(vendorCode);
        sb.append(", orderRecTime=").append(orderRecTime);
        sb.append(", realRecTime=").append(realRecTime);
        sb.append(", advanceDate=").append(advanceDate);
        sb.append(", advanceTime=").append(advanceTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}