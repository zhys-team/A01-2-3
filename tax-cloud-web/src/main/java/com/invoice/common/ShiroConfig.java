package com.invoice.common;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.invoice.controller.MyRealm;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);;
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/views/login/**", "anon");
        filterChainDefinitionMap.put("/static/views/js/**", "anon");
        filterChainDefinitionMap.put("/static/views/layer/**", "anon");
        filterChainDefinitionMap.put("/static/views/css/**", "anon");
        filterChainDefinitionMap.put("/static/views/images/**", "anon");
        filterChainDefinitionMap.put("/static/views/img/**", "anon"); 
        filterChainDefinitionMap.put("/static/views/alifont/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/static/views/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/static/views/login/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    
    /**  
     * cookie对象;  
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。  
     * @return  
    */  
   @Bean  
   public SimpleCookie rememberMeCookie(){  
         //System.out.println("ShiroConfiguration.rememberMeCookie()");  
         //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe  
         SimpleCookie simpleCookie = new SimpleCookie("rememberMe");  
         //<!-- 记住我cookie生效时间30天 ,单位秒;-->  
         simpleCookie.setMaxAge(259200);  
         return simpleCookie;  
   }  
     
   /**  
     * cookie管理对象;  
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中  
     * @return  
    */  
   @Bean  
   public CookieRememberMeManager rememberMeManager(){  
         //System.out.println("ShiroConfiguration.rememberMeManager()");  
         CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();  
         cookieRememberMeManager.setCookie(rememberMeCookie());  
         //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)  
         cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("2AvVhdsgUs0FSA3SDFAdag=="));  
         return cookieRememberMeManager;  
   }  
     

    @Bean
    public MyRealm myShiroRealm(){
    	MyRealm myShiroRealm = new MyRealm();
        return myShiroRealm;
    }


    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
}
