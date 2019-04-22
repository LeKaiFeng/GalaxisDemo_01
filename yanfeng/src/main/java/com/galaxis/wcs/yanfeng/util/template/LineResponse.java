package com.galaxis.wcs.yanfeng.util.template;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LineResponse extends LinePost {

    /**
     * 停线报警
     * 0: 不停线
     * 1: 停线
     */
    private Integer warn = 0;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}

	public void setErrorCode(Integer errorCodeNoBarcodeOnCheck) {
		// TODO Auto-generated method stub
		
	}

	public void setCartonNo(String cartonNo) {
		// TODO Auto-generated method stub
		
	}

	public void setLocation(Object location) {
		// TODO Auto-generated method stub
		
	}

	
}
