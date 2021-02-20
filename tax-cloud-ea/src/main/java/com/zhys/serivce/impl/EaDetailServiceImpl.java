package com.zhys.serivce.impl;

import com.zhys.ea.po.EaDetail;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.EaDetailService;

@Slf4j
@RestController
public class EaDetailServiceImpl extends BaseApiService implements EaDetailService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(EaDetail eaDetail){
	  EaDetail c = (EaDetail) manager.query("ea_detail.query",eaDetail);
		if(c != null ){//修改
		  return	manager.update("ea_detail.update", eaDetail);
		}else{//插入
		  return    manager.insert("ea_detail.create", eaDetail);
		}
		
	}
	
	
	@Override
	public EaDetail queryByEntity(EaDetail eaDetail){
	             return (EaDetail)manager.query("ea_detail.query", eaDetail);
	}
	@Override
	public List<EaDetail> queryList(EaDetail eaDetail){
	          return (List<EaDetail>)manager.list("ea_detail.queryList", eaDetail);
	}
	@Override
    public Pages<List<EaDetail>> pages(EaDetail eaDetail, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaDetail>>) manager.pages("ea_detail.page", eaDetail, page);
	
	}
	/**
    @Override
    public Pages<List<EaDetail>> pages(@RequestBody EaDetailPojo eaDetailPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaDetail>>) manager.pages("ea_detail.page", eaDetailPojo, page);
	
	}**/


	@Override
	public Pages<List<EaDetail>> pagesByPojo(EaDetail e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<EaDetail> queryListByPoJo(EaDetail e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(EaDetail t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateByIds(EaDetail t) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}