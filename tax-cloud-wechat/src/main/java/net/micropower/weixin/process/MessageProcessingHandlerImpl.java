package net.micropower.weixin.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.zhys.weixin.bean.InMessage;
import net.zhys.weixin.bean.Item;
import net.zhys.weixin.bean.OutMessage;
import net.micropower.weixin.process.MessageProcessingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhys.base.BaseRedisService;
import com.zhys.fegin.UserServiceFegin;
import com.zhys.user.po.SysUsers;
import com.zhys.utils.SpringUtil;



/**
 * 处理委托的实现，分别处理不同类别的消息
 * 
 * @author li.hui
 */
public class MessageProcessingHandlerImpl  implements MessageProcessingHandler 
{
	private static Logger logger = LoggerFactory.getLogger(MessageProcessingHandlerImpl.class);

	private static final String RECHARGE = "RECHARGE";
	private static final String RECHARGE_CONFIRME = "RECHARGE_CONFIRME";
	private static Properties p;
	private ServletContext servletContext;


	
	
	@Override
	public OutMessage locationTypeMsg(InMessage msg,HttpServletRequest request)
	{
		
		return null;
	}

	@Override
	public OutMessage imageTypeMsg(InMessage msg)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutMessage linkTypeMsg(InMessage msg)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public OutMessage eventTypeMsg(InMessage msg,HttpServletRequest request) {
		servletContext = request.getSession().getServletContext();
		OutMessage oms = new OutMessage();
		if ("subscribe".equals(msg.getEvent()))
			{
			    String openId =  msg.getFromUserName();
			    
				oms.setContent("欢迎关注!");
				oms.setMsgType("text");
				//首次关注时
				saveUser(openId);
			}
		if ("SCAN".equals(msg.getEvent()))
		{
			String openId =  msg.getFromUserName();
		    System.out.println("openId"+openId);
			oms.setContent("欢迎关注中航云税服务平台");
			oms.setMsgType("text");
			//不是首次关注时
		    saveUser(openId);
			
		}
		
		if ("CLICK".equals(msg.getEvent()))
		{   
			
			if("login".equals(msg.getEventKey())){
				oms.setMsgType("news");
				oms.setArticleCount(1);
				List<Item> items = new ArrayList<Item>();
				Item item = new Item();
				item.setDescription("您还未登录！请先登录后操作。。。");
				item.setPicUrl("http://oa.hzwgkj.com/mpserver/image/login.jpg");
				item.setTitle("登录");
				item.setUrl("http://oa.hzwgkj.com/mpnote/loginAction/login.action?openid="+""
						//msg.getFromUserName()+"&nickName="+js.getString("nickname")+"&headimgurl="+js.getString("headimgurl")
						);
				items.add(item);
				oms.setArticles(items);
				}
			
			
		}
		return oms;
	}
	


	@Override
	public OutMessage textTypeMsg(InMessage msg,HttpServletRequest request) {
		OutMessage oms = new OutMessage();
		return oms;
	}
	
	
	private Integer saveUser(String openId) {
		
		//BaseRedisService redis = (BaseRedisService) SpringUtil.getBean(BaseRedisService.class);
		UserServiceFegin fegin = (UserServiceFegin) SpringUtil.getBean(UserServiceFegin.class);
		//String token =(String) redis.getString("wechatAccessToken") ;
		SysUsers u = new SysUsers();
		u.setOpenid(openId);
		return fegin.save(u);
	}

}
