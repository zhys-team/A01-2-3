package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.CustomsPaymentBook;
import com.invoice.pojo.CustomsPaymentBookPojo;
import com.zhys.base.BaseService;

@RequestMapping("customsPaymentBook")
public interface CustomsPaymentBookService extends BaseService<CustomsPaymentBook,CustomsPaymentBookPojo>{
	
}