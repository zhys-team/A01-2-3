package com.zhys.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */

@Data 
public class InvoiceCondition {
    private String monthStart;
    private String monthEnd;
    private Integer orgId;
    private List<Item> items;
//    private String startTime;
//    private String endTime;
//    private String orgId;
//    private String custName;
//    private String custIdBill;
//    private String docNum;
//    private String groupStatus;
//    private String goldtaxNumStart;
//    private String goldtaxNumEnd;
//    private String goldtaxCode;
//    private String  billDateStart;
//    private String  billDateEnd;
//    private String  billGdateStart;
//    private String  billGdateEnd;
//
//    private String  billGdateStart1;
//    private String  billGdateEnd1;
//    private String issync;
//    private String invoiceType;
//    private long rowCount;
//    @NotNull(message = "页码不能为空")
//    private Integer pageSize;
//    @NotNull(message = "页码不能为空")
//    private Integer pageNum;
//
//    private List<InvoiceSplitLine> invoices;
    
}
