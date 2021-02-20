package com.zhys.serivce.impl;

import com.invoice.po.AddTaxDetailFree;
import com.invoice.po.AddTaxDetailSub;
import com.invoice.po.AddTaxFirst;
import com.invoice.po.AddTaxFive;
import com.invoice.po.AddTaxFour;
import com.invoice.po.AddTaxMain;
import com.invoice.po.AddTaxMainGrid;
import com.invoice.po.AddTaxThree;
import com.invoice.po.AddTaxTwo;
import com.invoice.pojo.AddTaxMainPojo;
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
import com.zhys.service.AddTaxMainService;

@Slf4j
@RestController
public class AddTaxMainServiceImpl extends BaseApiService implements AddTaxMainService{
	
	@Autowired
	private SQLManager manager;
	
	
	@Override
	public Integer save(@RequestBody AddTaxMain addTaxMain){
	  //AddTaxMain c = (AddTaxMain) manager.query("add_tax_main.query",addTaxMain);
		if(addTaxMain.getId() != null ){//修改
		  	manager.update("add_tax_main.update", addTaxMain);
		  	
		  	manager.delete("add_tax_detail_free.del", addTaxMain);
		  	manager.delete("add_tax_detail_sub.del", addTaxMain);
		  	manager.delete("add_tax_first.del", addTaxMain);
		  	manager.delete("add_tax_five.del", addTaxMain);
		  	manager.delete("add_tax_four.del", addTaxMain);
		  	manager.delete("add_tax_main_grid.del", addTaxMain);
		  	manager.delete("add_tax_three.del", addTaxMain);
		  	manager.delete("add_tax_two.del", addTaxMain);
		}else{//插入
		      manager.insert("add_tax_main.create", addTaxMain);
		}
		
		
		List<AddTaxMainGrid> mains = addTaxMain.getMains();
	  	if(mains!=null&&mains.size()>0) {
	  		for(AddTaxMainGrid m:mains) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_main_grid.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxDetailFree> detailFrees = addTaxMain.getDetailfrees();
	  	if(detailFrees!=null&&detailFrees.size()>0) {
	  		for(AddTaxDetailFree m:detailFrees) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_detail_free.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxDetailSub> detailSubs = addTaxMain.getDetailsubs();
	  	if(detailSubs!=null&&detailSubs.size()>0) {
	  		for(AddTaxDetailSub m:detailSubs) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_detail_sub.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxFirst> firsts = addTaxMain.getOnes();
	  	if(firsts!=null&&firsts.size()>0) {
	  		for(AddTaxFirst m:firsts) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_first.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxFive> fives  = addTaxMain.getFives();
	  	if(fives!=null&&fives.size()>0) {
	  		for(AddTaxFive m:fives) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_five.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxFour> fours  = addTaxMain.getFours();
	  	if(fours!=null&&fours.size()>0) {
	  		for(AddTaxFour m:fours) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_four.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxThree> threes = addTaxMain.getThrees();
	  	if(threes!=null&&threes.size()>0) {
	  		for(AddTaxThree m:threes) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_three.create", m);
		  	}
	  	}
	  	
	  	List<AddTaxTwo> twos = addTaxMain.getTwos();
	  	if(twos!=null&&twos.size()>0) {
	  		for(AddTaxTwo m:twos) {
	  			m.setParentId(addTaxMain.getId());
	  			manager.insert("add_tax_two.create", m);
		  	}
	  	}
	  	
	  	return 1;
		
	}
	
	
	@Override
	public AddTaxMain queryByEntity(@RequestBody AddTaxMain addTaxMain){
	             return (AddTaxMain)manager.query("add_tax_main.query", addTaxMain);
	}
	@Override
	public List<AddTaxMain> queryList(@RequestBody AddTaxMain addTaxMain){
	          return (List<AddTaxMain>)manager.list("add_tax_main.queryList", addTaxMain);
	}
	@Override
    public Pages<List<AddTaxMain>> pages(@RequestBody AddTaxMain addTaxMain, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxMain>>) manager.pages("add_tax_main.page", addTaxMain, page);
	
	}
	/**
    @Override
    public Pages<List<AddTaxMain>> pages(@RequestBody AddTaxMainPojo addTaxMainPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<AddTaxMain>>) manager.pages("add_tax_main.page", addTaxMainPojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody AddTaxMain addTaxMain) {
		return manager.update("add_tax_main.changeDelStateById", addTaxMain);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody AddTaxMainPojo addTaxMainPojo) {
		return manager.update("add_tax_main.changeDelStateByIds", addTaxMainPojo);
	}


	@Override
	public Pages<List<AddTaxMain>> pagesByPojo(AddTaxMainPojo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AddTaxMain> queryListByPoJo(AddTaxMainPojo e) {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}