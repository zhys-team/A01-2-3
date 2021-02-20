package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.FinanceSheet;
import com.invoice.pojo.FinanceSheetPojo;
import com.zhys.base.BaseService;

@RequestMapping("financeSheet")
public interface FinanceSheetService extends BaseService<FinanceSheet,FinanceSheetPojo>{
	
}