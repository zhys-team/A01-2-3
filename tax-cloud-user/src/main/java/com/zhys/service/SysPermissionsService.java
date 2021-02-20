package com.zhys.service;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysPermissions;

@RequestMapping("sysPermissions")
public interface SysPermissionsService extends BaseService<SysPermissions,SysPermissions>{
	
	/**
     * 获取用户所拥有权限信息
     * @param t
     * @return
     */
	@PostMapping("/queryListByUserNo")
	List<SysPermissions> queryListByUserNo(@RequestBody  SysPermissions sysPermissions);
	
}