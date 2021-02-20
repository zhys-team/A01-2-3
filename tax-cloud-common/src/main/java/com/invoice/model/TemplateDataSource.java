package com.invoice.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lycheeframework.core.model.IPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel(value="TemplateDataSource",description="模板数据源")
public class TemplateDataSource implements IPO{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value=" 主键 id",name="mid")
	private Long mid;
	@ApiModelProperty(value=" 数据源名称",name="dataSourceName")
	private String dataSourceName;
	@ApiModelProperty(value="sql语句",name="sqlStatement")
	private String sqlStatement;
	@ApiModelProperty(value=" 创建时间",name="createDate")
	private Date createDate;
	@ApiModelProperty(value=" 修改时间",name="modifyDate")
	private Date modifyDate;
	
	private String params;
	private Map<String,TemplateDataSourcePara> paraM = new HashMap<String,TemplateDataSourcePara>();
	
	
}
