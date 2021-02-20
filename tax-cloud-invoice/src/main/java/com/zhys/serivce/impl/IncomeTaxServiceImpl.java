package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.IncomeTax;
import com.invoice.pojo.IncomeTaxPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.IncomeTaxService;

@Slf4j
@RestController
public class IncomeTaxServiceImpl extends BaseApiService implements IncomeTaxService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody IncomeTax incomeTax){
	  IncomeTax c = (IncomeTax) manager.query("income_tax.query",incomeTax);
		if(c != null ){//修改
		  return	manager.update("income_tax.update", incomeTax);
		}else{//插入
		  return    manager.insert("income_tax.create", incomeTax);
		}
		
	}
	
	
	@Override
	public IncomeTax queryByEntity(@RequestBody IncomeTax incomeTax){
	             return (IncomeTax)manager.query("income_tax.query", incomeTax);
	}
	@Override
	public List<IncomeTax> queryList(@RequestBody IncomeTax incomeTax){
	          return (List<IncomeTax>)manager.list("income_tax.queryList", incomeTax);
	}
	@Override
    public Pages<List<IncomeTax>> pages(@RequestBody IncomeTax incomeTax, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<IncomeTax>>) manager.pages("income_tax.page", incomeTax, page);
	
	}
	/**
    @Override
    public Pages<List<IncomeTax>> pages(@RequestBody IncomeTaxPojo incomeTaxPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<IncomeTax>>) manager.pages("income_tax.page", incomeTaxPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody IncomeTax incomeTax) {
		return manager.update("income_tax.changeDelStateById", incomeTax);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody IncomeTaxPoJo incomeTaxPojo) {
		return manager.update("income_tax.changeDelStateByIds", incomeTaxPojo);
	}


	@Override
	public Pages<List<IncomeTax>> pagesByPojo(@RequestBody IncomeTaxPoJo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IncomeTax> queryListByPoJo(@RequestBody IncomeTaxPoJo e) {
		// TODO Auto-generated method stub
		return null;
	}

    
}