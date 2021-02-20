package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.Goods;
import com.zhys.user.pojo.GoodsPojo;

@RequestMapping("goods")
public interface GoodsService extends BaseService<Goods,GoodsPojo>{
	
}