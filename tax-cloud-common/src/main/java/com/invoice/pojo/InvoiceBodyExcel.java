/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-24
 */
package com.invoice.pojo;


import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import com.zhys.excel.ExcelCell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
public class InvoiceBodyExcel {

	
	/**
	 * 主票行号
	 */
	@ExcelCell(name="主票行号")
	private Long headId;
	
	/**
	 * 行号
	 */
	@ExcelCell(name="行号")
	private Integer hh;

	/**
	 * 货物或应税劳务名称
	 */
	@ExcelCell(name="货物或应税劳务名称")
	private String hwmc;

	/**
	 * 规格型号
	 */
	@ExcelCell(name="规格型号")
	private String ggxh;

	/**
	 * 单位
	 */
	@ExcelCell(name="单位")
	private String dw;

	/**
	 * 数量
	 */
	@ExcelCell(name="数量")
	private String sl;

	/**
	 * 单价
	 */
	@ExcelCell(name="单价")
	private String dj;

	/**
	 * 金额
	 */
	@ExcelCell(name="金额")
	private String je;

	/**
	 * 税率
	 */
	@ExcelCell(name="税率")
	private String slv;

	/**
	 * 税额
	 */
	@ExcelCell(name="税额")
	private String se;

	

}