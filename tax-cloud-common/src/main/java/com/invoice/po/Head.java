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
@ApiModel(value = "Head", description = "对账单主信息")
public class Head implements IPO {

//    public Head() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

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
     * 发送状态
     */
    @ExcelCell(name = "发送状态")
    @ApiModelProperty(value = "发送状态", name = "ssta")
    private String ssta;

    /**
     * 对账单状态
     */
    @ExcelCell(name = "对账单状态")
    @ApiModelProperty(value = "对账单状态", name = "tsta")
    private String tsta;

    /**
     * 公司代码
     */
    @ExcelCell(name = "公司代码")
    @ApiModelProperty(value = "公司代码", name = "bukrs")
    private String bukrs;

    /**
     * 公司代码描述
     */
    @ExcelCell(name = "公司代码描述")
    @ApiModelProperty(value = "公司代码描述", name = "bname")
    private String bname;

    /**
     * 工厂
     */
    @ExcelCell(name = "工厂")
    @ApiModelProperty(value = "工厂", name = "plant")
    private String plant;

    /**
     * 税码
     */
    @ExcelCell(name = "税码")
    @ApiModelProperty(value = "税码", name = "taxno")
    private String taxno;

    /**
     * 税率
     */
    @ExcelCell(name = "税率")
    @ApiModelProperty(value = "税率", name = "trate")
    private String trate;

    /**
     * 供应商编码
     */
    @ExcelCell(name = "供应商编码")
    @ApiModelProperty(value = "供应商编码", name = "vender")
    private String vender;

    /**
     * 供应商名称
     */
    @ExcelCell(name = "供应商名称")
    @ApiModelProperty(value = "供应商名称", name = "vname")
    private String vname;

    /**
     * 对账单开始日期
     */
    @ExcelCell(name = "对账单开始日期")
    @ApiModelProperty(value = "对账单开始日期", name = "fdate")
    private String fdate;

    /**
     * 对账单结束日期
     */
    @ExcelCell(name = "对账单结束日期")
    @ApiModelProperty(value = "对账单结束日期", name = "tdate")
    private String tdate;

    /**
     * 总发票金额   未w  税s  金j  额e
     */
    @ExcelCell(name = "总发票金额")
    @ApiModelProperty(value = "总发票金额", name = "wsje")
    private String wsje;

    /**
     * 含税金额
     */
    @ExcelCell(name = "含税金额")
    @ApiModelProperty(value = "含税金额", name = "hsje")
    private String hsje;
}
