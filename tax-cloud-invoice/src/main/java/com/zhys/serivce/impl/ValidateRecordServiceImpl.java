package com.zhys.serivce.impl;

import com.invoice.po.ValidateRecord;
import com.invoice.pojo.ValidateRecordPoJo;
import com.zhys.service.SQLManager;
import com.zhys.service.ValidateRecordService;

import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import com.zhys.exception.BusinessException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class ValidateRecordServiceImpl extends BaseApiService implements ValidateRecordService{
	
	@Autowired
	private SQLManager manager;
	
	
	
	@Async
	@Override
	public Integer save(ValidateRecord validateRecord){
	  
		  return    manager.insert("validate_record.create", validateRecord);
		
		
	}
	
	
	@Override
	public ValidateRecord queryByEntity(ValidateRecord validateRecord){
	             return (ValidateRecord)manager.query("validate_record.query", validateRecord);
	}
	@Override
	public List<ValidateRecord> queryList(ValidateRecord validateRecord){
	          return (List<ValidateRecord>)manager.list("validate_record.queryList", validateRecord);
	}


	@Override
	public Pages<List<ValidateRecord>> pages(ValidateRecord t, Integer pageSize, Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<ValidateRecord>>) manager.pages("validate_record.page", t, page);
	}


	@Override
	public Pages<List<ValidateRecord>> pagesByPojo(@RequestBody ValidateRecordPoJo e, Integer pageSize, Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<ValidateRecord>>) manager.pages("validate_record.page", e, page);
	}


	@Override
	public Integer changeDelStateById(ValidateRecord t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(ValidateRecordPoJo t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ValidateRecord> queryListByPoJo(ValidateRecordPoJo e) {
		// TODO Auto-generated method stub
		return null;
	}}