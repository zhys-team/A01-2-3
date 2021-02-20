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
@ApiModel(value="AddTaxDetailFree",description="增值税申报表-增值税免税申报明细表")
public class AddTaxDetailFree implements IPO {

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
	 * 免税性质代码及名称
	 */
	 @ApiModelProperty(value="免税性质代码及名称",name="hmc")
	private String hmc;

	/**
	 * 栏次
	 */
	 @ApiModelProperty(value="栏次",name="lc")
	private String lc;

	/**
	 * 免征增值税项目销售额
	 */
	 @ApiModelProperty(value="免征增值税项目销售额",name="mzzzsxse")
	private String mzzzsxse;

	/**
	 * 免税销售额扣除项目本期实际扣除金额
	 */
	 @ApiModelProperty(value="免税销售额扣除项目本期实际扣除金额",name="mssjkcje")
	private String mssjkcje;

	/**
	 * 扣除后免税销售额
	 */
	 @ApiModelProperty(value="扣除后免税销售额",name="kchmsxse")
	private String kchmsxse;

	/**
	 * 免税销售额对应的进项税额
	 */
	 @ApiModelProperty(value="免税销售额对应的进项税额",name="msxsejxse")
	private String msxsejxse;

	/**
	 * 免税额
	 */
	 @ApiModelProperty(value="免税额",name="mse")
	private String mse;


}