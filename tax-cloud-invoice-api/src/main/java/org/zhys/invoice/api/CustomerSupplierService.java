package org.zhys.invoice.api;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.invoice.po.CustomerSupplier;
import com.invoice.pojo.CustomerSupplierPoJo;



@RequestMapping("customerSupplier")
public interface CustomerSupplierService extends BaseService<CustomerSupplier,CustomerSupplierPoJo>{
	
}