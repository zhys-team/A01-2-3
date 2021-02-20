package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.EquivalentSale;
import com.invoice.pojo.EquivalentSalePojo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.EquivalentSaleService;

@Slf4j
@RestController
public class EquivalentSaleServiceImpl extends BaseApiService implements EquivalentSaleService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody EquivalentSale equivalentSale){
		if(equivalentSale.getId() != null ){//修改
		  return	manager.update("equivalent_sale.update", equivalentSale);
		}else{//插入
		  return    manager.insert("equivalent_sale.create", equivalentSale);
		}
		
	}
	
	
	@Override
	public EquivalentSale queryByEntity(@RequestBody EquivalentSale equivalentSale){
	             return (EquivalentSale)manager.query("equivalent_sale.query", equivalentSale);
	}
	@Override
	public List<EquivalentSale> queryList(@RequestBody EquivalentSale equivalentSale){
	          return (List<EquivalentSale>)manager.list("equivalent_sale.queryList", equivalentSale);
	}
	@Override
    public Pages<List<EquivalentSale>> pages(@RequestBody EquivalentSale equivalentSale, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EquivalentSale>>) manager.pages("equivalent_sale.page", equivalentSale, page);
	
	}
	/**
    @Override
    public Pages<List<EquivalentSale>> pages(@RequestBody EquivalentSalePojo equivalentSalePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EquivalentSale>>) manager.pages("equivalent_sale.page", equivalentSalePojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody EquivalentSale equivalentSale) {
		return manager.update("equivalent_sale.changeDelStateById", equivalentSale);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody EquivalentSalePojo equivalentSalePojo) {
		return manager.update("equivalent_sale.changeDelStateByIds", equivalentSalePojo);
	}


	@Override
	public Pages<List<EquivalentSale>> pagesByPojo(EquivalentSalePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<EquivalentSale> queryListByPoJo(EquivalentSalePojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}