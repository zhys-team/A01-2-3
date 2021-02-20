package com.invoice.pojo;

import java.util.Date;

import com.lycheeframework.core.model.IPO;

import lombok.Getter;
import lombok.Setter;
/**
 * 进项汇总统计
 * @author 11734
 *
 */
@Getter
@Setter
public  class IncomeCount  implements IPO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String fplx;
	private Integer sl;
	private String dkwsje;
	private String dkje;
	private String kpje; 
	private String taxAttr;
	private String rzState; 
	private Date createTimeStart; 
	private Date createTimeEnd; 
	private String kjdkq;
	
	
}
