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
@ApiModel(value="SysPermissions",description="")
public class SysPermissions implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	 @ApiModelProperty(value=" 主键 id",name="id")
	private Integer id;

	/**
	 * 名称
	 */
	 @ApiModelProperty(value="名称",name="name")
	private String name;

	/**
	 * 代码
	 */
	 @ApiModelProperty(value="代码",name="no")
	private String no;
	 
	/**
	 * 用户编码
	 */
	 @ApiModelProperty(value="用户编码",name="userNo")
	private String userNo;
	 
	/**
	 *  父主键 id
	 */
	 @ApiModelProperty(value=" 父主键 id",name="parentId")
	private Integer parentId;


}