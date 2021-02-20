package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import com.zhys.service.UserService;
import com.zhys.user.po.SysUsers;
import com.zhys.user.pojo.SysUsersPojo;

import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserServiceImpl extends BaseApiService implements UserService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Object save(@RequestBody SysUsers user){
	  SysUsers c = (SysUsers) manager.query("user.query",user);
		if(c != null ){//修改
		  return	manager.update("user.update", user);
		}else{//插入
			/**
			 * 先判断openid是否已存在  已存在则不处理 返回 0
			 */
			List<SysUsers> lists = (List<SysUsers>)manager.list("user.queryList", user);
			if(lists==null||lists.size()==0) {
				return  manager.insert("user.create", user);
				
			}
		  return   0; 
		}
		
	}
	
	
	@Override
	public SysUsers queryByEntity(SysUsers user){
	             return (SysUsers)manager.query("user.query", user);
	}
	@Override
	public List<SysUsers> queryList(SysUsers user){
	          return (List<SysUsers>)manager.list("user.queryList", user);
	}
	@Override
    public Pages<List<SysUsers>> pages(SysUsers user, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysUsers>>) manager.pages("user.page", user, page);
	
	}


	@Override
	public Pages<List<SysUsers>> pagesByPojo(SysUsersPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysUsers> queryListByPoJo(SysUsersPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(SysUsers t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(SysUsersPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}