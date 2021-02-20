package com.zhys.serivce.impl;

import com.zhys.user.po.SysRoles;
import com.zhys.service.SQLManager;

import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.SysRolesService;

@Slf4j
@RestController
public class SysRolesServiceImpl extends BaseApiService implements SysRolesService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody SysRoles sysRoles){
	  SysRoles c = (SysRoles) manager.query("sys_roles.query",sysRoles);
		if(c != null ){//修改
		  return	manager.update("sys_roles.update", sysRoles);
		}else{//插入
		  return    manager.insert("sys_roles.create", sysRoles);
		}
		
	}
	
	
	@Override
	public SysRoles queryByEntity(@RequestBody SysRoles sysRoles){
	             return (SysRoles)manager.query("sys_roles.query", sysRoles);
	}
	@Override
	public List<SysRoles> queryList(@RequestBody SysRoles sysRoles){
	          return (List<SysRoles>)manager.list("sys_roles.queryList", sysRoles);
	}
	@Override
    public Pages<List<SysRoles>> pages(@RequestBody SysRoles sysRoles, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRoles>>) manager.pages("sys_roles.page", sysRoles, page);
	
	}
	/**
    @Override
    public Pages<List<SysRoles>> pages(@RequestBody SysRolesPojo sysRolesPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRoles>>) manager.pages("sys_roles.page", sysRolesPojo, page);
	
	}**/


	@Override
	public Integer changeDelStateById(SysRoles t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody SysRoles t) {
		manager.delete("sys_roles.delete", t);
		return 1;
	}


	@Override
	public Pages<List<SysRoles>> pagesByPojo(SysRoles e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysRoles> queryListByPoJo(SysRoles e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}