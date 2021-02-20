/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-28
 */
package com.zhys.user.po;

import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="InvoiceAuth",description="")
public class InvoiceAuth implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	 @ApiModelProperty(value=" 主键 id",name="id")
	private Long id;

	/**
	 * 税控设备编号
	 */
	 @ApiModelProperty(value="税控设备编号",name="taxMechCode")
	private String taxMechCode;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="taxMechName")
	private String taxMechName;

	/**
	 * 发票种类
	 */
	 @ApiModelProperty(value="发票种类",name="invoiceType")
	private String invoiceType;

	/**
	 * 所属组织主键
	 */
	 @ApiModelProperty(value="所属组织主键",name="orgId")
	private Long orgId;
	 
	 /**
	 * 所属组织名称
	 */
	 @ApiModelProperty(value="所属组织名称",name="orgName")
	private Long orgName;


}