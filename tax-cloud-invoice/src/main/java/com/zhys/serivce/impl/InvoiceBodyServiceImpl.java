package com.zhys.serivce.impl;

import com.zhys.service.InvoiceBodyService;
import com.zhys.service.SQLManager;

import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.InvoiceBody;
import com.invoice.pojo.InvoiceBodyPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class InvoiceBodyServiceImpl implements InvoiceBodyService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(InvoiceBody invoiceBody){
	  InvoiceBody c = (InvoiceBody) manager.query("invoice_body.query",invoiceBody);
		if(c != null ){//修改
		return	manager.update("invoice_body.update", invoiceBody);
		}else{//插入
		return	manager.insert("invoice_body.create", invoiceBody);
		}
	}
	
	
	@Override
	public InvoiceBody queryByEntity(InvoiceBody invoiceBody){
	             return (InvoiceBody)manager.query("invoice_body.query", invoiceBody);
	}
	
	@Override
	public List<InvoiceBody> queryList(@RequestBody InvoiceBody invoiceBody){
	          return (List<InvoiceBody>)manager.list("invoice_body.queryList", invoiceBody);
	}
	
	
	@Override
	public List<InvoiceBody> queryListByPoJo(@RequestBody InvoiceBodyPoJo e) {
		return (List<InvoiceBody>)manager.list("invoice_body.queryListByPoJo", e);
	}
	
	
	@Override
    public Pages<List<InvoiceBody>> pages(@RequestBody InvoiceBody t,Integer pageSize,Integer pageNum){
	    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<InvoiceBody>>) manager.pages("invoice_body.page", t, page);
	
	}

	@Override
	public Pages<List<InvoiceBody>> pagesByPojo(InvoiceBodyPoJo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(InvoiceBody t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(InvoiceBodyPoJo t) {
		// TODO Auto-generated method stub
		return null;
	}


	

	
    
}