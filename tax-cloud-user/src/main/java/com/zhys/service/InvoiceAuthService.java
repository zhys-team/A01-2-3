package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.InvoiceAuth;
import com.zhys.user.pojo.InvoiceAuthPojo;

@RequestMapping("invoiceAuth")
public interface InvoiceAuthService extends BaseService<InvoiceAuth,InvoiceAuthPojo>{
	
}