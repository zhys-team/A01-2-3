package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysUsers;

@Component
@FeignClient(value="user",path="/sysUsers")
public interface SysUsersServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody SysUsers t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    SysUsers queryByEntity(SysUsers t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<SysUsers>> pages(@RequestBody SysUsers t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody SysUsers sysUsers);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody SysUsers sysUsers);
	
	@PostMapping("/queryList")
    List<SysUsers> queryList(@RequestBody SysUsers t);

	@PostMapping("/queryByNo")
	SysUsers queryByNo(@RequestBody SysUsers sysUsers);
	
	@PostMapping("/modifyPwd")
	Boolean modifyPwd(@RequestBody SysUsers sysUsers);
	
	

}
