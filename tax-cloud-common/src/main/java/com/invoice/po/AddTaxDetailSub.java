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
@ApiModel(value="AddTaxDetailSub",description="增值税申报表-增值税减税申报明细表")
public class AddTaxDetailSub implements IPO {

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
	 * 减税性质代码及名称
	 */
	 @ApiModelProperty(value="减税性质代码及名称",name="hmc")
	private String hmc;

	/**
	 * 栏次
	 */
	 @ApiModelProperty(value="栏次",name="lc")
	private String lc;

	/**
	 * 期初余额
	 */
	 @ApiModelProperty(value="期初余额",name="qcye")
	private String qcye;

	/**
	 * 本期发生额
	 */
	 @ApiModelProperty(value="本期发生额",name="bqfse")
	private String bqfse;

	/**
	 * 本期应抵减税额
	 */
	 @ApiModelProperty(value="本期应抵减税额",name="bqydjse")
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