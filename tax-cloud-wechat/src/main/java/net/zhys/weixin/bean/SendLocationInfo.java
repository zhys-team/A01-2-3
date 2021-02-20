/**
 * Project Name:mpserver
 * File Name:SendLocationInfo.java
 * Package Name:net.micropower.weixin.bean
 * Date:Jul 13, 201611:03:10 AM
 * Copyright (c) 2016, 1173499611@qq.com All Rights Reserved.
 *
*/

package net.zhys.weixin.bean;
/**
 * ClassName:SendLocationInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Jul 13, 2016 11:03:10 AM <br/>
 * @author   lihui
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class SendLocationInfo {
	
	private String Location_X;// 地理位置纬度
	private String Location_Y;// 地理位置经度
	private Long Scale;// 地图缩放大小
	private String Label;// 地理位置信息
	private String Precision;//地理位置精度
	private String Poiname;
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public Long getScale() {
		return Scale;
	}
	public void setScale(Long scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	public String getPoiname() {
		return Poiname;
	}
	public void setPoiname(String poiname) {
		Poiname = poiname;
	}
	
	

}

