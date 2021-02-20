package com.zhys.serivce.impl;

import com.invoice.po.AddTaxDetailSub;
import com.invoice.pojo.AddTaxDetailSubPojo;
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
import com.zhys.service.AddTaxDetailSubService;

@Slf4j
@RestController
public class AddTaxDetailSubServiceImpl extends BaseApiService implements AddTaxDetailSubService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxDetailSub addTaxDetailSub){
	  AddTaxDetailSub c = (AddTaxDetailSub) manager.query("add_tax_detail_sub.query",addTaxDetailSub);
		if(c != null ){//修改
		  return	manager.update("add_tax_detail_sub.update", addTaxDetailSub);
		}else{//插入
		  return    manager.insert("add_tax_detail_sub.create", addTaxDetailSub);
		}
		
	}
	
	
	@Override
	public AddTaxDetailSub queryByEntity(@RequestBody AddTaxDetailSub addTaxDetailSub){
	             return (AddTaxDetailSub)manager.query("add_tax_detail_sub.query", addTaxDetailSub);
	}
	@Override
	public List<AddTaxDetailSub> queryList(@RequestBody AddTaxDetailSub addTaxDetailSub){
	          return (List<AddTaxDetailSub>)manager.list("add_tax_detail_sub.queryList", addTaxDetailSub);
	}
	@Override
    public Pages<List<AddTaxDetailSub>> pages(@RequestBody AddTaxDetailSub addTaxDetailSub, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxDetailSub>>) manager.pages("add_tax_detail_sub.page", addTaxDetailSub, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxDetailSub>> pages(@RequestBody AddTaxDetailSubPojo addTaxDetailSubPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxDetailSub>>) manager.pages("add_tax_detail_sub.page", addTaxDetailSubPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxDetailSub addTaxDetailSub) {
		return manager.update("add_tax_detail_sub.changeDelStateById", addTaxDetailSub);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxDetailSubPojo addTaxDetailSubPojo) {
		return manager.update("add_tax_detail_sub.changeDelStateByIds", addTaxDetailSubPojo);
	}


	@Override
	public Pages<List<AddTaxDetailSub>> pagesByPojo(AddTaxDetailSubPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxDetailSub> queryListByPoJo(AddTaxDetailSubPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}