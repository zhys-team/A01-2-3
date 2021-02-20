/**
 * Copyright 2017-2019 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2019-04-22
 */
package com.invoice.po;

import java.util.Date;
import java.util.List;

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

@ApiModel(value="RuleMatch",description="规则匹配库主表")
public class RuleMatch implements IPO {

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
	 * 规则名称
	 */
	 @ApiModelProperty(value="规则名称",name="name")
	private String name;

	/**
	 * 规则类型 1：税务类型  2：发票大类  3：发票业务类型
	 */
	 @ApiModelProperty(value="规则类型 1：税务类型  2：发票大类  3：发票业务类型",name="ruleType")
	private String ruleType;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="canDeduct")
	private String canDeduct;

	/**
	 * 启用日期
	 */
	 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="启用日期",name="startDate")
	private Date startDate;

	/**
	 * 停用日期
	 */
	 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="停用日期",name="endDate")
	private Date endDate;
	 
	 @ApiModelProperty(value="明细",name="subs")
	private List<RuleMatchSub> subs;
	
	 @ApiModelProperty(value="规则编码",name="code")
	private String code;

}