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
@ApiModel(value="AddTaxFour",description="增值税申报表-附表四")
public class AddTaxFour implements IPO {

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
	 * 序号
	 */
	 @ApiModelProperty(value="序号",name="xh")
	private String xh;

	/**
	 * 本期余额
	 */
	 @ApiModelProperty(value="本期余额",name="bqye")
	private String bqye;

	/**
	 * 本期发生额
	 */
	 @ApiModelProperty(value="本期发生额",name="bqfse")
	private String bqfse;

	/**
	 * 本期应抵减税额 	
	 */
	 @ApiModelProperty(value="本期应抵减税额 	",name="bqydjse")
	private String bqydjse;

	/**
	 * 本期实际抵减税额
	 */
	 @ApiModelProperty(value="本期实际抵减税额",name="bqsjdjse")
	private String bqsjdjse;

	/**
	 * 期末余额
	 */
	 @ApiModelProperty(value="期末余额",name="qmye")
	private String qmye;


}