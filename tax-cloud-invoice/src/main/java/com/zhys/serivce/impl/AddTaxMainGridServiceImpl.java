package com.zhys.serivce.impl;

import com.invoice.po.AddTaxMainGrid;
import com.invoice.pojo.AddTaxMainGridPojo;
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
import com.zhys.service.AddTaxMainGridService;

@Slf4j
@RestController
public class AddTaxMainGridServiceImpl extends BaseApiService implements AddTaxMainGridService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxMainGrid addTaxMainGrid){
	  AddTaxMainGrid c = (AddTaxMainGrid) manager.query("add_tax_main_grid.query",addTaxMainGrid);
		if(c != null ){//修改
		  return	manager.update("add_tax_main_grid.update", addTaxMainGrid);
		}else{//插入
		  return    manager.insert("add_tax_main_grid.create", addTaxMainGrid);
		}
		
	}
	
	
	@Override
	public AddTaxMainGrid queryByEntity(@RequestBody AddTaxMainGrid addTaxMainGrid){
	             return (AddTaxMainGrid)manager.query("add_tax_main_grid.query", addTaxMainGrid);
	}
	@Override
	public List<AddTaxMainGrid> queryList(@RequestBody AddTaxMainGrid addTaxMainGrid){
	          return (List<AddTaxMainGrid>)manager.list("add_tax_main_grid.queryList", addTaxMainGrid);
	}
	@Override
    public Pages<List<AddTaxMainGrid>> pages(@RequestBody AddTaxMainGrid addTaxMainGrid, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxMainGrid>>) manager.pages("add_tax_main_grid.page", addTaxMainGrid, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxMainGrid>> pages(@RequestBody AddTaxMainGridPojo addTaxMainGridPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxMainGrid>>) manager.pages("add_tax_main_grid.page", addTaxMainGridPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxMainGrid addTaxMainGrid) {
		return manager.update("add_tax_main_grid.changeDelStateById", addTaxMainGrid);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxMainGridPojo addTaxMainGridPojo) {
		return manager.update("add_tax_main_grid.changeDelStateByIds", addTaxMainGridPojo);
	}


	@Override
	public Pages<List<AddTaxMainGrid>> pagesByPojo(AddTaxMainGridPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxMainGrid> queryListByPoJo(AddTaxMainGridPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}