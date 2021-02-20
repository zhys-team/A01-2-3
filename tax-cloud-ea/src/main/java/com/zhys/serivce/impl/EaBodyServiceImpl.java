package com.zhys.serivce.impl;

import com.zhys.ea.po.EaBody;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.EaBodyService;

@Slf4j
@RestController
public class EaBodyServiceImpl extends BaseApiService implements EaBodyService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(EaBody eaBody){
	  EaBody c = (EaBody) manager.query("ea_body.query",eaBody);
		if(c != null ){//修改
		  return	manager.update("ea_body.update", eaBody);
		}else{//插入
		  return    manager.insert("ea_body.create", eaBody);
		}
		
	}
	
	
	@Override
	public EaBody queryByEntity(EaBody eaBody){
	             return (EaBody)manager.query("ea_body.query", eaBody);
	}
	@Override
	public List<EaBody> queryList(EaBody eaBody){
	          return (List<EaBody>)manager.list("ea_body.queryList", eaBody);
	}
	@Override
    public Pages<List<EaBody>> pages(EaBody eaBody, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaBody>>) manager.pages("ea_body.page", eaBody, page);
	
	}
	/**
    @Override
    public Pages<List<EaBody>> pages(@RequestBody EaBodyPojo eaBodyPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaBody>>) manager.pages("ea_body.page", eaBodyPojo, page);
	
	}**/


	@Override
	public Pages<List<EaBody>> pagesByPojo(EaBody e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<EaBody> queryListByPoJo(EaBody e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(EaBody t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(EaBody t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}