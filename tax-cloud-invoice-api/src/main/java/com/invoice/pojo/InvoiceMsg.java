package com.invoice.pojo;

import java.util.List;

import com.invoice.po.InvoiceBody;
import com.invoice.po.InvoiceHead;

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
	
	@ApiModelProperty(value="查验状态码 0：成功 ",name="Code")
	private Integer Code;
}
