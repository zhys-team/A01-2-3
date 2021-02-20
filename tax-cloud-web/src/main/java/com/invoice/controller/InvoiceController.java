package com.invoice.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.invoice.fegin.InvoiceServiceFegin;
import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.zhys.base.ResponseBase;
import com.zhys.result.ResponseResult;

import springfox.documentation.annotations.ApiIgnore;
@ResponseResult
@Controller("/invoices")
@ApiIgnore
public class InvoiceController {
	
	@Autowired
	private InvoiceServiceFegin memberServiceFegin;
	
	private static final String INDEX = "index";
	private static final String LOGIN = "login";
	
	// 跳转登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return LOGIN;
	}

	
	@RequestMapping(value = "/testCloud", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBase testCloud(HttpServletRequest reqest) {
		com.invoice.po.UserEntity u = new UserEntity();
		u.setPhone("123644");
		return memberServiceFegin.testCloud(u);
	}
	
	
	@RequestMapping(value = "/findUserList", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserEntity> findUserList() {
		return memberServiceFegin.findUserList();
	}
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return INDEX;
	}
	
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseBase save(InvoiceHead t) {
//		return memberServiceFegin.save(t);
//	}
//	
//	@RequestMapping(value = "/queryByEntity", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseBase queryByEntity(InvoiceHead t) {
//		return memberServiceFegin.queryByEntity(t);
//	}
//
//	@RequestMapping(value = "/pages", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseBase pages(InvoiceHead t, EasyPage page) {
//		return memberServiceFegin.pages(t, page);
//	}
//
//	@RequestMapping(value = "/queryList", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseBase queryList(InvoiceHead t) {
//		return memberServiceFegin.queryList(t);
//	}
}
