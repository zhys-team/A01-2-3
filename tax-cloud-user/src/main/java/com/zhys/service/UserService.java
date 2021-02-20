package com.zhys.service;

import org.springframework.web.bind.annotation.RequestMapping;
import com.zhys.base.BaseService;
import com.zhys.user.po.SysUsers;
import com.zhys.user.pojo.SysUsersPojo;


@RequestMapping("user")
public interface UserService extends BaseService<SysUsers,SysUsersPojo>{
	
}