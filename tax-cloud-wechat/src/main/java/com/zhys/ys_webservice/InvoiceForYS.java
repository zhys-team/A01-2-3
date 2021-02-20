package com.zhys.ys_webservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceForYS {
	private String invoceNum;
	private String invoceCode;
	private String type;
	private String salesParty;
	private String buyer;
	private String amountMoney;
	private String taxRate;
	private String taxMoney;
	private String totalWithTax;
	private String makeDate;
	private String makeUser;
	private String typeInDate;
	private String imageUrl;
	private String status;
	private String invoce_sys_id;
	private String ygdm;

}
