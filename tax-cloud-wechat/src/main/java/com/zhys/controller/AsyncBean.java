package com.zhys.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.invoice.pojo.InvoiceMsg;
import com.zhys.fegin.ImagingServiceFegin;
import com.zhys.fegin.TzsysEasInvoicereceiveServiceFegin;
import com.zhys.po.TzsysEasInvoicereceive;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class AsyncBean {

	@Autowired
	private ImagingServiceFegin imFegin;
	
	@Autowired
	private TzsysEasInvoicereceiveServiceFegin easInvoicereceiveServiceFegin;
	
	public static String  createDateDir(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = sdf.format(new Date());
        String[] dayArr = dayStr.split("-");

        String year = dayArr[0];
        String month = dayArr[1];
        String day = dayArr[2];

        String yearDir = year;
        

        String monthDir = yearDir + File.separator + month;
        

        String dayDir = monthDir + File.separator + day+File.separator+key;
        
        System.out.println(dayDir);
        log.info("同步上传图片地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+dayDir);
        return dayDir;

    }
	
//	@Async
	public void upload(byte[] file,String fileName) {
		log.info("同步上传图片开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileName);
		imFegin.upload(file, fileName);
		log.info("同步上传图片结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileName);
	}

	//	@Async
	public void upload(MultipartFile file, String fileName) {
		log.info("MultipartFile异（同）步上传图片开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileName);
		imFegin.upload(file, fileName);
		log.info("MultipartFile异（同）步上传图片结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileName);
	}
	/**
	 * 异步同步到财务系统
	 * @param openId
	 * @param type
	 * @param file
	 * @param invoiceHead
	 * @return
	 */
	@Async
	public void asynToFin(List<InvoiceMsg> list) {
		if(list!=null&&list.size()>0) {
			for(InvoiceMsg msg:list) {
				if(msg.getId()!=null&&!"1".equals(msg.getBxState())) {
					TzsysEasInvoicereceive easInvoicereceive = new TzsysEasInvoicereceive();
					easInvoicereceive.setBuyer(msg.getSpfmc());
					easInvoicereceive.setBxState("1");
					//easInvoicereceive.setBxTime(new Date());
					if("1".equals(msg.getIsChange())) {
						easInvoicereceive.setChange("1");
					}else if("0".equals(msg.getIsChange())){
						log.info("{}票据已更改同步到oa",msg.getId());
						easInvoicereceive.setChange("2");
					}else {
						easInvoicereceive.setChange("1");
					}
					easInvoicereceive.setDocMaker(msg.getOpenId());
	                easInvoicereceive.setSalesParty(msg.getKpfmc());	
	                easInvoicereceive.setUserNo(msg.getOpenId());
	                easInvoicereceive.setFylb(msg.getMxsx());
	                easInvoicereceive.setRzState("1");
	                easInvoicereceive.setFpzl(msg.getFplx());
	                easInvoicereceive.setFpdm(msg.getFpdm());
	                easInvoicereceive.setFphm(msg.getFphm());
	                easInvoicereceive.setSpTime(new Date());
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                String kprq=null;
	                if(msg.getKprq()!=null) {
	                	kprq = sdf.format(msg.getKprq());
	                }else {
	                	kprq = sdf.format(new Date());
	                	log.info("{}:该票据无日期，自动赋值",msg.getFphm());
	                }
	                
	                easInvoicereceive.setKprq(kprq);
	                easInvoicereceive.setHsje(msg.getKpje());
	                easInvoicereceive.setWsje(msg.getHjje());
	                easInvoicereceive.setSe(msg.getHjse());
	                easInvoicereceive.setEaUserNo(msg.getOpenId());
	                easInvoicereceive.setInvoiceId(msg.getId());
	                easInvoicereceive.setImgUrl(msg.getPdfUrl());
	                easInvoicereceive.setJym(msg.getJym());
	                /**
	                 * 业务描述处理
	                 * 酒店住宿费 ：地点
	                 * 机票：出发地-目的地
	                 * 火车票：出发地-目的地
	                 * 长途汽车票（实名）：出发地-目的地
	                 */
	                String fylb = msg.getMxsx();
	                String ywms = "";
	                if(StringUtils.isNotEmpty(fylb)) {
	                	if("酒店住宿费".equals(fylb)) {
	                		ywms=msg.getKpfdzdh();
	                	}else if("机票".equals(fylb)||"火车票".equals(fylb)||"长途汽车票（实名）".equals(fylb)) {
	                		if(StringUtils.isNotEmpty(msg.getStationGeton())&&StringUtils.isNotEmpty(msg.getStationGetoff())) {
	                			ywms = msg.getStationGeton()+"-"+msg.getStationGetoff();
	                		}
	                		
	                		if("机票".equals(fylb)) {
		                		//	[{"date":"2020年04月16日","seat":"L","carrier":"国航","flight_number":"CA1756","from":"T3重庆","to":"杭州","time":"17:15"}]
		                		String flights = msg.getFlights();
		                		if(StringUtils.isNotEmpty(flights)) {
		                			JSONArray ja = JSONArray.parseArray(flights);
			                			if(ja!=null&ja.size()>0) {
			                				JSONObject jo = ja.getJSONObject(0);
			                				String from = jo.getString("from");
			                				String to = jo.getString("to");
			                				ywms = from+"-"+to;
				                		}
		                			}
		                		}
	                		
	                		
	                		if(StringUtils.isNotEmpty(msg.getName())) {
	                			ywms=ywms+","+msg.getName();
	                		}
	                		
	                		if(StringUtils.isNotEmpty(msg.getUserName())) {
	                			ywms=ywms+","+msg.getUserName();
	                		}
	                		
	                	}else if("火车票".equals(fylb)) {
	                		
	                	}else if("长途汽车票（实名）".equals(fylb)) {
	                		
	                	}
	                	
	                }
	                
	                easInvoicereceive.setYwms(ywms);
	                easInvoicereceive.setDcState(msg.getDcState()==null?"":""+msg.getDcState());
	                easInvoicereceive.setBodys(msg.getBodys());
	                if(msg.getBodys()!=null&&msg.getBodys().size()>0) {
	                	log.info("{}发票明细长度{}",easInvoicereceive.getInvoiceId(),msg.getBodys().size());
	                }
	                log.info("异步上传发票信息到中间表，id:{},内容：{}",easInvoicereceive.getInvoiceId(),JSONObject.toJSONString(easInvoicereceive));
//	                easInvoicereceiveServiceFegin.save(easInvoicereceive);
	                log.info("异步上传发票信息到中间表成功！id:{}",easInvoicereceive.getInvoiceId());
				}
				
			}
		}
		
	}
}
