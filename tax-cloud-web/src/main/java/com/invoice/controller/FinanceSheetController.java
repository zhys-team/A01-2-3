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
import com.invoice.fegin.FinanceSheetServiceFegin;
import com.invoice.po.FinanceSheet;
import com.invoice.pojo.FinanceSheetPojo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("financeSheet")
@Api(value="财务报表申报表接口",description="财务报表申报表接口" )
public class FinanceSheetController {

    @Autowired
	private FinanceSheetServiceFegin service;

    private static final String INDEX = "index";
    @ApiIgnore
    @GetMapping("index")
	public String index(){
		
		return INDEX;
	}

    

    @ApiOperation(value = "财务报表申报表分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<FinanceSheet>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="financeSheet",value="查询条件",required=true) @RequestBody(required=false) FinanceSheetPojo financeSheet){
		return  service.pages(financeSheet,pageSize,pageNum);
	}
    
    
    @PostMapping("/save")
	public  Integer save(@RequestBody FinanceSheet financeSheet){
		return  service.save(financeSheet);
	}

    @ApiOperation(value = "根据条件获取财务报表申报表",notes="根据条件获取财务报表申报表")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "taxNo", value = "税号", required = true,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "year", value = "年份", required = true, paramType="query",dataType = "Integer"),
		@ApiImplicitParam(name = "period", value = "季度", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/info")
	public FinanceSheet info(@RequestParam String taxNo,@RequestParam Integer year,@RequestParam Integer period,FinanceSheet financeSheet){
    	financeSheet.setNssbh(taxNo);
    	financeSheet.setPeriodYear(year);
    	financeSheet.setPeriodQuater(period);
		financeSheet = service.queryByEntity(financeSheet);
		
		return financeSheet;
	}
	
    
    @PostMapping("lists")
	public  List<FinanceSheet> queryList(@RequestBody FinanceSheet financeSheet){
		return  service.queryList(financeSheet);
	}
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	FinanceSheet financeSheet = new FinanceSheet();
    	financeSheet.setId(id);
		return  service.changeDelStateById(financeSheet);
	}
    
    
//    @ApiOperation(value = "批量删除",notes="根据主键删除批量数据")
//    @ApiImplicitParams({
//		@ApiImplicitParam(name = "ids", value = "多条数据的主键", required = true, paramType="query",dataType = "String"),
//		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
//    })
//    @GetMapping("/states/{ids}")
//	public  Integer changeDelStateByIds(@PathVariable("ids") String ids,@RequestParam String state){
//    	FinanceSheetPojo csPojo = new FinanceSheetPojo();
//    	csPojo.setIds(ids);
//    	csPojo.setDelState(state);
//		return  service.changeDelStateByIds(csPojo);
//	}
    
    
}