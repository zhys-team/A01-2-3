package com.zhys.serivce.impl;

import com.zhys.user.po.TaxMech;
import com.zhys.user.pojo.TaxMechPojo;
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
import com.zhys.service.TaxMechService;

@Slf4j
@RestController
public class TaxMechServiceImpl extends BaseApiService implements TaxMechService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody TaxMech taxMech){
	  TaxMech c = (TaxMech) manager.query("tax_mech.query",taxMech);
		if(c != null ){//修改
		  return	manager.update("tax_mech.update", taxMech);
		}else{//插入
		  return    manager.insert("tax_mech.create", taxMech);
		}
		
	}
	
	
	@Override
	public TaxMech queryByEntity(@RequestBody TaxMech taxMech){
	             return (TaxMech)manager.query("tax_mech.query", taxMech);
	}
	@Override
	public List<TaxMech> queryList(@RequestBody TaxMech taxMech){
	          return (List<TaxMech>)manager.list("tax_mech.queryList", taxMech);
	}
	@Override
    public Pages<List<TaxMech>> pages(@RequestBody TaxMech taxMech, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<TaxMech>>) manager.pages("tax_mech.page", taxMech, page);
	
	}
	/**
    @Override
    public Pages<List<TaxMech>> pages(@RequestBody TaxMechPojo taxMechPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<TaxMech>>) manager.pages("tax_mech.page", taxMechPojo, page);
	
	}**/


	@Override
	public Pages<List<TaxMech>> pagesByPojo(TaxMechPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<TaxMech> queryListByPoJo(TaxMechPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody TaxMech t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody TaxMechPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}