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

import lombok.Getter;
import lombok.Setter;
import com.zhys.user.po.SysUsersOrgs;
import io.swagger.annotations.ApiModel;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="SysUsersOrgsPojo",description="")
public class SysUsersOrgsPojo extends SysUsersOrgs {

	private String ids;


}