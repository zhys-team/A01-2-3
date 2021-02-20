package com.invoice.controller;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoice.fegin.SysPermissionsServiceFegin;
import com.invoice.fegin.SysUsersOrgsServiceFegin;
import com.invoice.fegin.SysUsersServiceFegin;
import com.zhys.user.po.SysPermissions;
import com.zhys.user.po.SysUsers;
import com.zhys.user.po.SysUsersOrgs;
import com.zhys.utils.MD5Util;

import lombok.extern.slf4j.Slf4j;



/**
 * 自定义的指定Shiro验证用户登录的类
 * 这里定义了两个用户：jadyer（拥有admin角色和admin:manage权限）、xuanyu（无任何角色和权限）
 * Created by 玄玉<https://jadyer.github.io/> on 2013/09/29 15:15.
 */
@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {
    /**
     * 为当前登录的Subject授予角色和权限
     * -----------------------------------------------------------------------------------------------
     * 经测试：本例中该方法的调用时机为需授权资源被访问时
     * 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持，则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时，先去访问Spring3.1提供的缓存，而不使用Shior提供的AuthorizationCache
     * -----------------------------------------------------------------------------------------------
     */
	@Autowired
	private SysPermissionsServiceFegin service;
	
	@Autowired
	private SysUsersServiceFegin userService;
	
	@Autowired
	private SysUsersOrgsServiceFegin uoservice;
	
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        //获取当前登录的用户名
        String currentUsername = (String)super.getAvailablePrincipal(principals);

        
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //根据当前用户名 获取角色
        Object o = getAuthenticationSession("user");
        if(o!=null&&o instanceof SysUsers){
        	SysUsers u = (SysUsers)o;
        	SysPermissions sysPermissions = new SysPermissions();
        	sysPermissions.setUserNo(u.getNo());
        	List<SysPermissions> list =  service.queryListByUserNo(sysPermissions);
        	if(list!=null&&list.size()>0) {
        		for(SysPermissions sp:list) {
        			if(sp!=null) {
        				simpleAuthorInfo.addStringPermission(sp.getName());
        			}
        		}
        	}
        }
            
            return simpleAuthorInfo;
        
        //若该方法什么都不做直接返回null的话
        //就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置
    }

    /**
     * 验证当前登录的Subject
     * 经测试：本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()的时候
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        //两个token的引用都是一样的，本例中是：org.apache.shiro.authc.UsernamePasswordToken@33799a1e
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        log.info("验证当前Subject时获取到token：");
        log.info(ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        SysUsers u = new SysUsers();
        u.setNo(token.getUsername());
        u= userService.queryByNo(u);
        log.info(">>>>>>>>>no:{}",u.getNo());
        log.info(">>>>>>>>>password:{}",u.getPassword());
        log.info(">>>>>>>>>name:{}",this.getName());
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(u.getNo(), u.getPassword(), getName());
        
//        SysUsers u = new SysUsers();
//        u.setNo("admin");
//        u= userService.queryByNo(u);
//        log.info(">>>>>>>>>password:{}",u.getPassword());
//        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("admin", "E10ADC3949BA59ABBE56E057F20F883E", "admin");
        this.setAuthenticationSession(u);
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时，就会在LoginController中抛出UnknownAccountException异常
        SysUsersOrgs suo = new SysUsersOrgs();
        suo.setUserId(Long.parseLong(u.getId().toString()));
        List<SysUsersOrgs> suos = uoservice.queryList(suo);
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            session.setAttribute("suos",suos);
        }
        return authcInfo;
    }

    /**
     * 将一些数据放到ShiroSession中，以便于其它地方使用
     * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setAuthenticationSession(Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            log.info("当前Session超时时间为[" + session.getTimeout() + "]毫秒");
            session.setTimeout(1000 * 60 * 60 * 12);
            log.info("修改Session超时时间为[" + session.getTimeout() + "]毫秒");
            session.setAttribute("user", value);
        }
    }
    
    /**
     * 将一些数据放到ShiroSession中，以便于其它地方使用
     * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private Object getAuthenticationSession(String name){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            log.info("当前Session超时时间为[" + session.getTimeout() + "]毫秒");
            session.setTimeout(1000 * 60 * 60 * 12);
            log.info("修改Session超时时间为[" + session.getTimeout() + "]毫秒");
          return   session.getAttribute(name);
        }
        return null;
    }
    
    //清除缓存

    public void clearCached() {

    PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
    super.clearCache(principals);

    }
}