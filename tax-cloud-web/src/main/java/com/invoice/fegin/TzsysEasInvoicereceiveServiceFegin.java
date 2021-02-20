package com.invoice.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.InvoiceBody;
import com.zhys.po.TzsysEasInvoicereceive;

@Component
@FeignClient(value="ea",path="/tzsysEasInvoicereceive")
public interface TzsysEasInvoicereceiveServiceFegin {
	@PostMapping("/save")
    Integer save(@RequestBody TzsysEasInvoicereceive easInvoicereceive );
	
	@GetMapping("/delete")
    int delete(@RequestParam("invoiceId") Long invoiceId );
	
	@GetMapping("/queryView")
	TzsysEasInvoicereceive queryView(@RequestParam("invoiceId") Long invoiceId );
	
	@PostMapping("/saveBody")
    Integer saveBody(@RequestBody InvoiceBody body);
}
