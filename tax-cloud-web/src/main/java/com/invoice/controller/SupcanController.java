package com.invoice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.invoice.fegin.SupcanServiceFegin;
import com.invoice.model.SupcanReport;
import com.invoice.model.TemplateDataSource;
import com.invoice.po.InvoiceHead;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.common.GsonData;
import com.zhys.common.GsonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("supcan")
@Api(value="报表接口",description="报表接口" )
public class SupcanController {
	
	@Autowired
	private SupcanServiceFegin service;
	
	@PostMapping("page")
	public @ResponseBody Pages<List<SupcanReport>> pages(@RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="report",value="查询条件",required=false) @RequestBody(required=false) SupcanReport report){
		Map<String, Object> map = new HashMap<>();
		return service.pages(report,pageSize,pageNum);
	}
	
	@PostMapping("save")
	public @ResponseBody Integer save(@RequestBody SupcanReport s){
		
		return service.save(s);
	}
    
	@PostMapping("saveTempletData")
    public @ResponseBody Integer saveTempletData(@RequestBody SupcanReport s){
    	
    	return service.saveTempletData(s);
    }
    
    @RequestMapping("readTempletData")
    public @ResponseBody String readTempletData(@RequestParam Integer mid){
    	SupcanReport s = new SupcanReport();
    	s.setMid(mid);
    	String st = service.getTempletData(s);
    	return st;
    }
    
    @RequestMapping("dspage")
	public @ResponseBody Pages<List<TemplateDataSource>> dspages(@RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="source",value="查询条件",required=false) @RequestBody(required=false) TemplateDataSource source){
		return service.dspages(source,pageSize,pageNum);
    }
    
//    @RequestMapping("ds1")
//    public @ResponseBody String getDSJSONData(HttpServletRequest request){
//    	String sql = service.getSql(request);
//    	String s = "";
//    	if(null!=sql&&!sql.trim().isEmpty()){
//    		s = service.getDSJSONData(sql);
//    	}
//    	return s;
//    }
//    
//    @RequestMapping("ds2")
//    public @ResponseBody String getDSJSONData2(HttpServletRequest request){
//    	String sql = service.getSql(request);
//    	String s = "";
//    	if(null!=sql&&!sql.trim().isEmpty()){
//    		s = service.getDSJSONData2(sql);
//    	}
//    	return s;
//    }
}
