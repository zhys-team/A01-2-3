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
@ApiModel(value="AddTaxMain",description="增值税申报表-主表")
public class AddTaxMain implements IPO {

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
	 * 纳税识别号
	 */
	 @ApiModelProperty(value="纳税识别号",name="nssbh")
	private String nssbh;

	/**
	 * 月
	 */
	 @ApiModelProperty(value="月",name="period")
	private String period;

	/**
	 * 年
	 */
	 @ApiModelProperty(value="年",name="year")
	private String year;

	/**
	 * 纳税人名称
	 */
	 @ApiModelProperty(value="纳税人名称",name="nsrmc")
	private String nsrmc;

	/**
	 * 填表日期
	 */
	 @ApiModelProperty(value="填表日期",name="tbrq")
	private Date tbrq;

	/**
	 * 税款所属时间起
	 */
	 @ApiModelProperty(value="税款所属时间起",name="sksssjq")
	private Date sksssjq;

	/**
	 * 税款所属时间止
	 */
	 @ApiModelProperty(value="税款所属时间止",name="sksssjz")
	private Date sksssjz;

	/**
	 * 金额单位
	 */
	 @ApiModelProperty(value="金额单位",name="jedw")
	private String jedw;

	/**
	 * 所属行业
	 */
	 @ApiModelProperty(value="所属行业",name="sshy")
	private String sshy;

	/**
	 * 法定代表人姓名
	 */
	 @ApiModelProperty(value="法定代表人姓名",name="fddbrxm")
	private String fddbrxm;

	/**
	 * 注册地址
	 */
	 @ApiModelProperty(value="注册地址",name="zcdz")
	private String zcdz;

	/**
	 * 生产经营地址
	 */
	 @ApiModelProperty(value="生产经营地址",name="scjydz")
	private String scjydz;

	/**
	 * 开户银行及账号
	 */
	 @ApiModelProperty(value="开户银行及账号",name="khyhjzh")
	private String khyhjzh;

	/**
	 * 登记注册类型
	 */
	 @ApiModelProperty(value="登记注册类型",name="djzclx")
	private String djzclx;

	/**
	 * 电话号码
	 */
	 @ApiModelProperty(value="电话号码",name="dhhm")
	private String dhhm;
	 
	 /**
	 * 删除状态  0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="删除状态  0：未删除  1：已删除",name="delState")
	private String delState;
	 
	 /**
	 * 组织id
	 */
	 @ApiModelProperty(value="组织id",name="orgid")
	private String orgid;
	 
	 private List<AddTaxMainGrid>  mains;
	 private List<AddTaxFirst>  ones;
	 private List<AddTaxTwo>  twos;
	 private List<AddTaxThree>  threes;
	 private List<AddTaxFour>  fours;
	 private List<AddTaxFive>  fives;
	 private List<AddTaxDetailFree>  detailfrees;
	 private List<AddTaxDetailSub>  detailsubs;
	 


}