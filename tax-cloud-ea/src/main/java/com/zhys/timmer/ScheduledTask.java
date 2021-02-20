package com.zhys.timmer;

import com.invoice.po.InvoiceHead;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.zhys.fegin.InvoiceHeadServiceFegin;
import com.zhys.po.ListTZInvoice;
import com.zhys.po.TzsysEasInvoicereceive;
import com.zhys.service.TzsysEasInvoicereceiveService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@Log
public class ScheduledTask {

    @Autowired
    private TzsysEasInvoicereceiveService service;
    
    @Autowired
    private InvoiceHeadServiceFegin invoiceHeadService;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");




    /**
     * 每秒钟调用中间表 查询是否有待同步数据
     *
     */
    @Scheduled(fixedRate = 1000*5)
    public void waitSend() {

    service.waitSend();
    }
    

    /**
     * 根据视图更新中间表
     *
     */
    @Scheduled(fixedRate = 1000*60)
    public void updateStatus() {
     service.updateStatus();
    
    }
    
    //@Scheduled(fixedRate = 1000*60000)
    public void pdfurl() {
    	List<InvoiceHead> list= invoiceHeadService.queryListByPoJo(new InvoiceHeadPoJo());
    	for(InvoiceHead head:list) {
    		TzsysEasInvoicereceive eas = new TzsysEasInvoicereceive();
    		eas.setInvoiceId(head.getId());
    		eas.setImgUrl(head.getPdfUrl());
    		service.updatePdfUrl(eas);
    	}
    
    }
    
  @Scheduled(fixedRate = 1000*60000)
    public void synRzrq() {
	  ListTZInvoice invoice = service.queryRzrqList();
	  invoiceHeadService.synRzrq(invoice);
    
    }
    
    
}
