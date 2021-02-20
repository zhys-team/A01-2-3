package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.ValidateRecord;
import com.invoice.pojo.ValidateRecordPoJo;
import com.zhys.base.BaseService;
@RequestMapping("/validateRecords")
public interface ValidateRecordService extends BaseService<ValidateRecord,ValidateRecordPoJo>{
	
}