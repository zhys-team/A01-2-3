package com.zhys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invoice.model.SupcanReport;
import com.invoice.model.TemplateDataSource;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
@RequestMapping("/supcan")
public interface SupcanService {

	@PostMapping("/pages")
	Pages<List<SupcanReport>> pages(@RequestBody SupcanReport t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);

	@PostMapping("/save")
	Integer save(@RequestBody SupcanReport s);

	@PostMapping("/saveTempletData")
	Integer saveTempletData(@RequestBody SupcanReport s);

	@PostMapping("/dspages")
	Pages<List<TemplateDataSource>> dspages(@RequestBody TemplateDataSource t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);

	@PostMapping("/getTempletData")
	String getTempletData(@RequestBody SupcanReport s);

	@GetMapping("/getDSJSONData")
	String getDSJSONData(String sql);

	@GetMapping("/getSql")
	String getSql(HttpServletRequest request);

	@GetMapping("/getDSJSONData2")
	String getDSJSONData2(String sql);
	
	@PostMapping("/ds1")
     String getDSJSONData(HttpServletRequest request);
    
	@PostMapping("/ds2")
    String getDSJSONData2(HttpServletRequest request);
}
