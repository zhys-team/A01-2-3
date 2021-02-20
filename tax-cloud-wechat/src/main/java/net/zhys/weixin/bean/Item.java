package net.zhys.weixin.bean;

/**
 * 多图文消息
 * @author LiHui 2013-08-13
 *
 */
public class Item {
	//为了后期方便转换成与微信定义相符合的xml数据，所有的成员变量均以大写字母开头
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
