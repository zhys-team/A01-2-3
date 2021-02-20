package com.zhys.fegin;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;
import com.zhys.ea.po.EaHead;

@Component

@FeignClient(value="ea",path="/eaHead")
public interface EaHeadServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody EaHead t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    EaHead queryByEntity(@RequestBody EaHead t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<EaHead>> pages(@RequestBody EaHead t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	@PostMapping("/changeAuditStateById")
	Integer changeAuditStateById(@RequestBody EaHead t);
	
	@PostMapping("/changeDelStateById")
	Integer changeDelStateById(@RequestBody EaHead t);
	
	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody EaHead t);
	

}
