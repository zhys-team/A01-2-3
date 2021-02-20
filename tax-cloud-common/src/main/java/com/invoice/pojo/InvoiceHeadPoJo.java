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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

import com.invoice.po.InvoiceHead;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value = "InvoiceHeadPoJo",description="查询条件")
public class InvoiceHeadPoJo extends InvoiceHead {

	/**
	 * 采集时间开始
	 */
	@ApiModelProperty(value="采集时间开始",name="createTimeStart")
	private Date createTimeStart;
	
	/**
	 * 采集时间结束
	 */
	@ApiModelProperty(value="采集时间结束",name="createTimeEnd")
	private Date createTimeEnd;
	
	/**
	 * 开票日期开始
	 */
	@ApiModelProperty(value="开票日期开始",name="kprqStart")
	private Date kprqStart;
	
	/**
	 * 开票日期结束
	 */
	@ApiModelProperty(value="开票日期结束",name="kprqEnd")
	private Date kprqEnd;
	
	/**
	 * 进项查询条件
	 */
	@ApiModelProperty(value="进项查询条件",name="queryString1")
	private String queryString1;
	
	/**
	 * 销项查询条件
	 */
	@ApiModelProperty(value="销项查询条件",name="queryString2")
	private String queryString2;
	
	/**
	 * 批量删除通过主键
	 */
	@ApiModelProperty(value="批量删除通过主键",name="ids")
	private String ids;
	
	/**
	 * 发票号码开始
	 */
	@ApiModelProperty(value="发票号码开始",name="fphmStart")
	private String fphmStart;
	
	/**
	 * 发票号码结束
	 */
	@ApiModelProperty(value="发票号码结束",name="fphmEnd")
	private String fphmEnd;
	
	/**
	 * 价税合计开始
	 */
	@ApiModelProperty(value="价税合计开始",name="kpjeStart")
	private String kpjeStart;
	
	/**
	 * 价税合计结束
	 */
	@ApiModelProperty(value="价税合计结束",name="jshjEnd")
	private String kpjeEnd;
	
	
	/**
	 * 供应商范围   是否内部 0：是  1：否
	 */
	 @ApiModelProperty(value="是否内部 0：是  1：否",name="isInside")
	private String isInside;
	 
	 /**
	 * 供应商名称
	 */
	 @ApiModelProperty(value="供应商名称",name="supplierName")
	private String supplierName;
	 
	 /**
		 * 开票日期开始
		 */
		@ApiModelProperty(value="财务认证日期开始",name="kprqStart")
		private Date rzrqStart;
		
		/**
		 * 开票日期结束
		 */
		@ApiModelProperty(value="财务认证日期结束",name="kprqEnd")
		private Date rzrqEnd;
	 
	
}