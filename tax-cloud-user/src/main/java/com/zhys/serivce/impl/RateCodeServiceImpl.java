package com.zhys.serivce.impl;

import com.zhys.base.po.RateCode;
import com.zhys.base.pojo.RateCodePojo;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.po.RateBusiness;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.RateCodeService;

@Slf4j
@RestController
public class RateCodeServiceImpl extends BaseApiService implements RateCodeService{
	
	@Autowired
	private SQLManager manager;
	
	@Transactional
	@Override
	public Integer save(@RequestBody RateCode rateCode){
		if(rateCode.getId() != null ){//修改
			manager.update("rate_code.update", rateCode);
			List<RateBusiness> bodys = rateCode.getBusinessList();
			if(bodys!=null&&bodys.size()>0) {
				manager.delete("rate_business.delByParent", rateCode);
				for(RateBusiness body:bodys) {
					body.setRateCodeId(rateCode.getId());
					manager.insert("rate_business.create", body);
				}
			}
			return 1;
		}else{//插入
			manager.insert("rate_code.create", rateCode);
			List<RateBusiness> bodys = rateCode.getBusinessList();
			if(bodys!=null&&bodys.size()>0) {
				for(RateBusiness body:bodys) {
					body.setRateCodeId(rateCode.getId());
					manager.insert("rate_business.create", body);
				}
			}
			return 1;
		}
		
	}
	
	
	@Override
	public RateCode queryByEntity(@RequestBody RateCode rateCode){
	             return (RateCode)manager.query("rate_code.query", rateCode);
	}
	@Override
	public List<RateCode> queryList(@RequestBody RateCode rateCode){
	          return (List<RateCode>)manager.list("rate_code.queryList", rateCode);
	}
	@Override
    public Pages<List<RateCode>> pages(@RequestBody RateCode rateCode, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RateCode>>) manager.pages("rate_code.page", rateCode, page);
	
	}
	/**
    @Override
    public Pages<List<RateCode>> pages(@RequestBody RateCodePojo rateCodePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RateCode>>) manager.pages("rate_code.page", rateCodePojo, page);
	
	}**/


	@Override
	public Pages<List<RateCode>> pagesByPojo(@RequestBody RateCodePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RateCode> queryListByPoJo(@RequestBody RateCodePojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody RateCode t) {
		// TODO Auto-generated method stub
		return manager.delete("rate_code.del", t);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody RateCodePojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}