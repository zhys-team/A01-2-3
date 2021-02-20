package com.zhys.serivce.impl;

import com.invoice.po.AddTaxDetailFree;
import com.invoice.pojo.AddTaxDetailFreePojo;
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
import com.zhys.service.AddTaxDetailFreeService;

@Slf4j
@RestController
public class AddTaxDetailFreeServiceImpl extends BaseApiService implements AddTaxDetailFreeService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxDetailFree addTaxDetailFree){
	  AddTaxDetailFree c = (AddTaxDetailFree) manager.query("add_tax_detail_free.query",addTaxDetailFree);
		if(c != null ){//修改
		  return	manager.update("add_tax_detail_free.update", addTaxDetailFree);
		}else{//插入
		  return    manager.insert("add_tax_detail_free.create", addTaxDetailFree);
		}
		
	}
	
	
	@Override
	public AddTaxDetailFree queryByEntity(@RequestBody AddTaxDetailFree addTaxDetailFree){
	             return (AddTaxDetailFree)manager.query("add_tax_detail_free.query", addTaxDetailFree);
	}
	@Override
	public List<AddTaxDetailFree> queryList(@RequestBody AddTaxDetailFree addTaxDetailFree){
	          return (List<AddTaxDetailFree>)manager.list("add_tax_detail_free.queryList", addTaxDetailFree);
	}
	@Override
    public Pages<List<AddTaxDetailFree>> pages(@RequestBody AddTaxDetailFree addTaxDetailFree, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxDetailFree>>) manager.pages("add_tax_detail_free.page", addTaxDetailFree, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxDetailFree>> pages(@RequestBody AddTaxDetailFreePojo addTaxDetailFreePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxDetailFree>>) manager.pages("add_tax_detail_free.page", addTaxDetailFreePojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxDetailFree addTaxDetailFree) {
		return manager.update("add_tax_detail_free.changeDelStateById", addTaxDetailFree);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxDetailFreePojo addTaxDetailFreePojo) {
		return manager.update("add_tax_detail_free.changeDelStateByIds", addTaxDetailFreePojo);
	}


	@Override
	public Pages<List<AddTaxDetailFree>> pagesByPojo(AddTaxDetailFreePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxDetailFree> queryListByPoJo(AddTaxDetailFreePojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}