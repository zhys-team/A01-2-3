package com.zhys.serivce.impl;

import com.invoice.po.AddTaxFour;
import com.invoice.pojo.AddTaxFourPojo;
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
import com.zhys.service.AddTaxFourService;

@Slf4j
@RestController
public class AddTaxFourServiceImpl extends BaseApiService implements AddTaxFourService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxFour addTaxFour){
	  AddTaxFour c = (AddTaxFour) manager.query("add_tax_four.query",addTaxFour);
		if(c != null ){//修改
		  return	manager.update("add_tax_four.update", addTaxFour);
		}else{//插入
		  return    manager.insert("add_tax_four.create", addTaxFour);
		}
		
	}
	
	
	@Override
	public AddTaxFour queryByEntity(@RequestBody AddTaxFour addTaxFour){
	             return (AddTaxFour)manager.query("add_tax_four.query", addTaxFour);
	}
	@Override
	public List<AddTaxFour> queryList(@RequestBody AddTaxFour addTaxFour){
	          return (List<AddTaxFour>)manager.list("add_tax_four.queryList", addTaxFour);
	}
	@Override
    public Pages<List<AddTaxFour>> pages(@RequestBody AddTaxFour addTaxFour, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFour>>) manager.pages("add_tax_four.page", addTaxFour, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxFour>> pages(@RequestBody AddTaxFourPojo addTaxFourPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFour>>) manager.pages("add_tax_four.page", addTaxFourPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxFour addTaxFour) {
		return manager.update("add_tax_four.changeDelStateById", addTaxFour);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxFourPojo addTaxFourPojo) {
		return manager.update("add_tax_four.changeDelStateByIds", addTaxFourPojo);
	}


	@Override
	public Pages<List<AddTaxFour>> pagesByPojo(AddTaxFourPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxFour> queryListByPoJo(AddTaxFourPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}