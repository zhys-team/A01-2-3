package com.invoice.fegin;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.zhys.base.ResponseBase;

@Component

//@FeignClient(name="invoice",fallback = InvoiceServiceImplFegin.class)
@FeignClient(name="invoice")
//public interface MemberServiceFegin extends MemberService {
public interface InvoiceServiceFegin  {	
	@RequestMapping("/findUserList")
	List<UserEntity> findUserList();

	@RequestMapping("/testCloud")
	ResponseBase testCloud(UserEntity user);
	
	
//	
//    /**
//     * 新增
//     * @param t
//     * @return
//     */
//	@RequestMapping("/save")
//	ResponseBase save(InvoiceHead t);
//
//
//    
//    /**
//     * 查询单个实体
//     * @return
//     */
//	@RequestMapping("/queryByEntity")
//	ResponseBase queryByEntity(InvoiceHead t);
//
//    /**
//     * 查询带分页
//     * @param t page
//     * @return
//     */
//	@RequestMapping("/pages")
//	ResponseBase pages(InvoiceHead t, EasyPage page);
//	
//	
//	/**
//     * 查询
//     * @param t
//     * @return
//     */
//	@RequestMapping("/queryList")
//	ResponseBase queryList(InvoiceHead t);
}
