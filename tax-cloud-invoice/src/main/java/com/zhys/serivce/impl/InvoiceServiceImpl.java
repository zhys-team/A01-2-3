package com.zhys.serivce.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import com.zhys.service.InvoiceService;
import com.zhys.service.SQLManager;
import com.github.pagehelper.PageHelper;
import com.invoice.po.InvoiceHead;
import com.invoice.po.UserEntity;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.BaseApiService;
import com.zhys.base.ResponseBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InvoiceServiceImpl  implements InvoiceService {
//	@Autowired
//	private InvoiceDao memberDao;

	@Autowired
	@Qualifier("sqlManager")
	private SQLManager manager;
	
	@Override
	public List<UserEntity> findUserList() {
		PageHelper.startPage(3, 10);
		//List<UserEntity> users = memberDao.selectUserList();
		List<UserEntity> users = (List<UserEntity>) manager.list("com.zhys.dao.InvoiceDao.selectUserList");
		return users;
	}

	@Override
	public Integer save(InvoiceHead t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceHead queryByEntity(InvoiceHead t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<InvoiceHead> queryList(InvoiceHead t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseBase testCloud(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pages<List<InvoiceHead>> pages(InvoiceHead t, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pages<List<InvoiceHead>> pagesByPojo(InvoiceHeadPoJo e, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer changeDelStateById(InvoiceHead t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer changeDelStateByIds(InvoiceHeadPoJo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoiceHead> queryListByPoJo(InvoiceHeadPoJo e) {
		// TODO Auto-generated method stub
		return null;
	}

	





	
}
