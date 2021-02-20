package com.zhys.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhys.base.BaseRedisService;
import com.zhys.bw.Fpcy;
import com.zhys.redis.RedisUtils;

import net.micropower.weixin.util.GetAccessToken;
import net.micropower.weixin.util.sign;

@Component
public class ScheduledTasks {
	
	@Autowired
	private RedisUtils redis;
	
	@Value("${accessTokenUrl}")
	private String accessTokenUrl;
	
	@Value("${invoice_url0}")
	private String invoice_url0;
	
	@Value("${invoice_url1}")
	private String invoice_url1;
	
	@Value("${invoice_url2}")
	private String invoice_url2;
	
	@Value("${invoice_url3}")
	private String invoice_url3;
	
	@Value("${invoice_url4}")
	private String invoice_url4;
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    //发票查验接口token openid
    @Scheduled(fixedRate = 1000*60*60)
    public void invoiceToken() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
        Map<String, String> m = Fpcy.getResult();
        redis.set("accessToken", m.get("accessToken"), 100000L);
        redis.set("openId", m.get("openId"), 100000L);
    }
    
  //微信 token 
    @Scheduled(fixedRate = 1000*60*60)
    public void wechatToken() {
    	String accessToken = GetAccessToken.getAccessToken(accessTokenUrl);
    	redis.set("wechatAccessToken", accessToken, 100000L);
    	Map<String, String> map0 = sign.getTicket(accessToken,invoice_url0);
        redis.set("timestamp0", map0.get("timestamp"), 100000L);
        redis.set("nonceStr0", map0.get("nonceStr"), 100000L);
        redis.set("signature0", map0.get("signature"), 100000L);
        
        Map<String, String> map1 = sign.getTicket(accessToken,invoice_url1);
        redis.set("timestamp1", map1.get("timestamp"), 100000L);
        redis.set("nonceStr1", map1.get("nonceStr"), 100000L);
        redis.set("signature1", map1.get("signature"), 100000L);
        
        Map<String, String> map2 = sign.getTicket(accessToken,invoice_url2);
        redis.set("timestamp2", map2.get("timestamp"), 100000L);
        redis.set("nonceStr2", map2.get("nonceStr"), 100000L);
        redis.set("signature2", map2.get("signature"), 100000L);
        
        Map<String, String> map3 = sign.getTicket(accessToken,invoice_url3);
        redis.set("timestamp3", map3.get("timestamp"), 100000L);
        redis.set("nonceStr3", map3.get("nonceStr"), 100000L);
        redis.set("signature3", map3.get("signature"), 100000L);
        
        Map<String, String> map4 = sign.getTicket(accessToken,invoice_url4);
        redis.set("timestamp4", map4.get("timestamp"), 100000L);
        redis.set("nonceStr4", map4.get("nonceStr"), 100000L);
        redis.set("signature4", map4.get("signature"), 100000L);
    }
}
