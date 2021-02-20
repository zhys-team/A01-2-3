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
@ApiModel(value="DeductParamSub",description="")
public class DeductParamSub implements IPO {

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
	 * 认证参数头信息id
	 */
	 @ApiModelProperty(value="认证参数头信息id",name="deductId")
	private Long deductId;

	/**
	 * 发票代码
	 */
	 @ApiModelProperty(value="发票代码",name="invoiceCode")
	private String invoiceCode;

	/**
	 * 发票号码
	 */
	 @ApiModelProperty(value="发票号码",name="invoiceNumber")
	private String invoiceNumber;

	/**
	 * 有效税额 如果为空默认与税额相等
	 */
	 @ApiModelProperty(value="有效税额 如果为空默认与税额相等",name="validTax")
	private String validTax;

	/**
	 * 转内销编码
	 */
	 @ApiModelProperty(value="转内销编码",name="exportRejectNo")
	private String exportRejectNo;

	/**
	 * 发票勾选状态 1：未勾选 2：已勾选
	 */
	 @ApiModelProperty(value="发票勾选状态 0-勾选同步中  1-已勾选  2:勾选失败  3取消勾选同步中  4：已取消勾选 5：取消勾选失败",name="state")
	private String state;


}