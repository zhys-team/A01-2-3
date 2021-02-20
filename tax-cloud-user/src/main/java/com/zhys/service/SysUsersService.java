package com.zhys.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhys.base.BaseService;
import com.zhys.user.po.SysUsers;

@RequestMapping("sysUsers")
public interface SysUsersService extends BaseService<SysUsers,SysUsers>{
	
	
	/**
     * 获取用户所拥有权限信息
     * @param t
     * @return
     */
	@PostMapping("/queryByNo")
	SysUsers queryByNo(@RequestBody SysUsers sysUsers);
	
	@PostMapping("/modifyPwd")
	Boolean modifyPwd(@RequestBody SysUsers sysUsers);

	@GetMapping("/login")
	SysUsers login(@RequestBody SysUsers sysUsers);
}