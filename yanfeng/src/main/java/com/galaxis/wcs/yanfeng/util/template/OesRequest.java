package com.galaxis.wcs.yanfeng.util.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OesRequest {

    /**
     * 任务类型
     */
    private String description;

    /**
     * 箱号
     */
    private String cartonNo;

    /**
     * 操作人
     */
    private String createUser;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
