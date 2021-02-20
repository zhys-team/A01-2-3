package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.AddTaxMainGrid;
import com.invoice.pojo.AddTaxMainGridPojo;

@RequestMapping("addTaxMainGrid")
public interface AddTaxMainGridService extends BaseService<AddTaxMainGrid,AddTaxMainGridPojo>{
	
}