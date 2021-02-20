package com.zhys.service;

import com.invoice.po.Head;
import com.invoice.pojo.HeadPojo;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.BaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping({"/heads"})
public interface HeadService extends BaseService<Head, InvoiceHeadPoJo> {
    @GetMapping({"/pageForMobile"})
    Pages<List<HeadPojo>> pageForMobile(@RequestParam("openId") String paramString1, @RequestParam("pageSize") Integer paramInteger1, @RequestParam("pageNum") Integer paramInteger2, @RequestParam("bxState") String paramString2);

}
