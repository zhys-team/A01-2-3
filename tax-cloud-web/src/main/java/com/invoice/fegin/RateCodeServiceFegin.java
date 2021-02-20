package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.zhys.base.po.RateCode;

@Component

@FeignClient(value="user",path="/rateCode")
public interface RateCodeServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody RateCode t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    RateCode queryByEntity(@RequestBody RateCode t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/queryList")
    List<RateCode> queryList(@RequestBody RateCode t);
	
	
	/**
     * 通过主键更新状态
     * @param t
     * @return
     */
	@PostMapping("/changeDelStateById")
	Integer changeDelStateById(@RequestBody RateCode t);




}
