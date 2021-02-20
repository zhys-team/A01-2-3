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
@ApiModel(value="FinanceBalanceSheet",description="")
public class FinanceBalanceSheet implements IPO {

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
	 * 财务申报主键
	 */
	 @ApiModelProperty(value="财务申报主键",name="parentId")
	private Long parentId;

	/**
	 * 行号
	 */
	 @ApiModelProperty(value="行号",name="hh")
	private String hh;

	/**
	 * 资产项目名称
	 */
	 @ApiModelProperty(value="资产项目名称",name="zcxmmc")
	private String zcxmmc;

	/**
	 * 资产期末余额
	 */
	 @ApiModelProperty(value="资产期末余额",name="qmyeZc")
	private String qmyeZc;

	/**
	 * 资产年初余额
	 */
	 @ApiModelProperty(value="资产年初余额",name="ncyeZc")
	private String ncyeZc;

	/**
	 * 权益项目名称
	 */
	 @ApiModelProperty(value="权益项目名称",name="qyxmmc")
	private String qyxmmc;

	/**
	 * 权益期末余额
	 */
	 @ApiModelProperty(value="权益期末余额",name="qmyeQy")
	private String qmyeQy;

	/**
	 * 权益年初余额
	 */
	 @ApiModelProperty(value="权益年初余额",name="ncyeQy")
	private String ncyeQy;


}