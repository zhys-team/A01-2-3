package com.zhys.po;

import java.util.Date;
import java.util.List;

import com.lycheeframework.core.model.IPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value="DeductRecord",description="认证记录")
public class DeductRecord  implements IPO{
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
	 * 
	 */
	 @ApiModelProperty(value="",name="requestId")
	private String requestId;
	 
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
		 @ApiModelProperty(value="发票勾选状态 0-未勾选 1-已勾选",name="state")
		private String state;
}
