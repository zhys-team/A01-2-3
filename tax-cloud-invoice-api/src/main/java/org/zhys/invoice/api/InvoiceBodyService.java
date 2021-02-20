package org.zhys.invoice.api;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.InvoiceBody;
import com.invoice.pojo.InvoiceBodyPoJo;
import com.zhys.base.BaseService;
import com.zhys.base.ResponseBase;
@RequestMapping("/invoiceBodys")
public interface InvoiceBodyService extends BaseService<InvoiceBody,InvoiceBodyPoJo>{
	
}