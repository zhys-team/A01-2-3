package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.po.FinanceSheet;
import com.invoice.pojo.FinanceSheetPojo;
import com.lycheeframework.core.cmp.kit.Pages;

@Component

@FeignClient(value="invoice",path="/financeSheet")
public interface FinanceSheetServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(FinanceSheet t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    FinanceSheet queryByEntity(FinanceSheet t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<FinanceSheet>> pages(@RequestBody FinanceSheetPojo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<FinanceSheet> queryList(FinanceSheet t);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody FinanceSheet t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody FinanceSheetPojo t) ;
	

}
