package com.invoice.controller;

import java.util.Map;

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

import com.invoice.fegin.CustomerSupplierServiceFegin;
import com.invoice.po.CustomerSupplier;
import com.invoice.pojo.CustomerSupplierPoJo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("customerSuppliers")
@Api(value="客商接口",description="客商接口" )
public class CustomerSupplierController {

    @Autowired
	private CustomerSupplierServiceFegin service;

    private static final String INDEX = "index";
    @ApiIgnore
    @GetMapping("index")
	public String index(){
		
		return INDEX;
	}

    

    @ApiOperation(value = "客商分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<CustomerSupplier>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="customerSupplier",value="查询条件",required=true) @RequestBody(required=false) CustomerSupplierPoJo customerSupplier){
		return  service.pages(customerSupplier,pageSize,pageNum);
	}
    
    
    @PostMapping("/save")
	public  Integer save(@RequestBody CustomerSupplier customerSupplier){
		return  service.save(customerSupplier);
	}

    
    @GetMapping("/info/{id}")
	public CustomerSupplier info(@PathVariable("id") Long id,CustomerSupplier customerSupplier){
    	customerSupplier.setId(id);
		customerSupplier = service.queryByEntity(customerSupplier);
		
		return customerSupplier;
	}
	
    
    @PostMapping("lists")
	public  List<CustomerSupplier> queryList(@RequestBody CustomerSupplier customerSupplier){
		return  service.queryList(customerSupplier);
	}
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	CustomerSupplier customerSupplier = new CustomerSupplier();
    	customerSupplier.setId(id);
    	customerSupplier.setDelState(state);
		return  service.changeDelStateById(customerSupplier);
	}
    
    
    @ApiOperation(value = "批量删除",notes="根据主键删除批量数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "多条数据的主键", required = true, paramType="query",dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/states/{ids}")
	public  Integer changeDelStateByIds(@PathVariable("ids") String ids,@RequestParam String state){
    	CustomerSupplierPoJo csPoJo = new CustomerSupplierPoJo();
    	csPoJo.setIds(ids);
    	csPoJo.setDelState(state);
		return  service.changeDelStateByIds(csPoJo);
	}
    
    
}