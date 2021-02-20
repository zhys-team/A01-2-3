package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxTwo;
import com.invoice.pojo.AddTaxTwoPojo;

@RequestMapping("addTaxTwo")
public interface AddTaxTwoService extends BaseService<AddTaxTwo,AddTaxTwoPojo>{
	
}