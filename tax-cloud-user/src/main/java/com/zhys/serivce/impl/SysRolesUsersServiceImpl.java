package com.zhys.serivce.impl;

import com.zhys.user.po.SysRolesUsers;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.SysRolesUsersService;

@Slf4j
@RestController
public class SysRolesUsersServiceImpl extends BaseApiService implements SysRolesUsersService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody SysRolesUsers sysRolesUsers){
		String userids = sysRolesUsers.getUserids();
		if(userids!=null&&userids.length()>0&&sysRolesUsers.getRoleid()!=null&&!sysRolesUsers.getRoleid().equals("")){
			 manager.delete("sys_roles_users.delete", sysRolesUsers);
			 userids = userids.replace("(", "").replace(")", "");
			 String is[]=userids.split(",");
			 for(int i=0;i<is.length;i++){
				 if(StringUtils.isNotEmpty(is[i])){
					 sysRolesUsers.setUserid(Integer.parseInt(is[i]));
					 manager.insert("sys_roles_users.create", sysRolesUsers); 
				 }
				 
			 }
		 }
			return 1;
		
	}
	
	
	@Override
	public SysRolesUsers queryByEntity(@RequestBody SysRolesUsers sysRolesUsers){
	             return (SysRolesUsers)manager.query("sys_roles_users.query", sysRolesUsers);
	}
	@Override
	public List<SysRolesUsers> queryList(@RequestBody SysRolesUsers sysRolesUsers){
	          return (List<SysRolesUsers>)manager.list("sys_roles_users.queryList", sysRolesUsers);
	}
	@Override
    public Pages<List<SysRolesUsers>> pages(@RequestBody SysRolesUsers sysRolesUsers, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRolesUsers>>) manager.pages("sys_roles_users.page", sysRolesUsers, page);
	
	}
	/**
    @Override
    public Pages<List<SysRolesUsers>> pages(@RequestBody SysRolesUsersPojo sysRolesUsersPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysRolesUsers>>) manager.pages("sys_roles_users.page", sysRolesUsersPojo, page);
	
	}**/


	@Override
	public Integer changeDelStateById(SysRolesUsers t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(SysRolesUsers t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Pages<List<SysRolesUsers>> pagesByPojo(SysRolesUsers e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysRolesUsers> queryListByPoJo(SysRolesUsers e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}