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
import com.invoice.fegin.AddTaxServiceFegin;
import com.invoice.po.AddTaxMain;
import com.invoice.pojo.AddTaxMainPojo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("addTax")
@Api(value="增值税申报表接口",description="增值税申报表接口" )
public class AddTaxController {

    @Autowired
	private AddTaxServiceFegin service;

    private static final String INDEX = "index";
    @ApiIgnore
    @GetMapping("index")
	public String index(){
		
		return INDEX;
	}

    

    @ApiOperation(value = "增值税申报表分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<AddTaxMain>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="addTax",value="查询条件",required=true) @RequestBody(required=false) AddTaxMainPojo addTax){
		return  service.pages(addTax,pageSize,pageNum);
	}
    
    
    @PostMapping("/save")
	public  Integer save(@RequestBody AddTaxMain addTax){
		return  service.save(addTax);
	}

    @ApiOperation(value = "根据条件获取增值税申报表",notes="根据条件获取增值税申报表")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "taxNo", value = "税号", required = true,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "year", value = "年份", required = true, paramType="query",dataType = "String"),
		@ApiImplicitParam(name = "period", value = "月份", required = true, paramType="query",dataType = "String")
    })
    @GetMapping("/info")
	public AddTaxMain info(@RequestParam String taxNo,@RequestParam String year,@RequestParam String period,AddTaxMain addTax){
    	addTax.setNssbh(taxNo);
    	addTax.setYear(year);
    	addTax.setPeriod(period);
		addTax = service.queryByEntity(addTax);
		
		return addTax;
	}
	
    
    @PostMapping("lists")
	public  List<AddTaxMain> queryList(@RequestBody AddTaxMain addTax){
		return  service.queryList(addTax);
	}
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	AddTaxMain addTax = new AddTaxMain();
    	addTax.setId(id);
    	addTax.setDelState(state);
		return  service.changeDelStateById(addTax);
	}
    
    
//    @ApiOperation(value = "批量删除",notes="根据主键删除批量数据")
//    @ApiImplicitParams({
//		@ApiImplicitParam(name = "ids", value = "多条数据的主键", required = true, paramType="query",dataType = "String"),
//		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
//    })
//    @GetMapping("/states/{ids}")
//	public  Integer changeDelStateByIds(@PathVariable("ids") String ids,@RequestParam String state){
//    	AddTaxMainPojo csPojo = new AddTaxMainPojo();
//    	csPojo.setIds(ids);
//    	csPojo.setDelState(state);
//		return  service.changeDelStateByIds(csPojo);
//	}
    
    
}