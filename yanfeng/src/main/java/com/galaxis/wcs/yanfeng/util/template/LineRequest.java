package com.galaxis.wcs.yanfeng.util.template;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * PLC的请求信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LineRequest extends LinePost {

    /**
     * 来自哪里
     */
    private String comeFrom;

    /**
     * 本箱是否抽检
     * 0: 不抽检
     * 1: 抽检
     */
    private Integer check;

    /**
     * 入库口的占用信息
     */
    private List<Integer> occupy;


    /**
     * 生产日期, 收货时用
     */
    private String productionDate;

    /**
     * 重量, 收货时用
     */
    private BigDecimal weight;

    /**
     * 长度, 收货时用, 范围
     */
    private BigDecimal length;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}

	public List<Integer> getOccupy() {
		return occupy;
	}

	public void setOccupy(List<Integer> occupy) {
		this.occupy = occupy;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
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

	

	
}
