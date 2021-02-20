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
public class Pages<T> {
	
	private long total;
	
	private T rows;
	
	private String string1;
	
	private String string2;
	
	private String string3;
	
	public Pages() {
		// ...
	}
	
	public Pages(long total, T rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

	public String getString1() {
		return string1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getString2() {
		return string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getString3() {
		return string3;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}
	
	
}