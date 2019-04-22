package com.galaxis.wcs.yanfeng.database.wcs.domain;

import java.io.Serializable;

/**
 * ga_locations
 * @author 
 */
public class GaLocation extends GaLocationKey implements Serializable {
    private Integer pos;

    private Integer aisle;

    private Integer state;

    private String area;

    private Integer priority;

    private String boxNumber;

    private Integer weight;

    private String liftArea;

    private Integer type;

    private String colorArea;

    private Integer boxType;

    private static final long serialVersionUID = 1L;

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getAisle() {
        return aisle;
    }

    public void setAisle(Integer aisle) {
        this.aisle = aisle;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public String getLiftArea() {
        return liftArea;
    }

    public void setLiftArea(String liftArea) {
        this.liftArea = liftArea;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getColorArea() {
        return colorArea;
    }

    public void setColorArea(String colorArea) {
        this.colorArea = colorArea;
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
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
        GaLocation other = (GaLocation) that;
        return (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getPos() == null ? other.getPos() == null : this.getPos().equals(other.getPos()))
            && (this.getAisle() == null ? other.getAisle() == null : this.getAisle().equals(other.getAisle()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getBoxNumber() == null ? other.getBoxNumber() == null : this.getBoxNumber().equals(other.getBoxNumber()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getLiftArea() == null ? other.getLiftArea() == null : this.getLiftArea().equals(other.getLiftArea()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getColorArea() == null ? other.getColorArea() == null : this.getColorArea().equals(other.getColorArea()))
            && (this.getBoxType() == null ? other.getBoxType() == null : this.getBoxType().equals(other.getBoxType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getPos() == null) ? 0 : getPos().hashCode());
        result = prime * result + ((getAisle() == null) ? 0 : getAisle().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getBoxNumber() == null) ? 0 : getBoxNumber().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getLiftArea() == null) ? 0 : getLiftArea().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getColorArea() == null) ? 0 : getColorArea().hashCode());
        result = prime * result + ((getBoxType() == null) ? 0 : getBoxType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pos=").append(pos);
        sb.append(", aisle=").append(aisle);
        sb.append(", state=").append(state);
        sb.append(", area=").append(area);
        sb.append(", priority=").append(priority);
        sb.append(", boxNumber=").append(boxNumber);
        sb.append(", weight=").append(weight);
        sb.append(", liftArea=").append(liftArea);
        sb.append(", type=").append(type);
        sb.append(", colorArea=").append(colorArea);
        sb.append(", boxType=").append(boxType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}