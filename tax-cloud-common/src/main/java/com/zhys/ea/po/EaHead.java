/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-07-20
 */
package com.zhys.ea.po;

import java.sql.Date;
import java.util.List;

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
@ApiModel(value="EaHead",description="")
public class EaHead implements IPO {

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
	 * 报销单位id
	 */
	 @ApiModelProperty(value="报销单位id",name="eaOrgId")
	private Long eaOrgId;

	/**
	 * 报销单位名称
	 */
	 @ApiModelProperty(value="报销单位名称",name="eaOrgName")
	private String eaOrgName;

	/**
	 * 费用项目名称
	 */
	 @ApiModelProperty(value="费用项目名称",name="feeName")
	private String feeName;

	/**
	 * 费用项目id
	 */
	 @ApiModelProperty(value="费用项目id",name="feeId")
	private Integer feeId;

	/**
	 * 单据日期
	 */
	 @ApiModelProperty(value="单据日期",name="createDate")
	private Date createDate;

	/**
	 * 报销人id
	 */
	 @ApiModelProperty(value="报销人id",name="eaManId")
	private Long eaManId;

	/**
	 * 报销人名称
	 */
	 @ApiModelProperty(value="报销人名称",name="eaManName")
	private String eaManName;

	/**
	 * 报销人部门id
	 */
	 @ApiModelProperty(value="报销人部门id",name="eaManDeptId")
	private Long eaManDeptId;

	/**
	 * 报销人部门名称
	 */
	 @ApiModelProperty(value="报销人部门名称",name="eaManDeptName")
	private String eaManDeptName;

	/**
	 * 费用承担单位id
	 */
	 @ApiModelProperty(value="费用承担单位id",name="feeBearOrgId")
	private Long feeBearOrgId;

	/**
	 * 费用承担单位
	 */
	 @ApiModelProperty(value="费用承担单位",name="feeBearOrgName")
	private String feeBearOrgName;

	/**
	 * 费用承担部门id
	 */
	 @ApiModelProperty(value="费用承担部门id",name="feeBearDeptId")
	private Long feeBearDeptId;

	/**
	 * 费用承担部门名称
	 */
	 @ApiModelProperty(value="费用承担部门名称",name="feeBearDeptName")
	private String feeBearDeptName;

	/**
	 * 报销项目id
	 */
	 @ApiModelProperty(value="报销项目id",name="eaProjectId")
	private Integer eaProjectId;

	/**
	 * 报销项目名称
	 */
	 @ApiModelProperty(value="报销项目名称",name="eaProjectName")
	private String eaProjectName;

	/**
	 * 事由
	 */
	 @ApiModelProperty(value="事由",name="eaCause")
	private String eaCause;

	/**
	 * 出差日期
	 */
	 @ApiModelProperty(value="出差日期",name="workStartDate")
	private Date workStartDate;

	/**
	 * 返回日期
	 */
	 @ApiModelProperty(value="返回日期",name="workEndDate")
	private Date workEndDate;

	/**
	 * 出差天数
	 */
	 @ApiModelProperty(value="出差天数",name="workDays")
	private Float workDays;

	/**
	 * 是否提供食宿 0：提供 1：不提供
	 */
	 @ApiModelProperty(value="是否提供食宿 0：提供 1：不提供",name="accommodate")
	private String accommodate;

	/**
	 * 出差补贴
	 */
	 @ApiModelProperty(value="出差补贴",name="travelAllowance")
	private Float travelAllowance;

	/**
	 * 出差补贴标准
	 */
	 @ApiModelProperty(value="出差补贴标准",name="travelAllowanceStand")
	private Integer travelAllowanceStand;

	/**
	 * 餐费补贴
	 */
	 @ApiModelProperty(value="餐费补贴",name="mealAllowance")
	private Float mealAllowance;

	/**
	 * 餐费补贴标准
	 */
	 @ApiModelProperty(value="餐费补贴标准",name="mealAllowanceStand")
	private Integer mealAllowanceStand;

	/**
	 * 补贴金额
	 */
	 @ApiModelProperty(value="补贴金额",name="subsidyAmount")
	private Float subsidyAmount;

	/**
	 * 报销金额
	 */
	 @ApiModelProperty(value="报销金额",name="eaAmount")
	private Float eaAmount;

	/**
	 * 0:未删除  1：已删除
	 */
	 @ApiModelProperty(value="0:未删除  1：已删除",name="delState")
	private String delState;
	 
	/**
	 * 业务事项
	 */
	 @ApiModelProperty(value="业务事项",name="ywsx")
	private String ywsx;
	 
   /**
	 * 微信号
	 */
	 @ApiModelProperty(value="微信号",name="openId")
	private String openId;
	
   /**
	 * 审核状态
	 */
	 @ApiModelProperty(value="审核状态 0已保存  1已提交待审核  2 审核通过 3 未审核通过",name="audit")
	private String audit;

	 
	private List<EaBody> bodys;

}