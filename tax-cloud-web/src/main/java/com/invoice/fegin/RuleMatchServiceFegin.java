package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.invoice.po.RuleMatch;
import com.lycheeframework.core.cmp.kit.Pages;

@Component
@FeignClient(value="invoice",path="/ruleMatch")
public interface RuleMatchServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody RuleMatch t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    RuleMatch queryByEntity(@RequestBody RuleMatch t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<RuleMatch>> pages(@RequestBody RuleMatch t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	@PostMapping("/queryList")
	 List<RuleMatch> queryList(@RequestBody RuleMatch ruleMatch);
	
	@PostMapping("/changeDelStateById")
	Integer changeDelStateById(@RequestBody RuleMatch ruleMatch);
	

}
