package com.zhys.serivce.impl;

import com.zhys.fegin.InvoiceHeadServiceFegin;
import com.zhys.po.ListTZInvoice;
import com.zhys.po.TzsysEasInvoicereceive;
import com.zhys.service.SQLManager;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.invoice.po.InvoiceBody;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.base.BaseApiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.TzsysEasInvoicereceiveService;

import io.swagger.models.auth.In;

@Slf4j
@RestController
public class TzsysEasInvoicereceiveServiceImpl extends BaseApiService implements TzsysEasInvoicereceiveService{
	
	@Autowired
	private SQLManager manager;

    @Autowired
    private InvoiceHeadServiceFegin invoiceHeadService;
	
	
	@Override
	@Transactional
	public Integer save(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceive){
	  TzsysEasInvoicereceive c = (TzsysEasInvoicereceive) manager.query("tzsys_eas_invoicereceive.query",tzsysEasInvoicereceive);
		if(c != null&&c.getInvoiceId()!=null ){//修改
			if("1".equals(c.getBxState())) {
				log.info("更新中间库发票主表信息=========");
				manager.update("tzsys_eas_invoicereceive.update", tzsysEasInvoicereceive);
				List<InvoiceBody> bodys = tzsysEasInvoicereceive.getBodys();
				if(bodys!=null&&bodys.size()>0) {
					log.info("删除中间库发票明细表信息=========");
					manager.delete("invoice_body.delByParent", tzsysEasInvoicereceive);
					log.info("新增中间库发票明细表信息=========");
					for(InvoiceBody body:bodys) {
						body.setHeadId(tzsysEasInvoicereceive.getInvoiceId());
						manager.insert("invoice_body.create", body);
					}
				}
			}
		  return	1;
		}else{//插入
			log.info("新增中间库发票主表信息=========");
			manager.insert("tzsys_eas_invoicereceive.create", tzsysEasInvoicereceive);
			List<InvoiceBody> bodys = tzsysEasInvoicereceive.getBodys();
			if(bodys!=null&&bodys.size()>0) {
				log.info("删除中间库发票明细表信息=========");
				manager.delete("invoice_body.delByParent", tzsysEasInvoicereceive);
				log.info("新增中间库发票明细表信息=========");
				for(InvoiceBody body:bodys) {
					body.setHeadId(tzsysEasInvoicereceive.getInvoiceId());
					manager.insert("invoice_body.create", body);
				}
			}
		  return    1;
		}
		
	}
	
	
	@Override
	public TzsysEasInvoicereceive queryByEntity(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceive){
	             return (TzsysEasInvoicereceive)manager.query("tzsys_eas_invoicereceive.query", tzsysEasInvoicereceive);
	}
	@Override
	public List<TzsysEasInvoicereceive> queryList(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceive){
	          return (List<TzsysEasInvoicereceive>)manager.list("tzsys_eas_invoicereceive.queryList", tzsysEasInvoicereceive);
	}
	@Override
    public Pages<List<TzsysEasInvoicereceive>> pages(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceive, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<TzsysEasInvoicereceive>>) manager.pages("tzsys_eas_invoicereceive.page", tzsysEasInvoicereceive, page);
	
	}
	/**
    @Override
    public Pages<List<TzsysEasInvoicereceive>> pages(@RequestBody TzsysEasInvoicereceivePojo tzsysEasInvoicereceivePojo, Integer pageSize, Integer pageNum){
    EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
	    return (Pages<List<TzsysEasInvoicereceive>>) manager.pages("tzsys_eas_invoicereceive.page", tzsysEasInvoicereceivePojo, page);
	
	}**/
	
	@Override
	public Integer changeDelStateById(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceive) {
		return manager.update("tzsys_eas_invoicereceive.changeDelStateById", tzsysEasInvoicereceive);
	}


	@Override
	public Integer changeDelStateByIds(@RequestBody TzsysEasInvoicereceive tzsysEasInvoicereceivePojo) {
		return manager.update("tzsys_eas_invoicereceive.changeDelStateByIds", tzsysEasInvoicereceivePojo);
	}

	/**
	 * 获取中间表所有待同步信息
	 * 调用发票服务接口将信息同步
	 * 若同步成功，则改状态
	 */
	@Override
	public void waitSend() {
		TzsysEasInvoicereceive invoicereceive = new TzsysEasInvoicereceive();
		invoicereceive.setIsSyn("1");
		List<TzsysEasInvoicereceive> list = (List<TzsysEasInvoicereceive>) manager.list("tzsys_eas_invoicereceive.queryList",invoicereceive);
		if(list!=null&&list.size()>0){
			/**
			 * 调用接口 进行传输
			 *
			 */
			ListTZInvoice invoice = new ListTZInvoice();
			invoice.setInvoices(list);
           boolean b =  invoiceHeadService.synInvoiceMsg(invoice);
            System.out.println(">>>>>>>>>>>>>>>>>>>同步成功："+b);
            log.info("同步成功{}",b);
            if(b) {
            	/**
            	 * 更新同步状态为已同步
            	 */
            	for(TzsysEasInvoicereceive easInvoicereceive:list) {
            		TzsysEasInvoicereceive po = new TzsysEasInvoicereceive();
            		po.setInvoiceId(easInvoicereceive.getInvoiceId());
            		po.setIsSyn("2");
            		manager.update("tzsys_eas_invoicereceive.update", po);
            	}
            }
		}
	}


	@Override
	public Pages<List<TzsysEasInvoicereceive>> pagesByPojo(TzsysEasInvoicereceive e, Integer pageSize,
			Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<TzsysEasInvoicereceive> queryListByPoJo(TzsysEasInvoicereceive e) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int delete(Long invoiceId) {
		TzsysEasInvoicereceive easInvoicereceive = new TzsysEasInvoicereceive();
		easInvoicereceive.setInvoiceId(invoiceId);
		TzsysEasInvoicereceive  easInvoicereceive1  =(TzsysEasInvoicereceive) manager.query("tzsys_eas_invoicereceive.queryView", easInvoicereceive);
		if(easInvoicereceive1!=null&&easInvoicereceive1.getInvoiceId()!=null) {
			return 0;
		}
		return manager.delete("tzsys_eas_invoicereceive.delete", easInvoicereceive);
	}


	@Override
	public void updateStatus() {
		
		manager.update("tzsys_eas_invoicereceive.update_status", null);
		
	}


	@Override
	public TzsysEasInvoicereceive queryView(Long invoiceId) {
		
		TzsysEasInvoicereceive easInvoicereceive = new TzsysEasInvoicereceive();
		easInvoicereceive.setInvoiceId(invoiceId);
		return (TzsysEasInvoicereceive) manager.query("tzsys_eas_invoicereceive.queryView", easInvoicereceive);
	}


	@Override
	public Integer saveBody(@RequestBody InvoiceBody body) {
		manager.insert("invoice_body.create", body);
		return 1;
	}


	@Override
	public void updatePdfUrl(@RequestBody TzsysEasInvoicereceive invoicereceive) {
		
		manager.update("tzsys_eas_invoicereceive.updatePdfUrl", invoicereceive);
	}


	@Override
	public ListTZInvoice queryRzrqList() {
		List<TzsysEasInvoicereceive> list = (List<TzsysEasInvoicereceive>) manager.list("tzsys_eas_invoicereceive.queryRzrqList",null);
		if(list!=null&&list.size()>0){
			/**
			 * 调用接口 进行传输
			 *
			 */
			ListTZInvoice invoice = new ListTZInvoice();
			invoice.setInvoices(list);
            return invoice;
            
		}
		return null;
	}


}