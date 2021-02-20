/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-23
 */
package com.zhys.base.pojo;

import lombok.Getter;
import lombok.Setter;
import com.zhys.base.po.BusinessMatters;
import io.swagger.annotations.ApiModel;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="BusinessMatters",description="业务事项")
public class BusinessMattersPojo extends BusinessMatters {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



}