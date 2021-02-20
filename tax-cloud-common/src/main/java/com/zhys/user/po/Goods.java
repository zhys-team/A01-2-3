/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-09-12
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
@ApiModel(value="Goods",description="")
public class Goods implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品主键
	 */
	 @ApiModelProperty(value="商品主键",name="id")
	private Long id;

	/**
	 * 商品名称
	 */
	 @ApiModelProperty(value="商品名称",name="name")
	private String name;

	/**
	 * 商品编码
	 */
	 @ApiModelProperty(value="商品编码",name="code")
	private String code;

	/**
	 * 税收分类编码
	 */
	 @ApiModelProperty(value="税收分类编码",name="rateCode")
	private String rateCode;

	/**
	 * 货物和劳务名称
	 */
	 @ApiModelProperty(value="货物和劳务名称",name="rateCodeName")
	private String rateCodeName;

	/**
	 * 简码
	 */
	 @ApiModelProperty(value="简码",name="simpleCode")
	private String simpleCode;

	/**
	 * 单价
	 */
	 @ApiModelProperty(value="单价",name="price")
	private String price;

	/**
	 * 税率
	 */
	 @ApiModelProperty(value="税率",name="rate")
	private String rate;

	/**
	 * 规格型号
	 */
	 @ApiModelProperty(value="规格型号",name="spec")
	private String spec;

	/**
	 * 计量单位
	 */
	 @ApiModelProperty(value="计量单位",name="unit")
	private String unit;

	/**
	 * 是否删除  0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="是否删除  0：未删除  1：已删除",name="delState")
	private String delState;

	/**
	 * 所属组织id
	 */
	 @ApiModelProperty(value="所属组织id",name="orgId")
	private String orgId;
	 
	/**
	 * 商品类型
	 */
	 @ApiModelProperty(value="商品类型",name="goodsType")
	private Long goodsType;


}