package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxFirst;
import com.invoice.pojo.AddTaxFirstPojo;

@RequestMapping("addTaxFirst")
public interface AddTaxFirstService extends BaseService<AddTaxFirst,AddTaxFirstPojo>{
	
}