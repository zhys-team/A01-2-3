/**
 * Copyright 2014-2016 www.lychee.com
 * All rights reserved.
 * 
 * @project
 * @author li.hui
 * @version 2.0
 * @date 2015-12-01
 */
package com.zhys.common;

/**
 * 
 * @author li.hui
 *
 */
public enum IDataEnum {
	
	RET(0), VERSION("1.0.0");
	
	public int i;
	
	public String s;
	
	IDataEnum(Object value) {
		if (value instanceof Integer) {
			this.i = (int) value;
		} else if (value instanceof String) {
			this.s = (String) value;
		}
	}
}