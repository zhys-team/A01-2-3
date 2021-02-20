/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-31
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
@ApiModel(value="DataBase",description="")
public class DataBase implements IPO {

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
	 * 
	 */
	 @ApiModelProperty(value="",name="parentId")
	private Long parentId;

	/**
	 * 名称
	 */
	 @ApiModelProperty(value="名称",name="name")
	private String name;

	/**
	 * 代码
	 */
	 @ApiModelProperty(value="代码",name="code")
	private String code;

	/**
	 * 等级 一级为0
	 */
	 @ApiModelProperty(value="等级 一级为0",name="rank")
	private Integer rank;

	/**
	 * 备注
	 */
	 @ApiModelProperty(value="备注",name="mark")
	private String mark;

	/**
	 * 删除状态 0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="删除状态 0：未删除  1：已删除",name="delState")
	private String delState;


}