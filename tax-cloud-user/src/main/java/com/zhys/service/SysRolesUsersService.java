package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysRolesUsers;

@RequestMapping("sysRolesUsers")
public interface SysRolesUsersService extends BaseService<SysRolesUsers,SysRolesUsers>{
	
}