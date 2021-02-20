package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxDetailSub;
import com.invoice.pojo.AddTaxDetailSubPojo;

@RequestMapping("addTaxDetailSub")
public interface AddTaxDetailSubService extends BaseService<AddTaxDetailSub,AddTaxDetailSubPojo>{
	
}