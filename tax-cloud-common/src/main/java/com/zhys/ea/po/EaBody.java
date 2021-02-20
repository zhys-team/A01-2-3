/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-07-20
 */
package com.zhys.ea.po;

import java.sql.Date;
import java.util.List;

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
@ApiModel(value = "EaBody", description = "")
public class EaBody implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 id
	 */
	@ApiModelProperty(value = " 主键 id", name = "id")
	private Long id;

	/**
	 * 
	 */
	@ApiModelProperty(value = "", name = "parentId")
	private Long parentId;

	/**
	 * 出发地
	 */
	@ApiModelProperty(value = "出发地", name = "startAddr")
	private String startAddr;

	/**
	 * 目的地
	 */
	@ApiModelProperty(value = "目的地", name = "endAddr")
	private String endAddr;

	/**
	 * 出差日期
	 */
	@ApiModelProperty(value = "出差日期", name = "workStartDate")
	private Date workStartDate;

	/**
	 * 返回日期
	 */
	@ApiModelProperty(value = "返回日期", name = "workEndDate")
	private Date workEndDate;

	/**
	 * 出差天数
	 */
	@ApiModelProperty(value = "出差天数", name = "workDays")
	private Float workDays;

	/**
	 * 删除状态 0：未删除 1：已删除
	 */
	@ApiModelProperty(value = "删除状态 0：未删除  1：已删除", name = "delState")
	private String delState;

	/**
	 * 明细费用类型 0：交通费用 1：住宿费用
	 */
	@ApiModelProperty(value = "明细费用类型 0：交通费用 1：住宿费用", name = "detailType")
	private String detailType;

	/**
	 * 交通工具 0火车、1飞机、2轮渡、3公交、4的士、5租车、6自驾、7高速（高速过路过桥费）、8公路（过路过桥费）
	 */
	@ApiModelProperty(value = "交通工具  0火车、1飞机、2轮渡、3公交、4的士、5租车、6自驾、7高速（高速过路过桥费）、8公路（过路过桥费）", name = "vehicle")
	private String vehicle;

	/**
	 * 票据费用
	 */
	@ApiModelProperty(value = "票据费用", name = "cast")
	private Float cast;

	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种", name = "currency")
	private String currency;

	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率", name = "exchangeRate")
	private Float exchangeRate;

	/**
	 * 本币金额
	 */
	@ApiModelProperty(value = "本币金额", name = "currencyAmount")
	private Float currencyAmount;

	/**
	 * 说明
	 */
	@ApiModelProperty(value = "说明", name = "mark")
	private String mark;

	/**
	 * 明细事项
	 */
	@ApiModelProperty(value = "明细事项", name = "mxsx")
	private String mxsx;
	
	/**
	 * 发票主键
	 */
	@ApiModelProperty(value = "发票主键", name = "invoiceId")
	private Long invoiceId;
}