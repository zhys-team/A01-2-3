package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysRoles;

@RequestMapping("sysRoles")
public interface SysRolesService extends BaseService<SysRoles,SysRoles>{
	
}