package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxDetailFree;
import com.invoice.pojo.AddTaxDetailFreePojo;

@RequestMapping("addTaxDetailFree")
public interface AddTaxDetailFreeService extends BaseService<AddTaxDetailFree,AddTaxDetailFreePojo>{
	
}