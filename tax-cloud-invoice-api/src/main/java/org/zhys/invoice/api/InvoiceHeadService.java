package org.zhys.invoice.api;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.IndexEntity;
import com.invoice.po.IndexQueryEntity;
import com.invoice.po.InvoiceHead;
import com.invoice.po.InvoiceHeadBW;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.BaseService;
@RequestMapping("/invoiceHeads")
public interface InvoiceHeadService extends BaseService<InvoiceHead,InvoiceHeadPoJo>{
	/**
     * 获取主页指标信息
     * @param t
     * @return
     */
	@GetMapping("/indexMsg")
	List<IndexEntity> indexMsg(@RequestParam("orgId") String orgId,@RequestParam("kprqStart") String kprqStart,@RequestParam("kprqEnd") String kprqEnd,@RequestParam("mx") String mx );
	
	/**
     * 查验发票
     * @param String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode,String fply
     * @return
     */
	@GetMapping("/checkInvoice")
	InvoiceMsg checkInvoice(@RequestParam("invoiceCode") String invoiceCode,@RequestParam("invoiceNumber") String invoiceNumber,@RequestParam("billingDate") String billingDate,@RequestParam("totalAmount") String totalAmount,@RequestParam("CheckCode") String CheckCode,@RequestParam("fply") String fply );
	
	
	
	/**
     * 查询带分页-手机
     * @param t page
     * @return
     */
	@GetMapping("/pageForMobile")
    Pages<List<PersonalTicket>> pageForMobile(@RequestParam("openId")String openId, @RequestParam("pageSize")Integer pageSize, @RequestParam("pageNum")Integer pageNum);
}