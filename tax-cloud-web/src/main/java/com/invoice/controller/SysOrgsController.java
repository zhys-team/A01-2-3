package com.invoice.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.SysOrgs;
import com.zhys.user.po.SysUsers;
import com.zhys.user.po.SysUsersOrgs;
import com.zhys.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.invoice.fegin.SysOrgsServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ResponseResult
@Slf4j
@RestController
@RequestMapping("sysOrgs")
@Api(value="组织接口",description="组织接口" )
public class SysOrgsController {

    @Autowired
	private SysOrgsServiceFegin service;


    

    @ApiOperation(value = "组织分页列表",notes="根据条件查询数据并分页")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true,paramType="query", dataType = "Integer"),
		@ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType="query",dataType = "Integer"),
    })
    @PostMapping(value = "page",produces = MediaType.APPLICATION_JSON_VALUE)
	public  Pages<List<SysOrgs>> pages( @RequestParam Integer pageSize,@RequestParam Integer pageNum,@ApiParam(name="sysOrgs",value="查询条件",required=true) 
	            @RequestBody(required=false) SysOrgs sysOrgs){
		return  service.pages(sysOrgs,pageSize,pageNum);
	}
    
    
    @PostMapping("save")
	public  Integer save(@RequestBody SysOrgs sysOrgs){
		return  service.save(sysOrgs);
	}

    
    @GetMapping("info/{id}")
	public SysOrgs info(@PathVariable("id") Long id,SysOrgs sysOrgs){
    	sysOrgs.setId(id);
		sysOrgs = service.queryByEntity(sysOrgs);
		
		return sysOrgs;
	}
	
    
    
    @ApiOperation(value = "通过主键删除",notes="根据主键删除数据")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键", required = true,paramType="path", dataType = "Integer"),
		@ApiImplicitParam(name = "state", value = "状态 0：未删除  1：删除", required = true, paramType="query",dataType = "Integer")
    })
    @GetMapping("/state/{id}")
	public  Integer changeDelStateById(@PathVariable("id") Long id,@RequestParam String state){
    	SysOrgs sysOrgs = new SysOrgs();
    	sysOrgs.setId(id);
		return  service.changeDelStateById(sysOrgs);
	}
    
    @PostMapping("/queryList")
    public List<SysOrgs> queryList(@RequestBody SysOrgs sysOrgs){
    	List<SysUsersOrgs> suos = null;
    	Subject currentUser = SecurityUtils.getSubject();
    	
        if(null != currentUser){
            Session session = currentUser.getSession();
            Object o = session.getAttribute("user");
            SysUsers u = null;
            if(o!=null&&o instanceof SysUsers){
            	 u = (SysUsers)o;
            	 if(!u.getNo().equals("admin")) {
            		 suos = (List<SysUsersOrgs>) session.getAttribute("suos");
                     if(suos!=null&&suos.size()>0) {
                     	String ids = "";
                     	for(SysUsersOrgs uo:suos) {
                     		ids=ids+"'"+uo.getOrgId()+"',";
                     	}
                     	if(ids.length()>0) {
                     		ids = ids.substring(0, ids.length()-1);
                     		sysOrgs.setOrgIds(ids);
                     	}
                     	
                     }else {
                  		sysOrgs.setOrgIds("'-1'");
                 	}
            	 }
            }
        }
    	return  service.queryList(sysOrgs);
    }  
    
    
}