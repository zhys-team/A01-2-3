package com.invoice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.invoice.fegin.SysUsersServiceFegin;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.result.ResponseResult;
import com.zhys.user.po.SysUsers;
import com.zhys.utils.MD5Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@ResponseResult
@Slf4j
@RestController
@Api(value="用户接口",description="用户接口" )
@RequestMapping("/sysUsers")
public class SysUsersController {
	
	@Autowired
	//private SysUsersServiceFegin service;
	private SysUsersServiceFegin ser;


    

    @ApiOperation(value = "用户分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<SysUsers>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="sysUsers",value="查询条件",required=true) 
	            @RequestBody(required=false) SysUsers sysUsers){
		return  ser.pages(sysUsers,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody SysUsers sysUsers){
		return  ser.save(sysUsers);
	}

    
    @GetMapping("{id}")
	public SysUsers info(@PathVariable("id") Integer id,SysUsers sysUsers){
    	sysUsers.setId(id);
		sysUsers = ser.queryByEntity(sysUsers);
		
		return sysUsers;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "主键", required = true,paramType="path", dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/states")
	public  Integer changeDelStateById(@RequestParam("ids") String ids,@RequestParam String state){
    	SysUsers sysUsers = new SysUsers();
    	sysUsers.setIds(ids);
    	sysUsers.setState(state);
		return  ser.changeDelStateByIds(sysUsers);
	}
    
    @GetMapping("/queryList")
    public List<SysUsers> queryList(){
    	SysUsers sysUsers = new SysUsers();
    	return  ser.queryList(sysUsers);
}
    

    @GetMapping("/login/{no}/{password}")
	public  Map<String, Object> login(@PathVariable(value="no") String no, @PathVariable(value="password") String password){
		 log.info("-------------------------------------------------------");
		    Map<String, Object> map = new HashMap<String, Object>(); 
	        UsernamePasswordToken token = null;
//	        if(user.getName()!=null){
//	        	token = new UsernamePasswordToken(no, user.getPassword());
//	        }else{
//	        	token = new UsernamePasswordToken(no, MD5Util.MD5(user.getPassword()));
//	        }
	        token = new UsernamePasswordToken(no, MD5Util.MD5(password));
	        
	        log.info("||输入的密码:"+password);
	        log.info("||输入的密码加密后:"+ MD5Util.MD5(password));
	        
	        token.setRememberMe(true);
	        log.info("为验证登录用户而封装的Token：");
	        log.info(ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
	        //获取当前的Subject
	        Subject currentUser = SecurityUtils.getSubject();
	        try {
	            //在调用了login方法后，SecurityManager会收到AuthenticationToken，并将其发送给已配置的Realm执行必须的认证检查
	            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
	            //所以这一步在调用login(token)方法时，它会走到MyRealm.doGetAuthenticationInfo()方法中，具体验证方式详见此方法
	            log.info("对用户[" + no + "]进行登录验证...验证开始");
	            currentUser.login(token);
	            log.info("对用户[" + no + "]进行登录验证...验证通过");
	        }catch(UnknownAccountException uae){
	            log.info("对用户[" + no + "]进行登录验证...验证未通过，未知账户");
	              map.put("data","未知账户");
	              map.put("success", false);
	              return map;
	        }catch(IncorrectCredentialsException ice){
	            log.info("对用户[" + no + "]进行登录验证...验证未通过，错误的凭证");
	              map.put("data","密码错误");
	              map.put("success", false);
	              return map;
	        }catch(LockedAccountException lae){
	            log.info("对用户[" + no + "]进行登录验证...验证未通过，账户已锁定");
	              map.put("data","账户已锁定");
	              map.put("success", false);
	              return map;
	        }catch(ExcessiveAttemptsException eae){
	            log.info("对用户[" + no + "]进行登录验证...验证未通过，错误次数过多");
	              map.put("data","错误次数过多");
	              return map;
	        }catch(AuthenticationException ae){
	            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
	            log.info("对用户[" + no + "]进行登录验证...验证未通过，堆栈轨迹如下");
	           
	            ae.printStackTrace();
	              map.put("data","用户名/密码错误");
	              map.put("success", false);
	              return map;
	        }
	        //验证是否登录成功
	        if(currentUser.isAuthenticated()){
	            log.info("用户[" + no + "]登录认证通过（这里可进行一些认证通过后的系统参数初始化操作）");
	              map.put("data","验证通过");
	              map.put("success", true);
	              return map;
	        }else{
	            token.clear();
	            
	        }
		  map.put("data","验证未通过");
		  map.put("success", false);
		  return map;
	}
	
	@RequestMapping("logout")
    public String logout(){
		new MyRealm().clearCached();
		Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            log.info("当前Session超时时间为[" + session.getTimeout() + "]毫秒");
            session.setTimeout(0);
            log.info("修改Session超时时间为[" + session.getTimeout() + "]毫秒");
        }
        SecurityUtils.getSubject().logout();
        return "views/signin.html";
    }
	
	@ApiOperation(value = "修改密码",notes="修改密码")
	@GetMapping("/updatePassword")
	public Object updatePassword(@RequestParam("id") Integer id,@RequestParam("password")  String password) {
		JSONObject json = new JSONObject();
		SysUsers sysUsers = new SysUsers();
		sysUsers.setId(id);
		sysUsers.setPassword(password);
		Boolean b = ser.modifyPwd(sysUsers);
		json.put("success", b);
		return json;
	}
	
    
}