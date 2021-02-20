package com.zhys.serivce.impl;

import com.zhys.base.po.DataBase;
import com.zhys.base.pojo.DataBasePojo;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.DataBaseService;

@Slf4j
@RestController
public class DataBaseServiceImpl extends BaseApiService implements DataBaseService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody DataBase dataBase){
	  DataBase c = (DataBase) manager.query("data_base.query",dataBase);
		if(c != null ){//修改
		  return	manager.update("data_base.update", dataBase);
		}else{//插入
		  return    manager.insert("data_base.create", dataBase);
		}
		
	}
	
	
	@Override
	public DataBase queryByEntity(@RequestBody DataBase dataBase){
	             return (DataBase)manager.query("data_base.query", dataBase);
	}
	@Override
	public List<DataBase> queryList(@RequestBody DataBase dataBase){
	          return (List<DataBase>)manager.list("data_base.queryList", dataBase);
	}
	@Override
    public Pages<List<DataBase>> pages(@RequestBody DataBase dataBase, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<DataBase>>) manager.pages("data_base.page", dataBase, page);
	
	}
	/**
    @Override
    public Pages<List<DataBase>> pages(@RequestBody DataBasePojo dataBasePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<DataBase>>) manager.pages("data_base.page", dataBasePojo, page);
	
	}**/


	@Override
	public Pages<List<DataBase>> pagesByPojo(DataBasePojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<DataBase> queryListByPoJo(DataBasePojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody DataBase t) {
		return manager.delete("data_base.changeDelStateById", t);
	}


	@Override
	public Integer changeDelStateByIds(DataBasePojo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}