package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.EquivalentSale;
import com.invoice.pojo.EquivalentSalePojo;
import com.zhys.base.BaseService;

@RequestMapping("equivalentSale")
public interface EquivalentSaleService extends BaseService<EquivalentSale,EquivalentSalePojo>{
	
}