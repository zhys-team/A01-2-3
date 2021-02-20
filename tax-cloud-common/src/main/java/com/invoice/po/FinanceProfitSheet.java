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
@ApiModel(value="FinanceProfitSheet",description="")
public class FinanceProfitSheet implements IPO {

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
	 * 行号
	 */
	 @ApiModelProperty(value="行号",name="hh")
	private String hh;

	/**
	 * 行名称
	 */
	 @ApiModelProperty(value="行名称",name="hmc")
	private String hmc;

	/**
	 * 本期金额
	 */
	 @ApiModelProperty(value="本期金额",name="bqje")
	private String bqje;

	/**
	 * 上期金额
	 */
	 @ApiModelProperty(value="上期金额",name="sqjr")
	private String sqjr;

	/**
	 * 财务申报主表id
	 */
	 @ApiModelProperty(value="财务申报主表id",name="parentId")
	private Long parentId;


}