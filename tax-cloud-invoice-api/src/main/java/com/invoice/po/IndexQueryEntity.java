package com.invoice.po;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="IndexQueryEntity",description="主页信息")
public class IndexQueryEntity {
	
	
	/**
	 *  组织编号
	 */
	@ApiModelProperty(value="组织编号",name="orgId")
	private String orgId;
	
	/**
	 *  开票起始日期
	 */
	@ApiModelProperty(value="开票起始日期",name="kprqStart")
	private String kprqStart;
	
	/**
	 *  开票结束日期
	 */
	@ApiModelProperty(value="开票结束日期",name="kprqEnd")
	private String kprqEnd;
	
	/**
	 *  是否明细 0：否 1：是
	 */
	@ApiModelProperty(value="是否明细 0：否 1：是",name="mx")
	private String mx;
	 
	
}
