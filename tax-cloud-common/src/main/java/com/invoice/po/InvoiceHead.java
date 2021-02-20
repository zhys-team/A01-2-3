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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lycheeframework.core.model.IPO;
import com.zhys.excel.ExcelCell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 11734
 *
 */
@Getter
@Setter
@ApiModel(value = "InvoiceHead", description = "发票主信息")
public class InvoiceHead implements IPO {

    public InvoiceHead() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @ApiModelProperty(value = " 主键 id", name = "id")
    private Long id;

    /**
     * 发票种类 0:蓝票 1：红票
     */
    @ExcelCell(name = "发票种类")
    @ApiModelProperty(value = "发票种类 0:蓝票  1：红票", name = "fpzl")
    private String fpzl;

    /**
     * 发票类型
     * 0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票
     * 9火车票、10机票、11公交票、12过路过桥、13罚款、14财政票据15：其他
     */
    @ExcelCell(name = "发票类型")
    @ApiModelProperty(value = "发票类型  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票 9火车票、10机票、11公交票、12过路过桥、13罚款、14财政票据15：其他 ", name = "fplx")
    private String fplx;

    /**
     * 发票代码
     */
    @ExcelCell(name = "发票代码")
    @ApiModelProperty(value = "发票代码", name = "fpdm")
    private String fpdm;

    /**
     * 发票号码
     */
    @ExcelCell(name = "发票号码")
    @ApiModelProperty(value = "发票号码", name = "fphm")
    private String fphm;

    /**
     * 开票日期
     */
    @ExcelCell(name = "开票日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开票日期", name = "kprq")
    private Date kprq;

    /**
     * 校验码
     */
    @ApiModelProperty(value = "校验码", name = "jym")
    private String jym;

    /**
     * 机器编号
     */
    @ApiModelProperty(value = "机器编号", name = "jqbh")
    private String jqbh;

    /**
     * 受票方名称
     */
    @ApiModelProperty(value = "受票方名称", name = "spfmc")
    private String spfmc;

    /**
     * 受票方识别号
     */
    @ApiModelProperty(value = "受票方识别号", name = "spfsbh")
    private String spfsbh;

    /**
     * 受票方地址及电话
     */
    @ApiModelProperty(value = "受票方地址及电话", name = "spfdzdh")
    private String spfdzdh;

    /**
     * 受票方银行及账号
     */
    @ApiModelProperty(value = "受票方银行及账号", name = "spfyhzh")
    private String spfyhzh;

    /**
     * 开票方名称
     */
    @ApiModelProperty(value = "开票方名称", name = "kpfmc")
    private String kpfmc;

    /**
     * 开票方识别号
     */
    @ApiModelProperty(value = "开票方识别号", name = "kpfsbh")
    private String kpfsbh;

    /**
     * 开票方地址及电话
     */
    @ApiModelProperty(value = "开票方地址及电话", name = "kpfdzdh")
    private String kpfdzdh;

    /**
     * 开票方银行及账号
     */
    @ApiModelProperty(value = "开票方银行及账号", name = "kpfyhzh")
    private String kpfyhzh;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额", name = "hjje")
    private String hjje;

    /**
     * 合计税额
     */
    @ApiModelProperty(value = "合计税额", name = "hjse")
    private String hjse;

