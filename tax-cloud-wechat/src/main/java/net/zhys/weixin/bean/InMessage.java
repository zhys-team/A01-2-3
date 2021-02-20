package net.zhys.weixin.bean;

/**
 * 用户发送过来的消息
 * 
 * @author Lihui 2013-8-13
 * 
 */
public class InMessage
{
	// 为了后期方便转换成与微信定义相符合的xml数据，所有的成员变量均以大写字母开头
	private String ToUserName;// 开发者微信号
	private String FromUserName;// 发送方帐号（一个OpenID）
	private Long CreateTime;// 消息创建时间 （整型）
	private String MsgType = "text";// 消息类型
	private Long MsgId; // 消息id，64位整型
	private Long AgentID;

	/**
	 * 文本消息
	 */
	private String Content; // 文本消息内容

	/**
	 * 图片消息
	 */
	private String PicUrl;// 图片链接
	
	/**
	 *地理位置信息
	 */
	private String Location_X;// 地理位置纬度
	private String Location_Y;// 地理位置经度
	private String Latitude;
	private String Longitude;
	private Long Scale;// 地图缩放大小
	private String Label;// 地理位置信息
	private String Precision;//地理位置精度
	private String Poiname;
    private SendLocationInfo SendLocationInfo;
    
	/**
	 * 链接消息
	 */
	private String Title;// 消息标题
	private String Description;// 消息描述
	private String Url;// 消息链接

	/**
	 * 事件推送
	 */
	private String Event;//事件类型，subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)
	private String EventKey;// 事件KEY值，与自定义菜单接口中KEY值对应
    private String Ticket;
	private String MenuId;
	
	/*
	 * 
	 * 支付接收
	 */
	
	private String OrderId;
	private String OrderStatus;
	private String ProductId;
	private String SkuInfo;
	

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getSkuInfo() {
		return SkuInfo;
	}

	public void setSkuInfo(String skuInfo) {
		SkuInfo = skuInfo;
	}

	public Long getAgentID() {
		return AgentID;
	}

	public void setAgentID(Long agentID) {
		AgentID = agentID;
	}

	public String getToUserName()
	{
		return ToUserName;
	}

	public void setToUserName(String toUserName)
	{
		ToUserName = toUserName;
	}

	public String getFromUserName()
	{
		return FromUserName;
	}

	public void setFromUserName(String fromUserName)
	{
		FromUserName = fromUserName;
	}

	public Long getCreateTime()
	{
		return CreateTime;
	}

	public void setCreateTime(Long createTime)
	{
		CreateTime = createTime;
	}

	public String getMsgType()
	{
		return MsgType;
	}

	public void setMsgType(String msgType)
	{
		MsgType = msgType;
	}

	public Long getMsgId()
	{
		return MsgId;
	}

	public void setMsgId(Long msgId)
	{
		MsgId = msgId;
	}

	public String getContent()
	{
		return Content;
	}

	public void setContent(String content)
	{
		Content = content;
	}

	public String getPicUrl()
	{
		return PicUrl;
	}

	public void setPicUrl(String picUrl)
	{
		PicUrl = picUrl;
	}

	

	


	public String getPoiname() {
		return Poiname;
	}

	public void setPoiname(String poiname) {
		Poiname = poiname;
	}

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

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getDescription()
	{
		return Description;
	}

	public void setDescription(String description)
	{
		Description = description;
	}

	public String getUrl()
	{
		return Url;
	}

	public void setUrl(String url)
	{
		Url = url;
	}

	public String getEvent()
	{
		return Event;
	}

	public void setEvent(String event)
	{
		Event = event;
	}

	public String getEventKey()
	{
		return EventKey;
	}

	public void setEventKey(String eventKey)
	{
		EventKey = eventKey;
	}

	public String getMenuId() {
		return MenuId;
	}

	public void setMenuId(String menuId) {
		MenuId = menuId;
	}

	public SendLocationInfo getSendLocationInfo() {
		return SendLocationInfo;
	}

	public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
		SendLocationInfo = sendLocationInfo;
	}

	
	
	
}
