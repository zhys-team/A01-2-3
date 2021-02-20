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
import com.zhys.user.po.Goods;
import com.zhys.user.pojo.GoodsPojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.invoice.fegin.GoodsServiceFegin;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("goods")
@Api(value="商品接口",description="商品接口" )
public class GoodsController {

    @Autowired
	private GoodsServiceFegin service;

    private static final String INDEX = "index";
    @ApiIgnore
    @GetMapping("index")
	public String index(){
		
		return INDEX;
	}

    

    @ApiOperation(value = "商品分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<Goods>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="goods",value="查询条件",required=true) @RequestBody(required=false) GoodsPojo goods){
		return  service.pages(goods,pageSize,pageNum);
	}
    
    
    @PostMapping("/save")
	public  Integer save(@RequestBody Goods goods){
		return  service.save(goods);
	}

    
    @GetMapping("/info/{id}")
	public Goods info(@PathVariable("id") Long id,Goods goods){
    	goods.setId(id);
		goods = service.queryByEntity(goods);
		
		return goods;
	}
	
    
    @PostMapping("lists")
	public  List<Goods> queryList(@RequestBody Goods goods){
		return  service.queryList(goods);
	}
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	Goods goods = new Goods();
    	goods.setId(id);
    	goods.setDelState(state);
		return  service.changeDelStateById(goods);
	}
    
    
    @ApiOperation(value = "批量删除",notes="根据主键删除批量数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "多条数据的主键", required = true, paramType="query",dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/states/{ids}")
	public  Integer changeDelStateByIds(@PathVariable("ids") String ids,@RequestParam String state){
    	GoodsPojo csPoJo = new GoodsPojo();
    	csPoJo.setIds(ids);
    	csPoJo.setDelState(state);
		return  service.changeDelStateByIds(csPoJo);
	}
    
    
}