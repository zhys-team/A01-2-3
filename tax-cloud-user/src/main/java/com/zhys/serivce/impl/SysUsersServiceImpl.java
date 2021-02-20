package com.zhys.serivce.impl;

import com.zhys.user.po.SysUsers;
import com.zhys.utils.MD5Util;
import com.zhys.service.SQLManager;

import java.util.List;

import com.lycheeframework.core.cmp.kit.Pages;
import com.lycheeframework.core.cmp.kit.EasyPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import com.zhys.service.SysUsersService;

@Slf4j
@RestController
public class SysUsersServiceImpl extends BaseApiService implements SysUsersService {

    @Autowired
    private SQLManager manager;


    @Override
    public Integer save(@RequestBody SysUsers sysUsers) {
        if (sysUsers != null && sysUsers.getId() != null) {//修改
            //sysUsers.setPassword(MD5Util.MD5(sysUsers.getPassword()));
            return manager.update("sys_users.update", sysUsers);
        } else {//插入
            sysUsers.setPassword(MD5Util.MD5(sysUsers.getPassword()));
            return manager.insert("sys_users.create", sysUsers);
        }

    }


    @Override
    public SysUsers login(@RequestBody SysUsers sysUsers) {
        return (SysUsers) manager.query("sys_users.login", sysUsers);
    }

    @Override
    public SysUsers queryByEntity(@RequestBody SysUsers sysUsers) {
        return (SysUsers) manager.query("sys_users.query", sysUsers);
    }

    @Override
    public List<SysUsers> queryList(@RequestBody SysUsers sysUsers) {
        return (List<SysUsers>) manager.list("sys_users.queryList", sysUsers);
    }

    @Override
    public Pages<List<SysUsers>> pages(@RequestBody SysUsers sysUsers, Integer pageSize, Integer pageNum) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        return (Pages<List<SysUsers>>) manager.pages("sys_users.page", sysUsers, page);

    }

    /**
     * @Override public Pages<List<SysUsers>> pages(@RequestBody SysUsersPojo sysUsersPojo, Integer pageSize, Integer pageNum){
     * EasyPage page = new EasyPage();
     * page.pageNum(pageNum);
     * page.pageSize(pageSize);
     * return (Pages<List<SysUsers>>) manager.pages("sys_users.page", sysUsersPojo, page);
     * <p>
     * }
     **/


    @Override
    public Integer changeDelStateById(SysUsers t) {
        return null;
    }


    @Override
    public Integer changeDelStateByIds(@RequestBody SysUsers t) {
        manager.update("sys_users.updateState", t);
        return 1;
    }


    @Override
    public Pages<List<SysUsers>> pagesByPojo(SysUsers e, Integer pageSize, Integer pageNum) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<SysUsers> queryListByPoJo(SysUsers e) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public SysUsers queryByNo(@RequestBody SysUsers sysUsers) {
        return (SysUsers) manager.query("sys_users.queryByNo", sysUsers);
    }


    @Override
    public Boolean modifyPwd(@RequestBody SysUsers sysUsers) {
        try {
            sysUsers.setPassword(MD5Util.MD5(sysUsers.getPassword()));
            manager.query("sys_users.modifyPwd", sysUsers);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改密码失败，原因{}", e.getMessage());
            return false;
        }
        return true;
    }


}