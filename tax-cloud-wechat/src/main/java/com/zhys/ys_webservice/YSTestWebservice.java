package com.zhys.ys_webservice;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zhys.utils.MD5Util;

import lombok.extern.java.Log;
@Log
public class YSTestWebservice {
	
	public static void main(String[] args) {
		IDataIOService dataIOService     = new DataIOService().getBasicHttpBindingIDataIOService();
		
		String s1 = "<?xml version=\"1.0\"?><Table Name='接收发票'><Row>"
				+ "<ygdm>LIHUI</ygdm>"
				+ "<invoceNum>777777</invoceNum>"
				+ "<invoceCode>6456464</invoceCode>"
				+ "</Row></Table>";
		String s = "<?xml version=\"1.0\"?><Table Name='接收发票'><Row>"
				+ "<ygdm>LIHUI</ygdm>"
				+ "<invoceNum>Z179A037683</invoceNum>"
				+ "<invoceCode>null</invoceCode>"
				+ "<type>10503</type>"
				+ "<salesParty></salesParty>"
				+ "<buyer></buyer>"
				+ "<amountMoney>33.50</amountMoney>"
				+ "<taxRate>0</taxRate><taxMoney>0</taxMoney>"
				+ "<totalWithTax>33.50</totalWithTax><makeDate>2016-06-06</makeDate>"
				+ "<makeUser>1</makeUser><typeInDate>2019-04-09</typeInDate>"
				+ "<imageUrl>http://zhys.ft-link.cn:8011/image/1554800197852.png</imageUrl>\r\n" + 
				"<status>0</status><invoce_sys_id>93505</invoce_sys_id></Row></Table>";
		String md5 = MD5Util.MD5("eossoftwaneossoftwanzh");
		String res = dataIOService.sendDataToEOS("eossoftwan",md5,"接收发票",s);
		System.out.println("结果>>>"+res);
		XStream xs = new XStream(new DomDriver());
		xs.alias("data", ResponseDto.class);
		ResponseDto msg = (ResponseDto) xs.fromXML(res);
		System.out.println("message:"+msg.getMessage());
		System.out.println("result:"+msg.getResult());
	}
	
	public static boolean  synToOA(JSONArray array) {
		StringBuffer s = new StringBuffer("<?xml version=\"1.0\"?><Table Name='接收发票'>");
		if(array.size()>0) {
			array.forEach(inv->{
				JSONObject jsonObject = (JSONObject) inv;
				String s1 = "<Row>"
						+ "<ygdm>LIHUI</ygdm>"
						+ "<invoceNum>"+jsonObject.get("invoceNum")+"</invoceNum>"
						+ "<invoceCode>"+jsonObject.get("invoceCode")+"</invoceCode>"
						+ "<type>"+jsonObject.get("type")+"</type>"
						+ "<salesParty>"+jsonObject.get("salesParty")+"</salesParty>"
						+ "<buyer>"+jsonObject.get("buyer")+"</buyer>"
						+ "<amountMoney>"+jsonObject.get("amountMoney")+"</amountMoney>"
						+ "<taxRate>"+jsonObject.get("taxRate")+"</taxRate>"
						+ "<taxMoney>"+jsonObject.get("taxMoney")+"</taxMoney>"
						+ "<totalWithTax>"+jsonObject.get("totalWithTax")+"</totalWithTax>"
						+ "<makeDate>"+jsonObject.get("makeDate")+"</makeDate>"
						+ "<makeUser>"+jsonObject.get("makeUser")+"</makeUser>"
						+ "<typeInDate>"+jsonObject.get("typeInDate")+"</typeInDate>"
						+ "<imageUrl>"+jsonObject.get("imageUrl")+"</imageUrl>"
						+ "<status>"+jsonObject.get("status")+"</status>"
						+ "<invoce_sys_id>"+jsonObject.get("invoce_sys_id")+"</invoce_sys_id>"
						+ "</Row>";
				s.append(s1);
			});
		}
		s.append("</Table>");
		System.out.println("结果>>>"+s.toString());
		IDataIOService dataIOService     = new DataIOService().getBasicHttpBindingIDataIOService();
		String md5 = MD5Util.MD5("eossoftwaneossoftwanzh");
		String res = dataIOService.sendDataToEOS("eossoftwan",md5,"接收发票",s.toString());
		System.out.println("结果>>>"+res);
		XStream xs = new XStream(new DomDriver());
		xs.alias("data", ResponseDto.class);
		ResponseDto msg = (ResponseDto) xs.fromXML(res);
		System.out.println("message:"+msg.getMessage());
		System.out.println("result:"+msg.getResult());
		if("Y".equals(msg.getResult())) {
			return true;
		}else {
			return false;
		}

	}

}
