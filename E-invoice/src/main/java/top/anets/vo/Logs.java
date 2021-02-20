/**
 * 
 */
package top.anets.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;

/**
 * @author Administrator
 *
 */ 
@Data
@Component
@Slf4j
public class Logs implements Serializable{
	
	 
	private static final long serialVersionUID = 1166947617464634976L;
	public static String ERROR = "error";
	public static String WARN = "warn";
	public static String SUCCESS = "success";
	public static String INFO = "info"; 
	
	
	private String orgId;
	private String orgMachine;
    private String level = Logs.INFO;
    private String day ;  //2020-5-30
    private String time ; //13:33:34
    private String title;
    private String content;
    private Object data;
	/**
	 * @param orgId
	 * @param level
	 * @param day
	 * @param time
	 * @param msg
	 */
  
    public Logs() {}
	public Logs(String orgId,String orgMachine,String day,String time,String level,String title ,  String content) {
		super();
		this.orgId=orgId;
		this.orgMachine=orgMachine;
		this.title=title; 
		this.level = level;
		this.day = day;
		this.time = time;
		this.content = content; 
   }  
    
	
	public Logs(String orgId,String orgMachine,String day,String time,String level,Object data) {
		super();
		this.orgId=orgId;
		this.orgMachine=orgMachine; 
		this.level = level;
		this.day = day;
		this.time = time;
		this.data=data;
   }  
	  
    /**
     * 给静态变量注入 redisService对象
     */
    @Autowired
    private  RedisService redisService1;
    private static RedisService redisService; 
    public static Logs newInstance = null;
	@PostConstruct  
    public void init() {  
		 System.out.println("初始化方法.....");
         Logs.redisService=redisService1;
    }  

	
//	单例模式
	public static synchronized Logs getInstance(String orgId,String orgMachine,String level,String title ,  String content) {
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String  day = dayFormat.format(date);
		String  time = timeFormat.format(date);
		if(newInstance==null) {
			newInstance =new Logs(orgId, orgMachine,day,time, level, title, content);
		}else {
			newInstance.setOrgId(orgId);
			newInstance.setOrgMachine(orgMachine);
			newInstance.setTitle(title);
			newInstance.setLevel(level);
			newInstance.setContent(content); 
			newInstance.setDay(day);
			newInstance.setTime(time);
		} 
		
//		存日志信息到redis中，过期时间
		if(StringUtils.isNotBlank(title)) {
			log.info("org:"+orgId+"/"+orgMachine+"["+title+"]"+content);
//			redisService.lpush(orgId+orgMachine+"-"+day, "["+time+"]"+title+"-"+content); 
			 
//			redisService.hset(RedisKey.Storj_log.getKey()+"-"+day, time, "["+title+"]"+content,RedisKey.Storj_log.getSeconds());
		}else {
			log.info("org:"+orgId+"/"+orgMachine+content);
//			redisService.hset(RedisKey.Storj_log.getKey()+"-"+day, time, content,RedisKey.Storj_log.getSeconds());
//			redisService.lpush(orgId+"-"+day, "["+time+"]" +content);
		} 
//		redisService.expire(orgId+orgMachine+"-"+day,RedisKey.Storj_log.getSeconds());
		return newInstance;
	}
	
	
//	单例模式
	public static synchronized Logs getInstance(String orgId,String orgMachine,String level,Object data) {
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String  day = dayFormat.format(date);
		String  time = timeFormat.format(date);
		if(newInstance==null) {
			newInstance =new Logs(orgId, orgMachine,day,time, level, data);
		}else {
			newInstance.setOrgId(orgId);
			newInstance.setOrgMachine(orgMachine); 
			newInstance.setLevel(level); 
			newInstance.setDay(day);
			newInstance.setTime(time);
		}
		
//		存日志信息到redis中，过期时间
//		System.out.println(RedisKey.Storj_log.getKey()+"-"+day +"-"+ time + data);
		log.info("org:"+orgId+"/"+orgMachine+ data);
//		redisService.hset(RedisKey.Storj_log.getKey()+"-"+day, time, data,RedisKey.Storj_log.getSeconds());
		return newInstance;
	}
    
	
	
	
	/**
	 *  把 日志 消息 通知 给用户
	* 
	*@param channel   消息管道 ，比如RedisKey.Channel_log.getKey()  
	*@param messageType  消息类型 ，比如  Message.Type_Log
	*@param target    目标用户
	*@param logLevel     日志级别，比如 Logs.INFO
	*@param logTitle     日志标题
	*@param logContent   日志内容
	 */
	public static void notify(String channel ,String messageType ,BusinessVo target,String logLevel,String logTitle,String logContent) { 
	 
		Message message = new Message(messageType, Logs.getInstance(target.getOrgId(), target.getOrgMachine(), logLevel, logTitle, logContent));
		String json = new Gson().toJson(message); 
		redisService.publish(channel,json ); 
		System.out.println("通知消息给"+target.getOrgId()+"/"+target.getOrgMachine()+":"+json);
	}
	public static void notify(String channel ,String messageType ,BusinessVo target,String logLevel,Object logData) { 
		String json = new Gson().toJson(new Message(messageType, Logs.getInstance(target.getOrgId(), target.getOrgMachine(), logLevel, logData)));
		redisService.publish(channel, json);
		System.out.println("通知消息给"+target.getOrgId()+"/"+target.getOrgMachine()+":"+json);	}
	 
}
