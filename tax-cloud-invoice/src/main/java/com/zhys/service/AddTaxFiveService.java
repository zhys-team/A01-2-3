package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxFive;
import com.invoice.pojo.AddTaxFivePojo;

@RequestMapping("addTaxFive")
public interface AddTaxFiveService extends BaseService<AddTaxFive,AddTaxFivePojo>{
	
}