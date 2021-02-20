/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-24
 */
package com.invoice.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

import com.invoice.po.ValidateRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value = "ValidateRecordPoJo",description="查询条件")
public class ValidateRecordPoJo extends ValidateRecord {

	/**
	 * 查验时间开始
	 */
	@ApiModelProperty(value="查验时间开始",name="createTimeStart")
	private Date createTimeStart;
	
	/**
	 * 查验时间结束
	 */
	@ApiModelProperty(value="查验时间结束",name="createTimeEnd")
	private Date createTimeEnd;
	
	
}