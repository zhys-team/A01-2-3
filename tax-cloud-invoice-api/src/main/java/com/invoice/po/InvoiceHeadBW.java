package com.invoice.po;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceHeadBW {

	private String BillingDate;
	private Double AmountTax;
	private String AdministrativeDivisionNo;
	private String SalesBank;
	private String PurchaserTaxNo;
	private String AmountTaxCN;
	private String Remarks;
	private String PurchaserBank;
	private String InvoiceNumber;
	private String AdministrativeDivisionName;
	private String SalesAddressPhone;
	private String PurchaserName;
	private String SalesTaxNo;
	private String SalesName;
	private String InvoiceCode;
	private String State;
	private String MachineCode;
	private String PurchaserAddressPhone;
	private Double TotalTax;
	private String TotalAmount;
	private String CheckCode;
	private List<InvoiceDetail> InvoiceList;
	private String InvoiceType;
	private String Message;
	private String Success;
	private Integer Code;
	
}
