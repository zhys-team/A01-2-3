package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysRolesUsers;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.SysRolesUsersServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("sysRolesUsers")
@Api(value="角色用户接口",description="角色用户接口" )
public class SysRolesUsersController {

    @Autowired
	private SysRolesUsersServiceFegin service;


    

    @ApiOperation(value = "角色用户分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<SysRolesUsers>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="sysRolesUsers",value="查询条件",required=true) 
	            @RequestBody(required=false) SysRolesUsers sysRolesUsers){
		return  service.pages(sysRolesUsers,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody SysRolesUsers sysRolesUsers){
		return  service.save(sysRolesUsers);
	}

    
    @GetMapping("{id}")
	public SysRolesUsers info(@PathVariable("id") Integer id,SysRolesUsers sysRolesUsers){
    	sysRolesUsers.setRoleid(id);
		sysRolesUsers = service.queryByEntity(sysRolesUsers);
		
		return sysRolesUsers;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Integer id,@RequestParam String state){
    	SysRolesUsers sysRolesUsers = new SysRolesUsers();
    	sysRolesUsers.setRoleid(id);
		return  service.changeDelStateById(sysRolesUsers);
	}
    
    @GetMapping("/queryList")
    public List<SysRolesUsers> queryList(@RequestParam Integer roleId){
    	SysRolesUsers sysRolesUsers = new SysRolesUsers();
    	sysRolesUsers.setRoleid(roleId);
    	return  service.queryList(sysRolesUsers);
}
    
    
    
}