package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.IncomeTax;
import com.invoice.pojo.IncomeTaxPoJo;
import com.lycheeframework.core.cmp.kit.Pages;

@Component

@FeignClient(value="invoice",path="/incomeTax")
public interface IncomeTaxServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(IncomeTax t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    IncomeTax queryByEntity(IncomeTax t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<IncomeTax>> pages(@RequestBody IncomeTaxPoJo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<IncomeTax> queryList(IncomeTax t);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody IncomeTax t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody IncomeTaxPoJo t) ;
	

}
