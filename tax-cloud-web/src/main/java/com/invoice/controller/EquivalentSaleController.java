package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.EquivalentSaleServiceFegin;
import com.invoice.po.EquivalentSale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@RestController
@RequestMapping("equivalentSale")
@Api(value="视同销售接口",description="视同销售接口" )
public class EquivalentSaleController {

    @Autowired
	private EquivalentSaleServiceFegin service;


    

    @ApiOperation(value = "视同销售分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<EquivalentSale>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="businessMatters",value="查询条件",required=true) 
	            @RequestBody(required=false) EquivalentSale businessMatters){
		return  service.pages(businessMatters,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody EquivalentSale businessMatters){
		return  service.save(businessMatters);
	}

    
    @GetMapping("info/{id}")
	public EquivalentSale info(@PathVariable("id") Long id,EquivalentSale businessMatters){
    	businessMatters.setId(id);
		businessMatters = service.queryByEntity(businessMatters);
		
		return businessMatters;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,EquivalentSale businessMatters){
    	businessMatters.setId(id);
		return  service.changeDelStateById(businessMatters);
	}
    
    @ApiOperation(value = "视同销售列表",notes="视同销售列表")
    @PostMapping(value = "queryList",produces = MediaType.APPLICATION_JSON_VALUE)
	public  List<EquivalentSale> queryList( @ApiParam(name="businessMatters",value="查询条件",required=false) 
	            @RequestBody(required=false) EquivalentSale businessMatters){
		return  service.queryList(businessMatters);
    
    }
    
}