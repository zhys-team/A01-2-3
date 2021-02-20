package com.zhys.serivce.impl;

import com.zhys.base.po.RateBusiness;
import com.zhys.base.pojo.RateBusinessPojo;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.RateBusinessService;

@Slf4j
@RestController
public class RateBusinessServiceImpl extends BaseApiService implements RateBusinessService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody RateBusiness rateBusiness){
	  RateBusiness c = (RateBusiness) manager.query("rate_business.query",rateBusiness);
		if(c != null ){//修改
		  return	manager.update("rate_business.update", rateBusiness);
		}else{//插入
		  return    manager.insert("rate_business.create", rateBusiness);
		}
		
	}
	
	
	@Override
	public RateBusiness queryByEntity(@RequestBody RateBusiness rateBusiness){
	             return (RateBusiness)manager.query("rate_business.query", rateBusiness);
	}
	@Override
	public List<RateBusiness> queryList(@RequestBody RateBusiness rateBusiness){
	          return (List<RateBusiness>)manager.list("rate_business.queryList", rateBusiness);
	}
	@Override
    public Pages<List<RateBusiness>> pages(@RequestBody RateBusiness rateBusiness, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RateBusiness>>) manager.pages("rate_business.page", rateBusiness, page);
	
	}
	/**
    @Override
    public Pages<List<RateBusiness>> pages(@RequestBody RateBusinessPojo rateBusinessPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RateBusiness>>) manager.pages("rate_business.page", rateBusinessPojo, page);
	
	}**/


	@Override
	public Pages<List<RateBusiness>> pagesByPojo(@RequestBody RateBusinessPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RateBusiness> queryListByPoJo(@RequestBody RateBusinessPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody RateBusiness t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody RateBusinessPojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}