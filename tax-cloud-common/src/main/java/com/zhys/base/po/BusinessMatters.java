/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-23
 */
package com.zhys.base.po;

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
@ApiModel(value="BusinessMatters",description="业务事项")
public class BusinessMatters implements IPO {

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
	 * 业务事项编码
	 */
	 @ApiModelProperty(value="业务事项编码",name="no")
	private String no;

	/**
	 * 业务事项名称
	 */
	 @ApiModelProperty(value="业务事项名称",name="name")
	private String name;

	/**
	 * 备注
	 */
	 @ApiModelProperty(value="备注",name="mark")
	private String mark;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="parentId")
	private Long parentId;

	/**
	 * 是否删除  0：否  1：是
	 */
	 @ApiModelProperty(value="是否删除  0：否  1：是",name="delStatus")
	private String delStatus;
	 
    /**
	 * 是否末级
	 */
	 @ApiModelProperty(value="是否末级  0：是  1：否",name="isLast")
	private String isLast;

   /**
	 * 业务事项名称
	 */
	 @ApiModelProperty(value="业务事项名称",name="name1")
	private String name1;
	 
	 /**
	 * 图标
	 */
	 @ApiModelProperty(value="图标",name="icon")
	private String icon;
}