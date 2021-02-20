/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-05-29
 */
package com.invoice.po;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value="InvoiceHead",description="发票主信息")
public class InvoiceHead implements IPO {

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
	 * 发票种类  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票8：其他
	 */
	 @ApiModelProperty(value="发票种类  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票8：其他",name="fpzl")
	private String fpzl;

	/**
	 * 发票类型  0:蓝票  1：红票
	 */
	 @ApiModelProperty(value="发票类型  0:蓝票  1：红票",name="fplx")
	private String fplx;

	/**
	 * 发票代码
	 */
	 @ApiModelProperty(value="发票代码",name="fpdm")
	private String fpdm;

	/**
	 * 发票号码
	 */
	 @ApiModelProperty(value="发票号码",name="fphm")
	private String fphm;

	/**
	 * 开票日期
	 */
	 @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="开票日期",name="kprq")
	private Date kprq;

	/**
	 * 校验码
	 */
	 @ApiModelProperty(value="校验码",name="jym")
	private String jym;

	/**
	 * 机器编号
	 */
	 @ApiModelProperty(value="机器编号",name="jqbh")
	private String jqbh;

	/**
	 * 受票方名称
	 */
	 @ApiModelProperty(value="受票方名称",name="spfmc")
	private String spfmc;

	/**
	 * 受票方识别号
	 */
	 @ApiModelProperty(value="受票方识别号",name="spfsbh")
	private String spfsbh;

	/**
	 * 受票方地址及电话
	 */
	 @ApiModelProperty(value="受票方地址及电话",name="spfdzdh")
	private String spfdzdh;

	/**
	 * 受票方银行及账号
	 */
	 @ApiModelProperty(value="受票方银行及账号",name="spfyhzh")
	private String spfyhzh;

	/**
	 * 开票方名称
	 */
	 @ApiModelProperty(value="开票方名称",name="kpfmc")
	private String kpfmc;

	/**
	 * 开票方识别号
	 */
	 @ApiModelProperty(value="开票方识别号",name="kpfsbh")
	private String kpfsbh;

	/**
	 * 开票方地址及电话
	 */
	 @ApiModelProperty(value="开票方地址及电话",name="kpfdzdh")
	private String kpfdzdh;

	/**
	 * 开票方银行及账号
	 */
	 @ApiModelProperty(value="开票方银行及账号",name="kpfyhzh")
	private String kpfyhzh;

	/**
	 * 合计金额
	 */
	 @ApiModelProperty(value="合计金额",name="hjje")
	private String hjje;

	/**
	 * 合计税额
	 */
	 @ApiModelProperty(value="合计税额",name="hjse")
	private String hjse;

	/**
	 * 价税合计
	 */
	 @ApiModelProperty(value="价税合计",name="kpje")
	private String kpje;

	/**
	 * 备注
	 */
	 @ApiModelProperty(value="备注",name="mark")
	private String mark;

	/**
	 * 密文
	 */
	 @ApiModelProperty(value="密文",name="secret")
	private String secret;

	/**
	 * 作废标志 0：正常 1：作废
	 */
	 @ApiModelProperty(value="作废标志 0：正常 1：作废",name="zfbz")
	private String zfbz;

	/**
	 * 清单标志 0：不存在清单 1：存在清单
	 */
	 @ApiModelProperty(value="清单标志 0：不存在清单 1：存在清单",name="qdbz")
	private String qdbz;

	/**
	 * 查验次数
	 */
	 @ApiModelProperty(value="查验次数",name="cycs")
	private Integer cycs;

	/**
	 * 组织id
	 */
	 @ApiModelProperty(value="组织id",name="orgId")
	private String orgId;

	/**
	 * 组织名称
	 */
	 @ApiModelProperty(value="组织名称",name="orgName")
	private String orgName;

	/**
	 * 打印状态 默认为0:未打印 1：已打印
	 */
	 @ApiModelProperty(value="打印状态 默认为0:未打印 1：已打印",name="dyState")
	private String dyState;

	/**
	 * 导出状态 默认为0 :未导出  1：已导出
	 */
	 @ApiModelProperty(value="导出状态 默认为0 :未导出  1：已导出",name="outState")
	private String outState;

	/**
	 * 采集时间
	 */
	 @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="采集时间",name="createTime")
	private Date createTime;

	/**
	 * 复检结果 0:正常 1：异常
	 */
	 @ApiModelProperty(value="复检结果 0:正常 1：异常",name="fjState")
	private String fjState;

	/**
	 * 进项类型
	 */
	 @ApiModelProperty(value="进项类型",name="jxType")
	private String jxType;

	/**
	 * 认证日期
	 */
	 @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="认证日期",name="rzrq")
	private Date rzrq;

	/**
	 * 认证状态 0：未认证 1：已认证
	 */
	 @ApiModelProperty(value="认证状态 0：未认证 1：已认证",name="rzState")
	private String rzState;

	/**
	 * 抵扣日期
	 */
	 @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	 @ApiModelProperty(value="抵扣日期",name="dcrq")
	private Date dcrq;

	/**
	 * 抵扣状态 1：不抵扣2：已抵扣
	 */
	 @ApiModelProperty(value="抵扣状态 1：不抵扣2：已抵扣",name="dcState")
	private Integer dcState;

	/**
	 * 单据类型 1 ：正常2 ：红冲
	 */
	 @ApiModelProperty(value="单据类型 1 ：正常2 ：红冲",name="djlx")
	private String djlx;

	/**
	 * 收票状态 10：未收票20：已收票
	 */
	 @ApiModelProperty(value="收票状态 10：未收票20：已收票",name="spState")
	private String spState;

	/**
	 * 创建人
	 */
	 @ApiModelProperty(value="创建人",name="createMan")
	private String createMan;

	/**
	 * 最后修改时间
	 */
	 @ApiModelProperty(value="最后修改时间",name="updateTime")
	private Date updateTime;

	/**
	 * 最后修改人
	 */
	 @ApiModelProperty(value="最后修改人",name="updateMan")
	private String updateMan;

	/**
	 * 采购业务员
	 */
	 @ApiModelProperty(value="采购业务员",name="procMan")
	private String procMan;

	/**
	 * 凭证号
	 */
	 @ApiModelProperty(value="凭证号",name="voucherNo")
	private String voucherNo;

	/**
	 * 发票来源  0 系统同步、1 线上查验、2 线下查验
	 */
	 @ApiModelProperty(value="发票来源  0 系统同步、1 线上查验、2 线下查验",name="fply")
	private String fply;

	 /**
	 *  删除标记 0：未删除  1：已删除
	 */
	 @ApiModelProperty(value=" 删除标记 0：未删除  1：已删除",name="delState")
	private String delState;
	 
	/**
	 * 0:进项  1：销项
	 */
	 @ApiModelProperty(value="0:进项  1：销项",name="inOrOut")
	private String inOrOut;

	/**
	 * 核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0
	 */
	 @ApiModelProperty(value="核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0",name="businessType")
	private String businessType;
	 
	 /**
		 * openid
		 */
		 @ApiModelProperty(value="openId",name="openId")
		private String openId;
	
}