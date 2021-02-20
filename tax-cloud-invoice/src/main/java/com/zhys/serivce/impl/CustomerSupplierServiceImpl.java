package com.zhys.serivce.impl;

import com.zhys.service.CustomerSupplierService;
import com.zhys.service.SQLManager;

import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.CustomerSupplier;
import com.invoice.pojo.CustomerSupplierPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class CustomerSupplierServiceImpl implements CustomerSupplierService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(CustomerSupplier customerSupplier){
	  CustomerSupplier c = (CustomerSupplier) manager.query("customer_supplier.query",customerSupplier);
		if(c != null ){//修改
		return	manager.update("customer_supplier.update", customerSupplier);
		}else{//插入
		return	manager.insert("customer_supplier.create", customerSupplier);
		}
	}
	
	
	@Override
	public CustomerSupplier queryByEntity(CustomerSupplier customerSupplier){
	             return (CustomerSupplier)manager.query("customer_supplier.query", customerSupplier);
	}
	@Override
	public List<CustomerSupplier> queryList(CustomerSupplier customerSupplier){
	          return (List<CustomerSupplier>)manager.list("customer_supplier.queryList", customerSupplier);
	}
	@Override
    public Pages<List<CustomerSupplier>> pages(@RequestBody CustomerSupplier t,Integer pageSize,Integer pageNum){
	    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<CustomerSupplier>>) manager.pages("customer_supplier.page", t, page);
	
	}

	@Override
    public Pages<List<CustomerSupplier>> pagesByPojo(@RequestBody CustomerSupplierPoJo t,Integer pageSize,Integer pageNum){
	    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<CustomerSupplier>>) manager.pages("customer_supplier.page", t, page);
	
	}


	@Override
	public Integer changeDelStateById(@RequestBody CustomerSupplier t) {
		return manager.update("customer_supplier.changeStateById", t);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody CustomerSupplierPoJo t) {
		return manager.update("customer_supplier.changeStateByIds", t);
	}


	@Override
	public List<CustomerSupplier> queryListByPoJo(CustomerSupplierPoJo e) {
		// TODO Auto-generated method stub
		return null;
	}
    
}