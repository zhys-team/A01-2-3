package com.zhys.serivce.impl;

import com.zhys.user.po.InvoiceAuth;
import com.zhys.user.po.SysOrgs;
import com.zhys.user.po.TaxMech;
import com.zhys.user.pojo.SysOrgsPojo;
import com.zhys.service.SQLManager;

import java.util.ArrayList;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.SysOrgsService;

@Slf4j
@RestController
public class SysOrgsServiceImpl extends BaseApiService implements SysOrgsService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	@Transactional
	public Integer save(@RequestBody SysOrgs sysOrgs){
		if(sysOrgs.getId() != null ){//修改
			
			manager.delete("invoice_auth.changeStateById", sysOrgs) ;
			manager.delete("tax_mech.changeStateById", sysOrgs) ;
		  	manager.update("sys_orgs.update", sysOrgs);
		}else{//插入
		      manager.insert("sys_orgs.create", sysOrgs);
		}
		
		List<InvoiceAuth> auths = sysOrgs.getAuths();
		if(auths!=null&&auths.size()>0) {
			for(InvoiceAuth auth:auths) {
				auth.setOrgId(sysOrgs.getId());
				manager.insert("invoice_auth.create", auth);
			}
			
		}
		List<TaxMech> mechs = sysOrgs.getMechs();
		if(mechs!=null&&mechs.size()>0) {
			for(TaxMech mech:mechs) {
				mech.setOrgId(sysOrgs.getId());
				manager.insert("tax_mech.create", mech);
			}
			
		}
		
		return 1;
	}
	
	
	@Override
	public SysOrgs queryByEntity(@RequestBody  SysOrgs sysOrgs){
		SysOrgs org =    (SysOrgs)manager.query("sys_orgs.query", sysOrgs);
	              
		return org;
	}
	@Override
	public List<SysOrgs> queryList(@RequestBody SysOrgs sysOrgs){
	          return (List<SysOrgs>)manager.list("sys_orgs.queryList", sysOrgs);
	}
	@Override
    public Pages<List<SysOrgs>> pages(@RequestBody SysOrgs sysOrgs, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<SysOrgs>>) manager.pages("sys_orgs.page", sysOrgs, page);
	
	}
	

	@Override
	public Pages<List<SysOrgs>> pagesByPojo(SysOrgsPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SysOrgs> queryListByPoJo(SysOrgsPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody SysOrgs t) {
		return manager.delete("sys_orgs.changeStateById", t);
	}


	@Override
	public Integer changeDelStateByIds(SysOrgsPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}