/**
 * Copyright 2017-2019 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2019-04-22
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
@ApiModel(value="RuleMatchSub",description="规则匹配库明细")
public class RuleMatchSub implements IPO {

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
	 * 发票类型（从数据字典中获取）
	 */
	 @ApiModelProperty(value="发票类型（从数据字典中获取）",name="invoiceType")
	private Long invoiceType;

	/**
	 * 1:启用 2：停用
	 */
	 @ApiModelProperty(value="1:启用 2：停用",name="isEnable")
	private String isEnable;

	/**
	 * 是否通过明细判断  1：是  2：否
	 */
	 @ApiModelProperty(value="是否通过明细判断  1：是  2：否",name="isDetailJudue")
	private String isDetailJudue;

	/**
	 * 明细字段
	 */
	 @ApiModelProperty(value="明细字段",name="detailField")
	private String detailField;

	/**
	 * 排除/选取 1:排除   2：选取
	 */
	 @ApiModelProperty(value="排除/选取 1:排除   2：选取",name="exclusionOrSelection")
	private String exclusionOrSelection;

	/**
	 * 税额计算方式(取票面税额、通过税率计算）
	 */
	 @ApiModelProperty(value="税额计算方式(取票面税额、通过税率计算）",name="taxCalMethod")
	private String taxCalMethod;

	/**
	 * 税率（%）
	 */
	 @ApiModelProperty(value="税率（%）",name="taxRate")
	private Integer taxRate;

	/**
	 * 是否特殊票据（例：海关缴纳书） 1:否 2：是
	 */
	 @ApiModelProperty(value="是否特殊票据（例：海关缴纳书） 1:否 2：是",name="isSpecialBill")
	private String isSpecialBill;

	/**
	 * 特殊票据编码
	 */
	 @ApiModelProperty(value="特殊票据编码",name="specialNo")
	private String specialNo;

	/**
	 * 规则库主键
	 */
	 @ApiModelProperty(value="规则库主键",name="ruleId")
	private Long ruleId;


}