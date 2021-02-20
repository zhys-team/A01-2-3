package com.zhys.service.impl;

import com.zhys.service.InvoiceViewService;
import com.zhys.service.SQLManager;
import com.zhys.util.DateUtils;
import com.zhys.vo.InvoiceCondition;
import com.zhys.vo.InvoiceStatistical;
import com.zhys.vo.Item;
import com.zhys.vo.Summary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class InvoiceViewServiceImpl implements InvoiceViewService {
    @Autowired
    private SQLManager manager;


    /* 张琦   统计每个组织，每个月，不同种类的相关发票 合计  */
    @Override
    public InvoiceCondition getInvoiceSummary(InvoiceCondition invoiceCondition) {
        String monthStart = invoiceCondition.getMonthStart();
        String monthEnd = invoiceCondition.getMonthEnd();
        Integer orgId = invoiceCondition.getOrgId();
        String[] orgIds = null;
        //获取所有组织id
        if (orgId == null) {
            List<String> list = (List<String>) manager.list("invoice_head.queryOrgIds", null);
            orgIds = new String[list.size()];
            list.toArray(orgIds);
        } else {
            orgIds = new String[1];
            orgIds[0] = String.valueOf(orgId);
        }
        //获取固定范围内所有月份
        List<String> months = new ArrayList<String>();
        if (monthStart != null && monthEnd != null) {
            months = DateUtils.getMonths(monthStart, monthEnd);
        }
        List<Item> items = new ArrayList<Item>();
        for (String orgId2 : orgIds) {
            Item item = new Item();
            item.setOrgId(orgId2);
            List<Summary> summary = new ArrayList<Summary>();
            if (months.size() != 0) {
                //查询某一组织某一月份各种类型的税额，含税净金额，未税净金额，发票张数
                for (String month : months) {
                    Summary s = new Summary();
                    String newMonth = month.substring(0,4)+"-"+month.substring(4);   //将yyyyMM类型时间转换为yyyy-MM
                    s.setMonth(newMonth);
                    ArrayList<InvoiceStatistical> data = new ArrayList<>();
                    String type = "";
                    for (int i = 0; i < 4; i++) {
                        InvoiceStatistical invoiceStatistical = new InvoiceStatistical();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("orgId", orgId2);
                        map.put("billGdate", month+"%");    //便于模糊查询
                        map.put("docStatus", "2");
                        //统计内销发票的税额，含税净金额，未税净金额，发票张数
                        if (i == 0) {
                            type = "内销";
                            map.put("invoiceType", "'1','2','4'");
                            map.put("invoiceRed", "");
                        }
                        //统计外销发票的税额，含税净金额，未税净金额，发票张数
                        if (i == 1) {
                            type = "外销";
                            map.put("invoiceType", "'5'");
                            map.put("invoiceRed", "");
                        }
                        //统计红字发票的税额，含税净金额，未税净金额，发票张数
                        if (i == 2) {
                            type = "红字";
                            map.put("invoiceRed", "X");
                        }
                        //统计已开所有发票的税额，含税净金额，未税净金额，发票张数
                        if (i == 3) {
                            type = "合计";
                            map.put("invoiceType", "'1','2','4','5'");
                        }
                        invoiceStatistical  = (InvoiceStatistical) this.manager.query("t_invoice.kphz",map);
                        invoiceStatistical.setType(type);
                        data.add(invoiceStatistical);
                    }
                    s.setData(data);
                    summary.add(s);
                }
                item.setSummary(summary);
            }
            items.add(item);
        }
        invoiceCondition.setItems(items);
        return invoiceCondition;
    }
}
