package com.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.zhys.base.po.RateCode;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.RateCodeServiceFegin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@RestController
@RequestMapping("rateCode")
@Api(value="税收分类编码接口",description="税收分类编码接口" )
public class RateCodeController {

    @Autowired
	private RateCodeServiceFegin service;
    
    
    @ApiOperation(value = "保存税收分类编码",notes="保存税收分类编码")
    @PostMapping("save")
	public  Integer save(@ApiParam(name="rateCode",value="税收分类编码信息",required=true) @RequestBody RateCode rateCode){
		return  service.save(rateCode);
	}

    @ApiOperation(value = "通过主键获取税码详情信息",notes="通过主键获取税码详情信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Long"),
    })
    @GetMapping("info/{id}")
	public RateCode info(@PathVariable("id") Long id,RateCode rateCode){
    	rateCode.setId(id);
		rateCode = service.queryByEntity(rateCode);
		return rateCode;
	}
    
    @ApiOperation(value = "获取所有税码信息",notes="获取所有1税码信息")
    @PostMapping("/queryList")
    public List<RateCode> queryList(@RequestBody RateCode rateCode){
    	return  service.queryList(rateCode);
} 
    /**
     * 通过主键更新状态
     * @param t
     * @return
     */
    @ApiOperation(value = "通过主键删除税码",notes="通过主键删除税码")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Long"),
    })
	@GetMapping("/del/{id}")
	public Integer changeDelStateById(@PathVariable("id") Long id,RateCode rateCode) {
    	rateCode.setId(id);
		  service.changeDelStateById(rateCode);
    	return null;
    }
    
    
}