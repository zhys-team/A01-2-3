/**
 * Copyright 2017-2020 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2020-03-19
 */
package com.zhys.po;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.invoice.po.InvoiceBody;
import com.lycheeframework.core.model.IPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="TzsysEasInvoicereceive",description="")
public class TzsysEasInvoicereceive implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="invoiceId")
	private Long invoiceId;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="fpzl")
	private String fpzl;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="fpdm")
	private String fpdm;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="fphm")
	private String fphm;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="kprq")
	private String kprq;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="jym")
	private String jym;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="hsje")
	private String hsje;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="wsje")
	private String wsje;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="se")
	private String se;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="rzState")
	private String rzState;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="bxState")
	private String bxState;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="bxTime")
	private Date bxTime;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="fylb")
	private String fylb;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="ywms")
	private String ywms;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="userNo")
	private String userNo;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="businessId")
	private String businessId;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="dept")
	private String dept;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="eaUserNo")
	private String eaUserNo;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="imgUrl")
	private String imgUrl;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="docMaker")
	private String docMaker;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="docType")
	private String docType;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="docNo")
	private String docNo;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="docName")
	private String docName;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="change")
	private String change;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="salesParty")
	private String salesParty;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="buyer")
	private String buyer;

	/**
	 * $pro.annotation
	 */
	 @ApiModelProperty(value="$pro.annotation",name="isSyn")
	private String isSyn;
	 
	 //扫票时间
	 private Date spTime;
	 
     private String lock;
     
     private String dcState;
     
     private List<InvoiceBody> bodys;

}