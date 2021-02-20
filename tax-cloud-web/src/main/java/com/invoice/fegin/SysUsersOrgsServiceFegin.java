package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysUsersOrgs;

@Component
@FeignClient(value="user",path="/sysUsersOrgs")
public interface SysUsersOrgsServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody SysUsersOrgs t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    SysUsersOrgs queryByEntity(SysUsersOrgs t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<SysUsersOrgs>> pages(@RequestBody SysUsersOrgs t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody SysUsersOrgs sysUsersOrgs);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody SysUsersOrgs sysUsersOrgs);
	
	@PostMapping("/queryList")
    List<SysUsersOrgs> queryList(@RequestBody SysUsersOrgs t);
	
	

}
