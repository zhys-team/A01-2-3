package com.zhys.controller;


import com.zhys.service.InvoiceViewService;
import com.zhys.util.Result;
import com.zhys.vo.InvoiceCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invoiceView")
public class InvoiceViewController {

    @Autowired
	private InvoiceViewService viewService;

/**
 * 主要逻辑
 * 主要是以下几点：1：通过invoiceView/getInvoiceSummary
 */

@PostMapping("getInvoiceSummary")
public @ResponseBody Result getInvoicesSummary(@RequestBody InvoiceCondition invoiceCondition){
	return Result.Success("查询成功!",viewService.getInvoiceSummary(invoiceCondition));
}






}