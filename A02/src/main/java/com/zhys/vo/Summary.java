package com.zhys.vo;

import lombok.Data;

import java.util.List;

@Data
public class Summary {

    private String month;

    //统计该月份下不同种类的相关发票
    private List<InvoiceStatistical> data;
}
