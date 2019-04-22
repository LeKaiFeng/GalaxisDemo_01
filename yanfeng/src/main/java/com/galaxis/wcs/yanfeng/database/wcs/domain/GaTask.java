package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ga_task
 * @author 
 */
public class GaTask implements Serializable {
    private Integer id;

    private Integer oLevel;

    private Integer oPos;

    private Integer tLevel;

    private Integer tPos;

    private Integer type;

    private Integer sLevel;

    private Integer sPos;

    private Integer sLocation;

    private Integer eLevel;

    private Integer ePos;

    private Integer eLocation;

    private Integer rLevel;

    private Integer rPos;

    private Integer rLocation;

    private Integer state;

    private String boxNumber;

    private Integer weight;

    private Integer priority;

    private Integer carNumber;

    private String aisle;

    private String area;

    private String wmsid;

    private LocalDateTime createTime;

    private LocalDateTime assignTime;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private Integer announceId;

    private String targetPos;

    private String targetSide;

    private LocalDateTime deadLine;

    private String liftArea;

    private Integer status;

    private Integer boxType;

    private Integer containerStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getoLevel() {
        return oLevel;
    }

    public void setoLevel(Integer oLevel) {
        this.oLevel = oLevel;
    }

    public Integer getoPos() {
        return oPos;
    }

    public void setoPos(Integer oPos) {
        this.oPos = oPos;
    }

    public Integer gettLevel() {
        return tLevel;
    }

    public void settLevel(Integer tLevel) {
        this.tLevel = tLevel;
    }

    public Integer gettPos() {
        return tPos;
    }

