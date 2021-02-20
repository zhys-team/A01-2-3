package com.zhys.serivce.impl;

import com.invoice.po.Head;
import com.invoice.po.InvoiceHead;
import com.invoice.pojo.HeadPojo;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.service.HeadService;
import com.zhys.service.SQLManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HeadServiceImpl implements HeadService {

    @Autowired
    private SQLManager manager;

    @Override
    public Pages<List<HeadPojo>> pageForMobile(String openId, Integer pageSize, Integer pageNum, String bxState) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        Head hd = new Head();
        Pages<List<HeadPojo>> ps = (Pages<List<HeadPojo>>) manager.pages("head.pageForMobile", hd, page);

        return ps;
    }

    @Override
    public Object save(Head head) {
        return null;
    }

    @Override
    public Head queryByEntity(Head head) {
        return null;
    }

    @Override
    public Pages<List<Head>> pages(Head head, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public Pages<List<Head>> pagesByPojo(InvoiceHeadPoJo invoiceHeadPoJo, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<Head> queryList(Head head) {
        return null;
    }

    @Override
    public List<Head> queryListByPoJo(InvoiceHeadPoJo invoiceHeadPoJo) {
        return null;
    }

    @Override
    public Integer changeDelStateById(Head head) {
        return null;
    }

    @Override
    public Integer changeDelStateByIds(InvoiceHeadPoJo t) {
        return null;
    }
}
