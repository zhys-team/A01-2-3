/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-06-08
 */
package com.invoice.pojo;

import lombok.Getter;
import lombok.Setter;

import com.invoice.po.CustomerSupplier;
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
@ApiModel(value="CustomerSupplierPoJo",description="查询条件")
public class CustomerSupplierPoJo extends CustomerSupplier {

	
	/**
	 * 批量删除通过主键
	 */
	@ApiModelProperty(value="批量删除通过主键",name="ids")
	private String ids;

}