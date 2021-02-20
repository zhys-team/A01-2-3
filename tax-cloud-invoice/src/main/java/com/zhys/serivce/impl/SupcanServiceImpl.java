package com.zhys.serivce.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.invoice.model.SupcanReport;
import com.invoice.model.TemplateDataSource;
import com.invoice.model.TemplateDataSourcePara;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.service.SQLManager;
import com.zhys.service.SupcanService;
@RestController
public class SupcanServiceImpl implements SupcanService {
	
	@Autowired
	private SQLManager manager;

	
	
	
	@Override
	public Pages<List<SupcanReport>> pages(@RequestBody SupcanReport t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<SupcanReport>>)manager.pages("supcan.page",t,page);
	}

	
	@Override
	public Integer save(@RequestBody SupcanReport s) {
		if(null!=s){
			if(null!=s.getMid()){
				SupcanReport os = (SupcanReport)manager.query("supcan._getObjectByMid", s.getMid());
				if(null!=os){
					manager.update("supcan._update", s);
				}else{
					manager.insert("supcan._insert", s);
				}
			}else{
				manager.insert("supcan._insert", s);
			}
			return 1;
		}	
		return null;
	}

	
	@Override
	public Integer saveTempletData(@RequestBody SupcanReport s) {
		if(null!=s){
			if(null!=s.getMid()){
				SupcanReport os = (SupcanReport)manager.query("supcan._getObjectByMid", s.getMid());
				if(null!=os){
					os.setTempletContext(s.getTempletContext());
					manager.update("supcan._updateTempletContext", os);
					return 1;
				}
			}
		}
		return null;
	}

	
	@Override
	public Pages<List<TemplateDataSource>> dspages(@RequestBody TemplateDataSource t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum) {
		EasyPage page = new EasyPage();
	    page.pageNum(pageNum);
	    page.pageSize(pageSize);
		return (Pages<List<TemplateDataSource>>)manager.pages("supcan._dspage", t, page);
	}

	
	@Override
	public String getTempletData(@RequestBody SupcanReport s) {
		String rs = "";
		String st = null;
		if(null!=s&&null!=s.getMid()){
			st = (String)manager.query("supcan._selectTempletData", s.getMid());
		}
		if(null!=st&&!st.trim().isEmpty()){
			rs = st.trim();
		}
		return rs;
	}

	
	@Override
	public String getDSJSONData(String sql) {
		String resu = "";
		
		try{
			Connection con = manager.getSqlSession().getConnection();
			CallableStatement stmt = con.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			System.out.println("sql:::"+sql);
			try{
				stmt.executeQuery();
			}catch (Exception e){
				resu = "{'Fault':{'faultCode':'500', 'detail': '"+e.getMessage()+"'}}";
				return resu;
			}
			ResultSet rs = null;
			JSONArray arr = new JSONArray();
			do{
				if ((rs = stmt.getResultSet()) != null){
					
					while (rs.next()){
						JSONObject j = new JSONObject();
						for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
							String columnName = rs.getMetaData().getColumnName(i);
			            	Object value = rs.getObject(i);
			            	String vstr = (null==value?"":value.toString());
			            	j.put(columnName, vstr);
						}
						arr.add(j);
					}
					
				}
			} while (!(stmt.getMoreResults() == false && stmt.getUpdateCount() == -1));
			
			resu = "{'Record':"+arr.toString()+"}";
		}catch(Exception e){
			
		}
		
		return resu;
	}
	
	@Override
	public String getSql(HttpServletRequest request){
		Long mid = 0L;
		try{mid = Long.parseLong(request.getParameter("dsid").trim());}catch(Exception e){mid = 0L;}
		TemplateDataSource t = new TemplateDataSource();
		t.setMid(mid);
		
		String sql = null;
		
		TemplateDataSource ds = null;
		if(null!=t&&null!=t.getMid()){
			ds = (TemplateDataSource)manager.query("supcan._selectTemplateDataSource", t.getMid());
		}
		if(null!=ds&&null!=ds.getSqlStatement()&&!ds.getSqlStatement().trim().isEmpty()){
			sql = ds.getSqlStatement().trim();
			
			Enumeration paranames = request.getParameterNames();
			while(paranames.hasMoreElements()){
				String parat = (String)paranames.nextElement();
				if(parat.trim().equals("dsid")){
					
				}else{
					List<TemplateDataSourcePara> paralist = (List<TemplateDataSourcePara>)manager.list("supcan._selectTemplateDataSourcePara", mid);
					
					for(TemplateDataSourcePara srp:paralist){
						ds.getParaM().put(srp.getParaName(), srp);
					}
					
					TemplateDataSourcePara pp = ds.getParaM().get(parat.trim());
					if(null!=pp){
						String pv = request.getParameter(parat.trim());
						if(2==pp.getParaType()||5==pp.getParaType()){
							pv = "'"+pv+"'";
							if(2==pp.getParaType()&&pv.indexOf('.')>0){
								pv=pv.replaceAll("\\.", "-");
							}
							if(2==pp.getParaType()){
								pv=pv.replaceAll("\u5e74?\ufffd\ufffd?\ufffd\ufffd","-");
								pv=pv.replaceAll("\u6708?\ufffd\ufffd?\ufffd\ufffd","-");
								pv=pv.replaceAll("\u65e5?\ufffd\ufffd?\ufffd\ufffd","");
							}
						}
						if(sql.indexOf(pp.getParaStr())>0&&null!=pv&&pv.trim().length()>0){
							if(1!=pp.getParaType()){
								sql = sql.replaceAll(pp.getParaStr(), pv.trim());
							}
						}else{
						}
					}
				}
			}
		}
		return sql;
	}

	
	@Override
	public String getDSJSONData2(String sql) {
String resu = "";
		
		try{
			Connection con = manager.getSqlSession().getConnection();
			CallableStatement stmt = con.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			System.out.println("sql:::"+sql);
			try{
				stmt.executeQuery();
			}catch (Exception e){
				resu = "{'Fault':{'faultCode':'500', 'detail': '"+e.getMessage()+"'}}";
				return resu;
			}
			ResultSet rs = null;
			JSONArray arr = new JSONArray();
			do{
				if ((rs = stmt.getResultSet()) != null){
					
					while (rs.next()){
						JSONObject j = new JSONObject();
						for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
							String columnName = rs.getMetaData().getColumnName(i);
			            	Object value = rs.getObject(i);
			            	String vstr = (null==value?"":value.toString());
			            	j.put(columnName, vstr);
						}
						arr.add(j);
					}
					
				}
			} while (!(stmt.getMoreResults() == false && stmt.getUpdateCount() == -1));
			
			resu = "{'Record':"+arr.toString()+"}";
		}catch(Exception e){
			
		}
		
		return resu;
	}
	
	@Override
    public @ResponseBody String getDSJSONData(HttpServletRequest request){
    	String sql = this.getSql(request);
    	String s = "";
    	if(null!=sql&&!sql.trim().isEmpty()){
    		s = this.getDSJSONData(sql);
    	}
    	return s;
    }
    
	@Override
    public @ResponseBody String getDSJSONData2(HttpServletRequest request){
    	String sql = this.getSql(request);
    	String s = "";
    	if(null!=sql&&!sql.trim().isEmpty()){
    		s = this.getDSJSONData2(sql);
    	}
    	return s;
    }

}
