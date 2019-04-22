package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * t_lk_rezult
 * @author 
 */
public class Rezult implements Serializable {
    private Integer id;

    private String orderNo;

    private String partNo;

    private String cartonNo;

    private BigDecimal qty;

    private String unit;

    private String prodLot;

    private String recLot;

    private String vendorCode;

    private String advanceDate;

    private String advanceTime;

    private String createUser;

    private String createYmd;

    private String createHms;

    private String remark;

    private String actflg;

    private String actymd;

    private String acthms;

    private String sid;

    private String guid;

    private static final long serialVersionUID = 1L;

    public Rezult() {
    }

    public Rezult(Integer id, String orderNo, String partNo, String cartonNo, BigDecimal qty, String unit, String prodLot, String recLot, String vendorCode, String advanceDate, String advanceTime, String createUser, String createYmd, String createHms, String remark, String actflg, String actymd, String acthms, String sid, String guid) {
        this.id = id;
        this.orderNo = orderNo;
        this.partNo = partNo;
        this.cartonNo = cartonNo;
        this.qty = qty;
        this.unit = unit;
        this.prodLot = prodLot;
        this.recLot = recLot;
        this.vendorCode = vendorCode;
        this.advanceDate = advanceDate;
        this.advanceTime = advanceTime;
        this.createUser = createUser;
        this.createYmd = createYmd;
        this.createHms = createHms;
        this.remark = remark;
        this.actflg = actflg;
        this.actymd = actymd;
        this.acthms = acthms;
        this.sid = sid;
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateYmd() {
        return createYmd;
    }

    public void setCreateYmd(String createYmd) {
        this.createYmd = createYmd;
    }

    public String getCreateHms() {
        return createHms;
    }

    public void setCreateHms(String createHms) {
        this.createHms = createHms;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActflg() {
        return actflg;
    }

    public void setActflg(String actflg) {
        this.actflg = actflg;
    }

    public String getActymd() {
        return actymd;
    }

    public void setActymd(String actymd) {
        this.actymd = actymd;
    }

    public String getActhms() {
        return acthms;
    }

    public void setActhms(String acthms) {
        this.acthms = acthms;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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
        Rezult other = (Rezult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getCartonNo() == null ? other.getCartonNo() == null : this.getCartonNo().equals(other.getCartonNo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getProdLot() == null ? other.getProdLot() == null : this.getProdLot().equals(other.getProdLot()))
            && (this.getRecLot() == null ? other.getRecLot() == null : this.getRecLot().equals(other.getRecLot()))
            && (this.getVendorCode() == null ? other.getVendorCode() == null : this.getVendorCode().equals(other.getVendorCode()))
            && (this.getAdvanceDate() == null ? other.getAdvanceDate() == null : this.getAdvanceDate().equals(other.getAdvanceDate()))
            && (this.getAdvanceTime() == null ? other.getAdvanceTime() == null : this.getAdvanceTime().equals(other.getAdvanceTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateYmd() == null ? other.getCreateYmd() == null : this.getCreateYmd().equals(other.getCreateYmd()))
            && (this.getCreateHms() == null ? other.getCreateHms() == null : this.getCreateHms().equals(other.getCreateHms()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getActflg() == null ? other.getActflg() == null : this.getActflg().equals(other.getActflg()))
            && (this.getActymd() == null ? other.getActymd() == null : this.getActymd().equals(other.getActymd()))
            && (this.getActhms() == null ? other.getActhms() == null : this.getActhms().equals(other.getActhms()))
            && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
            && (this.getGuid() == null ? other.getGuid() == null : this.getGuid().equals(other.getGuid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getCartonNo() == null) ? 0 : getCartonNo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getProdLot() == null) ? 0 : getProdLot().hashCode());
        result = prime * result + ((getRecLot() == null) ? 0 : getRecLot().hashCode());
        result = prime * result + ((getVendorCode() == null) ? 0 : getVendorCode().hashCode());
        result = prime * result + ((getAdvanceDate() == null) ? 0 : getAdvanceDate().hashCode());
        result = prime * result + ((getAdvanceTime() == null) ? 0 : getAdvanceTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateYmd() == null) ? 0 : getCreateYmd().hashCode());
        result = prime * result + ((getCreateHms() == null) ? 0 : getCreateHms().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getActflg() == null) ? 0 : getActflg().hashCode());
        result = prime * result + ((getActymd() == null) ? 0 : getActymd().hashCode());
        result = prime * result + ((getActhms() == null) ? 0 : getActhms().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getGuid() == null) ? 0 : getGuid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", partNo=").append(partNo);
        sb.append(", cartonNo=").append(cartonNo);
        sb.append(", qty=").append(qty);
        sb.append(", unit=").append(unit);
        sb.append(", prodLot=").append(prodLot);
        sb.append(", recLot=").append(recLot);
        sb.append(", vendorCode=").append(vendorCode);
        sb.append(", advanceDate=").append(advanceDate);
        sb.append(", advanceTime=").append(advanceTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", createYmd=").append(createYmd);
        sb.append(", createHms=").append(createHms);
        sb.append(", remark=").append(remark);
        sb.append(", actflg=").append(actflg);
        sb.append(", actymd=").append(actymd);
        sb.append(", acthms=").append(acthms);
        sb.append(", sid=").append(sid);
        sb.append(", guid=").append(guid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}