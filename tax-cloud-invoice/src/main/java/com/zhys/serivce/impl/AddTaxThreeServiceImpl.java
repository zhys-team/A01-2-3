package com.zhys.serivce.impl;

import com.invoice.po.AddTaxThree;
import com.invoice.pojo.AddTaxThreePojo;
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
import com.zhys.service.AddTaxThreeService;

@Slf4j
@RestController
public class AddTaxThreeServiceImpl extends BaseApiService implements AddTaxThreeService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxThree addTaxThree){
	  AddTaxThree c = (AddTaxThree) manager.query("add_tax_three.query",addTaxThree);
		if(c != null ){//修改
		  return	manager.update("add_tax_three.update", addTaxThree);
		}else{//插入
		  return    manager.insert("add_tax_three.create", addTaxThree);
		}
		
	}
	
	
	@Override
	public AddTaxThree queryByEntity(@RequestBody AddTaxThree addTaxThree){
	             return (AddTaxThree)manager.query("add_tax_three.query", addTaxThree);
	}
	@Override
	public List<AddTaxThree> queryList(@RequestBody AddTaxThree addTaxThree){
	          return (List<AddTaxThree>)manager.list("add_tax_three.queryList", addTaxThree);
	}
	@Override
    public Pages<List<AddTaxThree>> pages(@RequestBody AddTaxThree addTaxThree, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxThree>>) manager.pages("add_tax_three.page", addTaxThree, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxThree>> pages(@RequestBody AddTaxThreePojo addTaxThreePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxThree>>) manager.pages("add_tax_three.page", addTaxThreePojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxThree addTaxThree) {
		return manager.update("add_tax_three.changeDelStateById", addTaxThree);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxThreePojo addTaxThreePojo) {
		return manager.update("add_tax_three.changeDelStateByIds", addTaxThreePojo);
	}


	@Override
	public Pages<List<AddTaxThree>> pagesByPojo(AddTaxThreePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxThree> queryListByPoJo(AddTaxThreePojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}