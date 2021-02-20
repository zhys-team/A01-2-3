/**
 * Copyright 2014-2016 www.lychee.com
 * All rights reserved.
 * 
 * @project
 * @author li.hui
 * @version 2.0
 * @date 2015-12-01
 */
package com.lycheeframework.core.cmp.kit;

/**
 * 
 * @author li.hui
 *
 */
public final class KeyValue {
	
	private String key;
	
	private Object value;
	
	public KeyValue() {
		// ...
	}
	
	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}