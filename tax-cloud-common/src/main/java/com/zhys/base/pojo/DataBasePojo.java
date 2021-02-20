/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-31
 */
package com.zhys.base.pojo;

import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import com.zhys.base.po.DataBase;

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
public class DataBasePojo extends DataBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}