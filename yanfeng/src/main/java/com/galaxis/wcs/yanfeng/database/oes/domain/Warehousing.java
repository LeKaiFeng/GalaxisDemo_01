package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * t_lk_warehousing
 * @author 
 */
public class Warehousing implements Serializable {
    private Integer id;

    private String partNo;

    private String cartonNo;

    private BigDecimal qty;

    private String unit;

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

    public Warehousing() {
    }

    public Warehousing(Integer id, String partNo, String cartonNo, BigDecimal qty, String unit, String createUser, String createYmd, String createHms, String remark, String actflg, String actymd, String acthms, String sid, String guid) {
        this.id = id;
        this.partNo = partNo;
        this.cartonNo = cartonNo;
        this.qty = qty;
        this.unit = unit;
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
        Warehousing other = (Warehousing) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getCartonNo() == null ? other.getCartonNo() == null : this.getCartonNo().equals(other.getCartonNo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
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
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getCartonNo() == null) ? 0 : getCartonNo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
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
        sb.append(", partNo=").append(partNo);
        sb.append(", cartonNo=").append(cartonNo);
        sb.append(", qty=").append(qty);
        sb.append(", unit=").append(unit);
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