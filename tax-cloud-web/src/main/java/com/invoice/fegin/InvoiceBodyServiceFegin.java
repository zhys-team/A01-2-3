package com.invoice.fegin;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.InvoiceBody;
import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.invoice.pojo.InvoiceBodyPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;

@Component

@FeignClient(value="invoice",path="/invoiceBodys")
public interface InvoiceBodyServiceFegin  {	


	
	/**
     * 查询
     * @param t
     * @return
     */
	@RequestMapping("/queryList")
    List<InvoiceBody> queryList(@RequestBody InvoiceBody t);
	
	/**
     * 查询
     * @param e
     * @return
     */
	@RequestMapping("/queryListByPoJo")
    List<InvoiceBody> queryListByPoJo(@RequestBody InvoiceBodyPoJo e);
	

}
