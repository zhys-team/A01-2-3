package com.zhys.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.zhys.base.Result;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("")
@CrossOrigin
public class IndexController {

	private static final String INDEX = "redirect:/views/index/index.html";
   
	@GetMapping("")
	public String index(){
		
		return INDEX;
	}

	 @GetMapping({ "/wx/user/userId" })
	    public HashMap<String, String> setOpenid( String code,HttpServletResponse response) {
	        String openid = "123456test";
//	        System.out.println("code:---" + code);  
//	         String re = HttpGetUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + this.appId + "&secret=" + this.secret + "&code=" + code + "&grant_type=authorization_code");
//	         JSONObject js = JSONObject.parseObject(re);
//	        openid = js.getString("openid");
//	        CookieUtils.writeCookie(response, "openId", openid);
//	        
	        Result result = new Result();
	        result.setCode(200);
	        HashMap<String, String> map = new HashMap<>();
	        map.put("userid", openid); 
	        return map;
	    }
    
    
}