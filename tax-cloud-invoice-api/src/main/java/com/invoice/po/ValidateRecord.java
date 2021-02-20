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

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

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
@ApiModel(value="ValidateRecord",description="查验记录信息")
public class ValidateRecord implements IPO {

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
	 * 查验时间
	 */
	 @JsonFormat(pattern="yyyyMMdd HH:mm:SS", timezone = "GMT+8")
	 @ApiModelProperty(value="查验时间",name="createTime")
	private Date createTime;

	/**
	 * 查验人
	 */
	 @ApiModelProperty(value="查验人",name="createMan")
	private String createMan;

	/**
	 * 查验方式
	 */
	 @ApiModelProperty(value="查验方式",name="validateMode")
	private String validateMode;

	/**
	 * 受票组织id
	 */
	 @ApiModelProperty(value="受票组织id",name="orgId")
	private String orgId;

	/**
	 * 受票组织名称
	 */
	 @ApiModelProperty(value="受票组织名称",name="orgName")
	private String orgName;

	/**
	 * 发票代码
	 */
	 @ApiModelProperty(value="发票代码",name="fpdm")
	private String fpdm;

	/**
	 * 发票号码
	 */
	 @ApiModelProperty(value="发票号码",name="fphm")
	private String fphm;

	/**
	 * 发票种类  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：其他
	 */
	 @ApiModelProperty(value="发票种类  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：其他",name="fpzl")
	private String fpzl;

	/**
	 * 查验结果
	 */
	 @ApiModelProperty(value="查验结果",name="validateResult")
	private String validateResult;
	 
	 /**
		 * 原因
		 */
		 @ApiModelProperty(value="原因",name="reason")
		private String reason;


}