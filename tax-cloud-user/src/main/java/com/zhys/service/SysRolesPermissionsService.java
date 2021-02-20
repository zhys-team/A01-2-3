package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysRolesPermissions;

@RequestMapping("sysRolesPermissions")
public interface SysRolesPermissionsService extends BaseService<SysRolesPermissions,SysRolesPermissions>{
	
}