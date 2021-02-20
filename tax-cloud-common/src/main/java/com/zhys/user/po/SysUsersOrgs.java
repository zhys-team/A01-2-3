/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-07-05
 */
package com.zhys.user.po;

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
@ApiModel(value="SysUsersOrgs",description="")
public class SysUsersOrgs implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="userId")
	private Long userId;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="orgId")
	private String orgId;

	/**
	 *  主键 id
	 */
	 @ApiModelProperty(value=" 主键 id",name="id")
	private Integer id;

	/**
	 * 删除标记 0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="删除标记 0：未删除  1：已删除",name="delState")
	private String delState;
	 
   /**
	 * 
	 */
	private String orgIds;


}