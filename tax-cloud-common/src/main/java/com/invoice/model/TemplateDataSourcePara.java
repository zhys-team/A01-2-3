package com.invoice.model;

import java.util.Date;
import java.util.Map;

import com.lycheeframework.core.model.IPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel(value="TemplateDataSourcePara",description="模板数据源参数")
public class TemplateDataSourcePara implements IPO{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value=" 子表 id",name="nid")
	private Integer nid;
	@ApiModelProperty(value=" 父类主键",name="parentId")
	private Integer parentId;
	@ApiModelProperty(value=" 参数名称",name="paraName")
	private String paraName;
	@ApiModelProperty(value=" 占位符",name="paraStr")
	private String paraStr;//在sql语句中的占位符
	@ApiModelProperty(value=" 参数类型：1当前组织ID2日期3整数4小数5字符",name="paraType")
	private Integer paraType;//参数类型：1当前组织ID2日期3整数4小数5字符
}
