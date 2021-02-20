/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-29
 */
package com.invoice.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lycheeframework.core.model.IPO;
import com.zhys.EntityValidate.EntityValidate;
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
public class InvoiceHeadExcel implements IPO{
	

	/**
	 * 行号
	 */
	@EntityValidate(allowEmpty=false)
	@ExcelCell(name="行号")
	private Long id;
   
	/**
	 * 发票种类   0:蓝票  1：红票
	 */
	@EntityValidate(allowEmpty=false)
	@ExcelCell(name="发票种类")
	private String fpzl;

	/**
	 * 发票类型 0：增专 1：增普 2：电票3：外票 4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票8：其他
	 */
	 @ExcelCell(name="发票类型")
	private String fplx;

	/**
	 * 发票代码
	 */
	 @ExcelCell(name="发票代码")
	private String fpdm;

	/**
	 * 发票号码
	 */
	 @ExcelCell(name="发票号码")
	private String fphm;

	/**
	 * 开票日期
	 */
	 @ExcelCell(name="开票日期")
	private String kprq1;

	 private Date kprq;

	/**
	 * 受票方名称
	 */
	 @ExcelCell(name="受票方名称")
	private String spfmc;

	/**
	 * 受票方识别号
	 */
	 @ExcelCell(name="受票方识别号")
	private String spfsbh;

	/**
	 * 受票方地址及电话
	 */
	 @ExcelCell(name="受票方地址及电话")
	private String spfdzdh;

	/**
	 * 受票方银行及账号
	 */
	 @ExcelCell(name="受票方银行及账号")
	private String spfyhzh;

	/**
	 * 开票方名称
	 */
	 @ExcelCell(name="开票方名称")
	private String kpfmc;

	/**
	 * 开票方识别号
	 */
	 @ExcelCell(name="开票方识别号")
	private String kpfsbh;

	/**
	 * 开票方地址及电话
	 */
	 @ExcelCell(name="开票方地址及电话")
	private String kpfdzdh;

	/**
	 * 开票方银行及账号
	 */
	 @ExcelCell(name="开票方银行及账号")
	private String kpfyhzh;

	/**
	 * 合计金额
	 */
	 @ExcelCell(name="合计金额")
	private String hjje;

	/**
	 * 合计税额
	 */
	 @ExcelCell(name="合计税额")
	private String hjse;

	/**
	 * 价税合计
	 */
	 @ExcelCell(name="价税合计")
	private String kpje;




	 /**
	 * 进销项 0:进项  1：销项
	 */
	 @ExcelCell(name="进销项")
	private String inOrOut;


	/**
	 * 采购业务员
	 */
	 @ExcelCell(name="采购业务员")
	private String procMan;


	
	 
	

	/**
	 * 核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0
	 */
	 @ExcelCell(name="核算要素")
	private String businessType;
	
}