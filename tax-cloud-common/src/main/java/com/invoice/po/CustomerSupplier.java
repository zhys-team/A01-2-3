/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-06-08
 */
package com.invoice.po;

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
@ApiModel(value="CustomerSupplier",description="")
public class CustomerSupplier implements IPO {

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
	 * 名称
	 */
	 @ApiModelProperty(value="名称",name="name")
	private String name;

	/**
	 * 是否客户 0：是  1：否
	 */
	 @ApiModelProperty(value="是否客户 0：是  1：否",name="isCustomer")
	private String isCustomer;

	/**
	 * 是否供应商 0：是  1：否
	 */
	 @ApiModelProperty(value="是否供应商 0：是  1：否",name="isSupplier")
	private String isSupplier;

	/**
	 * 是否内部 0：是  1：否
	 */
	 @ApiModelProperty(value="是否内部 0：是  1：否",name="isInside")
	private String isInside;

	/**
	 * erp对应主键
	 */
	 @ApiModelProperty(value="erp对应主键",name="erpId")
	private String erpId;

	/**
	 * 删除标记 0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="删除标记 0：未删除  1：已删除",name="delState")
	private String delState;


}