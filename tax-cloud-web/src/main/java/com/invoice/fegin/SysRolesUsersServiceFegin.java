package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysRolesUsers;

@Component
@FeignClient(value="user",path="/sysRolesUsers")
public interface SysRolesUsersServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody SysRolesUsers t);


    
    /**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    SysRolesUsers queryByEntity(SysRolesUsers t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<SysRolesUsers>> pages(@RequestBody SysRolesUsers t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody SysRolesUsers sysRolesUsers);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody SysRolesUsers sysRolesUsers);
	
	@PostMapping("/queryList")
    List<SysRolesUsers> queryList(@RequestBody SysRolesUsers t);
	
	

}
