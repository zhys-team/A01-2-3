package com.zhys.bw;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseItem {
	private String rowNo;
    private String commodityName;
    private String amount;
    private String taxClassifyCode;
    
    private String specificationModel;
    private String unit;
    private String quantity;
    private String unitPrice;
    private String taxRate;
    private String tax;
    private String specialPolicySign;
    private String realTaxRate;
    private String realTax;
    
    
    private String carNumber;
    private String goodType;
    private String startDate;
    private String endDate;
    
    

}
