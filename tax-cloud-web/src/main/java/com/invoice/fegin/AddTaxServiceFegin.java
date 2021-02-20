package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.AddTaxMain;
import com.invoice.pojo.AddTaxMainPojo;
import com.lycheeframework.core.cmp.kit.Pages;

@Component

@FeignClient(value="invoice",path="/addTaxMain")
public interface AddTaxServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(AddTaxMain t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    AddTaxMain queryByEntity(AddTaxMain t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<AddTaxMain>> pages(@RequestBody AddTaxMainPojo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<AddTaxMain> queryList(AddTaxMain t);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody AddTaxMain t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody AddTaxMainPojo t) ;
	

}
