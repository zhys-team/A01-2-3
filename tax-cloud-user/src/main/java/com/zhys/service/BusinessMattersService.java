package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.base.po.BusinessMatters;

@RequestMapping("businessMatters")
public interface BusinessMattersService extends BaseService<BusinessMatters,BusinessMatters>{
	
}