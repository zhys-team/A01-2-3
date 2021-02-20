/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-10-10
 */
package com.invoice.po;

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
@ApiModel(value="AddTaxTwo",description="增值税申报表-附表二")
public class AddTaxTwo implements IPO {

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
	 * 
	 */
	 @ApiModelProperty(value="",name="parentId")
	private Long parentId;

	/**
	 * 栏次
	 */
	 @ApiModelProperty(value="栏次",name="lc")
	private String lc;

	/**
	 * 项目
	 */
	 @ApiModelProperty(value="项目",name="hmc")
	private String hmc;

	/**
	 * 份数
	 */
	 @ApiModelProperty(value="份数",name="fs")
	private String fs;

	/**
	 * 金额
	 */
	 @ApiModelProperty(value="金额",name="je")
	private String je;

	/**
	 * 税额
	 */
	 @ApiModelProperty(value="税额",name="se")
	private String se;


}