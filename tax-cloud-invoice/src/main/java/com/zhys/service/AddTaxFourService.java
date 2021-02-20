package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxFour;
import com.invoice.pojo.AddTaxFourPojo;

@RequestMapping("addTaxFour")
public interface AddTaxFourService extends BaseService<AddTaxFour,AddTaxFourPojo>{
	
}