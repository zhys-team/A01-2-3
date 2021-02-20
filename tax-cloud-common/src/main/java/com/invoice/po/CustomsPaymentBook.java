/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-10-30
 */
package com.invoice.po;

import java.sql.Date;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.lycheeframework.core.model.IPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value="CustomsPaymentBook",description="海关缴款书")
public class CustomsPaymentBook implements IPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	 @ApiModelProperty(value=" 主键 id",name="id")
	private Long id;

	/**
	 * 专用缴款书号码
	 */
	 @ApiModelProperty(value="专用缴款书号码",name="billingNo")
	private String billingNo;

	/**
	 * 填发日期
	 */
	 @ApiModelProperty(value="填发日期",name="billingDate")
	private Date billingDate;

	/**
	 * 缴款单位
	 */
	 @ApiModelProperty(value="缴款单位",name="paymentOrg")
	private String paymentOrg;

	/**
	 * 税款金额合计
	 */
	 @ApiModelProperty(value="税款金额合计",name="totalTax")
	private BigDecimal totalTax;

	/**
	 * 海关名称
	 */
	 @ApiModelProperty(value="海关名称",name="customsName")
	private String customsName;

	/**
	 * 海关代码
	 */
	 @ApiModelProperty(value="海关代码",name="customsCode")
	private String customsCode;

	/**
	 * 申请单位编号
	 */
	 @ApiModelProperty(value="申请单位编号",name="applyOrgNo")
	private String applyOrgNo;

	/**
	 * 报关单编号
	 */
	 @ApiModelProperty(value="报关单编号",name="customsNo")
	private String customsNo;

	/**
	 * 合同（批文）号
	 */
	 @ApiModelProperty(value="合同（批文）号",name="contractNo")
	private String contractNo;

	/**
	 * 运输工具号
	 */
	 @ApiModelProperty(value="运输工具号",name="transportNo")
	private String transportNo;

	/**
	 * 货主单位
	 */
	 @ApiModelProperty(value="货主单位",name="cargoOwner")
	private String cargoOwner;

	/**
	 * 经营单位
	 */
	 @ApiModelProperty(value="经营单位",name="operationEntity")
	private String operationEntity;

	/**
	 * 电子支付缴款期限
	 */
	 @ApiModelProperty(value="电子支付缴款期限",name="paymentPeriod")
	private String paymentPeriod;

	/**
	 * 提/装货单号
	 */
	 @ApiModelProperty(value="提/装货单号",name="loadingNo")
	private String loadingNo;

	/**
	 * 提交人
	 */
	 @ApiModelProperty(value="提交人",name="submitter")
	private String submitter;

	/**
	 * 提交日期
	 */
	 @ApiModelProperty(value="提交日期",name="submitDate")
	private String submitDate;

	/**
	 * 业务员
	 */
	 @ApiModelProperty(value="业务员",name="businessMan")
	private String businessMan;

	/**
	 * 稽核状态 1：未报送 2:待稽核 3：相符 4：不符 5：滞留 6：缺联
	 */
	 @ApiModelProperty(value="稽核状态 1：未报送 2:待稽核 3：相符 4：不符 5：滞留 6：缺联",name="checkStatus")
	private String checkStatus;

	/**
	 * 入账状态 1：未记账 2：已记账
	 */
	 @ApiModelProperty(value="入账状态 1：未记账 2：已记账",name="bookStatus")
	private String bookStatus;

	/**
	 * 备注
	 */
	 @ApiModelProperty(value="备注",name="mark")
	private String mark;

	/**
	 * 预算级次
	 */
	 @ApiModelProperty(value="预算级次",name="budgetaryLevels")
	private String budgetaryLevels;

	/**
	 * 账号
	 */
	 @ApiModelProperty(value="账号",name="paymentAccount")
	private String paymentAccount;

	/**
	 * 开户银行
	 */
	 @ApiModelProperty(value="开户银行",name="paymentBank")
	private String paymentBank;

	/**
	 * 收入机关
	 */
	 @ApiModelProperty(value="收入机关",name="revenueOrg")
	private String revenueOrg;

	/**
	 * 收款国库
	 */
	 @ApiModelProperty(value="收款国库",name="skgk")
	private String skgk;

	/**
	 * 文件路径
	 */
	 @ApiModelProperty(value="文件路径",name="filePath")
	private String filePath;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="orgId")
	private String orgId;

	/**
	 * 1:未删除   2：已删除
	 */
	 @ApiModelProperty(value="1:未删除   2：已删除",name="delState")
	private String delState;

	/**
	 * 完税价格
	 */
	 @ApiModelProperty(value="完税价格",name="totalPrice")
	private String totalPrice;

	/**
	 * 收入系统
	 */
	 @ApiModelProperty(value="收入系统",name="incomeSystem")
	private String incomeSystem;

	/**
	 * 创建时间
	 */
	 @ApiModelProperty(value="创建时间",name="createTime")
	private Date createTime;


}