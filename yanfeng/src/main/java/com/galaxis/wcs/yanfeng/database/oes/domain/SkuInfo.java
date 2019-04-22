package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * t_sku_info
 * @author 
 */
public class SkuInfo implements Serializable {
    private Integer id;

    private String partNo;

    private BigDecimal weight;

    private Integer checkTotal;

    private Integer checkRule;

    private Integer countTotal;

    private Integer countRule;

    /**
     * 抽点倍数
     */
    private BigDecimal countTimes;

    private Integer status;

    private String remark;

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    public Integer getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(Integer checkRule) {
        this.checkRule = checkRule;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public Integer getCountRule() {
        return countRule;
    }

    public void setCountRule(Integer countRule) {
        this.countRule = countRule;
    }

    public BigDecimal getCountTimes() {
        return countTimes;
    }

    public void setCountTimes(BigDecimal countTimes) {
        this.countTimes = countTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        SkuInfo other = (SkuInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getCheckTotal() == null ? other.getCheckTotal() == null : this.getCheckTotal().equals(other.getCheckTotal()))
            && (this.getCheckRule() == null ? other.getCheckRule() == null : this.getCheckRule().equals(other.getCheckRule()))
            && (this.getCountTotal() == null ? other.getCountTotal() == null : this.getCountTotal().equals(other.getCountTotal()))
            && (this.getCountRule() == null ? other.getCountRule() == null : this.getCountRule().equals(other.getCountRule()))
            && (this.getCountTimes() == null ? other.getCountTimes() == null : this.getCountTimes().equals(other.getCountTimes()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getCheckTotal() == null) ? 0 : getCheckTotal().hashCode());
        result = prime * result + ((getCheckRule() == null) ? 0 : getCheckRule().hashCode());
        result = prime * result + ((getCountTotal() == null) ? 0 : getCountTotal().hashCode());
        result = prime * result + ((getCountRule() == null) ? 0 : getCountRule().hashCode());
        result = prime * result + ((getCountTimes() == null) ? 0 : getCountTimes().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", weight=").append(weight);
        sb.append(", checkTotal=").append(checkTotal);
        sb.append(", checkRule=").append(checkRule);
        sb.append(", countTotal=").append(countTotal);
        sb.append(", countRule=").append(countRule);
        sb.append(", countTimes=").append(countTimes);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}