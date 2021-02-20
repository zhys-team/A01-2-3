package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.CustomsPaymentBook;
import com.lycheeframework.core.cmp.kit.Pages;

@Component
@FeignClient(value="invoice",path="/customsPaymentBook")
public interface CustomsPaymentBookServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody CustomsPaymentBook t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    CustomsPaymentBook queryByEntity(CustomsPaymentBook t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<CustomsPaymentBook>> pages(@RequestBody CustomsPaymentBook t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);

	

	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody CustomsPaymentBook businessMatters);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody CustomsPaymentBook businessMatters);
	
	
	@PostMapping("/queryList")
    List<CustomsPaymentBook> queryList(@RequestBody CustomsPaymentBook t);
	

}
