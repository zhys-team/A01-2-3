/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-09-12
 */
package com.zhys.user.pojo;

import lombok.Getter;
import lombok.Setter;
import com.zhys.user.po.Goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="GoodsPojo",description="")
public class GoodsPojo extends Goods {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids;

}