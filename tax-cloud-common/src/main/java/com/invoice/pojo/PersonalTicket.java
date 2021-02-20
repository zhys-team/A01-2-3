package com.invoice.pojo;

import com.lycheeframework.core.model.IPO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class PersonalTicket  implements IPO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Long id; 
	private String fpzl;
	private String fplx;
	private String kprq;
	private String kpfmc;
	private String kpje;
	private String fpdm; 
	private String fphm;
	private String spfmc; 
	private String spfsbh; 
	private String spfdzdh; 
	private String spfyhzh; 
	private String kpfsbh; 
	private String kpfdzdh; 
	private String kpfyhzh; 
	private String hjje; 
	private String hjse; 
	private String mark;
	private String pdfUrl;
	/**
	 * 开票内容
	 */
	private String kpnr;
	/**
	 * 累计金额
	 */
	private String ljje;
	/**
	 * 出发地
	 */
	 @ApiModelProperty(value="出发地",name="startAddr")
	private String startAddr;

	/**
	 * 目的地
	 */
	 @ApiModelProperty(value="目的地",name="endAddr")
	private String endAddr;
	 
	/**
	 * 座位类别 
	 */
	 @ApiModelProperty(value="座位类别 ",name="seatCategory")
	private String seatCategory;
	 
	 /**
		 * 业务事项
		 */
		 @ApiModelProperty(value="业务事项",name="ywsx")
		private String ywsx;
	 
	 /**
	 * 明细事项
	 */
	 @ApiModelProperty(value="明细事项",name="mxsx")
	private String mxsx;
 /**
	 * 乘车人
	 */
	 @ApiModelProperty(value="乘车人",name="passenger")
	private String passenger;
  /**
	 * 复检内容
	 */
	 @ApiModelProperty(value="复检内容",name="fjContent")
	private String fjContent;

	 /**
		 * 车次
		 */
		 @ApiModelProperty(value="车次",name="trainNum")
		private String trainNum;
		 
		 /**
			 * 机动车销售统一发票:机打代码
			 */
			 @ApiModelProperty(value="机动车销售统一发票:机打代码",name="machineCode")
			private String machineCode;

			/**
			 * 机动车销售统一发票:机打号码
			 */
			 @ApiModelProperty(value="机动车销售统一发票:机打号码",name="machineNumber")
			private String machineNumber;

			/**
			 * 机动车销售统一发票:主管税务机关
			 */
			 @ApiModelProperty(value="机动车销售统一发票:主管税务机关",name="taxAuthorities")
			private String taxAuthorities;

			/**
			 * 机动车销售统一发票:主管税务机关代码
			 */
			 @ApiModelProperty(value="机动车销售统一发票:主管税务机关代码",name="taxAuthoritiesCode")
			private String taxAuthoritiesCode;

			/**
			 * 机动车销售统一发票:车架号/车辆识别代码
			 */
			 @ApiModelProperty(value="机动车销售统一发票:车架号/车辆识别代码",name="carCode")
			private String carCode;

			/**
			 * 机动车销售统一发票:发动机号码
			 */
			 @ApiModelProperty(value="机动车销售统一发票:发动机号码",name="carEngineCode")
			private String carEngineCode;

			/**
			 * 机动车销售统一发票:厂牌型号
			 */
			 @ApiModelProperty(value="机动车销售统一发票:厂牌型号",name="carModel")
			private String carModel;

			/**
			 * 机动车销售统一发票:合格证号
			 */
			 @ApiModelProperty(value="机动车销售统一发票:合格证号",name="certificateNumber")
			private String certificateNumber;

			/**
			 * 出租车：上车时间
			 */
			 @ApiModelProperty(value="出租车：上车时间",name="timeGeton")
			private String timeGeton;

			/**
			 * 出租车：下车时间
			 */
			 @ApiModelProperty(value="出租车：下车时间",name="timeGetoff")
			private String timeGetoff;

			/**
			 * 出租车：里程
			 */
			 @ApiModelProperty(value="出租车：里程",name="mileage")
			private String mileage;

			/**
			 * 出租车：发票所在地
			 */
			 @ApiModelProperty(value="出租车：发票所在地",name="place")
			private String place;

			/**
			 * 火车票：乘车时间
			 */
			 @ApiModelProperty(value="火车票：乘车时间",name="time")
			private String time;

			/**
			 * 火车票：乘车人姓名
			 */
			 @ApiModelProperty(value="火车票：乘车人姓名",name="name")
			private String name;

			/**
			 * 火车站：上车车站
			 */
			 @ApiModelProperty(value="火车站：上车车站",name="stationGeton")
			private String stationGeton;

			/**
			 * 火车站：下车车站
			 */
			 @ApiModelProperty(value="火车站：下车车站",name="stationGetoff")
			private String stationGetoff;

			/**
			 * 火车票：车次
			 */
			 @ApiModelProperty(value="火车票：车次",name="trainNumber")
			private String trainNumber;

			/**
			 * 火车票：座位类型
			 */
			 @ApiModelProperty(value="火车票：座位类型",name="seat")
			private String seat;

			/**
			 * 种类，oil 表示是加油票
			 */
			 @ApiModelProperty(value="种类，oil 表示是加油票",name="category")
			private String category;

			/**
			 * 过路费：入口
			 */
			 @ApiModelProperty(value="过路费：入口",name="entrance")
			private String entrance;

			/**
			 * 过路费：出口
			 */
			 @ApiModelProperty(value="过路费：出口",name="exist")
			private String exist;

			/**
			 * 航空运输电子客票行程单:乘机人姓名
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:乘机人姓名",name="userName")
			private String userName;

			/**
			 * 航空运输电子客票行程单:乘机人身份证号码
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:乘机人身份证号码",name="userId")
			private String userId;

			/**
			 * 航空运输电子客票行程单:销售单位代号
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:销售单位代号",name="agentcode")
			private String agentcode;

			/**
			 * 航空运输电子客票行程单:填开单位
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:填开单位",name="issueBy")
			private String issueBy;

			/**
			 * 航空运输电子客票行程单:票价
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:票价",name="fare")
			private String fare;

			/**
			 * 航空运输电子客票行程单:税费
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:税费",name="tax")
			private String tax;

			/**
			 * 航空运输电子客票行程单:燃油附加费
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:燃油附加费",name="fuelSurcharge")
			private String fuelSurcharge;

			/**
			 * 航空运输电子客票行程单:民航发展基金
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:民航发展基金",name="caacDevelopmentFund")
			private String caacDevelopmentFund;

			/**
			 * 航空运输电子客票行程单:保险费
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:保险费",name="insurance")
			private String insurance;

			/**
			 * 航空运输电子客票行程单:航班信息
			 */
			 @ApiModelProperty(value="航空运输电子客票行程单:航班信息",name="flights")
			private String flights;

			/**
			 * 二手车销售统一发票:二手车市场
			 */
			 @ApiModelProperty(value="二手车销售统一发票:二手车市场",name="companyName")
			private String companyName;

			/**
			 * 二手车销售统一发票:二手车市场纳税人识别号
			 */
			 @ApiModelProperty(value="二手车销售统一发票:二手车市场纳税人识别号",name="companyTaxId")
			private String companyTaxId;

			/**
			 * 二手车销售统一发票:车牌号
			 */
			 @ApiModelProperty(value="二手车销售统一发票:车牌号",name="licensePlate")
			private String licensePlate;

			/**
			 * 二手车销售统一发票:登记证号
			 */
			 @ApiModelProperty(value="二手车销售统一发票:登记证号",name="registrationNumber")
			private String registrationNumber;

			/**
			 * 小票：店名
			 */
			 @ApiModelProperty(value="小票：店名",name="storeName")
			private String storeName;

			/**
			 * 小票：税前金额
			 */
			 @ApiModelProperty(value="小票：税前金额",name="subtotal")
			private String subtotal;

			/**
			 * 小票：折扣
			 */
			 @ApiModelProperty(value="小票：折扣",name="discount")
			private String discount;

			/**
			 * 小票：小费
			 */
			 @ApiModelProperty(value="小票：小费",name="tips")
			private String tips;

			/**
			 * 小票：币种， 使用 ISO 4217 Currency Codes 标准
			 */
			 @ApiModelProperty(value="小票：币种， 使用 ISO 4217 Currency Codes 标准",name="currencyCode")
			private String currencyCode;
			 
			/**
			 * 修改标志 0：已修改  1：未修改
			 */
			@ApiModelProperty(value = "修改标志 0：已修改  1：未修改", name = "isChange")
			private String isChange;
			
			@ApiModelProperty(value = "业务事项图标", name = "icon")
			private String icon;
			
			@ApiModelProperty(value = "校验码", name = "jym")
			private String jym;
}
