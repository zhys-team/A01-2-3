package com.zhys.serivce.impl;

import com.zhys.user.po.InvoiceAuth;
import com.zhys.user.pojo.InvoiceAuthPojo;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.InvoiceAuthService;

@Slf4j
@RestController
public class InvoiceAuthServiceImpl extends BaseApiService implements InvoiceAuthService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody InvoiceAuth invoiceAuth){
	  InvoiceAuth c = (InvoiceAuth) manager.query("invoice_auth.query",invoiceAuth);
		if(c != null ){//修改
		  return	manager.update("invoice_auth.update", invoiceAuth);
		}else{//插入
		  return    manager.insert("invoice_auth.create", invoiceAuth);
		}
		
	}
	
	
	@Override
	public InvoiceAuth queryByEntity(@RequestBody InvoiceAuth invoiceAuth){
	             return (InvoiceAuth)manager.query("invoice_auth.query", invoiceAuth);
	}
	@Override
	public List<InvoiceAuth> queryList(@RequestBody InvoiceAuth invoiceAuth){
	          return (List<InvoiceAuth>)manager.list("invoice_auth.queryList", invoiceAuth);
	}
	@Override
    public Pages<List<InvoiceAuth>> pages(@RequestBody InvoiceAuth invoiceAuth, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<InvoiceAuth>>) manager.pages("invoice_auth.page", invoiceAuth, page);
	
	}
	/**
    @Override
    public Pages<List<InvoiceAuth>> pages(@RequestBody InvoiceAuthPojo invoiceAuthPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<InvoiceAuth>>) manager.pages("invoice_auth.page", invoiceAuthPojo, page);
	
	}**/


	@Override
	public Pages<List<InvoiceAuth>> pagesByPojo(InvoiceAuthPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<InvoiceAuth> queryListByPoJo(InvoiceAuthPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody InvoiceAuth t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody InvoiceAuthPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}