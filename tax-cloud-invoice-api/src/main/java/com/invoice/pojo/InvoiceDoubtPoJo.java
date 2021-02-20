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

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

import com.invoice.po.InvoiceDoubt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value = "InvoiceDoubtPoJo",description="查询条件")
public class InvoiceDoubtPoJo extends InvoiceDoubt {

	/**
	 * 提交日期开始
	 */
	@ApiModelProperty(value="提交日期开始",name="createTimeStart")
	private Date createTimeStart;
	
	/**
	 * 提交日期结束
	 */
	@ApiModelProperty(value="提交日期结束",name="createTimeEnd")
	private Date createTimeEnd;
	
	/**
	 * 处理日期开始
	 */
	@ApiModelProperty(value="处理日期开始",name="updateTimeStart")
	private Date updateTimeStart;
	
	/**
	 * 处理日期结束
	 */
	@ApiModelProperty(value="处理日期结束",name="updateTimeEnd")
	private Date updateTimeEnd;
	

	/**
	 * 批量删除通过主键
	 */
	@ApiModelProperty(value="通过主键批量放行/禁止",name="ids")
	private String ids;
	
	/**
	 * 疑票查询条件
	 */
	@ApiModelProperty(value="疑票查询条件",name="queryString1")
	private String queryString1;
}