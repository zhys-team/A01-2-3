package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysOrgs;
import com.zhys.user.pojo.SysOrgsPojo;

@RequestMapping("sysOrgs")
public interface SysOrgsService extends BaseService<SysOrgs,SysOrgsPojo>{
	
}