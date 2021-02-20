package com.zhys.serivce.impl;

import com.zhys.user.po.SysUsersOrgs;
import com.zhys.user.pojo.SysUsersOrgsPojo;
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
import com.zhys.service.SysUsersOrgsService;

@Slf4j
@RestController
public class SysUsersOrgsServiceImpl extends BaseApiService implements SysUsersOrgsService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody SysUsersOrgs sysUsersOrgs){
		String orgIds = sysUsersOrgs.getOrgIds();
		if(orgIds!=null&&orgIds.length()>0&&sysUsersOrgs.getUserId()!=null&&!sysUsersOrgs.getUserId().equals("")){
			 manager.delete("sys_users_orgs.delete", sysUsersOrgs);
			 orgIds = orgIds.replace("(", "").replace(")", "");
			 String is[]=orgIds.split(",");
			 for(int i=0;i<is.length;i++){
				 if(StringUtils.isNotEmpty(is[i])){
					 sysUsersOrgs.setOrgId(is[i]);
					 sysUsersOrgs.setId(null);
					 manager.insert("sys_users_orgs.create", sysUsersOrgs); 
				 }
				 
			 }
		 }
			return 1;
		
	}
	
	
	@Override
	public SysUsersOrgs queryByEntity(@RequestBody SysUsersOrgs sysUsersOrgs){
	             return (SysUsersOrgs)manager.query("sys_users_orgs.query", sysUsersOrgs);
	}
	@Override
	public List<SysUsersOrgs> queryList(@RequestBody SysUsersOrgs sysUsersOrgs){
	          return (List<SysUsersOrgs>)manager.list("sys_users_orgs.queryList", sysUsersOrgs);
	}
	@Override
    public Pages<List<SysUsersOrgs>> pages(SysUsersOrgs sysUsersOrgs, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysUsersOrgs>>) manager.pages("sys_users_orgs.page", sysUsersOrgs, page);
	
	}

	@Override
	public Pages<List<SysUsersOrgs>> pagesByPojo(SysUsersOrgsPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysUsersOrgs> queryListByPoJo(SysUsersOrgsPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(SysUsersOrgs t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(SysUsersOrgsPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}