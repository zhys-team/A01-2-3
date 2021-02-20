package com.invoice.pojo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.invoice.po.InvoiceBody;
import com.invoice.po.InvoiceHead;
import com.zhys.utils.DateUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="InvoiceMsg",description="发票信息")
public class InvoiceMsg extends InvoiceHead{
	
	private List<InvoiceBody> bodys;
	@ApiModelProperty(value="查验返回信息",name="Message")
	private String Message;
	
	@ApiModelProperty(value="查验是否成功",name="Success")
	private String Success;
	
	@ApiModelProperty(value="查验状态码 1：成功 查验   失败：2\r\n" + 
			"非本企业： 3\r\n" + 
			"发票已存在： 4\r\n" + 
			"发票类型无法识别： 5 ",name="Code")
	private Integer Code;
	
	@ApiModelProperty(value="发票内容 ",name="kpnr")
	private String kpnr;
	
}
