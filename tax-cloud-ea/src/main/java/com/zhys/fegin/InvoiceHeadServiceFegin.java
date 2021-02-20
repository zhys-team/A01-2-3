package com.zhys.fegin;


import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.invoice.po.InvoiceHead;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.zhys.po.ListTZInvoice;

@Component
@FeignClient(value="invoice",path="/invoiceHeads")
public interface InvoiceHeadServiceFegin  {	

	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody InvoiceHead t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody InvoiceHeadPoJo t) ;

	 /**
     * 跟oa服务 数据同步
     * @return
     */
    @PostMapping("/synInvoiceMsg")
    public boolean synInvoiceMsg(@RequestBody ListTZInvoice invoice  );
    
    @PostMapping("/queryListByPoJo")
    List<InvoiceHead> queryListByPoJo(@RequestBody InvoiceHeadPoJo e);
    
    @PostMapping({"/synRzrq"})
    public void synRzrq(@RequestBody ListTZInvoice invoice);
}
