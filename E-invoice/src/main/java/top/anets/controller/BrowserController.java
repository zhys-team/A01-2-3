package top.anets.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.swing.JSpinner.ListEditor;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import net.bytebuddy.asm.Advice.This;
import top.anets.annotation.RedisListener;
import top.anets.listen.HttpSessionConfigurator;
import top.anets.utils.InvoiceUtils;
import top.anets.vo.BusinessVo;
import top.anets.vo.Logs;
import top.anets.vo.Message;
import top.anets.vo.User;
@ServerEndpoint(value="/websocket/{orgId}/{orgMachine}",configurator=HttpSessionConfigurator.class)
@Component
public class BrowserController {
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	
	private Session session=null;
	private HttpSession httpSession=null;
	private String userId=null;
	private static int count=0;
	//记录session和页面会话
	private static ConcurrentHashMap<String, List<Session>> map = new ConcurrentHashMap<>();
	private User user;
	
	
    @OnOpen
    public void onOpen(Session session, EndpointConfig config,@PathParam(value="orgId") String orgId,@PathParam(value="orgMachine") String orgMachine){
    	
    	String userId =orgId+"-"+orgMachine;
    	System.out.println("用户id"+userId);
    	System.out.println("连接建立,web会话"+session);
    	HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	System.out.println("session会话"+httpSession);
    	//初始化页面会话和会话
    	this.session=session;
    	this.httpSession=httpSession;
    	
//    	设置用户会话标识
    	this.httpSession.setAttribute("userId", userId);
    	
    	
//    	//如果用户没有登陆,直接返回
//    	if(httpSession.getAttribute("userId")==null||httpSession.getAttribute("user")==null){
//    		try {
//				session.close();
//			} catch (IOException e) {
//				System.err.println(e.getMessage());
//				e.printStackTrace();
//			}
//    		return;
//    	}
    	
    	
    	
    	
    	
    	
    	//用户登陆了
    	//需要记录 会话
//    	Integer userId = (Integer) httpSession.getAttribute("userId");
    	this.userId=userId;
//    	this.user =(User) httpSession.getAttribute("user");
    	if(map.get(userId)==null){
    		//如果是webSession是第一次的话
    		ArrayList<Session> list = new ArrayList<>();
    		list.add(session);
    		map.put(userId, list);
    	}else {
    		//如果不是第一次
    		List<Session> list = map.get(userId);
    		list.add(session);
    		map.put(userId, list);
		}
    	
    	
    	//用户登陆需要查询未查收的消息
    	
    }
    
    @RedisListener(channel = "logs")
    public void toSendLogs(String message) {
    	System.out.println("用户收到的消息："+message);
    	Message msg = (Message)new Gson().fromJson(message, Message.class);
    	if(msg==null) return ; 
    	String json = new Gson().toJson(msg.getData());
    	Logs data = (Logs)new Gson().fromJson(json, Logs.class);
    	
    	//取出当前目标用户 
     	List<Session> list = map.get(data.getOrgId()+"-"+data.getOrgMachine());
    	
    	if(list==null){
            BusinessVo business = new BusinessVo();
            business.setOrgId(data.getOrgId());
            business.setOrgMachine(data.getOrgMachine());
			//    		停止开票
    		InvoiceUtils.stopInvalidNotNotify(business);
    		InvoiceUtils.stopInvoiceNotNotify(business);
    		System.out.println("目标用户离线");
    		System.out.println("停止开票");
    		//告知用户目标离线
    		this.sendMsg(this.session,"User is offline!");
    		//消息同步云端,临时用户不同步
    		
    	}else{
    		for (Session session : list) {
				this.sendMsg(session, message);
			}
    		//同步消息
   
    	}
    	
    }
    
    
    @OnMessage
    public void onMessage(String message){
    	System.out.println("收到消息，消息内容是:"+message);
    	
//    	Map<String,Object> json = new Gson().fromJson(message,Map.class);
//    	
//    	//如果消息为空
//    	if(json==null){
//    		System.out.println("消息为空");
//    		this.sendMsg(this.session,"the message can not be null!");
//    		return;
//    	}
//    	
//    	Set<Entry<String, Object>> set = json.entrySet();
//    	for (Entry<String, Object> entry : set) {
//			System.out.println(entry.getKey()+":"+entry.getValue());
//		}
//    	//发送消息给目标用户
//    	Double orgId = (Double) json.get("to");
//    	int target = orgId.intValue();
//    	String msg=(String) json.get("content");
    	
    	String msg="服务端收到消息:"+message;
    	//取出目标用户
    	List<Session> list = map.get(this.userId);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = format.format(new Date());
    	if(list==null){
    		System.out.println("目标用户离线");
    		//告知用户目标离线
    		this.sendMsg(this.session,"User is offline!");
    		//消息同步云端,临时用户不同步
    		if(user!=null&&user.getIstemp()!=false){
//    			messageService.addMessage(new Message(this.userId,target, null, msg, time,false));
    		}
    	}else{
    		for (Session session : list) {
				this.sendMsg(session, msg);
			}
    		//同步消息
    		if(user!=null&&user.getIstemp()!=false){
//    		  messageService.addMessage(new Message(this.userId,target, null, msg, time,true));
    		}
    	}
        
    }
    
    
   
    @OnClose
    public void onClose(){
    	System.out.println("用户关闭连接");
    	if(map==null||this.userId==null){
    		return;
    	}
    	List<Session> list = map.get(this.userId);
    	if(list==null||list.isEmpty()){
    		//说明用户关闭了所有会话页面
    		System.out.println("用户"+this.userId+"关闭了所有会话页面");
    		map.remove(this.userId);
    	}else{
    		//移除关闭的页面
    		System.out.println("移除关闭的页面");
    		list.remove(session);
    		if(list.isEmpty()){
    			//如果用户移除了所有页面
    			map.remove(this.userId);
    		}else{
    			//如果还有页面在
    			map.put(this.userId, list);
    		}
    		
    	}
    }
    
    
    @OnError
    public void error(Throwable t){
    	System.out.println("连接异常");
    	System.out.println(t.getMessage());
    	t.printStackTrace();
    }
    
    //发送消息
    private void sendMsg(Session session,String msg){
    	try {
			if(session!=null&&session.getBasicRemote()!=null) {
				session.getBasicRemote().sendText(msg);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
    
}
