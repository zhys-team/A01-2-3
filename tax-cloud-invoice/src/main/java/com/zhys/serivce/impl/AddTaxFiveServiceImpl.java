package com.zhys.serivce.impl;

import com.invoice.po.AddTaxFive;
import com.invoice.pojo.AddTaxFivePojo;
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
import com.zhys.service.AddTaxFiveService;

@Slf4j
@RestController
public class AddTaxFiveServiceImpl extends BaseApiService implements AddTaxFiveService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxFive addTaxFive){
	  AddTaxFive c = (AddTaxFive) manager.query("add_tax_five.query",addTaxFive);
		if(c != null ){//修改
		  return	manager.update("add_tax_five.update", addTaxFive);
		}else{//插入
		  return    manager.insert("add_tax_five.create", addTaxFive);
		}
		
	}
	
	
	@Override
	public AddTaxFive queryByEntity(@RequestBody AddTaxFive addTaxFive){
	             return (AddTaxFive)manager.query("add_tax_five.query", addTaxFive);
	}
	@Override
	public List<AddTaxFive> queryList(@RequestBody AddTaxFive addTaxFive){
	          return (List<AddTaxFive>)manager.list("add_tax_five.queryList", addTaxFive);
	}
	@Override
    public Pages<List<AddTaxFive>> pages(@RequestBody AddTaxFive addTaxFive, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFive>>) manager.pages("add_tax_five.page", addTaxFive, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxFive>> pages(@RequestBody AddTaxFivePojo addTaxFivePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFive>>) manager.pages("add_tax_five.page", addTaxFivePojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxFive addTaxFive) {
		return manager.update("add_tax_five.changeDelStateById", addTaxFive);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxFivePojo
			addTaxFivePojo) {
		return manager.update("add_tax_five.changeDelStateByIds", addTaxFivePojo);
	}


	@Override
	public Pages<List<AddTaxFive>> pagesByPojo(AddTaxFivePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxFive> queryListByPoJo(AddTaxFivePojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}