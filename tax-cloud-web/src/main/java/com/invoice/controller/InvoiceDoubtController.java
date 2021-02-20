package com.invoice.controller;

import com.invoice.fegin.InvoiceDoubtServiceFegin;
import com.invoice.po.InvoiceDoubt;
import com.invoice.pojo.InvoiceDoubtPoJo;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ResponseResult
@RestController
@RequestMapping({"invoiceDoubts"})
@Api(description = "疑票接口")
public class InvoiceDoubtController {
  private static final Logger log = LoggerFactory.getLogger(InvoiceDoubtController.class);
  
  @Autowired
  private InvoiceDoubtServiceFegin service;
  
  private static final String INDEX = "index";
  
  @ApiIgnore
  @GetMapping({"index"})
  public String index() {
    return "index";
  }
  
  @ApiOperation(value = "疑票分页列表", notes = "根据条件查询数据并分页")
  @ApiImplicitParams({@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true, paramType = "query", dataType = "Integer"), @ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType = "query", dataType = "Integer")})
  @PostMapping(value = {"page"}, produces = {"application/json"})
  public Pages<List<InvoiceDoubt>> pages(@RequestParam Integer pageSize, @RequestParam Integer pageNum, @ApiParam(name = "invoiceDoubt", value = "查询条件", required = false) @RequestBody(required = false) InvoiceDoubtPoJo invoiceDoubt) {
    return this.service.pages(invoiceDoubt, pageSize, pageNum);
  }
  
  @PostMapping
  public Integer save(@RequestBody InvoiceDoubt invoiceDoubt) {
    invoiceDoubt.setCreateTime(new Date());
    return this.service.save(invoiceDoubt);
  }
  
  @GetMapping({"{id}"})
  public InvoiceDoubt info(@PathVariable("id") Long id, InvoiceDoubt invoiceDoubt) {
    invoiceDoubt.setId(id);
    invoiceDoubt = this.service.queryByEntity(invoiceDoubt);
    return invoiceDoubt;
  }
  
  @PostMapping({"lists"})
  public List<InvoiceDoubt> queryList(@RequestBody InvoiceDoubt invoiceDoubt) {
    return this.service.queryList(invoiceDoubt);
  }
  
  @ApiOperation(value = "通过主键放行/禁止", notes = "根据主键放行/禁")
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "Integer"), @ApiImplicitParam(name = "state", value = "状态 0：未处理  1：已放行 2：已禁止", required = true, paramType = "query", dataType = "Integer")})
  @GetMapping({"/state/{id}"})
  public Integer changeStateById(@PathVariable("id") Long id, @RequestParam String state) {
    InvoiceDoubt doubt = new InvoiceDoubt();
    doubt.setId(id);
    doubt.setState(state);
    doubt.setUpdateTime(new Date());
    return this.service.changeStateById(doubt);
  }
  
  @ApiOperation(value = "通过主键批量放行/禁止", notes = "通过主键批量放行/禁止")
  @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "多条数据的主键", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "state", value = "状态 0：未处理 1：已放行 2：已禁止", required = true, paramType = "query", dataType = "Integer")})
  @GetMapping({"/states/{ids}"})
  public Integer changeStateByIds(@PathVariable("ids") String ids, @RequestParam String state) {
    InvoiceDoubtPoJo doubt = new InvoiceDoubtPoJo();
    doubt.setIds(ids);
    doubt.setState(state);
    doubt.setUpdateTime(new Date());
    return this.service.changeStateByIds(doubt);
  }
}
