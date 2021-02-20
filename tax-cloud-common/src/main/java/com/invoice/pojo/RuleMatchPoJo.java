/**
 * Copyright 2017-2019 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2019-04-22
 */
package com.invoice.pojo;


import lombok.Getter;
import lombok.Setter;

import com.invoice.po.RuleMatch;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
public class RuleMatchPoJo extends RuleMatch {


	/**
	 * 发票类型
	 */
	private String invoiceType;

	


}