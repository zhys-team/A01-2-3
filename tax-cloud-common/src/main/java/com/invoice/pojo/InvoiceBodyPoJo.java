/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-24
 */
package com.invoice.pojo;


import lombok.Getter;
import lombok.Setter;

import com.invoice.po.InvoiceBody;
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
@ApiModel(value="InvoiceBodyPoJo",description="发票明细信息查询条件")
public class InvoiceBodyPoJo extends InvoiceBody {
	
	/**
	 * 主表MIDs
	 */
	@ApiModelProperty(value="主表MIDs",name="headIds")
	private String headIds;

}