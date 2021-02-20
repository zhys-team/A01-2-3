/**
 * Copyright 2014-2016 www.lychee.com
 * All rights reserved.
 * 
 * @project
 * @author li.hui
 * @version 2.0
 * @date 2016-08-05
 */
package com.zhys.common;

/**
 * 
 * @author li.hui
 *
 */
public abstract class IData {
	
	/**
	 * CODE
	 */
	protected int ret = IDataEnum.RET.i;
	
	/**
	 * 数据
	 */
	private Object data;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}