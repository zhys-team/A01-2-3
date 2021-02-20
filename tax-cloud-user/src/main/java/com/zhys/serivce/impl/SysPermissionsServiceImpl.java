package com.zhys.serivce.impl;

import com.zhys.user.po.SysPermissions;
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
import com.zhys.service.SysPermissionsService;

@Slf4j
@RestController
public class SysPermissionsServiceImpl extends BaseApiService implements SysPermissionsService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody SysPermissions sysPermissions){
	  SysPermissions c = (SysPermissions) manager.query("sys_permissions.query",sysPermissions);
		if(c != null ){//修改
		  return	manager.update("sys_permissions.update", sysPermissions);
		}else{//插入
		  return    manager.insert("sys_permissions.create", sysPermissions);
		}
		
	}
	
	
	@Override
	public SysPermissions queryByEntity(@RequestBody SysPermissions sysPermissions){
	             return (SysPermissions)manager.query("sys_permissions.query", sysPermissions);
	}
	@Override
	public List<SysPermissions> queryList(@RequestBody SysPermissions sysPermissions){
	          return (List<SysPermissions>)manager.list("sys_permissions.queryList", sysPermissions);
	}
	@Override
    public Pages<List<SysPermissions>> pages(@RequestBody SysPermissions sysPermissions, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysPermissions>>) manager.pages("sys_permissions.page", sysPermissions, page);
	
	}
	/**
    @Override
    public Pages<List<SysPermissions>> pages(@RequestBody SysPermissionsPojo sysPermissionsPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysPermissions>>) manager.pages("sys_permissions.page", sysPermissionsPojo, page);
	
	}**/





	@Override
	public Integer changeDelStateById(SysPermissions t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(SysPermissions t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Pages<List<SysPermissions>> pagesByPojo(SysPermissions e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysPermissions> queryListByPoJo(SysPermissions e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysPermissions> queryListByUserNo(@RequestBody SysPermissions sysPermissions) {
		return (List<SysPermissions>)manager.list("sys_permissions.queryListByUserNo", sysPermissions);
	}
	
    
}