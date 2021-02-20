package com.invoice.fegin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invoice.model.SupcanReport;
import com.invoice.model.TemplateDataSource;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysUsers;

@Component
@FeignClient(value="invoice",path="/supcan")
public interface SupcanServiceFegin  {	

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

//	@GetMapping("/getDSJSONData")
//	String getDSJSONData(@RequestParam("sql") String sql);
//
//	@GetMapping("/getSql")
//	String getSql(HttpServletRequest request);
//
//	@GetMapping("/getDSJSONData2")
//	String getDSJSONData2(@RequestParam("sql") String sql);
	
	

}
