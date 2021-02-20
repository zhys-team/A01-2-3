/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-02
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
@ApiModel(value="SysRolesUsers",description="")
public class SysRolesUsers implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色用户表
	 */
	 @ApiModelProperty(value="角色用户表",name="roleid")
	private Integer roleid;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="userid")
	private Integer userid;
	 
	 private String userids;


}