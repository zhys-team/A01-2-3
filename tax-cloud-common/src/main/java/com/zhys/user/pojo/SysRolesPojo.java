/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-06-21
 */
package com.zhys.user.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import com.zhys.user.po.SysRoles;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="SysRolesPojo",description="")
public class SysRolesPojo extends SysRoles {

	private String ids;


}