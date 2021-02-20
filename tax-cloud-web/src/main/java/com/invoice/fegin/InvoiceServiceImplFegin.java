package com.invoice.fegin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;
@Component
public class InvoiceServiceImplFegin implements InvoiceServiceFegin{


	@Override
	public ResponseBase testCloud(UserEntity u) {
		ResponseBase rb = new ResponseBase();
		rb.setData("应该返回默认值或缓存");
		rb.setMsg("容灾信息");
		rb.setRtnCode(500);
		return rb;
	}

	@Override
	public List<UserEntity> findUserList() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Integer save(InvoiceHead t) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public InvoiceHead queryByEntity(InvoiceHead t) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Pages<List<InvoiceHead>> pages(InvoiceHead t, EasyPage page) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<InvoiceHead> queryList(InvoiceHead t) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	


}
