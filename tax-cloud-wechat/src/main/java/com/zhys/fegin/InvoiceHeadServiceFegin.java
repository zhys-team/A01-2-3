package com.zhys.fegin;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.IndexEntity;
import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;

//@Component("invoiceHeadServiceFegin")
@Service("invoiceHeadServiceFegin")
@FeignClient(value="invoice",path="/invoiceHeads")
public interface InvoiceHeadServiceFegin  {	

	/**
     * 查验发票
     * @param String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode,String fply
     * @return
     */
	@PostMapping("/checkInvoice")
	InvoiceMsg checkInvoice(@RequestParam(name = "openId",required=false)String openId,@RequestParam("invoiceCode") String invoiceCode,
			@RequestParam("invoiceNumber") String invoiceNumber,@RequestParam("billingDate") String billingDate,
			@RequestParam("totalAmount") String totalAmount,@RequestParam("checkCode") String checkCode,
			@RequestParam("fply") String fply,@RequestParam("pdfUrl") String pdfUrl,@RequestParam("kind") String kind,@RequestParam("fplx") String fplx );
   
	/**
     * 查询带分页-手机
     * @param t page
     * @return
     */
	@GetMapping("/pageForMobile")
    Pages<List<PersonalTicket>> pageForMobile(@RequestParam("openId")String openId, @RequestParam("pageSize")Integer pageSize, @RequestParam("pageNum")Integer pageNum
    		, @RequestParam("bxState")String  bxState);

	
	/**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
	InvoiceHead save(@RequestBody InvoiceHead t);
	
	@GetMapping("/queryByTicketId")
	Integer queryByTicketId(@RequestParam("ticketId") String ticketId);
	
	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody InvoiceHeadPoJo t);
	
	@GetMapping("/delInvoice")
	Integer delInvoice(@RequestParam("openId") String openId,@RequestParam("invoiceId") String invoiceId);
}
