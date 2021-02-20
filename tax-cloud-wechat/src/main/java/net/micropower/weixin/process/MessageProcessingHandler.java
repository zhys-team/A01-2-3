package net.micropower.weixin.process;

import javax.servlet.http.HttpServletRequest;

import net.zhys.weixin.bean.InMessage;
import net.zhys.weixin.bean.OutMessage;
/**
 * 消息处理器接口
 * @author LiuYu 2013-8-14
 *
 */
public interface MessageProcessingHandler {
	public final static String MSG_TYPE_TEXT = "text";
	public final static String MSG_TYPE_LOCATION = "location";
	public final static String MSG_TYPE_IMAGE = "image";
	public final static String MSG_TYPE_LINK = "link";
	public final static String MSG_TYPE_EVENT = "event";
	
	public final static String MSG_TYPE_NEWS = "news";
	public final static String MSG_TYPE_MUSIC = "music";
	public final static String MSG_TYPE_VOICE = "voice";
	
	/**
	 * 文字内容的消息处理
	 * @param msg
	 * @return
	 */
	public OutMessage textTypeMsg(InMessage msg,HttpServletRequest request);
	
	/**
	 * 地理位置类型的消息处理
	 * @param msg
	 * @return
	 */
	public OutMessage locationTypeMsg(InMessage msg,HttpServletRequest request);
	
	/**
	 * 图片类型的消息处理
	 * @param msg
	 * @return
	 */
	public OutMessage imageTypeMsg(InMessage msg);
	
	/**
	 * 链接类型的消息处理
	 * @param msg
	 * @return
	 */
	public OutMessage linkTypeMsg(InMessage msg);
	/**
	 * 事件类型的消息处理。<br/>
	 * 在用户首次关注公众账号时，系统将会推送一条subscribe的事件
	 * @param msg
	 * @return
	 */
	public OutMessage eventTypeMsg(InMessage msg,HttpServletRequest request);
}
