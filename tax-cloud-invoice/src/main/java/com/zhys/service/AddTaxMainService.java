package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxMain;
import com.invoice.pojo.AddTaxMainPojo;

@RequestMapping("addTaxMain")
public interface AddTaxMainService extends BaseService<AddTaxMain,AddTaxMainPojo>{
	
}