package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysUsersOrgs;
import com.zhys.user.pojo.SysUsersOrgsPojo;

@RequestMapping("sysUsersOrgs")
public interface SysUsersOrgsService extends BaseService<SysUsersOrgs,SysUsersOrgsPojo>{
	
}