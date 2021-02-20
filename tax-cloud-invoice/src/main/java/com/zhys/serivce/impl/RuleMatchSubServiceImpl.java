package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.RuleMatchSub;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.RuleMatchSubService;

@Slf4j
@RestController
public class RuleMatchSubServiceImpl extends BaseApiService implements RuleMatchSubService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody RuleMatchSub ruleMatchSub){
	  RuleMatchSub c = (RuleMatchSub) manager.query("rule_match_sub.query",ruleMatchSub);
		if(c != null ){//修改
		  return	manager.update("rule_match_sub.update", ruleMatchSub);
		}else{//插入
		  return    manager.insert("rule_match_sub.create", ruleMatchSub);
		}
		
	}
	
	
	@Override
	public RuleMatchSub queryByEntity(@RequestBody RuleMatchSub ruleMatchSub){
	             return (RuleMatchSub)manager.query("rule_match_sub.query", ruleMatchSub);
	}
	@Override
	public List<RuleMatchSub> queryList(@RequestBody RuleMatchSub ruleMatchSub){
	          return (List<RuleMatchSub>)manager.list("rule_match_sub.queryList", ruleMatchSub);
	}
	@Override
    public Pages<List<RuleMatchSub>> pages(@RequestBody RuleMatchSub ruleMatchSub, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RuleMatchSub>>) manager.pages("rule_match_sub.page", ruleMatchSub, page);
	
	}
	/**
    @Override
    public Pages<List<RuleMatchSub>> pages(@RequestBody RuleMatchSubPojo ruleMatchSubPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RuleMatchSub>>) manager.pages("rule_match_sub.page", ruleMatchSubPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody RuleMatchSub ruleMatchSub) {
		return manager.update("rule_match_sub.changeDelStateById", ruleMatchSub);
	}


	@Override
	public Pages<List<RuleMatchSub>> pagesByPojo(RuleMatchSub e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RuleMatchSub> queryListByPoJo(RuleMatchSub e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(RuleMatchSub t) {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public Integer changeDelStateByIds(@RequestBody RuleMatchSubPojo ruleMatchSubPojo) {
//		return manager.update("rule_match_sub.changeDelStateByIds", ruleMatchSubPojo);
//	}
	
    
}