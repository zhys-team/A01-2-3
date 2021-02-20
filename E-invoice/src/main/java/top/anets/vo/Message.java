/**
 * 
 */
package top.anets.vo;

import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
public class Message {
	
	public static final String Type_download = "download";
	public static String Type_Log = "log";          //日志
	public static String Type_Invoice = "invoice";  //发票
	
	/**
	 * 消息类型
	 */
    private String type;
    /**
     * 消息内容
     */
    private Object data;
	/**
	 * @param type
	 * @param data
	 */
	public Message(String type, Object data) {
		super();
		this.type = type;
		this.data = data;
	}
    
    
    
    
    
    
}
