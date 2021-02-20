package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysRoles;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.SysRolesServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("sysRoles")
@Api(value="角色接口",description="角色接口" )
public class SysRolesController {

    @Autowired
	private SysRolesServiceFegin service;


    

    @ApiOperation(value = "角色分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<SysRoles>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="sysRoles",value="查询条件",required=true) 
	            @RequestBody(required=false) SysRoles sysRoles){
		return  service.pages(sysRoles,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody SysRoles sysRoles){
		return  service.save(sysRoles);
	}

    
    @GetMapping("{id}")
	public SysRoles info(@PathVariable("id") Integer id,SysRoles sysRoles){
    	sysRoles.setId(id);
		sysRoles = service.queryByEntity(sysRoles);
		
		return sysRoles;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/states")
	public  Integer changeDelStateById(@RequestParam("ids") String ids,@RequestParam String state){
    	SysRoles sysRoles = new SysRoles();
    	sysRoles.setIds(ids);
		return  service.changeDelStateByIds(sysRoles);
	}
    
    
    
    
    
}