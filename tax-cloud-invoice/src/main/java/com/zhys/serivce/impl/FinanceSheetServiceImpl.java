package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.AddTaxMainGrid;
import com.invoice.po.FinanceBalanceSheet;
import com.invoice.po.FinanceProfitSheet;
import com.invoice.po.FinanceSheet;
import com.invoice.pojo.FinanceSheetPojo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.FinanceSheetService;

@Slf4j
@RestController
public class FinanceSheetServiceImpl extends BaseApiService implements FinanceSheetService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody FinanceSheet financeSheet){
		if(financeSheet.getId() != null ){//修改
		  	manager.update("finance_sheet.update", financeSheet);
		  	manager.delete("finance_balance_sheet.del", financeSheet);
		  	manager.delete("finance_profit_sheet.del", financeSheet);
		}else{//插入
		      manager.insert("finance_sheet.create", financeSheet);
		      
		}
		List<FinanceBalanceSheet> balanceSheets = financeSheet.getBalances();
	  	if(balanceSheets!=null&&balanceSheets.size()>0) {
	  		for(FinanceBalanceSheet m:balanceSheets) {
	  			m.setParentId(financeSheet.getId());
	  			manager.insert("finance_balance_sheet.create", m);
		  	}
	  	}
	  	List<FinanceProfitSheet> financeProfitSheets = financeSheet.getProfits();
	  	if(financeProfitSheets!=null&&financeProfitSheets.size()>0) {
	  		for(FinanceProfitSheet m:financeProfitSheets) {
	  			m.setParentId(financeSheet.getId());
	  			manager.insert("finance_profit_sheet.create", m);
		  	}
	  	}
		return 1;
		
	}
	
	
	@Override
	public FinanceSheet queryByEntity(@RequestBody FinanceSheet financeSheet){
	             return (FinanceSheet)manager.query("finance_sheet.query", financeSheet);
	}
	@Override
	public List<FinanceSheet> queryList(@RequestBody FinanceSheet financeSheet){
	          return (List<FinanceSheet>)manager.list("finance_sheet.queryList", financeSheet);
	}
	@Override
    public Pages<List<FinanceSheet>> pages(@RequestBody FinanceSheet financeSheet, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<FinanceSheet>>) manager.pages("finance_sheet.page", financeSheet, page);
	
	}
	/**
    @Override
    public Pages<List<FinanceSheet>> pages(@RequestBody FinanceSheetPojo financeSheetPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<FinanceSheet>>) manager.pages("finance_sheet.page", financeSheetPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody FinanceSheet financeSheet) {
		return manager.update("finance_sheet.changeDelStateById", financeSheet);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody FinanceSheetPojo financeSheetPojo) {
		return manager.update("finance_sheet.changeDelStateByIds", financeSheetPojo);
	}


	@Override
	public Pages<List<FinanceSheet>> pagesByPojo(FinanceSheetPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<FinanceSheet> queryListByPoJo(FinanceSheetPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}