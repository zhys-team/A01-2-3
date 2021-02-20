package com.zhys.webservice;



import java.util.Date;
import java.util.logging.Level;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.java.Log;
@Log
public class TestWebservice {
	
//	public static void main(String[] args) {
//		Long l1 = new Date().getTime();
//		InvoiceServicePortType invoiceServicePortType    = new InvoiceService().getInvoiceServiceHttpPort();
//		String re = invoiceServicePortType.docreateInvoice("{\"data\":[{\"invoce_sys_id\":93505,\"salesParty\":\"\",\"type\":\"10503\",\"taxMoney\":0,\"buyer\":\"\",\"taxRate\":0,\"makeUser\":\"1\",\"typeInDate\":\"2019-03-14\",\"imageUrl\":\"http://zhys.ft-link.cn:8011/image/1552541209993.png\",\"invoceNum\":\"Z179A037683\",\"amountMoney\":\"33.50\",\"totalWithTax\":\"33.50\",\"makeDate\":1465142400000,\"status\":\"0\"}]}");
//		Long l2 = new Date().getTime();
//		System.out.println(l2-l1);
//		System.out.println(re);
//	}
	
	public static boolean  synToOA(String invoiceMsg) {
		/**InvoiceServicePortType invoiceServicePortType    = new InvoiceService().getInvoiceServiceHttpPort();
		String re = invoiceServicePortType.docreateInvoice(invoiceMsg);
		JSONObject jsonObject = JSONObject.parseObject(re);
		if(jsonObject!=null&&"0".equals(jsonObject.getString("errcode"))) {
			return true;
		}
		log.info(jsonObject.toJSONString());
		return false;**/
		return true;

	}

}
