package com.zhys.serivce.impl;

import com.zhys.user.po.SysRolesPermissions;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.SysRolesPermissionsService;

@Slf4j
@RestController
public class SysRolesPermissionsServiceImpl extends BaseApiService implements SysRolesPermissionsService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody SysRolesPermissions sysRolesPermissions){
		String permissionids = sysRolesPermissions.getPermissionids();
		if(permissionids!=null&&permissionids.length()>0&&sysRolesPermissions.getRoleid()!=null&&!sysRolesPermissions.getRoleid().equals("")){
			 manager.delete("sys_roles_permissions.delete", sysRolesPermissions);
			 permissionids = permissionids.replace("(", "").replace(")", "");
			 String is[]=permissionids.split(",");
			 for(int i=0;i<is.length;i++){
				 if(StringUtils.isNotEmpty(is[i])){
					 sysRolesPermissions.setPermissionid(Integer.parseInt(is[i]));
					 manager.insert("sys_roles_permissions.create", sysRolesPermissions); 
				 }
				 
			 }
		 }
			return 1;
		
	}
	
	
	@Override
	public SysRolesPermissions queryByEntity(@RequestBody SysRolesPermissions sysRolesPermissions){
	             return (SysRolesPermissions)manager.query("sys_roles_permissions.query", sysRolesPermissions);
	}
	@Override
	public List<SysRolesPermissions> queryList(@RequestBody SysRolesPermissions sysRolesPermissions){
	          return (List<SysRolesPermissions>)manager.list("sys_roles_permissions.queryList", sysRolesPermissions);
	}
	@Override
    public Pages<List<SysRolesPermissions>> pages(@RequestBody SysRolesPermissions sysRolesPermissions, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRolesPermissions>>) manager.pages("sys_roles_permissions.page", sysRolesPermissions, page);
	
	}
	/**
    @Override
    public Pages<List<SysRolesPermissions>> pages(@RequestBody SysRolesPermissionsPojo sysRolesPermissionsPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRolesPermissions>>) manager.pages("sys_roles_permissions.page", sysRolesPermissionsPojo, page);
	
	}**/


	@Override
	public Integer changeDelStateById(SysRolesPermissions t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(SysRolesPermissions t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Pages<List<SysRolesPermissions>> pagesByPojo(SysRolesPermissions e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysRolesPermissions> queryListByPoJo(SysRolesPermissions e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}