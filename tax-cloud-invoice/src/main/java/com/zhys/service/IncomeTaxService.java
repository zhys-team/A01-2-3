package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.IncomeTax;
import com.invoice.pojo.IncomeTaxPoJo;
import com.zhys.base.BaseService;

@RequestMapping("incomeTax")
public interface IncomeTaxService extends BaseService<IncomeTax,IncomeTaxPoJo>{
	
}