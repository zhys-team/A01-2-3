/**
 * 
 */
package top.anets.utils;

/**
 * @author Administrator
 * 此处定义一些可能 常用的错误 ，不常用的错误 就用400,500，只不过更改默认值就好了
 */
public enum Status {
     Success("200","成功"),
     ErrorRequest("400","请求错误"),
     ErrorServer("500","服务器错误"),
     
     NeedQuery("1000","已经开具过发票，请调用查询接口")
     ;
	

	/**
	 * @param i
	 * @param string
	 */
	private String codez;
	private String msgz;
	
	
	Status(String code, String msg) {
		this.codez=code;
		this.msgz=msg;
	}
	
	public String code() {
		return this.codez;
	}
	
	public String msg() {
		return this.msgz;
	}
	
}
