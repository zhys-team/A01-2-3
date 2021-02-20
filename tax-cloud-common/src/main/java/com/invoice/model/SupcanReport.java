package com.invoice.model;

import java.util.Date;

import com.lycheeframework.core.model.IPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel(value="SupcanReport",description="报表信息")
public class SupcanReport implements IPO{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value=" 主键 id",name="mid")
	private Integer mid;
	@ApiModelProperty(value="报表名称",name="reportName")
	private String reportName;
	@ApiModelProperty(value="模板内容",name="templetContext")
	private String templetContext;
	@ApiModelProperty(value=" 创建时间",name="createDate")
	private Date createDate;
	@ApiModelProperty(value=" 修改时间",name="modifyDate")
	private Date modifyDate;

	
}
