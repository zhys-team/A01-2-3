package com.zhys.serivce.impl;

import com.invoice.po.AddTaxFirst;
import com.invoice.pojo.AddTaxFirstPojo;
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
import com.zhys.service.AddTaxFirstService;

@Slf4j
@RestController
public class AddTaxFirstServiceImpl extends BaseApiService implements AddTaxFirstService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxFirst addTaxFirst){
	  AddTaxFirst c = (AddTaxFirst) manager.query("add_tax_first.query",addTaxFirst);
		if(c != null ){//修改
		  return	manager.update("add_tax_first.update", addTaxFirst);
		}else{//插入
		  return    manager.insert("add_tax_first.create", addTaxFirst);
		}
		
	}
	
	
	@Override
	public AddTaxFirst queryByEntity(@RequestBody AddTaxFirst addTaxFirst){
	             return (AddTaxFirst)manager.query("add_tax_first.query", addTaxFirst);
	}
	@Override
	public List<AddTaxFirst> queryList(@RequestBody AddTaxFirst addTaxFirst){
	          return (List<AddTaxFirst>)manager.list("add_tax_first.queryList", addTaxFirst);
	}
	@Override
    public Pages<List<AddTaxFirst>> pages(@RequestBody AddTaxFirst addTaxFirst, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFirst>>) manager.pages("add_tax_first.page", addTaxFirst, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxFirst>> pages(@RequestBody AddTaxFirstPojo addTaxFirstPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxFirst>>) manager.pages("add_tax_first.page", addTaxFirstPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxFirst addTaxFirst) {
		return manager.update("add_tax_first.changeDelStateById", addTaxFirst);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxFirstPojo addTaxFirstPojo) {
		return manager.update("add_tax_first.changeDelStateByIds", addTaxFirstPojo);
	}


	@Override
	public Pages<List<AddTaxFirst>> pagesByPojo(AddTaxFirstPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxFirst> queryListByPoJo(AddTaxFirstPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}