/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-21
 */
package com.zhys.base.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
@ApiModel(value="RateCode",description="税收分类编码")
public class RateCode implements IPO {

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
	 * 税码
	 */
	 @ApiModelProperty(value="税码",name="code")
	private String code;

	/**
	 * 税码名称
	 */
	 @ApiModelProperty(value="税码名称",name="rateName")
	private String rateName;

	/**
	 * 税率
	 */
	 @ApiModelProperty(value="税率",name="rate")
	private String rate;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="parentId")
	private Long parentId;

	/**
	 * 备注
	 */
	 @ApiModelProperty(value="备注",name="mark")
	private String mark;

	/**
	 * 默认业务事项id
	 */
	 @ApiModelProperty(value="默认业务事项id",name="businessMatterId")
	private Long businessMatterId;

	/**
	 * 默认业务事项名称
	 */
	 @ApiModelProperty(value="默认业务事项名称",name="businessMatterName")
	private String businessMatterName;
	 
	/**
	 * 业务事项明细
	 */
	 @ApiModelProperty(value="业务事项明细",name="businessList")
	private List<RateBusiness> businessList;

	/**
	 * 简称
	 */
	 @ApiModelProperty(value="简称",name="jc")
	private String jc;
	 
	/**
	 * 是否末级 0：否   1：是
	 */
	 @ApiModelProperty(value="是否末级 0：否   1：是",name="isLast")
	private String isLast;
	 
	 
	 /**
	 * 业务事项图标
	 */
	 @ApiModelProperty(value="业务事项图标",name="icon")
	private String icon;
	 
	 /**
	 * 业务事项大类
	 */
	 @ApiModelProperty(value="业务事项大类",name="dl")
	private String dl;
	 
	 @ApiModelProperty(value="业务事项大类ID",name="dlId")
	private Long dlId;

}