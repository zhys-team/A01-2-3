package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxThree;
import com.invoice.pojo.AddTaxThreePojo;

@RequestMapping("addTaxThree")
public interface AddTaxThreeService extends BaseService<AddTaxThree,AddTaxThreePojo>{
	
}