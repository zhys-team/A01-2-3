package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.invoice.po.CustomerSupplier;
import com.invoice.pojo.CustomerSupplierPoJo;
import com.lycheeframework.core.cmp.kit.Pages;

@Component

@FeignClient(value="invoice",path="/customerSupplier")
public interface CustomerSupplierServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(CustomerSupplier t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    CustomerSupplier queryByEntity(CustomerSupplier t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<CustomerSupplier>> pages(@RequestBody CustomerSupplierPoJo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@GetMapping("/queryList")
    List<CustomerSupplier> queryList(CustomerSupplier t);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody CustomerSupplier t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody CustomerSupplierPoJo t) ;
	

}
