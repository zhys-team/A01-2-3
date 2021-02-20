package com.zhys.fegin;

import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysOrgs;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value="user",path="/sysOrgs")
public interface SysOrgsServiceFegin {

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody SysOrgs t);

	/**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    SysOrgs queryByEntity(SysOrgs t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<SysOrgs>> pages(@RequestBody SysOrgs t, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody SysOrgs sysOrgs);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById(@RequestBody SysOrgs sysOrgs);
	
	@PostMapping("/queryList")
    List<SysOrgs> queryList(@RequestBody SysOrgs t);
	
	

}
