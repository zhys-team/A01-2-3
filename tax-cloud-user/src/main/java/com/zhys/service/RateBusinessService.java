package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.base.po.RateBusiness;
import com.zhys.base.pojo.RateBusinessPojo;

@RequestMapping("rateBusiness")
public interface RateBusinessService extends BaseService<RateBusiness,RateBusinessPojo>{
	
}