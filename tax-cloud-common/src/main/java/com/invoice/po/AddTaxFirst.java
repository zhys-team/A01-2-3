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
@ApiModel(value="AddTaxFirst",description="增值税申报表-附表一")
public class AddTaxFirst implements IPO {

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
	 * 开具增值税专用发票-销售额
	 */
	 @ApiModelProperty(value="开具增值税专用发票-销售额",name="zzxse")
	private String zzxse;

	/**
	 * 开具增值税专用发票-销项(应纳)税额
	 */
	 @ApiModelProperty(value="开具增值税专用发票-销项(应纳)税额",name="zzxxse")
	private String zzxxse;

	/**
	 * 开具其他发票-销售额
	 */
	 @ApiModelProperty(value="开具其他发票-销售额",name="kqtxse")
	private String kqtxse;

	/**
	 * 开具其他发票-销项(应纳)税额
	 */
	 @ApiModelProperty(value="开具其他发票-销项(应纳)税额",name="kqtxxse")
	private String kqtxxse;

	/**
	 * 未开具发票-销售额
	 */
	 @ApiModelProperty(value="未开具发票-销售额",name="wkxse")
	private String wkxse;

	/**
	 * 未开具发票--销项(应纳)税额
	 */
	 @ApiModelProperty(value="未开具发票--销项(应纳)税额",name="wkxxse")
	private String wkxxse;

	/**
	 * 纳税检查调整--销售额
	 */
	 @ApiModelProperty(value="纳税检查调整--销售额",name="nsxse")
	private String nsxse;

	/**
	 * 纳税检查调整-销项(应纳)税额
	 */
	 @ApiModelProperty(value="纳税检查调整-销项(应纳)税额",name="nsxxse")
	private String nsxxse;

	/**
	 * 合计--销售额
	 */
	 @ApiModelProperty(value="合计--销售额",name="hjxse")
	private String hjxse;

	/**
	 * 合计--销项(应纳)税额
	 */
	 @ApiModelProperty(value="合计--销项(应纳)税额",name="hjxxse")
	private String hjxxse;

	/**
	 * 合计--价税合计
	 */
	 @ApiModelProperty(value="合计--价税合计",name="hjjshj")
	private String hjjshj;

	/**
	 * 服务、不动产和无形资产扣除项目本期实际扣除金额
	 */
	 @ApiModelProperty(value="服务、不动产和无形资产扣除项目本期实际扣除金额",name="fwsjkcje")
	private String fwsjkcje;

	/**
	 * 扣除后--含税(免税)销售额
	 */
	 @ApiModelProperty(value="扣除后--含税(免税)销售额",name="kcxse")
	private String kcxse;

	/**
	 * 扣除后--销项税额
	 */
	 @ApiModelProperty(value="扣除后--销项税额",name="kcxxse")
	private String kcxxse;


}