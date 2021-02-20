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
@ApiModel(value="SysUsers",description="")
public class SysUsers implements IPO {

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
	 * 
	 */
	 @ApiModelProperty(value="",name="password")
	private String password;

	/**
	 * 代码
	 */
	 @ApiModelProperty(value="代码",name="no")
	private String no;

	/**
	 * 微信号
	 */
	 @ApiModelProperty(value="微信号",name="openid")
	private String openid;

	/**
	 * 状态 0：正常  1：禁用
	 */
	 @ApiModelProperty(value="状态 0：正常  1：禁用",name="state")
	private String state;

	/**
	 * 默认组织
	 */
	 @ApiModelProperty(value="默认组织",name="orgid")
	private String orgid;

	/**
	 * 其他系统用户主键
	 */
	 @ApiModelProperty(value="其他系统用户主键",name="otherid")
	private String otherid;
	 
	 
	 private String ids;


}