package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * t_main_task
 *
 * @author
 */
public class MainTask implements Serializable {
    private Integer id;

    private Integer type;

    private String description;

    private String orderNo;

    private String partNo;

    private String cartonNo;

    private Integer sLevel;

    private Integer sLocation;

    private Integer eLevel;

    private Integer eLocation;

    private Integer rLevel;

    private Integer rLocation;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime endTime;

    private Integer ktId;

    private String name;

    private String area;

    private String remark;

    private static final long serialVersionUID = 1L;

    public MainTask() {
    }

    public MainTask(Integer id, Integer type, String description, String orderNo, String partNo, String cartonNo, Integer sLevel, Integer sLocation, Integer eLevel, Integer eLocation, Integer rLevel, Integer rLocation, Integer status, LocalDateTime createTime, LocalDateTime endTime, Integer ktId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.orderNo = orderNo;
        this.partNo = partNo;
        this.cartonNo = cartonNo;
        this.sLevel = sLevel;
        this.sLocation = sLocation;
        this.eLevel = eLevel;
        this.eLocation = eLocation;
        this.rLevel = rLevel;
        this.rLocation = rLocation;
        this.status = status;
        this.createTime = createTime;
        this.endTime = endTime;
        this.ktId = ktId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getsLevel() {
        return sLevel;
    }

    public void setsLevel(Integer sLevel) {
        this.sLevel = sLevel;
    }

    public Integer getsLocation() {
        return sLocation;
    }

    public void setsLocation(Integer sLocation) {
        this.sLocation = sLocation;
    }

    public Integer geteLevel() {
        return eLevel;
    }

    public void seteLevel(Integer eLevel) {
        this.eLevel = eLevel;
    }

    public Integer geteLocation() {
        return eLocation;
    }

    public void seteLocation(Integer eLocation) {
        this.eLocation = eLocation;
    }

    public Integer getrLevel() {
        return rLevel;
    }

    public void setrLevel(Integer rLevel) {
        this.rLevel = rLevel;
    }

    public Integer getrLocation() {
        return rLocation;
    }

    public void setrLocation(Integer rLocation) {
        this.rLocation = rLocation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getKtId() {
        return ktId;
    }

    public void setKtId(Integer ktId) {
        this.ktId = ktId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
        MainTask other = (MainTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
                && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
                && (this.getCartonNo() == null ? other.getCartonNo() == null : this.getCartonNo().equals(other.getCartonNo()))
                && (this.getsLevel() == null ? other.getsLevel() == null : this.getsLevel().equals(other.getsLevel()))
                && (this.getsLocation() == null ? other.getsLocation() == null : this.getsLocation().equals(other.getsLocation()))
                && (this.geteLevel() == null ? other.geteLevel() == null : this.geteLevel().equals(other.geteLevel()))
                && (this.geteLocation() == null ? other.geteLocation() == null : this.geteLocation().equals(other.geteLocation()))
                && (this.getrLevel() == null ? other.getrLevel() == null : this.getrLevel().equals(other.getrLevel()))
                && (this.getrLocation() == null ? other.getrLocation() == null : this.getrLocation().equals(other.getrLocation()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getKtId() == null ? other.getKtId() == null : this.getKtId().equals(other.getKtId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getPartNo() == null) ? 0 : getPartNo().hashCode());
        result = prime * result + ((getCartonNo() == null) ? 0 : getCartonNo().hashCode());
        result = prime * result + ((getsLevel() == null) ? 0 : getsLevel().hashCode());
        result = prime * result + ((getsLocation() == null) ? 0 : getsLocation().hashCode());
        result = prime * result + ((geteLevel() == null) ? 0 : geteLevel().hashCode());
        result = prime * result + ((geteLocation() == null) ? 0 : geteLocation().hashCode());
        result = prime * result + ((getrLevel() == null) ? 0 : getrLevel().hashCode());
        result = prime * result + ((getrLocation() == null) ? 0 : getrLocation().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getKtId() == null) ? 0 : getKtId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", partNo=").append(partNo);
        sb.append(", cartonNo=").append(cartonNo);
        sb.append(", sLevel=").append(sLevel);
        sb.append(", sLocation=").append(sLocation);
        sb.append(", eLevel=").append(eLevel);
        sb.append(", eLocation=").append(eLocation);
        sb.append(", rLevel=").append(rLevel);
        sb.append(", rLocation=").append(rLocation);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", ktId=").append(ktId);
        sb.append(", name=").append(name);
        sb.append(", area=").append(area);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}