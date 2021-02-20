package com.invoice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.result.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.invoice.fegin.InvoiceBodyServiceFegin;
import com.invoice.po.InvoiceBody;
import com.invoice.pojo.InvoiceBodyPoJo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("invoiceBodys")
@Api(value="发票明细接口",description="发票明细接口" )
public class InvoiceBodyController {

    @Autowired
	private InvoiceBodyServiceFegin service;

    
    @ApiOperation(value = "获取发票明细",notes="根据发票主信息主键获取发票明细")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "发票主信息主键", required = true, paramType="path",dataType = "String"),
    })
    @GetMapping("list/{id}")
	public @ResponseBody List<InvoiceBody> queryList(@PathVariable("id") Long id){
    	InvoiceBody invoiceBody = new InvoiceBody();
    	invoiceBody.setHeadId(id);
		return  service.queryList(invoiceBody);
	}
    
    @ApiOperation(value = "批量获取发票明细",notes="根据多个发票主信息主键获取多条发票明细")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "发票主信息主键", required = true, paramType="path",dataType = "String"),
    })
    @GetMapping("lists/{ids}")
	public @ResponseBody List<InvoiceBody> queryListByPoJo(@PathVariable("ids") String id){
    	InvoiceBodyPoJo invoiceBody = new InvoiceBodyPoJo();
    	invoiceBody.setHeadIds(id);
		return  service.queryListByPoJo(invoiceBody);
	}
	
    
}