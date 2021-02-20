/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-11-01
 */
package com.invoice.po;

import java.sql.Date;
import java.math.BigDecimal;
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
@ApiModel(value="EquivalentSale",description="")
public class EquivalentSale implements IPO {

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
	 * 日期
	 */
	 @ApiModelProperty(value="日期",name="billDate")
	private Date billDate;

	/**
	 * 摘要
	 */
	 @ApiModelProperty(value="摘要",name="mark")
	private String mark;

	/**
	 * 计税方式  1：一般计税 2：简易计税
	 */
	 @ApiModelProperty(value="计税方式  1：一般计税 2：简易计税",name="taxType")
	private String taxType;

	/**
	 * 销项分类 1：货物及加工修理修配劳务  2：服务、不动产和无形资产
	 */
	 @ApiModelProperty(value="销项分类 1：货物及加工修理修配劳务  2：服务、不动产和无形资产",name="saleClassification")
	private String saleClassification;

	/**
	 * 税率
	 */
	 @ApiModelProperty(value="税率",name="taxRate")
	private String taxRate;

	/**
	 * 不含税销售额
	 */
	 @ApiModelProperty(value="不含税销售额",name="bhsxse")
	private BigDecimal bhsxse;

	/**
	 * 税额
	 */
	 @ApiModelProperty(value="税额",name="se")
	private BigDecimal se;

	/**
	 * 价税合计
	 */
	 @ApiModelProperty(value="价税合计",name="jshj")
	private BigDecimal jshj;

	/**
	 * 组织id
	 */
	 @ApiModelProperty(value="组织id",name="corpid")
	private String corpid;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="orgid")
	private String orgid;


}