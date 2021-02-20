package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.base.po.RateCode;
import com.zhys.base.pojo.RateCodePojo;

@RequestMapping("rateCode")
public interface RateCodeService extends BaseService<RateCode,RateCodePojo>{
	
}