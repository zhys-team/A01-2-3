package com.zhys.service;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.InvoiceDoubt;
import com.invoice.pojo.InvoiceDoubtPoJo;
import com.zhys.base.BaseService;
@RequestMapping("/invoiceDoubts")
public interface InvoiceDoubtService extends BaseService<InvoiceDoubt,InvoiceDoubtPoJo>{
	
	
	/**
     * 通过id修改状态
     * @param t
     * @return
     */
	@PostMapping("/changeStateById")
	Integer changeStateById(@RequestBody InvoiceDoubt t);
	
	/**
     * 通过ids批量修改状态
     * @param t
     * @return
     */
	@PostMapping("/changeStateByIds")
	Integer changeStateByIds(@RequestBody InvoiceDoubtPoJo t);
}