/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-10-19
 */
package com.invoice.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
@ApiModel(value="FinanceSheet",description="")
public class FinanceSheet implements IPO {

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
	 * 纳税识别号
	 */
	 @ApiModelProperty(value="纳税识别号",name="nssbh")
	private String nssbh;

	/**
	 * 所属期
	 */
	 @ApiModelProperty(value="所属期",name="periodYear")
	private Integer periodYear;

	/**
	 * 季度
	 */
	 @ApiModelProperty(value="季度",name="periodQuater")
	private Integer periodQuater;

	/**
	 * 纳税人名称
	 */
	 @ApiModelProperty(value="纳税人名称",name="nsrmc")
	private String nsrmc;

	/**
	 * 资产负债日
	 */
	 @ApiModelProperty(value="资产负债日",name="zcfzr")
	private String zcfzr;

	/**
	 * 所属日期起
	 */
	 @ApiModelProperty(value="所属日期起",name="ssrqq")
	private String ssrqq;

	/**
	 * 所属日期止
	 */
	 @ApiModelProperty(value="所属日期止",name="ssrqz")
	private String ssrqz;

	 /**
		 * 组织id
		 */
		 @ApiModelProperty(value="组织id",name="orgId")
		private String orgId;
		 
		 private List<FinanceBalanceSheet> balances;
		 private List<FinanceProfitSheet> profits;
}