package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.RuleMatch;
import com.zhys.base.BaseService;

@RequestMapping("ruleMatch")
public interface RuleMatchService extends BaseService<RuleMatch,RuleMatch>{
	
}