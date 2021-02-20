/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-21
 */
package com.zhys.base.po;

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
@ApiModel(value="RateBusiness",description="业务事项-税收分类编码对应关系")
public class RateBusiness implements IPO {

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
	 * 税收分类编码主键
	 */
	 @ApiModelProperty(value="税收分类编码主键",name="rateCodeId")
	private Long rateCodeId;

	/**
	 * 业务事项id
	 */
	 @ApiModelProperty(value="业务事项id",name="businessMatterId")
	private Long businessMatterId;

	/**
	 * 业务事项名称
	 */
	 @ApiModelProperty(value="业务事项名称",name="businessMatterName")
	private String businessMatterName;

	/**
	 * 组织id
	 */
	 @ApiModelProperty(value="组织id",name="orgId")
	private Long orgId;

	/**
	 * 组织名称
	 */
	 @ApiModelProperty(value="组织名称",name="orgName")
	private String orgName;


}