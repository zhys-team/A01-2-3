package com.zhys.serivce.impl;

import com.zhys.user.po.Goods;
import com.zhys.user.pojo.GoodsPojo;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.GoodsService;

@Slf4j
@RestController
public class GoodsServiceImpl extends BaseApiService implements GoodsService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody Goods goods){
	  
		if(goods.getId() != null ){//修改
		  return	manager.update("goods.update", goods);
		}else{//插入
		  return    manager.insert("goods.create", goods);
		}
		
	}
	
	
	@Override
	public Goods queryByEntity(@RequestBody Goods goods){
	             return (Goods)manager.query("goods.query", goods);
	}
	@Override
	public List<Goods> queryList(@RequestBody Goods goods){
	          return (List<Goods>)manager.list("goods.queryList", goods);
	}
	@Override
    public Pages<List<Goods>> pages(@RequestBody Goods goods, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<Goods>>) manager.pages("goods.page", goods, page);
	
	}
	/**
    @Override
    public Pages<List<Goods>> pages(@RequestBody GoodsPojo goodsPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<Goods>>) manager.pages("goods.page", goodsPojo, page);
	
	}**/


	@Override
	public Pages<List<Goods>> pagesByPojo(@RequestBody GoodsPojo e, Integer pageSize, Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<Goods>>) manager.pages("goods.page", e, page);
	}


	@Override
	public List<Goods> queryListByPoJo(GoodsPojo e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody Goods t) {
		return manager.update("goods.changeDelStateById", t);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody GoodsPojo t) {
		return manager.update("goods.changeDelStateByIds", t);
	}
	
    
}