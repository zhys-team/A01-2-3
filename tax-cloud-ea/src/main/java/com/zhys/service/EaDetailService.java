package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.ea.po.EaDetail;

@RequestMapping("eaDetail")
public interface EaDetailService extends BaseService<EaDetail,EaDetail>{
	
}