package com.invoice.fegin;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.InvoiceDoubt;
import com.invoice.pojo.InvoiceDoubtPoJo;
import com.lycheeframework.core.cmp.kit.Pages;

@Component

@FeignClient(value="invoice",path="/invoiceDoubts")
public interface InvoiceDoubtServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(InvoiceDoubt t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    InvoiceDoubt queryByEntity(InvoiceDoubt t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<InvoiceDoubt>> pages(@RequestBody InvoiceDoubtPoJo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@RequestMapping("/queryList")
    List<InvoiceDoubt> queryList(InvoiceDoubt t);
	
	/**
     * 通过id修改状态
     * @param t
     * @return
     */
	@PostMapping("/changeStateById")
	Integer changeStateById(@RequestBody InvoiceDoubt t);
	
	/**
     * 通过ids批量修改状态
     * @param t
     * @return
     */
	@PostMapping("/changeStateByIds")
	Integer changeStateByIds(@RequestBody InvoiceDoubtPoJo t);
	

}
