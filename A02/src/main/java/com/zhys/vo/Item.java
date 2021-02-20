package com.zhys.vo;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private String orgId;

    //统计该组织号下各月份的不同发票类型的相关数据
    private List<Summary> summary;
}
