package com.zhys.serivce.impl;

import com.zhys.ea.po.EaBody;
import com.zhys.ea.po.EaHead;
import com.zhys.fegin.InvoiceHeadServiceFegin;
import com.zhys.service.EaHeadService;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.pojo.InvoiceHeadPoJo;
//import com.lorne.tx.annotation.TxTransaction;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EaHeadServiceImpl extends BaseApiService implements EaHeadService{
	
	@Autowired
	private SQLManager manager;
	
	@Autowired
	private InvoiceHeadServiceFegin invoiceHeadService;
	
	@PostMapping("/save")
	@Override
//	@TxTransaction
	@Transactional
	public Integer save(@RequestBody EaHead eaHead){
		if(eaHead != null&&eaHead.getId()!=null ){//修改
		  	manager.update("ea_head.update", eaHead);
		  	EaBody b = new EaBody();
		  	b.setParentId(eaHead.getId());
		  	manager.delete("ea_body.changeStateById", b);
		  	List<EaBody> bodys = eaHead.getBodys();
			  if(bodys!=null&&bodys.size()>0) {
				  for(EaBody body :bodys) {
					  body.setParentId(eaHead.getId());
					  manager.insert("ea_body.create", body);
				  }
			  }
		  	return 1;
		}else{//插入
		  int i = manager.insert("ea_head.create", eaHead);
		  List<EaBody> bodys = eaHead.getBodys();
		  if(bodys!=null&&bodys.size()>0) {
			  InvoiceHeadPoJo h = new InvoiceHeadPoJo();
			  String ids = "";
			  for(EaBody body :bodys) {
				  body.setParentId(eaHead.getId());
				  manager.insert("ea_body.create", body);
				  if(body.getInvoiceId()!=null) {
					  ids=ids+body.getInvoiceId()+",";
				  }
				  
			  }
			  ids = ids.substring(0, ids.length()-1);
			  h.setIds(ids);
			  h.setBxState("1");
			  //改变发票状态为已报销
			  invoiceHeadService.changeDelStateByIds(h);
		  }
		  return    i;
		}
		
	}
	
	@PostMapping("/queryByEntity")
	@Override
	public EaHead queryByEntity(@RequestBody EaHead eaHead){
		   EaHead head = (EaHead)manager.query("ea_head.query", eaHead);
	         return head;
	}
	@Override
	public List<EaHead> queryList(@RequestBody EaHead eaHead){
	          return (List<EaHead>)manager.list("ea_head.queryList", eaHead);
	}
	@Override
    public Pages<List<EaHead>> pages(@RequestBody EaHead eaHead, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaHead>>) manager.pages("ea_head.page", eaHead, page);
	
	}
	/**
    @Override
    public Pages<List<EaHead>> pages(@RequestBody EaHeadPojo eaHeadPojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<EaHead>>) manager.pages("ea_head.page", eaHeadPojo, page);
	
	}**/


	@Override
	public Pages<List<EaHead>> pagesByPojo(EaHead e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<EaHead> queryListByPoJo(EaHead e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer changeDelStateById(@RequestBody EaHead t) {
		EaBody eaBody = new EaBody();
		eaBody.setParentId(t.getId());
		List<EaBody> bodys = (List<EaBody>)manager.list("ea_body.queryList", eaBody);
		  if(bodys!=null&&bodys.size()>0) {
			  InvoiceHeadPoJo h = new InvoiceHeadPoJo();
			  String ids = "";
			  for(EaBody body :bodys) {
				  if(body.getInvoiceId()!=null) {
					  ids=ids+body.getInvoiceId()+",";
				  }
				  
			  }
			  ids = ids.substring(0, ids.length()-1);
			  h.setIds(ids);
			  h.setBxState("0");
			  //改变发票状态为已报销
			  invoiceHeadService.changeDelStateByIds(h);
		  }
		return manager.update("ea_head.changeDelStateById", t);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody EaHead t) {
		 return manager.update("ea_head.changeDelStateByIds", t);
	}

	@Override
	public Integer changeAuditStateById(@RequestBody EaHead t) {
		return manager.update("ea_head.changeAuditStateById", t);
	}
	
    
}