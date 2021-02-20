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
public class GsonData extends IData {
	
	public static GsonData getInstance() {
		
		return (GsonData) local.get();
	}
	
	public static GsonData getInstance(int ret, Object data) {
		GsonData GsonData = (GsonData) local.get();
		
		GsonData.setRet(ret);
		GsonData.setData(data);
		
		return GsonData;
	}
	
	public static ThreadLocal<GsonData> local = new ThreadLocal<GsonData>() {
		
		protected GsonData initialValue() {
			
			return new GsonData();
		}
	};
}