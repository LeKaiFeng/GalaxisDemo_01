package com.galaxis.wcs.yanfeng.database.oes.domain;

import java.io.Serializable;

/**
 * t_lk_quality
 * @author 
 */
public class Quality implements Serializable {
    private Integer id;

    private String partNo;

    private Integer actType;

    private String enabled;

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

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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
        Quality other = (Quality) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartNo() == null ? other.getPartNo() == null : this.getPartNo().equals(other.getPartNo()))
            && (this.getActType() == null ? other.getActType() == null : this.getActType().equals(other.getActType()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
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
        result = prime * result + ((getActType() == null) ? 0 : getActType().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
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
        sb.append(", actType=").append(actType);
        sb.append(", enabled=").append(enabled);
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