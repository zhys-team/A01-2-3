/**
 * Copyright 2017-2018 1173499611@qq.com
 * All rights reserved.
 * 
 * @project
 * @author 11734
 * @version 1.0
 * @date 2018-08-28
 */
package com.zhys.user.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
@ApiModel(value="SysOrgs",description="")
public class SysOrgs implements IPO {

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
	 * 组织名称
	 */
	 @ApiModelProperty(value="组织名称",name="orgName")
	private String orgName;

	/**
	 * 组织编号
	 */
	 @ApiModelProperty(value="组织编号",name="orgNo")
	private String orgNo;

	/**
	 * 是否部门0：是 1否
	 */
	 @ApiModelProperty(value="是否部门0：是 1否",name="isDept")
	private String isDept;

	/**
	 * 组织属性 0：非组织  1：财务 2 税务 3 采购 4 销售 5 法人
	 */
	 @ApiModelProperty(value="组织属性 0：非组织  1：财务 2 税务 3 采购 4 销售 5 法人",name="isOrg")
	private String isOrg;

	/**
	 * 父节点主键
	 */
	 @ApiModelProperty(value="父节点主键",name="parentId")
	private Long parentId;

	/**
	 * 纳税人识别号
	 */
	 @ApiModelProperty(value="纳税人识别号",name="taxNo")
	private String taxNo;

	/**
	 * 删除标记 0：未删除  1：已删除
	 */
	 @ApiModelProperty(value="删除标记 0：未删除  1：已删除",name="delState")
	private String delState;

	/**
	 * 是否开票  0：否 1：是
	 */
	 @ApiModelProperty(value="是否开票  0：否 1：是",name="isKp")
	private String isKp;

	/**
	 * 开票地址
	 */
	 @ApiModelProperty(value="开票地址",name="addr")
	private String addr;

	/**
	 * 开票电话
	 */
	 @ApiModelProperty(value="开票电话",name="tel")
	private String tel;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="khhmc")
	private String khhmc;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="khhzh")
	private String khhzh;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="kpr")
	private String kpr;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="fhr")
	private String fhr;

	/**
	 * 
	 */
	 @ApiModelProperty(value="",name="skr")
	private String skr;

	/**
	 * 是否受票 0：否  1：是
	 */
	 @ApiModelProperty(value="是否受票 0：否  1：是",name="isSp")
	private String isSp;

	/**
	 * 报销受票邮箱
	 */
	 @ApiModelProperty(value="报销受票邮箱",name="bxspyx")
	private String bxspyx;

	/**
	 * 采购受票邮箱
	 */
	 @ApiModelProperty(value="采购受票邮箱",name="cgspyx")
	private String cgspyx;

	/**
	 * 是否需要认证 0：否 1：是
	 */
	 @ApiModelProperty(value="是否需要认证 0：否 1：是",name="isRz")
	private String isRz;

    private List<InvoiceAuth>  auths;
    
    private List<TaxMech>  mechs;
    
	private String orgIds;
}