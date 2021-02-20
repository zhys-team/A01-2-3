package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.po.DataBase;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.DataBaseServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("dataBase")
@Api(value="基础数据接口",description="基础数据接口" )
public class DataBaseController {

    @Autowired
	private DataBaseServiceFegin service;


    

    @ApiOperation(value = "基础数据分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<DataBase>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="dataBase",value="查询条件",required=true) 
	            @RequestBody(required=false) DataBase dataBase){
		return  service.pages(dataBase,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody DataBase dataBase){
    	System.out.println(">>>>>");
		return  service.save(dataBase);
	}

    
    @GetMapping("info/{id}")
	public DataBase info(@PathVariable("id") Long id,DataBase dataBase){
    	dataBase.setId(id);
		dataBase = service.queryByEntity(dataBase);
		
		return dataBase;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	DataBase dataBase = new DataBase();
    	dataBase.setId(id);
    	dataBase.setDelState(state);
		return  service.changeDelStateById(dataBase);
	}
    
    @PostMapping("/queryList")
    public List<DataBase> queryList(@RequestBody DataBase dataBase){
    	return  service.queryList(dataBase);
    }  
    
    
}