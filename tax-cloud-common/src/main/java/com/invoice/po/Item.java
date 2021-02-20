package com.invoice.po;

import com.lycheeframework.core.model.IPO;
import com.zhys.excel.ExcelCell;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wenjie
 *
 */
@Getter
@Setter
@ApiModel(value = "item", description = "对账单表体信息")
public class Item implements IPO{

    public Item() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 1L;


    /**
     * 主键 客户端
     */
    @ApiModelProperty(value = " 主键 客户端 client", name = "client")
    private String client;

    /**
     * 主键  对账单号
     */
    @ExcelCell(name = "对账单号")
    @ApiModelProperty(value = "对账单号", name = "stno")
    private String stno;

    /**
     * 主键  对账单行号
     */
    @ExcelCell(name = "对账单行号")
    @ApiModelProperty(value = "对账单行号", name = "stni")
    private String stni;

    /**
     * 收/退货
     */
    @ExcelCell(name = "收/退货")
    @ApiModelProperty(value = "收/退货", name = "fot")
    private String fot;

    /**
     * 物料编码
     */
    @ExcelCell(name = "物料编码")
    @ApiModelProperty(value = "物料编码", name = "matar")
    private String matar;

    /**
     * 物料描述
     */
    @ExcelCell(name = "物料描述")
    @ApiModelProperty(value = "物料描述", name = "matax")
    private String matax;

    /**
     * 物料凭证号
     */
    @ExcelCell(name = "物料凭证号")
    @ApiModelProperty(value = "物料凭证号", name = "belnrm")
    private String belnrm;

    /**
     * 年度
     */
    @ExcelCell(name = "年度")
    @ApiModelProperty(value = "年度", name = "gjahr")
    private String gjahr;

    /**
     * 物料凭证行项目
     */
    @ExcelCell(name = "物料凭证行项目")
    @ApiModelProperty(value = "物料凭证行项目", name = "beite")
    private String beite;

    /**
     * 过账日期
     */
    @ExcelCell(name = "过账日期")
    @ApiModelProperty(value = "过账日期", name = "budat")
    private String budat;

    /**
     * 数量
     */
    @ExcelCell(name = "数量")
    @ApiModelProperty(value = "数量", name = "quantity")
    private String quantity;

    /**
     * 单价
     */
    @ExcelCell(name = "单价")
    @ApiModelProperty(value = "单价", name = "zprice_wsj")
    private String zpriceWsj;

    /**
     * 折扣金额
     */
    @ExcelCell(name = "折扣金额")
    @ApiModelProperty(value = "折扣金额", name = "zamount_hzws")
    private String zamountHzws;

    /**
     * 不含税金额
     */
    @ExcelCell(name = "不含税金额")
    @ApiModelProperty(value = "不含税金额", name = "zamount_wsj")
    private String zamountWsj;

    /**
     * 单位
     */
    @ExcelCell(name = "单位")
    @ApiModelProperty(value = "单位", name = "unit_name")
    private String unitName;

    /**
     * 单位系数
     */
    @ExcelCell(name = "单位系数")
    @ApiModelProperty(value = "单位系数", name = "unit_c")
    private String unitC;

    /**
     * 规格
     */
    @ExcelCell(name = "规格")
    @ApiModelProperty(value = "规格", name = "item_spec")
    private String itemSpec;

    /**
     * 税率
     */
    @ExcelCell(name = "税率")
    @ApiModelProperty(value = "税率", name = "trate")
    private String trate;

    /**
     * 含税金额
     */
    @ExcelCell(name = "含税金额")
    @ApiModelProperty(value = "含税金额", name = "zamount_hsj")
    private String zamountHsj;
}
