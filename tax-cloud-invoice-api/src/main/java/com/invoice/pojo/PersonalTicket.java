package com.invoice.pojo;

import com.lycheeframework.core.model.IPO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class PersonalTicket  implements IPO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Long id; 
	private String fpzl;
	private String kprq;
	private String kpfmc;
	private String kpje;
	private String fpdm; 
	private String fphm;
	private String spfmc; 
	private String spfsbh; 
	private String spfdzdh; 
	private String spfyhzh; 
	private String kpfsbh; 
	private String kpfdzdh; 
	private String kpfyhzh; 
	private String hjje; 
	private String hjse; 
	private String mark;
	/**
	 * 开票内容
	 */
	private String kpnr;
	/**
	 * 累计金额
	 */
	private String ljje;
	

}
