package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysPermissions;
import com.zhys.user.po.SysUsers;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.SysPermissionsServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("sysPermissions")
@Api(value="权限接口",description="权限接口" )
public class SysPermissionsController {

    @Autowired
	private SysPermissionsServiceFegin service;


    

    @ApiOperation(value = "权限分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<SysPermissions>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="sysPermissions",value="查询条件",required=true) 
	            @RequestBody(required=false) SysPermissions sysPermissions){
		return  service.pages(sysPermissions,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody SysPermissions sysPermissions){
		return  service.save(sysPermissions);
	}

    
    @GetMapping("{id}")
	public SysPermissions info(@PathVariable("id") Integer id,SysPermissions sysPermissions){
    	sysPermissions.setId(id);
		sysPermissions = service.queryByEntity(sysPermissions);
		
		return sysPermissions;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Integer id,@RequestParam String state){
    	SysPermissions sysPermissions = new SysPermissions();
    	sysPermissions.setId(id);
		return  service.changeDelStateById(sysPermissions);
	}
    
    @GetMapping("/queryList")
    public List<SysPermissions> queryList(){
    	SysPermissions sysPermissions = new SysPermissions();
    	return  service.queryList(sysPermissions);
    }  
    
    
}