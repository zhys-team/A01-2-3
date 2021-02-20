package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysPermissions;
import com.zhys.user.po.SysUsers;

@Component
@FeignClient(value="user",path="/sysPermissions")
public interface SysPermissionsServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody SysPermissions t);

	/**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    SysPermissions queryByEntity(SysPermissions t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<SysPermissions>> pages(@RequestBody SysPermissions t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody SysPermissions sysPermissions);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody SysPermissions sysPermissions);
	
	@PostMapping("/queryList")
    List<SysPermissions> queryList(@RequestBody SysPermissions t);
	
	/**
     * 获取用户所拥有权限信息
     * @param t
     * @return
     */
	@PostMapping("/queryListByUserNo")
	List<SysPermissions> queryListByUserNo(@RequestBody  SysPermissions sysPermissions);

}
