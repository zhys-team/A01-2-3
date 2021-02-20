package com.zhys.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.ea.po.EaHead;

@RequestMapping("eaHead")
public interface EaHeadService extends BaseService<EaHead,EaHead>{
	
	@PostMapping("/changeAuditStateById")
	Integer changeAuditStateById(@RequestBody EaHead t);
	
}