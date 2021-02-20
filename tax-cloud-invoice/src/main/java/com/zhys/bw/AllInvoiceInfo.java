package com.zhys.bw;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllInvoiceInfo {
	 
	private String invoiceCode;
    private String invoiceNumber;
    private String billingDate;
    private String invoiceType;
    private String purchaserTaxNo;
    private String purchaserName;
    private String salesTaxNo;
    private String salesName;
    private String amountTax;
    private String totalAmount;
    private String state;
    private String machineCode;
    private String checkCode;
    private String checkTime;
    
    private String purchaserAddressPhone;
    private String purchaserBank;
    private String salesAddressPhone;
    private String salesBank;
    private String totalTax;
    private String remarks;
    private String refinedOilState;
    
    private String supplySign;
    
    private String payee;
    
    
    private String tollSign;
    
    private String receiverTaxNo;
    private String receiverName;
    private String senderTaxNo;
    private String senderName;
    private String goodsInfo;
    private String transportRoute;
    private String taxRate;
    private String taxPanelNo;
    private String vehicleTypeNo;
    private String tonnage;
    private String taxAuthorityNo;
    private String taxAuthorityName;
    
    private String purchaserAddress;
    private String purchaserPhone;
    private String carNumber;
    private String registration;
    private String goodType;
    private String vehicleNo;
    private String brandModel;
    private String transferVehicle;
    private String salesAddress;
    private String salesPhone;
    private String auctionUnit;
    private String auctionUnitAddress;
    private String auctionUnitTaxNo;
    private String auctionBankAndNo;
    private String auctionUnitPhone;
    private String usedCarMarket;
    private String usedCarMarketTaxNo;
    private String usedCarMarketAddress;
    private String usedCarBankAndNo;
    private String usedCarPhone;
    
    private String idCardNo;
    private String vehicleType;
    private String originPlace;
    private String certificateNo;
    private String inspectionListNo;
    private String engineNo;
    private String importCertificateNo;
    private String salesBankNo;
    private String paymentVoucherNo;
    private String passengersLimited;
    private String specialPolicySign;
    private String realTaxRate;
    private String realTax;
    
    private String message;
    
    private List<BaseItem> items;
}
