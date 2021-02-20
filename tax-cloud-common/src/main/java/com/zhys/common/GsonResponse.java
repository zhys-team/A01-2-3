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
public class GsonResponse implements IResponse {
	
	public static GsonData toData(Object data) {
		
		return toData(IDataEnum.RET.i, data);
	}
	
	public static GsonData toData(int ret, Object data) {
		
		return GsonData.getInstance(ret, data);
	}
}