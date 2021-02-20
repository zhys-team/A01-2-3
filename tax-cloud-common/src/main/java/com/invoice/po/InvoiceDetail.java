package com.invoice.po;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetail {

	private String Amount;
	private String RowNo;
	private String Quantity;
	private String Tax;
	private String TaxRate;
	private BigDecimal UnitPrice=BigDecimal.ZERO;
	private String SpecificationModel;
	private String CommodityName;
	private String Unit;
}