    public void settPos(Integer tPos) {
        this.tPos = tPos;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getsLevel() {
        return sLevel;
    }

    public void setsLevel(Integer sLevel) {
        this.sLevel = sLevel;
    }

    public Integer getsPos() {
        return sPos;
    }

    public void setsPos(Integer sPos) {
        this.sPos = sPos;
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

    public Integer getePos() {
        return ePos;
    }

    public void setePos(Integer ePos) {
        this.ePos = ePos;
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

    public Integer getrPos() {
        return rPos;
    }

    public void setrPos(Integer rPos) {
        this.rPos = rPos;
    }

    public Integer getrLocation() {
        return rLocation;
    }

    public void setrLocation(Integer rLocation) {
        this.rLocation = rLocation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWmsid() {
        return wmsid;
    }

    public void setWmsid(String wmsid) {
        this.wmsid = wmsid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(LocalDateTime assignTime) {
        this.assignTime = assignTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(Integer announceId) {
        this.announceId = announceId;
    }

    public String getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(String targetPos) {
        this.targetPos = targetPos;
    }

    public String getTargetSide() {
        return targetSide;
    }

    public void setTargetSide(String targetSide) {
        this.targetSide = targetSide;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public String getLiftArea() {
        return liftArea;
    }

    public void setLiftArea(String liftArea) {
        this.liftArea = liftArea;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
    }

    public Integer getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(Integer containerStatus) {
        this.containerStatus = containerStatus;
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
        GaTask other = (GaTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getoLevel() == null ? other.getoLevel() == null : this.getoLevel().equals(other.getoLevel()))
            && (this.getoPos() == null ? other.getoPos() == null : this.getoPos().equals(other.getoPos()))
            && (this.gettLevel() == null ? other.gettLevel() == null : this.gettLevel().equals(other.gettLevel()))
            && (this.gettPos() == null ? other.gettPos() == null : this.gettPos().equals(other.gettPos()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getsLevel() == null ? other.getsLevel() == null : this.getsLevel().equals(other.getsLevel()))
            && (this.getsPos() == null ? other.getsPos() == null : this.getsPos().equals(other.getsPos()))
            && (this.getsLocation() == null ? other.getsLocation() == null : this.getsLocation().equals(other.getsLocation()))
            && (this.geteLevel() == null ? other.geteLevel() == null : this.geteLevel().equals(other.geteLevel()))
            && (this.getePos() == null ? other.getePos() == null : this.getePos().equals(other.getePos()))
            && (this.geteLocation() == null ? other.geteLocation() == null : this.geteLocation().equals(other.geteLocation()))
            && (this.getrLevel() == null ? other.getrLevel() == null : this.getrLevel().equals(other.getrLevel()))
            && (this.getrPos() == null ? other.getrPos() == null : this.getrPos().equals(other.getrPos()))
            && (this.getrLocation() == null ? other.getrLocation() == null : this.getrLocation().equals(other.getrLocation()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getBoxNumber() == null ? other.getBoxNumber() == null : this.getBoxNumber().equals(other.getBoxNumber()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getCarNumber() == null ? other.getCarNumber() == null : this.getCarNumber().equals(other.getCarNumber()))
            && (this.getAisle() == null ? other.getAisle() == null : this.getAisle().equals(other.getAisle()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getWmsid() == null ? other.getWmsid() == null : this.getWmsid().equals(other.getWmsid()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAssignTime() == null ? other.getAssignTime() == null : this.getAssignTime().equals(other.getAssignTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getFinishTime() == null ? other.getFinishTime() == null : this.getFinishTime().equals(other.getFinishTime()))
            && (this.getAnnounceId() == null ? other.getAnnounceId() == null : this.getAnnounceId().equals(other.getAnnounceId()))
            && (this.getTargetPos() == null ? other.getTargetPos() == null : this.getTargetPos().equals(other.getTargetPos()))
            && (this.getTargetSide() == null ? other.getTargetSide() == null : this.getTargetSide().equals(other.getTargetSide()))
            && (this.getDeadLine() == null ? other.getDeadLine() == null : this.getDeadLine().equals(other.getDeadLine()))
            && (this.getLiftArea() == null ? other.getLiftArea() == null : this.getLiftArea().equals(other.getLiftArea()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBoxType() == null ? other.getBoxType() == null : this.getBoxType().equals(other.getBoxType()))
            && (this.getContainerStatus() == null ? other.getContainerStatus() == null : this.getContainerStatus().equals(other.getContainerStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getoLevel() == null) ? 0 : getoLevel().hashCode());
        result = prime * result + ((getoPos() == null) ? 0 : getoPos().hashCode());
        result = prime * result + ((gettLevel() == null) ? 0 : gettLevel().hashCode());
        result = prime * result + ((gettPos() == null) ? 0 : gettPos().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getsLevel() == null) ? 0 : getsLevel().hashCode());
        result = prime * result + ((getsPos() == null) ? 0 : getsPos().hashCode());
        result = prime * result + ((getsLocation() == null) ? 0 : getsLocation().hashCode());
        result = prime * result + ((geteLevel() == null) ? 0 : geteLevel().hashCode());
        result = prime * result + ((getePos() == null) ? 0 : getePos().hashCode());
        result = prime * result + ((geteLocation() == null) ? 0 : geteLocation().hashCode());
        result = prime * result + ((getrLevel() == null) ? 0 : getrLevel().hashCode());
        result = prime * result + ((getrPos() == null) ? 0 : getrPos().hashCode());
        result = prime * result + ((getrLocation() == null) ? 0 : getrLocation().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getBoxNumber() == null) ? 0 : getBoxNumber().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getCarNumber() == null) ? 0 : getCarNumber().hashCode());
        result = prime * result + ((getAisle() == null) ? 0 : getAisle().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getWmsid() == null) ? 0 : getWmsid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAssignTime() == null) ? 0 : getAssignTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getFinishTime() == null) ? 0 : getFinishTime().hashCode());
        result = prime * result + ((getAnnounceId() == null) ? 0 : getAnnounceId().hashCode());
        result = prime * result + ((getTargetPos() == null) ? 0 : getTargetPos().hashCode());
        result = prime * result + ((getTargetSide() == null) ? 0 : getTargetSide().hashCode());
        result = prime * result + ((getDeadLine() == null) ? 0 : getDeadLine().hashCode());
        result = prime * result + ((getLiftArea() == null) ? 0 : getLiftArea().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBoxType() == null) ? 0 : getBoxType().hashCode());
        result = prime * result + ((getContainerStatus() == null) ? 0 : getContainerStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", oLevel=").append(oLevel);
        sb.append(", oPos=").append(oPos);
        sb.append(", tLevel=").append(tLevel);
        sb.append(", tPos=").append(tPos);
        sb.append(", type=").append(type);
        sb.append(", sLevel=").append(sLevel);
        sb.append(", sPos=").append(sPos);
        sb.append(", sLocation=").append(sLocation);
        sb.append(", eLevel=").append(eLevel);
        sb.append(", ePos=").append(ePos);
        sb.append(", eLocation=").append(eLocation);
        sb.append(", rLevel=").append(rLevel);
        sb.append(", rPos=").append(rPos);
        sb.append(", rLocation=").append(rLocation);
        sb.append(", state=").append(state);
        sb.append(", boxNumber=").append(boxNumber);
        sb.append(", weight=").append(weight);
        sb.append(", priority=").append(priority);
        sb.append(", carNumber=").append(carNumber);
        sb.append(", aisle=").append(aisle);
        sb.append(", area=").append(area);
        sb.append(", wmsid=").append(wmsid);
        sb.append(", createTime=").append(createTime);
        sb.append(", assignTime=").append(assignTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", announceId=").append(announceId);
        sb.append(", targetPos=").append(targetPos);
        sb.append(", targetSide=").append(targetSide);
        sb.append(", deadLine=").append(deadLine);
        sb.append(", liftArea=").append(liftArea);
        sb.append(", status=").append(status);
        sb.append(", boxType=").append(boxType);
        sb.append(", containerStatus=").append(containerStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}