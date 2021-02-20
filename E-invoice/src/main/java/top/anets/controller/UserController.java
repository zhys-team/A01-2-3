/**
 * 
 */
package top.anets.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import top.anets.entity.SysUsers;
import top.anets.mapper.BusinessMapper;
import top.anets.redis.RedisService;
import top.anets.service.BusinessService;
import top.anets.service.UserService;
import top.anets.utils.CookieUtils;
import top.anets.utils.Result;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BusinessService businessService;
    @RequestMapping("/queryUsers")
    public Result queryUser() {
    	List<SysUsers> all = userService.queryAll();
    	return Result.Success(all);
    }
    
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody SysUsers users) {
    	System.out.println(users);
    	 
    	try {
    		userService.saveOrUpdate(users);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.Error("保存失败!"+e.getMessage(),e.getStackTrace());
		}
    	return Result.Success("保存成功");
    }
    
    @RequestMapping("/deleteUser")
    public Result deleteUser(Integer id) {   
    	try {
    		userService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.Error("删除失败!"+e.getMessage(),e.getStackTrace());
		}
    	return Result.Success("删除成功");
    }
    
    
    
    @RequestMapping("/login")
    public Result login(@RequestBody SysUsers sysUsers,HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isBlank(sysUsers.getNo())||StringUtils.isBlank(sysUsers.getPassword())) {
        	return Result.Error("用户名账号或密码不能为空");
        }    	
        SysUsers users =userService.getUser(sysUsers.getNo(), sysUsers.getPassword());
    	// 1根据用户名密码获取用户
        if(users==null) {
        	return Result.Error("用户名或密码错误");
        }
//      如果不为null生成token 
        String token = UUID.randomUUID().toString();
        String jsonString = JSON.toJSONString(users);
        redisService.set(token, jsonString, 0);
//      存到cookie中 
        CookieUtils.setCookie(request, response, "token", token);
       
        return Result.Success("登录成功",users);
        
    }
    
    
    
    @RequestMapping("/getUser")
    public Result getUser( HttpServletRequest request,HttpServletResponse response) {
    	String token = CookieUtils.getCookieValue(request, "token");
    	if(StringUtils.isBlank(token)) {
    		return Result.Error("用户尚未登录，请先登录 😊 ");
    	}
        
    	String usrStr = redisService.get(token);
    	SysUsers  user = new Gson().fromJson(usrStr, SysUsers.class); 
    	
        return Result.Success("登录成功😄",user);
        
    }
    
    @RequestMapping("/logout")
    public Result login( HttpServletRequest request,HttpServletResponse response) {
        String token = CookieUtils.getCookieValue(request, "token");
        redisService.expire(token, -1);
        CookieUtils.deleteCookie(request, response, "token");
        
//      存到cookie中 
        return Result.Success("登出成功!"); 
    }
}
