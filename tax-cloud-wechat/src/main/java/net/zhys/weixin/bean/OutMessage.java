package net.zhys.weixin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回给用户的消息
 * 
 * @author Lihui 
 * 
 */
public class OutMessage
{
	// 为了后期方便转换成与微信定义相符合的xml数据，所有的成员变量均以大写字母开头
	private String ToUserName;// 接收方微信号
	private String FromUserName;// 发送方微信号，若为普通用户，则是一个OpenID
	private Long CreateTime;// 消息创建时间
	private String MsgType = "text";// 消息类型，默认文本
	private int ArticleCount;
	private Long MsgId;// 消息id，64位整型
	private String Content;// 文本消息内容
	private Long AgentID;

	private String Encrypt;
	private String MsgSignature;
	private String TimeStamp;
	private String Nonce;
	
	private List<Item> Articles;
	
	
	
	
	
	public List<Item> getArticles() {
		return Articles;
	}
	public void setArticles(List<Item> articles) {
		Articles = articles;
	}
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public Long getAgentID() {
		return AgentID;
	}
	public void setAgentID(Long agentID) {
		AgentID = agentID;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getEncrypt() {
		return Encrypt;
	}
	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
	public String getMsgSignature() {
		return MsgSignature;
	}
	public void setMsgSignature(String msgSignature) {
		MsgSignature = msgSignature;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getNonce() {
		return Nonce;
	}
	public void setNonce(String nonce) {
		Nonce = nonce;
	}
}