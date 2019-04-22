package com.galaxis.wcs.yanfeng.util.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WcsSend {
    private Integer id;
    private String messageName;
    private String boxId;
    private Integer sLevel;
    private Integer sLocation;
    private Long wmsId;
    private Integer priority = 1;
    private Object data;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
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
	public Long getWmsId() {
		return wmsId;
	}
	public void setWmsId(Long wmsId) {
		this.wmsId = wmsId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setSLevel(Integer getsLevel) {
		// TODO Auto-generated method stub
		
	}
	public void setSLocation(Integer getsLocation) {
		// TODO Auto-generated method stub
		
	}

}
