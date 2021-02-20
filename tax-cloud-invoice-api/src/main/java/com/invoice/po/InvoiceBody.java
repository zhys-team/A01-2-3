/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-24
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
@ApiModel(value="InvoiceBody",description="发票明细信息")
public class InvoiceBody implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	private Long id;

	/**
	 * 主表MID
	 */
	@ApiModelProperty(value="主表MID",name="headId")
	private Long headId;

	/**
	 * 行号
	 */
	@ApiModelProperty(value="行号",name="hh")
	private Integer hh;

	/**
	 * 货物或应税劳务名称
	 */
	@ApiModelProperty(value="货物或应税劳务名称",name="hwmc")
	private String hwmc;

	/**
	 * 规格型号
	 */
	@ApiModelProperty(value="规格型号",name="ggxh")
	private String ggxh;

	/**
	 * 单位
	 */
	@ApiModelProperty(value="单位",name="dw")
	private String dw;

	/**
	 * 数量
	 */
	@ApiModelProperty(value="数量",name="sl")
	private String sl;

	/**
	 * 单价
	 */
	@ApiModelProperty(value="单价",name="dj")
	private String dj;

	/**
	 * 金额
	 */
	@ApiModelProperty(value="金额",name="je")
	private String je;

	/**
	 * 税率
	 */
	@ApiModelProperty(value="税率",name="slv")
	private String slv;

	/**
	 * 税额
	 */
	@ApiModelProperty(value="税额",name="se")
	private String se;

	/**
	 * 开票项目编码
	 */
	private String xmbm;

	/**
	 * 开票项目名称
	 */
	private String xmmc;

	/**
	 * 部门
	 */
	private String bmmc;


}