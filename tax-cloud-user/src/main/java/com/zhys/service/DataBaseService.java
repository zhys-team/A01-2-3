package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.base.po.DataBase;
import com.zhys.base.pojo.DataBasePojo;

@RequestMapping("dataBase")
public interface DataBaseService extends BaseService<DataBase,DataBasePojo>{
	
}