package com.zhys.service;

import com.zhys.po.ListTZInvoice;
import com.zhys.po.TzsysEasInvoicereceive;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.InvoiceBody;
import com.zhys.base.BaseService;

@RequestMapping("tzsysEasInvoicereceive")
public interface TzsysEasInvoicereceiveService extends BaseService<TzsysEasInvoicereceive, TzsysEasInvoicereceive>{

    void waitSend();
    
    @GetMapping("/delete")
    int delete(@RequestParam("invoiceId") Long invoiceId );
    
    void updateStatus();
    
    @GetMapping("/queryView")
    TzsysEasInvoicereceive queryView(@RequestParam("invoiceId") Long invoiceId );
    
    @PostMapping("/saveBody")
    Integer saveBody(@RequestBody InvoiceBody body);
    
    
    @PostMapping("/updatePdfUrl")
    void updatePdfUrl(@RequestBody TzsysEasInvoicereceive invoicereceive);
    
    @GetMapping("/queryRzrqList")
    ListTZInvoice queryRzrqList( );
	
}