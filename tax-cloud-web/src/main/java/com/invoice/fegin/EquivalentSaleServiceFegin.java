package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.EquivalentSale;
import com.lycheeframework.core.cmp.kit.Pages;

@Component
@FeignClient(value="invoice",path="/equivalentSale")
public interface EquivalentSaleServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody EquivalentSale t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    EquivalentSale queryByEntity(EquivalentSale t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<EquivalentSale>> pages(@RequestBody EquivalentSale t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);

	

	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody EquivalentSale businessMatters);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody EquivalentSale businessMatters);
	
	
	@PostMapping("/queryList")
    List<EquivalentSale> queryList(@RequestBody EquivalentSale t);
	

}
