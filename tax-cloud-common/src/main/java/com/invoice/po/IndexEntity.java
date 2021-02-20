package com.invoice.po;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="IndexEntity",description="主页信息")
public class IndexEntity {
	
	/**
	 *  进项税额
	 */
	@ApiModelProperty(value="进项税额",name="jxse")
	private String jxse;
	 
	/**
	 *  进项含税总额
	 */
	@ApiModelProperty(value="进项含税总额",name="jxhsze")
	private String jxhsze;
	
	/**
	 *  费用
	 */
	@ApiModelProperty(value="费用",name="fy")
	private String fy;
	
	/**
	 *  销项税额
	 */
	@ApiModelProperty(value="销项税额",name="xxse")
	private String xxse;
	 
	/**
	 *  销项含税总额
	 */
	@ApiModelProperty(value="销项含税总额",name="xxhsze")
	private String xxhsze;
	
	/**
	 *  不含税收入
	 */
	@ApiModelProperty(value="不含税收入",name="bhssr")
	private String bhssr;
	
	/**
	 *  开票日期
	 */
	@ApiModelProperty(value="开票日期",name="kprq")
	private String kprq;
}
