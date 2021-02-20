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

import com.invoice.fegin.ValidateRecordServiceFegin;
import com.invoice.po.ValidateRecord;
import com.invoice.pojo.ValidateRecordPoJo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("validateRecords")
@Api(description="发票查验记录接口")
public class ValidateRecordController {

    @Autowired
	private ValidateRecordServiceFegin service;

    private static final String INDEX = "index";
    @ApiIgnore
    @GetMapping("index")
	public String index(){
		
		return INDEX;
	}

    

    @ApiOperation(value = "发票查验记录分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<ValidateRecord>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="validateRecord",value="查询条件",required=true) @RequestBody(required=false) ValidateRecordPoJo validateRecord){
		return  service.pages(validateRecord,pageSize,pageNum);
	}
    
    
    @PostMapping
	public  Integer save(@RequestBody ValidateRecord validateRecord){
		return  service.save(validateRecord);
	}

    
    @GetMapping("{id}")
	public ValidateRecord info(@PathVariable("id") Long id,ValidateRecord validateRecord){
    	validateRecord.setId(id);
		validateRecord = service.queryByEntity(validateRecord);
		
		return validateRecord;
	}
	
    
    @PostMapping("lists")
	public  List<ValidateRecord> queryList(@RequestBody ValidateRecord validateRecord){
		return  service.queryList(validateRecord);
	}
	
    
}