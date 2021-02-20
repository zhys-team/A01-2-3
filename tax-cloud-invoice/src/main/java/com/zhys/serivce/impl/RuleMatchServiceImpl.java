package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.RuleMatch;
import com.invoice.po.RuleMatchSub;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.RuleMatchService;

@Slf4j
@RestController
public class RuleMatchServiceImpl extends BaseApiService implements RuleMatchService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody RuleMatch ruleMatch){
	  //RuleMatch c = (RuleMatch) manager.query("rule_match.query",ruleMatch);
		if(ruleMatch != null&&ruleMatch.getId()!=null ){//修改
		  	manager.update("rule_match.update", ruleMatch);
		  	manager.delete("rule_match_sub.del",ruleMatch);
		}else{//插入
		      manager.insert("rule_match.create", ruleMatch);
		}
		List<RuleMatchSub>  subs = ruleMatch.getSubs();
		if(subs!=null&&subs.size()>0) {
			for(RuleMatchSub matchSub:subs) {
				matchSub.setRuleId(ruleMatch.getId());
				manager.insert("rule_match_sub.create", matchSub);
			}
		}
		return 1;
		
	}
	
	
	@Override
	public RuleMatch queryByEntity(@RequestBody RuleMatch ruleMatch){
	             return (RuleMatch)manager.query("rule_match.query", ruleMatch);
	}
	@Override
	public List<RuleMatch> queryList(@RequestBody RuleMatch ruleMatch){
	          return (List<RuleMatch>)manager.list("rule_match.queryList", ruleMatch);
	}
	@Override
    public Pages<List<RuleMatch>> pages(@RequestBody RuleMatch ruleMatch, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RuleMatch>>) manager.pages("rule_match.page", ruleMatch, page);
	
	}
	/**
    @Override
    public Pages<List<RuleMatch>> pages(@RequestBody RuleMatchPojo ruleMatchPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<RuleMatch>>) manager.pages("rule_match.page", ruleMatchPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody RuleMatch ruleMatch) {
		return manager.delete("rule_match.del", ruleMatch);
	}


	@Override
	public Pages<List<RuleMatch>> pagesByPojo(RuleMatch e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RuleMatch> queryListByPoJo(RuleMatch e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(RuleMatch t) {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public Integer changeDelStateByIds(@RequestBody RuleMatchPojo ruleMatchPojo) {
//		return manager.update("rule_match.changeDelStateByIds", ruleMatchPojo);
//	}
	
    
}