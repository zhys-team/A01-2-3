package com.zhys.serivce.impl;

import com.zhys.base.po.BusinessMatters;
import com.zhys.base.po.RateCode;
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
import com.zhys.service.BusinessMattersService;

@Slf4j
@RestController
public class BusinessMattersServiceImpl extends BaseApiService implements BusinessMattersService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody BusinessMatters businessMatters){
		if(businessMatters.getId() != null ){//修改
			//判断是否是末级
			//是末级，修改rate_code中的dl、icon
			//不是末级，修改rate——code中的业务事项名称
			if("0".equals(businessMatters.getIsLast())) {
				RateCode rateCode =new RateCode();
				rateCode.setDlId(businessMatters.getId());
				rateCode.setDl(businessMatters.getName());
				rateCode.setIcon(businessMatters.getIcon());
				manager.update("rate_code.update_dl",rateCode);
			}else {
				RateCode rateCode =new RateCode();
				rateCode.setBusinessMatterId(businessMatters.getId());
				rateCode.setBusinessMatterName(businessMatters.getName());
				manager.update("rate_code.update_bm",rateCode);
			}
		  return	manager.update("business_matters.update", businessMatters);
		}else{//插入
		  return    manager.insert("business_matters.create", businessMatters);
		}
		
	}
	
	
	@Override
	public BusinessMatters queryByEntity(@RequestBody BusinessMatters businessMatters){
	             return (BusinessMatters)manager.query("business_matters.query", businessMatters);
	}
	@Override
	public List<BusinessMatters> queryList(@RequestBody BusinessMatters businessMatters){
	          return (List<BusinessMatters>)manager.list("business_matters.queryList", businessMatters);
	}
	@Override
    public Pages<List<BusinessMatters>> pages(@RequestBody BusinessMatters businessMatters, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<BusinessMatters>>) manager.pages("business_matters.page", businessMatters, page);
	
	}
	/**
    @Override
    public Pages<List<BusinessMatters>> pages(@RequestBody BusinessMattersPojo businessMattersPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<BusinessMatters>>) manager.pages("business_matters.page", businessMattersPojo, page);
	
	}**/


	@Override
	public Integer changeDelStateById(@RequestBody BusinessMatters t) {
		manager.delete("business_matters.changeStateById", t);
		return 1;
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody BusinessMatters t) {
		manager.delete("business_matters.changeStateByIds", t);
		return 1;
	}


	@Override
	public Pages<List<BusinessMatters>> pagesByPojo(BusinessMatters e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<BusinessMatters> queryListByPoJo(BusinessMatters e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}