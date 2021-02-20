/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-10-10
 */
package com.invoice.po;

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
@ApiModel(value="AddTaxMainGrid",description="增值税申报表-主表中列表")
public class AddTaxMainGrid implements IPO {

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
	 * 
	 */
	 @ApiModelProperty(value="",name="parentId")
	private Long parentId;

	/**
	 * 按适用税率计税销售额
	 */
	 @ApiModelProperty(value="按适用税率计税销售额",name="asysljsxse")
	private String asysljsxse;

	/**
	 * 其中：应税货物销售额
	 */
	 @ApiModelProperty(value="其中：应税货物销售额",name="yshwxse")
	private String yshwxse;

	/**
	 * 应税劳务销售额
	 */
	 @ApiModelProperty(value="应税劳务销售额",name="yslwxse")
	private String yslwxse;

	/**
	 * 纳税检查调整的销售额
	 */
	 @ApiModelProperty(value="纳税检查调整的销售额",name="nsjctzxse")
	private String nsjctzxse;

	/**
	 * （二）按简易办法计税销售额
	 */
	 @ApiModelProperty(value="（二）按简易办法计税销售额",name="ajjybfjsxse")
	private String ajjybfjsxse;

	/**
	 * 其中：纳税检查调整的销售额
	 */
	 @ApiModelProperty(value="其中：纳税检查调整的销售额",name="qznsjctzxse")
	private String qznsjctzxse;

	/**
	 * 三）免、抵、退办法出口销售额
	 */
	 @ApiModelProperty(value="三）免、抵、退办法出口销售额",name="mdtckxse")
	private String mdtckxse;

	/**
	 * （四）免税销售额
	 */
	 @ApiModelProperty(value="（四）免税销售额",name="msxse")
	private String msxse;

	/**
	 * 其中：免税货物销售额
	 */
	 @ApiModelProperty(value="其中：免税货物销售额",name="mshwxse")
	private String mshwxse;

	/**
	 * 免税劳务销售额
	 */
	 @ApiModelProperty(value="免税劳务销售额",name="mslwxse")
	private String mslwxse;

	/**
	 * 销项税额
	 */
	 @ApiModelProperty(value="销项税额",name="xsse")
	private String xsse;

	/**
	 * 进项税额
	 */
	 @ApiModelProperty(value="进项税额",name="jxse")
	private String jxse;

	/**
	 * 上期留抵税额
	 */
	 @ApiModelProperty(value="上期留抵税额",name="sqldse")
	private String sqldse;

	/**
	 * 进项税额转出
	 */
	 @ApiModelProperty(value="进项税额转出",name="jxsezc")
	private String jxsezc;

	/**
	 * 免、抵、退应退税额
	 */
	 @ApiModelProperty(value="免、抵、退应退税额",name="mdtytse")
	private String mdtytse;

	/**
	 * 按适用税率计算的纳税检查应补缴税额
	 */
	 @ApiModelProperty(value="按适用税率计算的纳税检查应补缴税额",name="asysljsbjse")
	private String asysljsbjse;

	/**
	 * 应抵扣税额合计
	 */
	 @ApiModelProperty(value="应抵扣税额合计",name="ydksehj")
	private String ydksehj;

	/**
	 * 实际抵扣税额
	 */
	 @ApiModelProperty(value="实际抵扣税额",name="sjdkse")
	private String sjdkse;

	/**
	 * 应纳税额
	 */
	 @ApiModelProperty(value="应纳税额",name="ynse")
	private String ynse;

	/**
	 * 期末留抵税额
	 */
	 @ApiModelProperty(value="期末留抵税额",name="qmldse")
	private String qmldse;

	/**
	 * 简易计税办法计算的应纳税额
	 */
	 @ApiModelProperty(value="简易计税办法计算的应纳税额",name="jyjsynse")
	private String jyjsynse;

	/**
	 * 按简易计税办法计算的纳税检查应补缴税额
	 */
	 @ApiModelProperty(value="按简易计税办法计算的纳税检查应补缴税额",name="ajyjsybjse")
	private String ajyjsybjse;

	/**
	 * 应纳税额减征额
	 */
	 @ApiModelProperty(value="应纳税额减征额",name="ynsejze")
	private String ynsejze;

	/**
	 * 应纳税额合计
	 */
	 @ApiModelProperty(value="应纳税额合计",name="ynsehj")
	private String ynsehj;

	/**
	 * 期初未缴税额（多缴为负数）
	 */
	 @ApiModelProperty(value="期初未缴税额（多缴为负数）",name="qcwjse")
	private String qcwjse;

	/**
	 * 实收出口开具专用缴款书退税额
	 */
	 @ApiModelProperty(value="实收出口开具专用缴款书退税额",name="ssckkjtse")
	private String ssckkjtse;

	/**
	 * 本期已缴税额
	 */
	 @ApiModelProperty(value="本期已缴税额",name="bqyjse")
	private String bqyjse;

	/**
	 * ①分次预缴税额
	 */
	 @ApiModelProperty(value="①分次预缴税额",name="fqyjse")
	private String fqyjse;

	/**
	 * ②出口开具专用缴款书预缴税额
	 */
	 @ApiModelProperty(value="②出口开具专用缴款书预缴税额",name="ckkjyjse")
	private String ckkjyjse;

	/**
	 * ③本期缴纳上期应纳税额
	 */
	 @ApiModelProperty(value="③本期缴纳上期应纳税额",name="bqjnyjse")
	private String bqjnyjse;

	/**
	 * ④本期缴纳欠缴税额
	 */
	 @ApiModelProperty(value="④本期缴纳欠缴税额",name="bqjnqjse")
	private String bqjnqjse;

	/**
	 * 期末未缴税额（多缴为负数）
	 */
	 @ApiModelProperty(value="期末未缴税额（多缴为负数）",name="qmwjse")
	private String qmwjse;

	/**
	 * 其中：欠缴税额（≥0）
	 */
	 @ApiModelProperty(value="其中：欠缴税额（≥0）",name="qzqjse")
	private String qzqjse;

	/**
	 * 本期应补（退）税额
	 */
	 @ApiModelProperty(value="本期应补（退）税额",name="bqybse")
	private String bqybse;

	/**
	 * 即征即退实际退税额
	 */
	 @ApiModelProperty(value="即征即退实际退税额",name="jzjtsjtse")
	private String jzjtsjtse;

	/**
	 * 期初未缴查补税额
	 */
	 @ApiModelProperty(value="期初未缴查补税额",name="qcwjcbse")
	private String qcwjcbse;

	/**
	 * 本期入库查补税额
	 */
	 @ApiModelProperty(value="本期入库查补税额",name="bqrkcbse")
	private String bqrkcbse;

	/**
	 * 期末未缴查补税额
	 */
	 @ApiModelProperty(value="期末未缴查补税额",name="qmwjcbse")
	private String qmwjcbse;


}