/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-09-27
 */
package com.invoice.pojo;

import lombok.Getter;
import lombok.Setter;

import com.invoice.po.IncomeTax;
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
@ApiModel(value="IncomeTaxPoJo",description="")
public class IncomeTaxPoJo extends IncomeTax implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String ids;

}