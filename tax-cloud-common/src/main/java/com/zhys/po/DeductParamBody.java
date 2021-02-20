/**
 * Copyright 2017-2020 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2020-05-24
 */
package com.zhys.po;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 认证参数
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="DeductParamBody",description="认证主数据")
public class DeductParamBody implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="id")
	private Long id;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="taxNo")
	private String taxNo;

	/**
	 * 当前属期 日期格式为：”yyyyMM”
	 */
	 @ApiModelProperty(value="当前属期 日期格式为：”yyyyMM”",name="period")
	private String period;

	/**
	 * 勾选类型 1-  抵扣 2-退税 3-代办退税6-撤销抵扣 4不抵扣 7 撤销不抵扣
	 */
	 @ApiModelProperty(value="勾选类型 1-  抵扣 2-退税 3-代办退税6-撤销抵扣 4不抵扣 7 撤销不抵扣",name="deductType")
	private String deductType;

	/**
	 * 创建时间
	 */
	 @ApiModelProperty(value="创建时间",name="createTime")
	private Date createTime;

	/**
	 * 状态  1：状态同步中  2：勾选成功
	 */
	 @ApiModelProperty(value="状态  1：状态同步中  2：勾选成功  3：勾选失败     4:撤销状态同步中 5：撤销勾选成功  6 ：撤销勾选失败",name="state")
	private String state;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="requestId")
	private String requestId;
	 
	 private List<DeductParamSub> invoices;


}