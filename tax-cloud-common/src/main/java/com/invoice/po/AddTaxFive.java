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
@ApiModel(value="AddTaxFive",description="增值税申报表-附表五")
public class AddTaxFive implements IPO {

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
	 * 期初待抵扣不动产进项税额
	 */
	 @ApiModelProperty(value="期初待抵扣不动产进项税额",name="qcddkjxse")
	private String qcddkjxse;

	/**
	 * 本期不动产进项税额增加额
	 */
	 @ApiModelProperty(value="本期不动产进项税额增加额",name="bqbdczje")
	private String bqbdczje;

	/**
	 * 本期可抵扣不动产进项税额
	 */
	 @ApiModelProperty(value="本期可抵扣不动产进项税额",name="bqkdkjxse")
	private String bqkdkjxse;

	/**
	 * 本期转入的待抵扣不动产进项税
	 */
	 @ApiModelProperty(value="本期转入的待抵扣不动产进项税",name="bqzrjxse")
	private String bqzrjxse;

	/**
	 * 本期转出的待抵扣不动产进项税额
	 */
	 @ApiModelProperty(value="本期转出的待抵扣不动产进项税额",name="bqzcjxse")
	private String bqzcjxse;

	/**
	 * 期末待抵扣不动产进项税额
	 */
	 @ApiModelProperty(value="期末待抵扣不动产进项税额",name="qmddkjxse")
	private String qmddkjxse;


}