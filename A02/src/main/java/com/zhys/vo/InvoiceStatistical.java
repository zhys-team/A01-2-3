package com.zhys.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceStatistical {
    //发票类型   内销、外销、红字、合计
    private String type;
    //含税金额
    private BigDecimal hsj = new BigDecimal(0.00);
    //未税金额
    private BigDecimal wsj= new BigDecimal(0.00);
    //税额
    private BigDecimal sej= new BigDecimal(0.00);
    //发票张数
    private Long pieces;
}
