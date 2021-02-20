package com.zhys.serivce.impl;

import com.invoice.po.InvoiceDoubt;
import com.invoice.pojo.InvoiceDoubtPoJo;
import com.zhys.service.InvoiceDoubtService;
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


@Slf4j
@RestController
public class InvoiceDoubtServiceImpl extends BaseApiService implements InvoiceDoubtService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(InvoiceDoubt invoiceDoubt){
	  InvoiceDoubt c = (InvoiceDoubt) manager.query("invoice_doubt.query",invoiceDoubt);
		if(c != null ){//修改
		  return	manager.update("invoice_doubt.update", invoiceDoubt);
		}else{//插入
		  return    manager.insert("invoice_doubt.create", invoiceDoubt);
		}
		
	}
	
	
	@Override
	public InvoiceDoubt queryByEntity(InvoiceDoubt invoiceDoubt){
	             return (InvoiceDoubt)manager.query("invoice_doubt.query", invoiceDoubt);
	}
	@Override
	public List<InvoiceDoubt> queryList(InvoiceDoubt invoiceDoubt){
	          return (List<InvoiceDoubt>)manager.list("invoice_doubt.queryList", invoiceDoubt);
	}


	@Override
	public Pages<List<InvoiceDoubt>> pages(InvoiceDoubt invoiceDoubt, Integer pageSize, Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<InvoiceDoubt>>) manager.pages("invoice_doubt.page", invoiceDoubt, page);
	}


	@Override
	public Pages<List<InvoiceDoubt>> pagesByPojo(@RequestBody InvoiceDoubtPoJo invoiceDoubt, Integer pageSize, Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<InvoiceDoubt>>) manager.pages("invoice_doubt.page", invoiceDoubt, page);
	}


	@Override
	public Integer changeDelStateById(InvoiceDoubt t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(InvoiceDoubtPoJo t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeStateById(@RequestBody InvoiceDoubt t) {
		return manager.update("invoice_doubt.changeStateById", t);
	}


	@Override
	public Integer changeStateByIds(@RequestBody InvoiceDoubtPoJo t) {
		return manager.update("invoice_doubt.changeStateByIds", t);
	}


	@Override
	public List<InvoiceDoubt> queryListByPoJo(InvoiceDoubtPoJo e) {
		// TODO Auto-generated method stub
		return null;
	}

	
    
}