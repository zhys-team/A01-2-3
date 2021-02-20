package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.TaxMech;
import com.zhys.user.pojo.TaxMechPojo;

@RequestMapping("taxMech")
public interface TaxMechService extends BaseService<TaxMech,TaxMechPojo>{
	
}