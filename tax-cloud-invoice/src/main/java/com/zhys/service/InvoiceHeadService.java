package com.zhys.service;

import com.alibaba.fastjson.JSONObject;
import com.fapiao.neon.param.in.DeductParamBody;
import com.fapiao.neon.param.in.InvoiceInspectionParamBody;
import com.invoice.po.IndexEntity;
import com.invoice.po.InvoiceHead;
import com.invoice.pojo.HeadList;
import com.invoice.pojo.IncomeCount;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.BaseService;
import com.zhys.bw.AllInvoiceInfo;
import com.zhys.po.Dkhz;
import com.zhys.po.ListTZInvoice;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping({"/invoiceHeads"})
public interface InvoiceHeadService extends BaseService<InvoiceHead, InvoiceHeadPoJo> {
  @GetMapping({"/indexMsg"})
  List<IndexEntity> indexMsg(@RequestParam("orgId") String paramString1, @RequestParam("kprqStart") String paramString2, @RequestParam("kprqEnd") String paramString3, @RequestParam("mx") String paramString4);
  
  @PostMapping({"/checkInvoice"})
  InvoiceMsg checkInvoice(@RequestParam(name = "openId", required = false) String paramString1, @RequestParam("invoiceCode") String paramString2, @RequestParam("invoiceNumber") String paramString3, @RequestParam("billingDate") String paramString4, @RequestParam("totalAmount") String paramString5, @RequestParam("checkCode") String paramString6, @RequestParam("fply") String paramString7, @RequestParam("pdfUrl") String paramString8, @RequestParam("kind") String paramString9, @RequestParam("fplx") String paramString10);
  
  @GetMapping({"/pageForMobile"})
  Pages<List<PersonalTicket>> pageForMobile(@RequestParam("openId") String paramString1, @RequestParam("pageSize") Integer paramInteger1, @RequestParam("pageNum") Integer paramInteger2, @RequestParam("bxState") String paramString2);
  
  @PostMapping({"/fileUpload"})
  String fileUpload(@RequestBody HeadList paramHeadList);
  
  @GetMapping({"/queryByTicketId"})
  Integer queryByTicketId(@RequestParam("ticketId") String paramString);
  
  @PostMapping({"/jxtj"})
  List<IncomeCount> jxtj(@RequestBody IncomeCount paramIncomeCount);
  
  @GetMapping({"/delInvoice"})
  Integer delInvoice(@RequestParam("openId") String paramString1, @RequestParam("invoiceId") String paramString2);
  
  AllInvoiceInfo fpcy(InvoiceInspectionParamBody paramInvoiceInspectionParamBody);
  
  @PostMapping({"/synInvoiceMsg"})
  boolean synInvoiceMsg(@RequestBody ListTZInvoice paramListTZInvoice);
  
  @GetMapping({"/invoiceReview"})
  Object invoiceReview(@RequestParam String paramString);
  
  @PostMapping({"/gx"})
  JSONObject gx(@RequestBody com.zhys.po.DeductParamBody paramDeductParamBody);
  
  @PostMapping({"/cancel_gx"})
  JSONObject cancel_gx(@RequestBody com.zhys.po.DeductParamBody paramDeductParamBody);
  
  @PostMapping({"/updateRzState"})
  Integer updateRzState(@RequestBody InvoiceHeadPoJo ih);
  
  @PostMapping({"/updateRzStateAll"})
  Integer updateRzStateAll(@RequestBody InvoiceHeadPoJo ih);
  
  @GetMapping({"/getDkhz"})
  List<Dkhz> getDkhz(@RequestParam("nf") String nf);
  
  @PostMapping({"/DeductPages"})
  Pages<List<com.zhys.po.DeductRecord>> DeductPages(@RequestBody com.zhys.po.DeductRecord body,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
  
  @PostMapping({"/synRzrq"})
  public void synRzrq(@RequestBody ListTZInvoice invoice);
  
}
