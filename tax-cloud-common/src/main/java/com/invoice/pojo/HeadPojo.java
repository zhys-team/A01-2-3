package com.invoice.pojo;

import com.lycheeframework.core.model.IPO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadPojo implements IPO {

    private static final long serialVersionUID = 1L;

    private String client;
    private String stno;
    private String ssta;
    private String tsta;
    private String bukrs;
    private String bname;
    private String plant;
    private String taxno;
    private String trate;
    private String vender;
    private String vname;
    private String fdate;
    private String tdate;
    private String wsje;
    private String hsje;

}
