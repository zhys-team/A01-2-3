package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.po.BusinessMatters;

@Component
@FeignClient(value="user",path="/businessMatters")
public interface BusinessMattersServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody BusinessMatters t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    BusinessMatters queryByEntity(BusinessMatters t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<BusinessMatters>> pages(@RequestBody BusinessMatters t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);

	

	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody BusinessMatters businessMatters);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody BusinessMatters businessMatters);
	
	
	@PostMapping("/queryList")
    List<BusinessMatters> queryList(@RequestBody BusinessMatters t);
	

}
