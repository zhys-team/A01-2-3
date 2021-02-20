package com.zhys.po;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="Dkhz",description="抵扣汇总")
public class Dkhz {
	
	@ApiModelProperty(value=" 认证税额",name="rzse")
	private BigDecimal rzse =BigDecimal.ZERO;;
	
	
	@ApiModelProperty(value=" 转出税额",name="sbse")
	private BigDecimal sbse =BigDecimal.ZERO;;
	
	@ApiModelProperty(value=" 转出税额",name="zcse")
	private BigDecimal zcse =BigDecimal.ZERO;;
	
	@ApiModelProperty(value=" 当期抵扣",name="dqdkje")
	private BigDecimal dqdkse =BigDecimal.ZERO;
	
	@ApiModelProperty(value=" 当期月份",name="yf")
	private String yf ;
	
	

}