    /**
     * 价税合计
     */
    @ApiModelProperty(value = "价税合计", name = "kpje")
    private String kpje;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "mark")
    private String mark;

    /**
     * 密文
     */
    @ApiModelProperty(value = "密文", name = "secret")
    private String secret;

    /**
     * 作废标志 0：正常 1：作废
     */
    @ApiModelProperty(value = "作废标志 0：正常 1：作废", name = "zfbz")
    private String zfbz;

    /**
     * 清单标志 0：不存在清单 1：存在清单
     */
    @ApiModelProperty(value = "清单标志 0：不存在清单 1：存在清单", name = "qdbz")
    private String qdbz;

    /**
     * 修改标志 0：已修改  1：未修改
     */
    @ApiModelProperty(value = "修改标志 0：已修改  1：未修改", name = "isChange")
    private String isChange;

    /**
     * 查验次数
     */
    @ApiModelProperty(value = "查验次数", name = "cycs")
    private Integer cycs;

    /**
     * 受票组织id
     */
    @ApiModelProperty(value = "受票组织id", name = "orgId")
    private String orgId;

    /**
     * 受票组织名称
     */
    @ApiModelProperty(value = "受票组织名称", name = "orgName")
    private String orgName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id", name = "deptId")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", name = "deptName")
    private String deptName;

    /**
     * 打印状态 默认为0:未打印 1：已打印
     */
    @ApiModelProperty(value = "打印状态 默认为0:未打印 1：已打印", name = "dyState")
    private String dyState;

    /**
     * 导出状态 默认为0 :未导出 1：已导出
     */
    @ApiModelProperty(value = "导出状态 默认为0 :未导出  1：已导出", name = "outState")
    private String outState;

    /**
     * 采集时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "采集时间", name = "createTime")
    private Date createTime;

    /**
     * 复检结果 0:正常 1：异常
     */
    @ApiModelProperty(value = "复检结果 0:正常 1：异常", name = "fjState")
    private String fjState;

    /**
     * 复检内容
     */
    @ApiModelProperty(value = "复检内容", name = "fjContent")
    private String fjContent;

    /**
     * 进项类型
     */
    @ApiModelProperty(value = "进项类型", name = "jxType")
    private String jxType;

    /**
     * 认证日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "认证日期:财务的认证日期", name = "rzrq")
    private Date rzrq;

    /**
     * 认证状态  1：初始状态 2：待认证 3：已认证
     * 模块	请求参数	回调参数	描述
     1、客运类发票申报（批量）	报销状态（已报销）、未申报、发票类型：客运发票	返回是否成功即可，请求成功之后需要记录下会计抵扣期，以及将rzState标记为已申报
     2、客运类发票申报（全部）	报销状态（已报销）、未申报、发票类型：客运发票	返回是否成功即可，请求成功之后需要记录下会计抵扣期，以及将rzState标记为已申报	与1不同的地方是，用户点击全部申报，将历史数据中，已报销、未申报的客运发票的rzState标记为已申报
     3、发票转出	时间区间、报销状态（已报销）、未认证	已经认证的发票 点击转出。标记为已转出发票
     4、抵扣汇总表	时间区间、报销状态（已报销）、未认证	"1、认证的进项税额。2、申报的进项税额。3、转出的进项税额、当期抵扣税额（认证+申报-转出）

     "

     */
    @ApiModelProperty(value = "1：初始状态 2：待认证  2.5 :已提交认证 3：已认证/已申报  4：已转出", name = "rzState")
    private String rzState;

    /**
     * 抵扣日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "抵扣日期", name = "dcrq")
    private Date dcrq;

    @ApiModelProperty(value = "抵扣金额", name = "dkje")
    private BigDecimal dkje;

    @ApiModelProperty(value = "可抵扣未税金额", name = "dkwsje")
    private BigDecimal dkwsje;

    /**
     * 抵扣状态 1：可抵扣    2：不可抵扣
     */
    @ApiModelProperty(value = "抵扣状态 1：可抵扣2：不可抵扣", name = "dcState")
    private Integer dcState;

    /**
     * 单据类型 1 ：正常2 ：红冲
     */
    @ApiModelProperty(value = "单据类型 1 ：正常2 ：红冲", name = "djlx")
    private String djlx;

    /**
     * 收票状态 10：未收票20：已收票
     */
    @ApiModelProperty(value = "收票状态 10：未收票20：已收票", name = "spState")
    private String spState;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createMan")
    private String createMan;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间", name = "updateTime")
    private Date updateTime;

    /**
     * 最后修改人
     */
    @ApiModelProperty(value = "最后修改人", name = "updateMan")
    private String updateMan;

    /**
     * 采购业务员
     */
    @ApiModelProperty(value = "采购业务员", name = "procMan")
    private String procMan;

    /**
     * 凭证号
     */
    @ApiModelProperty(value = "凭证号", name = "voucherNo")
    private String voucherNo;

    /**
     * 发票来源 0 系统同步、1 线上查验、2 线下查验
     */
    @ApiModelProperty(value = "发票来源  0 系统同步、1 线上查验、2 线下查验", name = "fply")
    private String fply;

    /**
     * 删除标记 0：未删除 1：已删除
     */
    @ApiModelProperty(value = " 删除标记 0：未删除  1：已删除", name = "delState")
    private String delState;

    /**
     * 0:进项 1：销项
     */
    @ApiModelProperty(value = "0:进项  1：销项", name = "inOrOut")
    private String inOrOut;

    /**
     * 核算要素 ，跟进销绑定 进（0：成本 1：费用） 销（0：主营收入 1：其他收入） 默认为0
     */
    @ApiModelProperty(value = "核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0", name = "businessType")
    private String businessType;

    /**
     * openid
     */
    @ApiModelProperty(value = "openId", name = "openId")
    private String openId;

    /**
     * 记账状态 0:未记账 1：已记账
     */
    @ApiModelProperty(value = "记账状态 0:未记账  1：已记账", name = "jzState")
    private String jzState;

    /**
     * 报销状态 0:未报销 1：已报销
     */
    @ApiModelProperty(value = "报销状态   0:未报销  1：已报销", name = "bxState")
    private String bxState;

    /**
     * 支付状态 0:未支付 1：已支付
     */
    @ApiModelProperty(value = "支付状态  0:未支付  1：已支付", name = "zfState")
    private String zfState;

    /**
     * 税务属性 1：认证抵扣 2：申报抵扣 3：非税抵扣 4：分期抵扣 5：进口退税 6：销项抵扣
     */
    @ApiModelProperty(value = "税务属性  1：认证抵扣 2：申报抵扣 3：非税抵扣 4：分期抵扣 5：进口退税 6：销项抵扣", name = "taxAttr")
    private String taxAttr;

    /**
     * 电票pdf
     */
    @ApiModelProperty(value = "电票pdf", name = "pdfUrl")
    private String pdfUrl;

    private List<InvoiceBody> bodys;

    /**
     * 发票大类  1：增值税发票 2：客运发票 3：不动产台账 4：农产品采购 5：海关缴纳书
     */
    @ApiModelProperty(value = "票据类型  1：增值税发票 2：客运发票 3：不动产台账 4：农产品采购 5：海关缴纳书", name = "specialType")
    private String specialType;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private Date startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private Date endDate;

    /**
     * 出发地
     */
    @ApiModelProperty(value = "出发地", name = "startAddr")
    private String startAddr;

    /**
     * 目的地
     */
    @ApiModelProperty(value = "目的地", name = "endAddr")
    private String endAddr;

    /**
     * 含税金额
     */
    @ApiModelProperty(value = "含税金额", name = "amount")
    private String amount;

    /**
     * 交通工具
     */
    @ApiModelProperty(value = "交通工具", name = "jtgj")
    private String jtgj;

    /**
     * 业务事项
     */
    @ApiModelProperty(value = "业务事项", name = "ywsx")
    private String ywsx;

    /**
     * 张数
     */
    @ApiModelProperty(value = "张数", name = "num")
    private Integer num;

    /**
     * 座位类别
     */
    @ApiModelProperty(value = "座位类别 ", name = "seatCategory")
    private String seatCategory;

    /**
     * 明细事项
     */
    @ApiModelProperty(value = "明细事项", name = "mxsx")
    private String mxsx;

    /**
     * 乘车人
     */
    @ApiModelProperty(value = "乘车人", name = "passenger")
    private String passenger;
    /**
     * 车次
     */
    @ApiModelProperty(value = "车次", name = "trainNum")
    private String trainNum;
    /**
     * 票据id
     */
    @ApiModelProperty(value = "票据id", name = "ticketId")
    private String ticketId;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "乘车时间/发生时间", name = "rideTime")
    private String rideTime;


    private String orgIds;


    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "来源业务系统", name = "lyywxt")
    private String lyywxt;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "业务系统单据号", name = "ywxtbh")
    private String ywxtbh;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "累计转出税额", name = "ljzcse")
    private String ljzcse;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "一次性全额抵扣", name = "ycxqedk")
    private String ycxqedk;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "是否比例转出 1:是  2：否", name = "sfblzc")
    private String sfblzc;
    /**
     * 乘车时间/发生时间
     */
    @ApiModelProperty(value = "计税方式  1：一般计税 2：简易计税", name = "jsfs")
    private String jsfs;


    /**
     * 机动车销售统一发票:机打代码
     */
    @ApiModelProperty(value = "机动车销售统一发票:机打代码", name = "machineCode")
    private String machineCode;

    /**
     * 机动车销售统一发票:机打号码
     */
    @ApiModelProperty(value = "机动车销售统一发票:机打号码", name = "machineNumber")
    private String machineNumber;

    /**
     * 机动车销售统一发票:主管税务机关
     */
    @ApiModelProperty(value = "机动车销售统一发票:主管税务机关", name = "taxAuthorities")
    private String taxAuthorities;

    /**
     * 机动车销售统一发票:主管税务机关代码
     */
    @ApiModelProperty(value = "机动车销售统一发票:主管税务机关代码", name = "taxAuthoritiesCode")
    private String taxAuthoritiesCode;

    /**
     * 机动车销售统一发票:车架号/车辆识别代码
     */
    @ApiModelProperty(value = "机动车销售统一发票:车架号/车辆识别代码", name = "carCode")
    private String carCode;

    /**
     * 机动车销售统一发票:发动机号码
     */
    @ApiModelProperty(value = "机动车销售统一发票:发动机号码", name = "carEngineCode")
    private String carEngineCode;

    /**
     * 机动车销售统一发票:厂牌型号
     */
    @ApiModelProperty(value = "机动车销售统一发票:厂牌型号", name = "carModel")
    private String carModel;

    /**
     * 机动车销售统一发票:合格证号
     */
    @ApiModelProperty(value = "机动车销售统一发票:合格证号", name = "certificateNumber")
    private String certificateNumber;

    /**
     * 出租车：上车时间
     */
    @ApiModelProperty(value = "出租车：上车时间", name = "timeGeton")
    private String timeGeton;

    /**
     * 出租车：下车时间
     */
    @ApiModelProperty(value = "出租车：下车时间", name = "timeGetoff")
    private String timeGetoff;

    /**
     * 出租车：里程
     */
    @ApiModelProperty(value = "出租车：里程", name = "mileage")
    private String mileage;

    /**
     * 出租车：发票所在地
     */
    @ApiModelProperty(value = "出租车：发票所在地", name = "place")
    private String place;

    /**
     * 火车票：乘车时间
     */
    @ApiModelProperty(value = "火车票：乘车时间", name = "time")
    private String time;

    /**
     * 火车票：乘车人姓名
     */
    @ApiModelProperty(value = "火车票：乘车人姓名", name = "name")
    private String name;

    /**
     * 火车站：上车车站
     */
    @ApiModelProperty(value = "火车站：上车车站", name = "stationGeton")
    private String stationGeton;

    /**
     * 火车站：下车车站
     */
    @ApiModelProperty(value = "火车站：下车车站", name = "stationGetoff")
    private String stationGetoff;

    /**
     * 火车票：车次
     */
    @ApiModelProperty(value = "火车票：车次", name = "trainNumber")
    private String trainNumber;

    /**
     * 火车票：座位类型
     */
    @ApiModelProperty(value = "火车票：座位类型", name = "seat")
    private String seat;

    /**
     * 种类，oil 表示是加油票
     */
    @ApiModelProperty(value = "种类，oil 表示是加油票", name = "category")
    private String category;

    /**
     * 过路费：入口
     */
    @ApiModelProperty(value = "过路费：入口", name = "entrance")
    private String entrance;

    /**
     * 过路费：出口
     */
    @ApiModelProperty(value = "过路费：出口", name = "exist")
    private String exist;

    /**
     * 航空运输电子客票行程单:乘机人姓名
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:乘机人姓名", name = "userName")
    private String userName;

    /**
     * 航空运输电子客票行程单:乘机人身份证号码
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:乘机人身份证号码", name = "userId")
    private String userId;

    /**
     * 航空运输电子客票行程单:销售单位代号
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:销售单位代号", name = "agentcode")
    private String agentcode;

    /**
     * 航空运输电子客票行程单:填开单位
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:填开单位", name = "issueBy")
    private String issueBy;

    /**
     * 航空运输电子客票行程单:票价
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:票价", name = "fare")
    private String fare;

    /**
     * 航空运输电子客票行程单:税费
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:税费", name = "tax")
    private String tax;

    /**
     * 航空运输电子客票行程单:燃油附加费
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:燃油附加费", name = "fuelSurcharge")
    private String fuelSurcharge;

    /**
     * 航空运输电子客票行程单:民航发展基金
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:民航发展基金", name = "caacDevelopmentFund")
    private String caacDevelopmentFund;

    /**
     * 航空运输电子客票行程单:保险费
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:保险费", name = "insurance")
    private String insurance;

    /**
     * 航空运输电子客票行程单:航班信息
     */
    @ApiModelProperty(value = "航空运输电子客票行程单:航班信息", name = "flights")
    private String flights;

    /**
     * 二手车销售统一发票:二手车市场
     */
    @ApiModelProperty(value = "二手车销售统一发票:二手车市场", name = "companyName")
    private String companyName;

    /**
     * 二手车销售统一发票:二手车市场纳税人识别号
     */
    @ApiModelProperty(value = "二手车销售统一发票:二手车市场纳税人识别号", name = "companyTaxId")
    private String companyTaxId;

    /**
     * 二手车销售统一发票:车牌号
     */
    @ApiModelProperty(value = "二手车销售统一发票:车牌号", name = "licensePlate")
    private String licensePlate;

    /**
     * 二手车销售统一发票:登记证号
     */
    @ApiModelProperty(value = "二手车销售统一发票:登记证号", name = "registrationNumber")
    private String registrationNumber;

    /**
     * 小票：店名
     */
    @ApiModelProperty(value = "小票：店名", name = "storeName")
    private String storeName;

    /**
     * 小票：税前金额
     */
    @ApiModelProperty(value = "小票：税前金额", name = "subtotal")
    private String subtotal;

    /**
     * 小票：折扣
     */
    @ApiModelProperty(value = "小票：折扣", name = "discount")
    private String discount;

    /**
     * 小票：小费
     */
    @ApiModelProperty(value = "小票：小费", name = "tips")
    private String tips;

    /**
     * 小票：币种， 使用 ISO 4217 Currency Codes 标准
     */
    @ApiModelProperty(value = "小票：币种， 使用 ISO 4217 Currency Codes 标准", name = "currencyCode")
    private String currencyCode;


    @ApiModelProperty(value = "报账单编号", name = "reimburseNo")
    private String reimburseNo;


    @ApiModelProperty(value = "业务类型", name = "serviceType")
    private String serviceType;

    @ApiModelProperty(value = "税率", name = "taxRate")
    private String taxRate;

    @ApiModelProperty(value = "业务事项图标", name = "icon")
    private String icon;

    @ApiModelProperty(value = "会计抵扣期", name = "kjdkq")
    private String kjdkq;

}