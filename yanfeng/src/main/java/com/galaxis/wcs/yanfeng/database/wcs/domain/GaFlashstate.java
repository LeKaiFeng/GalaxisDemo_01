package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.io.Serializable;

/**
 * ga_flashstate
 * @author 
 */
public class GaFlashstate implements Serializable {
    private Integer carNumber;

    private String type;

    private Integer carLevel;

    private Integer carPos;

    private Integer flashState;

    private String ip;

    private Integer port;

    private Integer wmscommand;

    private Integer workmode;

    private Integer isActive;

    private Integer orderLiftNo;

    private Integer nLevel;

    private Integer inoutPossible;

    private Integer nPos;

    private static final long serialVersionUID = 1L;

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCarLevel() {
        return carLevel;
    }

    public void setCarLevel(Integer carLevel) {
        this.carLevel = carLevel;
    }

    public Integer getCarPos() {
        return carPos;
    }

    public void setCarPos(Integer carPos) {
        this.carPos = carPos;
    }

    public Integer getFlashState() {
        return flashState;
    }

    public void setFlashState(Integer flashState) {
        this.flashState = flashState;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWmscommand() {
        return wmscommand;
    }

    public void setWmscommand(Integer wmscommand) {
        this.wmscommand = wmscommand;
    }

    public Integer getWorkmode() {
        return workmode;
    }

    public void setWorkmode(Integer workmode) {
        this.workmode = workmode;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getOrderLiftNo() {
        return orderLiftNo;
    }

    public void setOrderLiftNo(Integer orderLiftNo) {
        this.orderLiftNo = orderLiftNo;
    }

    public Integer getnLevel() {
        return nLevel;
    }

    public void setnLevel(Integer nLevel) {
        this.nLevel = nLevel;
    }

    public Integer getInoutPossible() {
        return inoutPossible;
    }

    public void setInoutPossible(Integer inoutPossible) {
        this.inoutPossible = inoutPossible;
    }

    public Integer getnPos() {
        return nPos;
    }

    public void setnPos(Integer nPos) {
        this.nPos = nPos;
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
        GaFlashstate other = (GaFlashstate) that;
        return (this.getCarNumber() == null ? other.getCarNumber() == null : this.getCarNumber().equals(other.getCarNumber()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCarLevel() == null ? other.getCarLevel() == null : this.getCarLevel().equals(other.getCarLevel()))
            && (this.getCarPos() == null ? other.getCarPos() == null : this.getCarPos().equals(other.getCarPos()))
            && (this.getFlashState() == null ? other.getFlashState() == null : this.getFlashState().equals(other.getFlashState()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getWmscommand() == null ? other.getWmscommand() == null : this.getWmscommand().equals(other.getWmscommand()))
            && (this.getWorkmode() == null ? other.getWorkmode() == null : this.getWorkmode().equals(other.getWorkmode()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()))
            && (this.getOrderLiftNo() == null ? other.getOrderLiftNo() == null : this.getOrderLiftNo().equals(other.getOrderLiftNo()))
            && (this.getnLevel() == null ? other.getnLevel() == null : this.getnLevel().equals(other.getnLevel()))
            && (this.getInoutPossible() == null ? other.getInoutPossible() == null : this.getInoutPossible().equals(other.getInoutPossible()))
            && (this.getnPos() == null ? other.getnPos() == null : this.getnPos().equals(other.getnPos()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCarNumber() == null) ? 0 : getCarNumber().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCarLevel() == null) ? 0 : getCarLevel().hashCode());
        result = prime * result + ((getCarPos() == null) ? 0 : getCarPos().hashCode());
        result = prime * result + ((getFlashState() == null) ? 0 : getFlashState().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getWmscommand() == null) ? 0 : getWmscommand().hashCode());
        result = prime * result + ((getWorkmode() == null) ? 0 : getWorkmode().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
        result = prime * result + ((getOrderLiftNo() == null) ? 0 : getOrderLiftNo().hashCode());
        result = prime * result + ((getnLevel() == null) ? 0 : getnLevel().hashCode());
        result = prime * result + ((getInoutPossible() == null) ? 0 : getInoutPossible().hashCode());
        result = prime * result + ((getnPos() == null) ? 0 : getnPos().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", carNumber=").append(carNumber);
        sb.append(", type=").append(type);
        sb.append(", carLevel=").append(carLevel);
        sb.append(", carPos=").append(carPos);
        sb.append(", flashState=").append(flashState);
        sb.append(", ip=").append(ip);
        sb.append(", port=").append(port);
        sb.append(", wmscommand=").append(wmscommand);
        sb.append(", workmode=").append(workmode);
        sb.append(", isActive=").append(isActive);
        sb.append(", orderLiftNo=").append(orderLiftNo);
        sb.append(", nLevel=").append(nLevel);
        sb.append(", inoutPossible=").append(inoutPossible);
        sb.append(", nPos=").append(nPos);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}