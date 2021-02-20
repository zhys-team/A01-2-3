package com.zhys.controller;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.po.BusinessMatters;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.zhys.fegin.BusinessMattersServiceFegin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@RestController
@RequestMapping("businessMatters")
@Api(value = "业务事项接口", description = "业务事项接口")
public class BusinessMattersController {

    @Autowired
    private BusinessMattersServiceFegin service;


    @ApiOperation(value = "业务事项分页列表", notes = "根据条件查询数据并分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType = "query", dataType = "Integer"),
    })
    @PostMapping(value = "page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pages<List<BusinessMatters>> pages(@RequestParam Integer pageSize, @RequestParam Integer pageNum, @ApiParam(name = "businessMatters", value = "查询条件", required = true)
    @RequestBody(required = false) BusinessMatters businessMatters) {
        return service.pages(businessMatters, pageSize, pageNum);
    }


    @PostMapping("save")
    public Integer save(@RequestBody BusinessMatters businessMatters) {
        return service.save(businessMatters);
    }


    @GetMapping("info/{id}")
    public BusinessMatters info(@PathVariable("id") Long id, BusinessMatters businessMatters) {
        businessMatters.setId(id);
        businessMatters = service.queryByEntity(businessMatters);

        return businessMatters;
    }


    @ApiOperation(value = "通过主键删除", notes = "根据主键删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType = "query", dataType = "Integer")
    })
    @GetMapping("/state/{id}")
    public Integer changeDelStateById(@PathVariable("id") Long id, BusinessMatters businessMatters) {
        businessMatters.setId(id);
        businessMatters.setDelStatus("1");
        return service.changeDelStateById(businessMatters);
    }

    @ApiOperation(value = "业务事项列表", notes = "业务事项列表")
    @PostMapping(value = "queryList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessMatters> queryList(@ApiParam(name = "businessMatters", value = "查询条件", required = false)
                                           @RequestBody(required = false) BusinessMatters businessMatters) {
        return service.queryList(businessMatters);

    }

    @ApiOperation(value = "业务事项列表(只能通过名称查询)", notes = "业务事项列表(只能通过名称查询)")
    @GetMapping(value = "queryListByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessMatters> queryListByName(
            @RequestParam(value = "name") String name, BusinessMatters businessMatters) {
        businessMatters.setName(name);
        return service.queryList(businessMatters);

    }
}