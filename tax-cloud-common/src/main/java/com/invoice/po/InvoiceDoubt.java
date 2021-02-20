/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-29
 */
package com.invoice.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value="InvoiceDoubt",description="疑票信息")
public class InvoiceDoubt implements IPO {

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
	 * 发票代码
	 */
	 @ApiModelProperty(value="发票代码",name="fpdm")
	private String fpdm;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="fphm")
	private String fphm;

	 
	 /**
	 * 开票日期
	 */
	@ApiModelProperty(value="开票日期",name="kprq")
	private String kprq;
	
	/**
	 * 开票金额
	 */
	@ApiModelProperty(value="开票金额",name="kpje")
	private String kpje;
	
	/**
	 * 校验码
	 */
	@ApiModelProperty(value="校验码",name="jym")
	private String jym;
	 
	/**
	 * 受票方名称
	 */
	 @ApiModelProperty(value="受票方名称",name="spfmc")
	private String spfmc;

	/**
	 * 销售方名称
	 */
	 @ApiModelProperty(value="销售方名称",name="kpfmc")
	private String kpfmc;

	/**
	 * 提交人
	 */
	 @ApiModelProperty(value="提交人",name="createMan")
	private String createMan;

	/**
	 * 提交日期
	 */
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:SS", timezone = "GMT+8")
	 @ApiModelProperty(value="提交日期",name="createTime")
	private Date createTime;

	/**
	 * 状态 0：未处理  1：已放行 2：已禁止
	 */
	 @ApiModelProperty(value="状态 0：未处理  1：已放行 2：已禁止",name="state")
	private String state;

	/**
	 * 处理人
	 */
	 @ApiModelProperty(value="处理人",name="updateMan")
	private String updateMan;

	/**
	 * 处理日期
	 */
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:SS", timezone = "GMT+8")
	 @ApiModelProperty(value="处理日期",name="updateTime")
	private Date updateTime;

	/**
	 * 组织id
	 */
	 @ApiModelProperty(value="组织id",name="orgId")
	private String orgId;

	/**
	 * 组织名称
	 */
	 @ApiModelProperty(value="组织名称",name="orgName")
	private String orgName;
	 
 /**
	 * 原因
	 */
	 @ApiModelProperty(value="原因",name="reason")
	private String reason;
	 
	 /**
	 * 处理结果
	 */
	 @ApiModelProperty(value="处理结果",name="dealResult")
	private String dealResult;

}