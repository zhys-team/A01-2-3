package com.invoice.po;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhys.excel.ExcelCell;
import com.zhys.excel.ExportExcelUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JxtztjExcel {
	
	    @ExcelCell(name = "报账单编号",index=0)
		@ApiModelProperty(value="报账单编号",name="reimburseNo")
	    private String reimburseNo;
		
		/**
		 * 发票代码
		 */
		@ExcelCell(name = "发票代码",index=1)
		@ApiModelProperty(value = "发票代码", name = "fpdm")
		private String fpdm;
	
		/**
		 * 发票号码
		 */
		@ExcelCell(name = "发票号码",index=2)
		@ApiModelProperty(value = "发票号码", name = "fphm")
		private String fphm;
	
		/**
		 * 明细事项
		 */
		@ExcelCell(name = "费用类型",index=3)
		@ApiModelProperty(value = "费用类型", name = "mxsx")
		private String mxsx;
		
		/**
		 * 开票日期
		 */
		@ExcelCell(name = "开票日期",index=4)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
		@ApiModelProperty(value = "开票日期", name = "kprq")
		private Date kprq;
		
		
		
		
		/**
		 * 价税合计
		 */
		@ExcelCell(name = "金额",index=5)
		@ApiModelProperty(value = "价税合计", name = "kpje")
		private String kpje;
		
		/**
		 * 合计税额
		 */
		@ExcelCell(name = "税额",index=6)
		@ApiModelProperty(value = "合计税额", name = "dkje")
		private String dkje;

		

		/**
		 * 认证状态  1：初始状态 2：待认证 3：已认证
		 */
		@ExcelCell(name = "认证状态",index=7)
		@ApiModelProperty(value = "1：初始状态 2：待认证 3：已认证", name = "rzState")
		private String rzState;
		 
		
		/**
		 * 开票方名称
		 */
		@ExcelCell(name = "开票方名称",index=8)
		@ApiModelProperty(value = "开票方名称", name = "kpfmc")
		private String kpfmc;

		/**
		 * 开票方识别号
		 */
		@ExcelCell(name = "开票方识别号",index=9)
		@ApiModelProperty(value = "开票方识别号", name = "kpfsbh")
		private String kpfsbh;
	

}
