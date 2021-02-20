package com.zhys.serivce.impl;

import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.CustomsPaymentBook;
import com.invoice.pojo.CustomsPaymentBookPojo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.CustomsPaymentBookService;

@Slf4j
@RestController
public class CustomsPaymentBookServiceImpl extends BaseApiService implements CustomsPaymentBookService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody CustomsPaymentBook customsPaymentBook){
		if(customsPaymentBook.getId() != null ){//修改
		  return	manager.update("customs_payment_book.update", customsPaymentBook);
		}else{//插入
		  return    manager.insert("customs_payment_book.create", customsPaymentBook);
		}
		
	}
	
	
	@Override
	public CustomsPaymentBook queryByEntity(@RequestBody CustomsPaymentBook customsPaymentBook){
	             return (CustomsPaymentBook)manager.query("customs_payment_book.query", customsPaymentBook);
	}
	@Override
	public List<CustomsPaymentBook> queryList(@RequestBody CustomsPaymentBook customsPaymentBook){
	          return (List<CustomsPaymentBook>)manager.list("customs_payment_book.queryList", customsPaymentBook);
	}
	@Override
    public Pages<List<CustomsPaymentBook>> pages(@RequestBody CustomsPaymentBook customsPaymentBook, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<CustomsPaymentBook>>) manager.pages("customs_payment_book.page", customsPaymentBook, page);
	
	}
	/**
    @Override
    public Pages<List<CustomsPaymentBook>> pages(@RequestBody CustomsPaymentBookPojo customsPaymentBookPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<CustomsPaymentBook>>) manager.pages("customs_payment_book.page", customsPaymentBookPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody CustomsPaymentBook customsPaymentBook) {
		return manager.update("customs_payment_book.changeDelStateById", customsPaymentBook);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody CustomsPaymentBookPojo customsPaymentBookPojo) {
		return manager.update("customs_payment_book.changeDelStateByIds", customsPaymentBookPojo);
	}


	@Override
	public Pages<List<CustomsPaymentBook>> pagesByPojo(CustomsPaymentBookPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CustomsPaymentBook> queryListByPoJo(CustomsPaymentBookPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}