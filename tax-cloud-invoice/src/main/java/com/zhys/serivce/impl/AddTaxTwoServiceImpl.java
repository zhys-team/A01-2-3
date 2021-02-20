package com.zhys.serivce.impl;

import com.invoice.po.AddTaxTwo;
import com.invoice.pojo.AddTaxTwoPojo;
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
import com.zhys.service.AddTaxTwoService;

@Slf4j
@RestController
public class AddTaxTwoServiceImpl extends BaseApiService implements AddTaxTwoService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxTwo addTaxTwo){
	  AddTaxTwo c = (AddTaxTwo) manager.query("add_tax_two.query",addTaxTwo);
		if(c != null ){//修改
		  return	manager.update("add_tax_two.update", addTaxTwo);
		}else{//插入
		  return    manager.insert("add_tax_two.create", addTaxTwo);
		}
		
	}
	
	
	@Override
	public AddTaxTwo queryByEntity(@RequestBody AddTaxTwo addTaxTwo){
	             return (AddTaxTwo)manager.query("add_tax_two.query", addTaxTwo);
	}
	@Override
	public List<AddTaxTwo> queryList(@RequestBody AddTaxTwo addTaxTwo){
	          return (List<AddTaxTwo>)manager.list("add_tax_two.queryList", addTaxTwo);
	}
	@Override
    public Pages<List<AddTaxTwo>> pages(@RequestBody AddTaxTwo addTaxTwo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxTwo>>) manager.pages("add_tax_two.page", addTaxTwo, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxTwo>> pages(@RequestBody AddTaxTwoPojo addTaxTwoPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxTwo>>) manager.pages("add_tax_two.page", addTaxTwoPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxTwo addTaxTwo) {
		return manager.update("add_tax_two.changeDelStateById", addTaxTwo);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxTwoPojo addTaxTwoPojo) {
		return manager.update("add_tax_two.changeDelStateByIds", addTaxTwoPojo);
	}


	@Override
	public Pages<List<AddTaxTwo>> pagesByPojo(AddTaxTwoPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxTwo> queryListByPoJo(AddTaxTwoPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}