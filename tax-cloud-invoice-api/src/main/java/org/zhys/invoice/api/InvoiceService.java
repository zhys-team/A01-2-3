package org.zhys.invoice.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.zhys.base.BaseService;
import com.zhys.base.ResponseBase;

public interface InvoiceService  extends BaseService<InvoiceHead,InvoiceHeadPoJo>{
	
	
	@RequestMapping("/findUserList")
	List<UserEntity> findUserList();
	
	
	@RequestMapping("/testCloud")
	ResponseBase testCloud(UserEntity user);
	
	
	

}
