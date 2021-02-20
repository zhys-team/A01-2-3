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
@ApiModel(value="AddTaxThree",description="增值税申报表-附表三")
public class AddTaxThree implements IPO {

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
	 * 本期服务、不动产和无形资产价税合计额（免税销售额）
	 */
	 @ApiModelProperty(value="本期服务、不动产和无形资产价税合计额（免税销售额）",name="bqhje")
	private String bqhje;

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
	 * 本期应扣除金额
	 */
	 @ApiModelProperty(value="本期应扣除金额",name="bqykcje")
	private String bqykcje;

	/**
	 * 本期实际扣除金额
	 */
	 @ApiModelProperty(value="本期实际扣除金额",name="bqsjkcje")
	private String bqsjkcje;

	/**
	 * 期末余额
	 */
	 @ApiModelProperty(value="期末余额",name="qmye")
	private String qmye;


}